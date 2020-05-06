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

import {LAYOUT_DATA_ITEM_TYPE_LABELS} from '../../config/constants/layoutDataItemTypeLabels';
import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';

export default function getLabelName(item, fragmentEntryLinks) {
	let name;

	if (
		item.type === LAYOUT_DATA_ITEM_TYPES.fragment &&
		item.config &&
		item.config.fragmentEntryLinkId &&
		fragmentEntryLinks[item.config.fragmentEntryLinkId]
	) {
		name = fragmentEntryLinks[item.config.fragmentEntryLinkId].name;
	}
	else if (item.type === LAYOUT_DATA_ITEM_TYPES.collectionItem) {
		name = LAYOUT_DATA_ITEM_TYPE_LABELS.collectionItem;
	}
	else if (item.type === LAYOUT_DATA_ITEM_TYPES.collection) {
		name = LAYOUT_DATA_ITEM_TYPE_LABELS.collection;
	}
	else if (item.type === LAYOUT_DATA_ITEM_TYPES.container) {
		name = LAYOUT_DATA_ITEM_TYPE_LABELS.container;
	}
	else if (item.type === LAYOUT_DATA_ITEM_TYPES.column) {
		name = LAYOUT_DATA_ITEM_TYPE_LABELS.column;
	}
	else if (item.type === LAYOUT_DATA_ITEM_TYPES.dropZone) {
		name = LAYOUT_DATA_ITEM_TYPE_LABELS.dropZone;
	}
	else if (item.type === LAYOUT_DATA_ITEM_TYPES.row) {
		name = LAYOUT_DATA_ITEM_TYPE_LABELS.row;
	}

	return name;
}
