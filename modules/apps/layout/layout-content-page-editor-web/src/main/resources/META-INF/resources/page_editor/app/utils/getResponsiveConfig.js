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

const ORDERED_VIEWPORT_SIZES = [
	VIEWPORT_SIZES.desktop,
	VIEWPORT_SIZES.tablet,
	VIEWPORT_SIZES.landscapeMobile,
	VIEWPORT_SIZES.portraitMobile,
];

export function getResponsiveConfig(config, viewportSize) {
	const viewportSizeIndex = ORDERED_VIEWPORT_SIZES.indexOf(viewportSize);

	let responsiveConfig = {
		styles: {},
	};

	Object.keys(config)
		.filter((key) => !ORDERED_VIEWPORT_SIZES.includes(key))
		.forEach((key) => {
			responsiveConfig[key] = config[key];
		});

	for (let i = 0; i <= viewportSizeIndex; i++) {
		responsiveConfig = mergeDeep(
			responsiveConfig,
			config[ORDERED_VIEWPORT_SIZES[i]] || {}
		);
	}

	return responsiveConfig;
}

function mergeDeep(...objects) {
	const target = {};

	objects.forEach((object) => {
		Object.keys(object).forEach((key) => {
			if (
				typeof object[key] === 'object' &&
				object[key] !== null &&
				typeof target[key] === 'object' &&
				target[key] !== null
			) {
				target[key] = mergeDeep(target[key], object[key]);
			}
			else {
				target[key] = object[key];
			}
		});
	});

	return target;
}
