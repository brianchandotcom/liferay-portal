/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {VIEWPORT_SIZES} from '../config/constants/viewportSizes';

const ORDERED_VIEWPORT_SIZES = [
	VIEWPORT_SIZES.desktop,
	VIEWPORT_SIZES.tablet,
	VIEWPORT_SIZES.landscapeMobile,
	VIEWPORT_SIZES.portraitMobile,
];

export function getResponsiveColumnSize(config, viewportSize) {
	const getViewportSize = (config, viewportSize) => {
		const viewportSizePosition = ORDERED_VIEWPORT_SIZES.indexOf(
			viewportSize
		);

		if (
			viewportSize === VIEWPORT_SIZES.desktop ||
			viewportSizePosition === -1
		) {
			return VIEWPORT_SIZES.desktop;
		}

		return config[viewportSize] && config[viewportSize].size
			? viewportSize
			: getViewportSize(
					config,
					ORDERED_VIEWPORT_SIZES[viewportSizePosition - 1]
			  );
	};

	const newViewportSize = getViewportSize(config, viewportSize);

	return config[newViewportSize] ? config[newViewportSize].size : config.size;
}
