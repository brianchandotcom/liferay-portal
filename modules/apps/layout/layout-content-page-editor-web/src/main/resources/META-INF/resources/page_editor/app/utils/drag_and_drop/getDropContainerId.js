/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
