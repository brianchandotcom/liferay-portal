/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getItemValueFromPath from '../../src/main/resources/META-INF/resources/utils/getItemValueFromPath';

const item = {
	embedded: {
		author: {
			givenName: 'User',
			name: 'User',
		},
		id: 456,
	},
	id: 123,
	name: 'Test',
};

describe('getItemValueFromPath', () => {
	it('is defined', () => {
		expect(getItemValueFromPath).toBeDefined();
	});

	it('retrieves an item value when the selectedItemsKey is simple', () => {
		expect(getItemValueFromPath(item, 'id')).toEqual(123);
	});

	it('retrieves an item value using "id" if selectedItemsKye is not provided', () => {
		expect(getItemValueFromPath(item)).toEqual(123);
	});

	it('retrieves an item value when the selectedItemsKey is a path to a nested object property (2 levels)', () => {
		expect(getItemValueFromPath(item, 'embedded.id')).toEqual(456);
	});

	it('retrieves an item value when the selectedItemsKey is a path to a nested object property (3 levels)', () => {
		expect(getItemValueFromPath(item, 'embedded.author.name')).toEqual(
			'User'
		);
	});

	it('returns null if the path does not match any property', () => {
		expect(getItemValueFromPath(item, 'embedded.author.id')).toBeNull();
	});
});
