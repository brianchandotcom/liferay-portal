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
import {config} from '../config/index';

const ORDERED_VIEWPORT_SIZES = [
	VIEWPORT_SIZES.desktop,
	VIEWPORT_SIZES.tablet,
	VIEWPORT_SIZES.landscapeMobile,
	VIEWPORT_SIZES.portraitMobile,
];

export function getResetLabelByViewport(selectedViewportSize) {
	let previousViewport = Liferay.Language.get('initial');

	if (selectedViewportSize !== VIEWPORT_SIZES.desktop) {
		previousViewport =
			config.availableViewportSizes[
				ORDERED_VIEWPORT_SIZES[
					ORDERED_VIEWPORT_SIZES.indexOf(selectedViewportSize) - 1
				]
			].label;
	}

	return Liferay.Util.sub(
		Liferay.Language.get('reset-to-x-value'),
		previousViewport
	);
}
