/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {VIEWPORT_SIZES} from '../config/constants/viewportSizes';
import {getResponsiveConfig} from './getResponsiveConfig';

export default function isItemEmpty(
	item,
	layoutData,
	selectedViewportSize = VIEWPORT_SIZES.desktop
) {
	return (
		!item.children.length ||
		item.children.every((childId) =>
			isItemHidden(layoutData, childId, selectedViewportSize)
		)
	);
}

function isItemHidden(layoutData, itemId, selectedViewportSize) {
	const item = layoutData?.items[itemId];

	if (!item) {
		return false;
	}

	const responsiveConfig = getResponsiveConfig(
		item.config,
		selectedViewportSize
	);

	return responsiveConfig.styles.display === 'none';
}
