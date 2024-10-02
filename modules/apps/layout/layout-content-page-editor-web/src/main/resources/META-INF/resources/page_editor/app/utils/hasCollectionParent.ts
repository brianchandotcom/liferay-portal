/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutData, LayoutDataItem} from '../../types/layout_data/LayoutData';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';

export function hasCollectionParent(
	item: LayoutDataItem,
	layoutData: LayoutData | null
): boolean {
	if (item.type === LAYOUT_DATA_ITEM_TYPES.collection) {
		return true;
	}

	const parent = layoutData?.items?.[item.parentId];

	if (!parent) {
		return false;
	}

	return hasCollectionParent(parent, layoutData);
}
