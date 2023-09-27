/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getUniqueName from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/getUniqueName';

describe('getUniqueName', () => {
	test.each([
		[
			'untitled 5',
			[
				{name: 'untitled'},
				{name: 'untitled 2'},
				{name: 'untitled 3'},
				{name: 'untitled 4'},
			],
		],
		[
			'untitled 3',
			[
				{name: 'untitled'},
				{name: 'untitled 2'},
				{name: 'untitled 6'},
				{name: 'untitled 7'},
			],
		],
		[
			'untitled 2',
			[
				{name: 'test'},
				{name: 'untitled'},
				{name: 'test 1'},
				{name: 'test 3'},
			],
		],
		[
			'untitled',
			[
				{name: 'test'},
				{name: 'test 1'},
				{name: 'test 2'},
				{name: 'test 3'},
			],
		],
	])('getUniqueName returns "%s"', (result, items) => {
		expect(getUniqueName(items, 'untitled')).toBe(result);
	});
});
