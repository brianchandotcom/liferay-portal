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
import {getResetLabelByViewport} from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/getResetLabelByViewport';

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

const getLabel = (viewport) => `reset-to-${viewport}-value`;

describe('getResetLabelByViewport', () => {
	test.each([
		[VIEWPORT_SIZES.desktop, getLabel('initial')],
		[VIEWPORT_SIZES.tablet, getLabel(VIEWPORT_SIZES.desktop)],
		[VIEWPORT_SIZES.landscapeMobile, getLabel(VIEWPORT_SIZES.tablet)],
		[
			VIEWPORT_SIZES.portraitMobile,
			getLabel(VIEWPORT_SIZES.landscapeMobile),
		],
	])('when the viewport is %p returns %p', (viewport, title) => {
		expect(getResetLabelByViewport(viewport)).toBe(title);
	});
});
