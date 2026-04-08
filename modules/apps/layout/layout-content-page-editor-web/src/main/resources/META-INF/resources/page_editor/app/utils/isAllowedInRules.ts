/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutData, LayoutDataItem} from '../../types/layout_data/LayoutData';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {isLayoutDataItemDeleted} from './isLayoutDataItemDeleted';

export function isAllowedInRules(item: LayoutDataItem, layoutData: LayoutData) {
	if (!Liferay.FeatureFlags['LPS-169837']) {
		return false;
	}

	return (
		item.type !== LAYOUT_DATA_ITEM_TYPES.collectionItem &&
		item.type !== LAYOUT_DATA_ITEM_TYPES.column &&
		item.type !== LAYOUT_DATA_ITEM_TYPES.dropZone &&
		item.type !== LAYOUT_DATA_ITEM_TYPES.fragmentDropZone &&
		item.type !== LAYOUT_DATA_ITEM_TYPES.root &&
		!isLayoutDataItemDeleted(layoutData, item.itemId)
	);
}
