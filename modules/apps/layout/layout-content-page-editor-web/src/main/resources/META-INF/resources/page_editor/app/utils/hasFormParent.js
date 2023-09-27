/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';

export function hasFormParent(item, layoutData) {
	if (item.type === LAYOUT_DATA_ITEM_TYPES.form) {
		return true;
	}

	const parent = layoutData?.items?.[item.parentId];

	if (!parent) {
		return false;
	}

	return hasFormParent(parent, layoutData);
}
