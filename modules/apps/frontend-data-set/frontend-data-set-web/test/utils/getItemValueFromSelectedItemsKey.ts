/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getItemValueFromSelectedItemsKey from '../../src/main/resources/META-INF/resources/utils/getItemValueFromSelectedItemsKey';

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

describe('getItemValueFromSelectedItemsKey', () => {
	it('is defined', () => {
		expect(getItemValueFromSelectedItemsKey).toBeDefined();
	});

	it('retrieves an item value when the selectedItemsKey is simple', () => {
		expect(getItemValueFromSelectedItemsKey(item, 'id')).toEqual(123);
	});

	it('retrieves an item value when the selectedItemsKey is a path to a nested object property (2 levels)', () => {
		expect(getItemValueFromSelectedItemsKey(item, 'embedded.id')).toEqual(
			456
		);
	});

	it('retrieves an item value when the selectedItemsKey is a path to a nested object property (3 levels)', () => {
		expect(
			getItemValueFromSelectedItemsKey(item, 'embedded.author.name')
		).toEqual('User');
	});
});
