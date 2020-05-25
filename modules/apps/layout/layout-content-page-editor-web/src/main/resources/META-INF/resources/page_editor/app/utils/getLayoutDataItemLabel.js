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

import {LAYOUT_DATA_ITEM_TYPE_LABELS} from '../config/constants/layoutDataItemTypeLabels';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';

export default function getLayoutDataItemLabel(item, fragmentEntryLinks) {
	if (
		item.type === LAYOUT_DATA_ITEM_TYPES.fragment &&
		item.config &&
		item.config.fragmentEntryLinkId &&
		fragmentEntryLinks[item.config.fragmentEntryLinkId]
	) {
		return fragmentEntryLinks[item.config.fragmentEntryLinkId].name;
	}

	const itemTypeKey = Object.keys(LAYOUT_DATA_ITEM_TYPES).find(
		(key) => LAYOUT_DATA_ITEM_TYPES[key] === item.type
	);

	if (itemTypeKey in LAYOUT_DATA_ITEM_TYPE_LABELS) {
		return LAYOUT_DATA_ITEM_TYPE_LABELS[itemTypeKey];
	}

	return item.type;
}
