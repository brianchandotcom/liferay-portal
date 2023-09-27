/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
