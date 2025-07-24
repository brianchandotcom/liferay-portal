/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getSelectedItemValue from '../../src/main/resources/META-INF/resources/utils/getSelectedItemValue';

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
		expect(getSelectedItemValue).toBeDefined();
	});

	it('retrieves an item value when the selectedItemsKey is simple', () => {
		expect(getSelectedItemValue({item, path: 'id'})).toEqual(123);
	});

	it('retrieves an item value using "id" if selectedItemsKey is not provided', () => {
		expect(getSelectedItemValue({item})).toEqual(123);
	});

	it('retrieves an item value using "id" if selectedItemsKey is undefined', () => {
		expect(getSelectedItemValue({item, path: undefined})).toEqual(123);
	});

	it('retrieves an item value using "id" if selectedItemsKey is null', () => {
		expect(getSelectedItemValue({item, path: null})).toEqual(123);
	});

	it('retrieves an item value when the selectedItemsKey is a path to a nested object property (2 levels)', () => {
		expect(getSelectedItemValue({item, path: 'embedded.id'})).toEqual(456);
	});

	it('retrieves an item value when the selectedItemsKey is a path to a nested object property (3 levels)', () => {
		expect(
			getSelectedItemValue({item, path: 'embedded.author.name'})
		).toEqual('User');
	});

	it('returns null if the path does not match any property', () => {
		expect(
			getSelectedItemValue({item, path: 'embedded.author.id'})
		).toBeNull();
	});
});
