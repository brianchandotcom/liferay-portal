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

import removeItem from '../../../../src/main/resources/META-INF/resources/page_editor/app/actions/removeItem';
import {LAYOUT_DATA_ITEM_TYPES} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemTypes';
import layoutDataReducer from '../../../../src/main/resources/META-INF/resources/page_editor/app/reducers/layoutDataReducer';

describe('layoutDataReducer', () => {
	let state;

	beforeEach(() => {
		state = {
			layoutData: {
				items: {
					'example-fragment-0': {
						children: [],
						config: {},
						itemId: 'example-fragment-0',
						parentId: 'root-item-0',
						type: LAYOUT_DATA_ITEM_TYPES.fragment
					},

					'root-item-0': {
						children: ['example-fragment-0'],
						config: {},
						itemId: 'root-item-0',
						parentId: null,
						type: LAYOUT_DATA_ITEM_TYPES.root
					}
				}
			}
		};
	});

	describe('REMOVE_ITEM', () => {
		it('removes an item from layoutData.items', () => {
			const nextState = layoutDataReducer(
				state,
				removeItem({itemId: 'example-fragment-0'})
			);

			expect(nextState.layoutData.items).toEqual({
				'root-item-0': {
					children: [],
					config: {},
					itemId: 'root-item-0',
					parentId: null,
					type: LAYOUT_DATA_ITEM_TYPES.root
				}
			});
		});

		it('does nothing if item does not exist', () => {
			const nextState = layoutDataReducer(
				state,
				removeItem({itemId: 'no-existing-item'})
			);

			expect(nextState.layoutData.items).toEqual(state.layoutData.items);
		});
	});
});
