/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getNormalizedDragItems from '../../../../src/main/resources/META-INF/resources/page_editor/app/utils/getNormalizedDragItems';

const defaultItem = {
	config: {},
	itemId: 'item01',
	type: 'container',
};

const layoutData = {
	items: {
		item01: defaultItem,
		item02: {
			config: {},
			itemId: 'item02',
			type: 'container',
		},
		item03: {
			config: {},
			itemId: 'item03',
			type: 'container',
		},
	},
};

describe('useNormalizeDragItems', () => {
	const normalizedDragItem = (itemId) => ({
		config: {},
		fieldTypes: [],
		fragmentEntryType: null,
		isWidget: false,
		itemId,
		name: 'container',
		type: 'container',
	});

	it('normalizes drag items', () => {
		expect(
			getNormalizedDragItems(
				defaultItem,
				['item01', 'item02', 'item03'],
				layoutData,
				{}
			)
		).toStrictEqual([
			normalizedDragItem('item02'),
			normalizedDragItem('item03'),
			normalizedDragItem('item01'),
		]);
	});
});
