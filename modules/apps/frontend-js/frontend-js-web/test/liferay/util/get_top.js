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
