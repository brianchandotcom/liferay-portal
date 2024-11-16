/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LayoutData, LayoutDataItem} from '../../types/layout_data/LayoutData';
import hasDropZoneChild from '../components/layout_data_items/hasDropZoneChild';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';

export default function canBeRemoved(
	item: LayoutDataItem,
	layoutData: LayoutData
) {
	switch (item.type) {
		case LAYOUT_DATA_ITEM_TYPES.collectionItem:
		case LAYOUT_DATA_ITEM_TYPES.column:
		case LAYOUT_DATA_ITEM_TYPES.dropZone:
		case LAYOUT_DATA_ITEM_TYPES.formStep:
		case LAYOUT_DATA_ITEM_TYPES.formStepContainer:
		case LAYOUT_DATA_ITEM_TYPES.root:
			return false;

		default:
			return !hasDropZoneChild(item, layoutData);
	}
}
