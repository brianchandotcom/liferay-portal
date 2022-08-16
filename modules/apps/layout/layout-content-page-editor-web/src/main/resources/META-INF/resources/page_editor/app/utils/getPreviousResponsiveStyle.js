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

import {VIEWPORT_SIZES} from '../../app/config/constants/viewportSizes';

const ORDERED_VIEWPORT_SIZES = [
	VIEWPORT_SIZES.desktop,
	VIEWPORT_SIZES.tablet,
	VIEWPORT_SIZES.landscapeMobile,
	VIEWPORT_SIZES.portraitMobile,
];

export default function getPreviousResponsiveStyle(
	field,
	config,
	viewportSize
) {
	if (viewportSize === VIEWPORT_SIZES.desktop) {
		return null;
	}

	const getViewportSize = (config, viewportSize) => {
		if (
			viewportSize === VIEWPORT_SIZES.desktop ||
			viewportSize === VIEWPORT_SIZES.tablet
		) {
			return VIEWPORT_SIZES.desktop;
		}

		const viewportSizePosition = ORDERED_VIEWPORT_SIZES.indexOf(
			viewportSize
		);

		const previousViewportSize =
			ORDERED_VIEWPORT_SIZES[viewportSizePosition - 1];

		return config[previousViewportSize]?.styles[field]
			? previousViewportSize
			: getViewportSize(config, previousViewportSize);
	};

	const newViewportSize = getViewportSize(config, viewportSize);

	return config[newViewportSize]
		? config[newViewportSize].styles[field]
		: config.styles[field];
}
