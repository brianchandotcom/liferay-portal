/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getToControlsId} from '../components/layout_data_items/Collection';
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
