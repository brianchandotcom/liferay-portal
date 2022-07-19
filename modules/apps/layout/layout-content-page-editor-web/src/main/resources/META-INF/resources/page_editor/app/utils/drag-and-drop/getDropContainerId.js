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

import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';
import {TARGET_POSITIONS} from './constants/targetPositions';

export default function getDropContainerId(
	layoutData,
	targetItem,
	targetPosition
) {
	if (targetPosition === TARGET_POSITIONS.MIDDLE) {
		return targetItem.itemId;
	}

	const targetParent = layoutData.items[targetItem.parentId];

	if (!targetParent) {
		return null;
	}

	if (targetParent.type === LAYOUT_DATA_ITEM_TYPES.dropZone) {
		return layoutData.items[targetParent.parentId].itemId;
	}

	return targetParent.type !== LAYOUT_DATA_ITEM_TYPES.root
		? targetParent.itemId
		: null;
}
