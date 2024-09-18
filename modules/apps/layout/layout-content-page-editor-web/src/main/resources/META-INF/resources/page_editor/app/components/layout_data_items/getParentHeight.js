/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LAYOUT_DATA_ITEM_TYPES} from '../../config/constants/layoutDataItemTypes';

export default function getParentHeight(item, layoutData) {
	if (!item) {
		return null;
	}

	const parentItem = layoutData.items[item.parentId];

	if (!parentItem) {
		return null;
	}

	const hasHeight = item?.config?.styles?.height;
	const hasType = [
		LAYOUT_DATA_ITEM_TYPES.form,
		LAYOUT_DATA_ITEM_TYPES.formStepContainer,
		LAYOUT_DATA_ITEM_TYPES.fragment,
	].includes(item.type);

	if (hasHeight && hasType) {
		return hasHeight;
	}
	else {
		return getParentHeight(parentItem, layoutData);
	}
}
