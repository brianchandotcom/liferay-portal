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

import uid from './uid';

describe('uid', () => {
	it('uid() returns a string of 8 characters', () => {
		expect(uid()).toHaveLength(8);
	});

	it('uid() returns a hexadecimal string', () => {
		const regex = /^[0-9a-f]+$/;
		expect(regex.test(uid())).toBe(true);
	});

	it('uid() returns unique strings', () => {
		expect(uid()).not.toBe(uid());
	});

	it('uid() returns the same string for the same seed', () => {
		const seed = 0.5;

		Math.random = jest.fn(() => seed);

		expect(uid()).toBe(uid());
	});
});
