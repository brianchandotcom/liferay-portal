/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {VIEWPORT_SIZES} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import getPreviousResponsiveStyle from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/getPreviousResponsiveStyle';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/config/index',
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
