/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import hasDropZoneChild from '../components/layout_data_items/hasDropZoneChild';
import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';

export default function canBeSaved(item, layoutData) {
	switch (item.type) {
		case LAYOUT_DATA_ITEM_TYPES.container:
		case LAYOUT_DATA_ITEM_TYPES.form:
		case LAYOUT_DATA_ITEM_TYPES.row:
			return !hasDropZoneChild(item, layoutData);

		default:
			return false;
	}
}
