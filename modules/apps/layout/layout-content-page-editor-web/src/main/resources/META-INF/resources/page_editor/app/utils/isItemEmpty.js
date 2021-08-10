/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {VIEWPORT_SIZES} from '../config/constants/viewportSizes';
import {getResponsiveConfig} from './getResponsiveConfig';

export default function isItemEmpty(
	column,
	layoutData,
	selectedViewportSize = VIEWPORT_SIZES.desktop
) {
	return (
		!column.children.length ||
		column.children.every((childId) =>
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
