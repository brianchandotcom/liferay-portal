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

import {VIEWPORT_SIZES} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import {getResponsiveConfig} from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/getResponsiveConfig';

const SAMPLE_RESPONSIVE_CONFIG = {
	nonViewport: 'yep',

	[VIEWPORT_SIZES.portraitMobile]: {
		deep: {
			common: 'commonMobile',
			mobile: 'deepMobile',
		},
		sample: 'mobileValue',
	},

	[VIEWPORT_SIZES.desktop]: {
		deep: {
			common: 'commonDesktop',
			desktop: 'deepDesktop',
		},
		sample: 'desktopValue',
	},
};

describe('getResponsiveConfig', () => {
	it('keeps non-viewport related configuration', () => {
		expect(
			getResponsiveConfig(
				SAMPLE_RESPONSIVE_CONFIG,
				VIEWPORT_SIZES.portraitMobile
			).nonViewport
		).toBe('yep');
	});

	it('merges deep objects', () => {
		expect(
			getResponsiveConfig(
				SAMPLE_RESPONSIVE_CONFIG,
				VIEWPORT_SIZES.portraitMobile
			).deep
		).toEqual({
			common: 'commonMobile',
			desktop: 'deepDesktop',
			mobile: 'deepMobile',
		});
	});

	it('always contains styles', () => {
		expect(
			getResponsiveConfig(
				SAMPLE_RESPONSIVE_CONFIG,
				VIEWPORT_SIZES.desktop
			).styles
		).toEqual({});
	});

	it('merges configuration in order', () => {
		expect(
			getResponsiveConfig(
				SAMPLE_RESPONSIVE_CONFIG,
				VIEWPORT_SIZES.portraitMobile
			).sample
		).toBe('mobileValue');
	});

	it('ignores smaller viewport sizes', () => {
		expect(
			getResponsiveConfig(
				SAMPLE_RESPONSIVE_CONFIG,
				VIEWPORT_SIZES.desktop
			).sample
		).toBe('desktopValue');
	});
});
