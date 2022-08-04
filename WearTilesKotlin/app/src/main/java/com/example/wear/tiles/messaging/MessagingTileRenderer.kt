/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.wear.tiles.messaging

import android.content.Context
import android.graphics.Bitmap
import androidx.wear.tiles.DeviceParametersBuilders
import androidx.wear.tiles.LayoutElementBuilders.LayoutElement
import androidx.wear.tiles.ResourceBuilders.Resources
import com.example.wear.tiles.R
import com.google.android.horologist.tiles.images.drawableResToImageResource
import com.google.android.horologist.tiles.render.SingleTileLayoutRenderer

class MessagingTileRenderer(context: Context) :
    SingleTileLayoutRenderer<MessagingTileState, Map<Contact, Bitmap>>(context) {

    override fun renderTile(
        state: MessagingTileState,
        deviceParameters: DeviceParametersBuilders.DeviceParameters
    ): LayoutElement {
        return messagingTileLayout(context, deviceParameters, state)
    }

    override fun Resources.Builder.produceRequestedResources(
        resourceResults: Map<Contact, Bitmap>,
        deviceParameters: DeviceParametersBuilders.DeviceParameters,
        resourceIds: MutableList<String>
    ) {
        if (resourceIds.isEmpty() || resourceIds.contains(ID_IC_SEARCH)) {
            addIdToImageMapping(
                ID_IC_SEARCH,
                drawableResToImageResource(R.drawable.ic_search)
            )
        }

        // Add the scaled & cropped avatar images
        resourceResults.forEach { (contact, bitmap) ->
            val imageResource = bitmapToImageResource(bitmap)
            // Add each created image resource to the list
            addIdToImageMapping("$ID_CONTACT_PREFIX${contact.id}", imageResource)
        }
    }

    companion object {
        // Resource identifiers for images
        internal const val ID_IC_SEARCH = "ic_search"
        internal const val ID_CONTACT_PREFIX = "contact:"
    }
}
