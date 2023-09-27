/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getTop from '../../../src/main/resources/META-INF/resources/liferay/util/get_top';

describe('Liferay.Util.getTop', () => {
	it('returns the window object if there is no parent window', () => {
		Object.defineProperty(window, 'parent', {value: null, writable: true});

		global.name = 'foo';

		const topWindow = getTop();

		expect(topWindow.parent).toBe(null);
		expect(topWindow.name).toEqual('foo');
	});

	it('returns the window object if the parent window has no location.href', () => {
		const parent = {
			name: 'bar',
			themeDisplay: {
				isStatePopUp: jest.fn(() => false),
			},
		};

		Object.defineProperty(window, 'parent', {
			value: parent,
			writable: true,
		});

		global.name = 'foo';

		const topWindow = getTop();

		expect(topWindow.name).toEqual('foo');
	});

	it("returns the window object if the parent window has no themeDisplay or its name is 'simulationDeviceIframe'", () => {
		const parent = {
			location: {
				href: 'bar',
			},
			name: 'simulationDeviceIframe',
			themeDisplay: {
				isStatePopUp: jest.fn(() => false),
			},
		};

		Object.defineProperty(window, 'parent', {
			value: parent,
			writable: true,
		});

		global.name = 'foo';

		const topWindow = getTop();

		expect(topWindow.name).toEqual('foo');
	});
});
