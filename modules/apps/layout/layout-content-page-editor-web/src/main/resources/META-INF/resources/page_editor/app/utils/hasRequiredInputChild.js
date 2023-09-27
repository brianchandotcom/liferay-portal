/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {LAYOUT_DATA_ITEM_TYPES} from '../config/constants/layoutDataItemTypes';
import {getDescendantIds} from './getDescendantIds';
import {getResponsiveConfig} from './getResponsiveConfig';
import {isRequiredFormInput} from './isRequiredFormInput';

function isItemHidden(layoutData, itemId, selectedViewportSize) {
	const item = layoutData?.items[itemId];

	if (!item) {
		return false;
	}

	const responsiveConfig = getResponsiveConfig(
		item.config,
		selectedViewportSize
	);

	return (
		responsiveConfig.styles.display === 'none' ||
		isItemHidden(layoutData, item.parentId, selectedViewportSize)
	);
}

export default function hasRequiredInputChild({
	checkHidden = false,
	formFields,
	fragmentEntryLinks,
	itemId,
	layoutData,
	selectedViewportSize = null,
}) {
	const descendantIds = getDescendantIds(layoutData, itemId);

	return descendantIds.some((descendantId) => {
		const item = layoutData.items[descendantId];

		if (item.type !== LAYOUT_DATA_ITEM_TYPES.fragment) {
			return false;
		}

		return (
			(!checkHidden ||
				isItemHidden(layoutData, descendantId, selectedViewportSize)) &&
			isRequiredFormInput(item, fragmentEntryLinks, formFields)
		);
	});
}
