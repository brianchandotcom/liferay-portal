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

import addItem from '../../../../src/main/resources/META-INF/resources/page_editor/app/actions/addItem';
import removeItem from '../../../../src/main/resources/META-INF/resources/page_editor/app/actions/removeItem';
import {LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS} from '../../../../src/main/resources/META-INF/resources/page_editor/app/config/constants/layoutDataItemDefaultConfigurations';
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

	describe('ADD_ITEM', () => {
		it('adds an item to layoutData.items', () => {
			const nextState = layoutDataReducer(
				state,
				addItem({
					itemId: 'new-container',
					itemType: LAYOUT_DATA_ITEM_TYPES.container,
					parentId: 'root-item-0'
				})
			);

			expect(nextState.layoutData.items['new-container']).toEqual({
				children: [],
				config: LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS.container,
				itemId: 'new-container',
				parentId: 'root-item-0',
				type: LAYOUT_DATA_ITEM_TYPES.container
			});

			expect(
				nextState.layoutData.items['root-item-0'].children
			).toContain('new-container');
		});

		it('allows extending default configuration', () => {
			const nextState = layoutDataReducer(
				state,
				addItem({
					config: {
						color: 'blue',
						gutters: false
					},
					itemId: 'new-row',
					itemType: LAYOUT_DATA_ITEM_TYPES.row,
					parentId: 'root-item-0'
				})
			);

			expect(nextState.layoutData.items['new-row']).toEqual({
				children: [],
				config: {
					...LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS.row,
					color: 'blue',
					gutters: false
				},
				itemId: 'new-row',
				parentId: 'root-item-0',
				type: LAYOUT_DATA_ITEM_TYPES.row
			});
		});

		it('allows specifying a child position', () => {
			let nextState = state;

			nextState = layoutDataReducer(
				nextState,
				addItem({
					itemId: 'row-at-position-3',
					itemType: LAYOUT_DATA_ITEM_TYPES.row,
					parentId: 'root-item-0'
				})
			);

			nextState = layoutDataReducer(
				nextState,
				addItem({
					itemId: 'row-at-position-0',
					itemType: LAYOUT_DATA_ITEM_TYPES.container,
					parentId: 'root-item-0',
					position: 0
				})
			);

			nextState = layoutDataReducer(
				nextState,
				addItem({
					itemId: 'row-at-position-2',
					itemType: LAYOUT_DATA_ITEM_TYPES.row,
					parentId: 'root-item-0',
					position: 2
				})
			);

			expect(nextState.layoutData.items['root-item-0'].children).toEqual([
				'row-at-position-0',
				'example-fragment-0',
				'row-at-position-2',
				'row-at-position-3'
			]);
		});

		it('does nothing if there is an item with same id', () => {
			const nextState = layoutDataReducer(
				state,
				addItem({itemId: 'root-item-0'})
			);

			expect(nextState).toEqual(state);
		});
	});

	describe('REMOVE_ITEM', () => {
		it('removes an item from layoutData.items', () => {
			const nextState = layoutDataReducer(
				state,
				removeItem('example-fragment-0')
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
				removeItem('no-existing-item')
			);

			expect(nextState.layoutData.items).toEqual(state.layoutData.items);
		});
	});
});
