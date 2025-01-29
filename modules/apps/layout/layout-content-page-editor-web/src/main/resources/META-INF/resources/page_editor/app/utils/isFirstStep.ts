/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FormStepLayoutDataItem} from '../../types/layout_data/FormStepLayoutDataItem';
import {LayoutData} from '../../types/layout_data/LayoutData';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';

export default function isFirstStep(
	item: FormStepLayoutDataItem,
	layoutData: LayoutData
) {
	const parent = layoutData.items[item.parentId];

	return (
		item.type === LAYOUT_DATA_ITEM_TYPES.formStep &&
		parent.children.indexOf(item.itemId) === 0
	);
}
