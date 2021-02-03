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

import {getToControlsId} from '../../components/layout-data-items/Collection';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';

/**
 * Translates the given item ID into a collectionId-itemId if the item is
 * inside a collection. Otherwise, returns the plain itemId.
 * @param {{current: object}} layoutDataRef
 * @param {object} item
 * @return {string}
 */
export default function toControlsId(layoutDataRef, item) {
	const baseItem = item;

	const computeControlsId = (layoutDataRef, item) => {
		const parent = layoutDataRef.current.items[item.parentId];

		if (
			item.type === LAYOUT_DATA_ITEM_TYPES.collectionItem &&
			baseItem.collectionItemIndex &&
			parent
		) {
			return getToControlsId(
				parent.itemId,
				baseItem.collectionItemIndex
			)(baseItem.itemId);
		}
		else if (parent) {
			return computeControlsId(layoutDataRef, parent);
		}

		return baseItem.itemId;
	};

	return computeControlsId(layoutDataRef, item);
}
