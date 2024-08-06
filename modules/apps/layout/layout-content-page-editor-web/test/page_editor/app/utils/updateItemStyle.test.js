/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {VIEWPORT_SIZES} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/viewportSizes';
import updateItemConfig from '../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig';
import updateItemStyle from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/updateItemStyle';

jest.mock(
	'../../../../src/main/resources/META-INF/resources/page_editor/app/thunks/updateItemConfig',
	() => jest.fn()
);

const INITIAL_CONFIG = {
	dispatch: jest.fn(),
	itemIds: ['fragment01'],
	selectedViewportSize: VIEWPORT_SIZES.desktop,
	styleName: 'display',
	styleValue: 'none',
};

describe('updateItemStyle', () => {
	it('updates item styles for Desktop viewport', () => {
		updateItemStyle(INITIAL_CONFIG);

		expect(updateItemConfig).toBeCalledWith({
			itemConfig: {styles: {display: 'none'}},
			itemIds: ['fragment01'],
		});
	});

	it('updates item styles for Tablet viewport', () => {
		updateItemStyle({
			...INITIAL_CONFIG,
			selectedViewportSize: VIEWPORT_SIZES.tablet,
		});

		expect(updateItemConfig).toBeCalledWith({
			itemConfig: {tablet: {styles: {display: 'none'}}},
			itemIds: ['fragment01'],
		});
	});

	it('updates item styles for Landscape Mobile viewport', () => {
		updateItemStyle({
			...INITIAL_CONFIG,
			selectedViewportSize: VIEWPORT_SIZES.landscapeMobile,
		});

		expect(updateItemConfig).toBeCalledWith({
			itemConfig: {landscapeMobile: {styles: {display: 'none'}}},
			itemIds: ['fragment01'],
		});
	});

	it('updates item styles for Portrait Mobile viewport', () => {
		updateItemStyle({
			...INITIAL_CONFIG,
			selectedViewportSize: VIEWPORT_SIZES.portraitMobile,
		});

		expect(updateItemConfig).toBeCalledWith({
			itemConfig: {portraitMobile: {styles: {display: 'none'}}},
			itemIds: ['fragment01'],
		});
	});
});
