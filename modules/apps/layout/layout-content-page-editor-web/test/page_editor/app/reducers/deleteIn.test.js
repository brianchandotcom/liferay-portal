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

import {LAYOUT_DATA_ITEM_TYPES} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import deleteIn from '../../../../src/main/resources/META-INF/resources/page_editor/app/reducers/deleteIn';

describe('deleteIn', () => {
	let state;

	beforeEach(() => {
		state = {
			layoutData: {
				items: {
					frag: {
						children: [],
						config: {},
						itemId: 'frag',
						type: LAYOUT_DATA_ITEM_TYPES.fragment
					},

					root: {
						children: ['frag'],
						config: {},
						itemId: 'root',
						type: LAYOUT_DATA_ITEM_TYPES.root
					}
				}
			}
		};
	});

	it('removes an item from an array', () => {
		const newItems = deleteIn(state.layoutData.items, [
			'root',
			'children',
			0
		]);

		expect(newItems).toEqual({
			frag: {
				children: [],
				config: {},
				itemId: 'frag',
				type: LAYOUT_DATA_ITEM_TYPES.fragment
			},

			root: {
				children: [],
				config: {},
				itemId: 'root',
				type: LAYOUT_DATA_ITEM_TYPES.root
			}
		});
	});

	it('removes an item from an object', () => {
		const newItems = deleteIn(state.layoutData.items, ['root', 'children']);

		expect(newItems).toEqual({
			frag: {
				children: [],
				config: {},
				itemId: 'frag',
				type: LAYOUT_DATA_ITEM_TYPES.fragment
			},

			root: {
				config: {},
				itemId: 'root',
				type: LAYOUT_DATA_ITEM_TYPES.root
			}
		});
	});

	it('does nothing if path does not exist', () => {
		const newItems = deleteIn(state.layoutData.items, [
			'no-path',
			'here',
			'go',
			'away'
		]);

		expect(newItems).toEqual({
			frag: {
				children: [],
				config: {},
				itemId: 'frag',
				type: LAYOUT_DATA_ITEM_TYPES.fragment
			},

			root: {
				children: ['frag'],
				config: {},
				itemId: 'root',
				type: LAYOUT_DATA_ITEM_TYPES.root
			}
		});
	});
});
