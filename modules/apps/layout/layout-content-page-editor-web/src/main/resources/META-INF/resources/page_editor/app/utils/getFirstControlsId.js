/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {getToControlsId} from '../components/layout-data-items/Collection';
import {ITEM_TYPES} from '../config/constants/itemTypes';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';

/**
 * Obtains the first controls id of an item.
 *
 * Generally, you should be using the toControlsId provided by the CollectionItemContext,
 * but there are place where this context is not available. In those cases this function
 * may be used as an alternative.
 */
export default function getFirstControlsId({item, layoutData}) {
	const collectionItems = getCollectionItems(
		item.itemType === ITEM_TYPES.editable ? item.parentId : item.id,
		layoutData
	);

	if (!collectionItems.length) {
		return item.id;
	}

	const toControlsId = collectionItems.reduce(
		(acc, collectionItemId) => {

			// If the item.id correspond to a collectionId ignore it,
			// that id is only applied to the children not to the collection itself.

			if (collectionItemId === item.id) {
				return acc;
			}

			return getToControlsId(collectionItemId, 0, acc);
		},
		(itemId) => itemId
	);

	return toControlsId(item.id);
}

function getCollectionItems(itemId, layoutData, collectionItems = []) {
	const item = layoutData.items[itemId];

	if (!item) {
		return collectionItems;
	}

	const nextCollectionItems =
		item.type === LAYOUT_DATA_ITEM_TYPES.collection
			? [itemId, ...collectionItems]
			: collectionItems;

	return getCollectionItems(item.parentId, layoutData, nextCollectionItems);
}
