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
