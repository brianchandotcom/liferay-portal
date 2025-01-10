/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutData, LayoutDataItem} from '../../types/layout_data/LayoutData';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';

/**
 * Returns the parent form step index or null
 * if it doesn't find any parent form step
 */
export function getFormStepIndex(
	item: LayoutDataItem,
	layoutData: LayoutData | null
): number | null {
	const parent = layoutData?.items?.[item.parentId];

	if (item.type === LAYOUT_DATA_ITEM_TYPES.formStep) {
		const index = parent!.children.indexOf(item.itemId);

		return index;
	}

	if (!parent) {
		return null;
	}

	return getFormStepIndex(parent, layoutData);
}
