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
import getPreviousResponsiveStyle from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/getPreviousResponsiveStyle';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/config',
	() => ({
		config: {
			availableViewportSizes: {
				desktop: {label: 'desktop'},
				landscapeMobile: {label: 'landscapeMobile'},
				portraitMobile: {label: 'portraitMobile'},
				tablet: {label: 'tablet'},
			},
		},
	})
);

const FIELD = 'fontWeight';

describe('getPreviousResponsiveStyle', () => {
	describe('Config has styles for all viewports', () => {
		const CONFIG = {
			landscapeMobile: {
				styles: {
					fontWeight: 'landscapeStyle',
				},
			},
			portraitMobile: {
				styles: {
					fontWeight: 'portraitStyle',
				},
			},
			styles: {
				fontWeight: 'desktopStyle',
			},
			tablet: {
				styles: {
					fontWeight: 'tabletStyle',
				},
			},
		};

		test.each([
			[VIEWPORT_SIZES.desktop, null],
			[VIEWPORT_SIZES.tablet, 'desktopStyle'],
			[VIEWPORT_SIZES.landscapeMobile, 'tabletStyle'],
			[VIEWPORT_SIZES.portraitMobile, 'landscapeStyle'],
		])('when the viewport is %p returns %p', (viewport, style) => {
			expect(getPreviousResponsiveStyle(FIELD, CONFIG, viewport)).toBe(
				style
			);
		});
	});

	describe('Config with styles for Tablet and Portrait Mobile viewports', () => {
		const CONFIG = {
			landscapeMobile: {
				styles: {},
			},
			portraitMobile: {
				styles: {
					fontWeight: 'portraitStyle',
				},
			},
			styles: {},
			tablet: {
				styles: {fontWeight: 'tabletStyle'},
			},
		};

		it('when the viewport is "portraitMobile" returns "tabletStyle"', () => {
			expect(
				getPreviousResponsiveStyle(
					FIELD,
					CONFIG,
					VIEWPORT_SIZES.portraitMobile
				)
			).toBe('tabletStyle');
		});
	});
});
