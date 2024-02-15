/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getResponsiveConfig} from './getResponsiveConfig';

export function isItemHidden(
	layoutData,
	itemId,
	selectedViewportSize,
	options = {recursive: false}
) {
	const item = layoutData?.items[itemId];

	if (!item) {
		return false;
	}

	const responsiveConfig = getResponsiveConfig(
		item.config,
		selectedViewportSize
	);

	if (options.recursive) {
		return (
			responsiveConfig.styles.display === 'none' ||
			isItemHidden(
				layoutData,
				item.parentId,
				selectedViewportSize,
				options
			)
		);
	}

	return responsiveConfig.styles.display === 'none';
}
