/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isNullOrUndefined} from '@liferay/layout-js-components-web';

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
			else if (!isNullOrUndefined(object[key]) && object[key] !== '') {
				target[key] = object[key];
			}
		});
	});

	return target;
}
