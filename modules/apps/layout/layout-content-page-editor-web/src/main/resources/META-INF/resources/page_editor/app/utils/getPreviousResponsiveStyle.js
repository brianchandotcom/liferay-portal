/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
