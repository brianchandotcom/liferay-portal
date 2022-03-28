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

import sub from '../../../src/main/resources/META-INF/resources/liferay/util/sub';

describe('sub', () => {
	it('replaces the matching regex with just one argument', () => {
		expect(sub('foo {0}', 'bar')).toBe('foo bar');
	});

	it('replaces the matching regex with string arguments', () => {
		expect(sub('foo {0} {1} {2} {3}', 'bar', 'baz', 'lorem', 'ipsum')).toBe(
			'foo bar baz lorem ipsum'
		);
	});

	it('replaces the matching regex with an array of arguments', () => {
		expect(
			sub('foo {0} {1} {2} {3}', ['bar', 'baz', 'lorem', 'ipsum'])
		).toBe('foo bar baz lorem ipsum');
	});

	it('replaces the matching regex with html elements in form of string', () => {
		expect(
			sub(
				'{0}Learn how{1} to tailor categories to your needs',
				'<a href="" target="_blank">',
				'</a>'
			)
		).toBe(
			'<a href="" target="_blank">Learn how</a> to tailor categories to your needs'
		);
	});
});
