/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import sortItemIds from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/sortItemIds';

const layoutData = {
	items: {
		['item-1']: {
			children: ['item-5', 'item-6'],
			itemId: 'item-1',
		},
		['item-2']: {
			children: [],
			itemId: 'item-2',
		},
		['item-3']: {
			children: [],
			itemId: 'item-3',
		},
		['item-4']: {
			children: [],
			itemId: 'item-4',
		},
		['item-5']: {
			children: [],
			itemId: 'item-5',
		},
		['item-6']: {
			children: [],
			itemId: 'item-6',
		},
		['root-id']: {
			children: ['item-1', 'item-2', 'item-3', 'item-4'],
			itemId: 'root-id',
		},
	},
	rootItems: {
		main: 'root-id',
	},
};

describe('sortItemIds', () => {
	it('sort items of one level, whose parent is root', () => {
		expect(sortItemIds(['item-4', 'item-2'], layoutData)).toStrictEqual([
			'item-2',
			'item-4',
		]);
	});

	it('sorts items of various levels', () => {
		expect(
			sortItemIds(['item-3', 'item-6', 'item-1', 'item-5'], layoutData)
		).toStrictEqual(['item-1', 'item-5', 'item-6', 'item-3']);
	});
});
