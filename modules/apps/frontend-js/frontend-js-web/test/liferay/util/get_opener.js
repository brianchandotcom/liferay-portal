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

import getOpener from '../../../src/main/resources/META-INF/resources/liferay/util/get_opener';

describe('Liferay.Util.getOpener', () => {
	Liferay = {
		Util: {
			Window: {
				getById: jest.fn(() => global.name),
			},
			getTop: jest.fn(() => global),
			getWindowName: jest.fn(() => global.name),
		},
	};

	beforeEach(() => {
		global.name = 'foo';
		global.opener = global;
		global.opener.name = 'bar';
	});

	it('returns the opener window', () => {
		const opener = getOpener();

		expect(opener.name).toBe('bar');
	});

	it('returns the most parent opener window', () => {
		global.parent.parent.parent.name = 'baz';

		const opener = getOpener();

		expect(opener.name).toBe('baz');
	});
});
