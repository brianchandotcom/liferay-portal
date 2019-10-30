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

	describe('ADD_ITEM', () => {
		it('adds an item to layoutData.items', () => {
			const nextState = layoutDataReducer(
				state,
				addItem({
					itemId: 'new',
					itemType: LAYOUT_DATA_ITEM_TYPES.container,
					parentId: 'root'
				})
			);

			expect(nextState.layoutData.items.new).toEqual({
				children: [],
				config: LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS.container,
				itemId: 'new',
				type: LAYOUT_DATA_ITEM_TYPES.container
			});

			expect(nextState.layoutData.items.root.children).toContain('new');
		});

		it('allows extending default configuration', () => {
			const nextState = layoutDataReducer(
				state,
				addItem({
					config: {
						color: 'blue',
						gutters: false
					},
					itemId: 'newrow',
					itemType: LAYOUT_DATA_ITEM_TYPES.row,
					parentId: 'root'
				})
			);

			expect(nextState.layoutData.items.newrow).toEqual({
				children: [],
				config: {
					...LAYOUT_DATA_ITEM_DEFAULT_CONFIGURATIONS.row,
					color: 'blue',
					gutters: false
				},
				itemId: 'newrow',
				type: LAYOUT_DATA_ITEM_TYPES.row
			});
		});

		it('allows specifying a child position', () => {
			let nextState = state;

			nextState = layoutDataReducer(
				nextState,
				addItem({
					itemId: 'position3',
					itemType: LAYOUT_DATA_ITEM_TYPES.row,
					parentId: 'root'
				})
			);

			nextState = layoutDataReducer(
				nextState,
				addItem({
					itemId: 'position0',
					itemType: LAYOUT_DATA_ITEM_TYPES.container,
					parentId: 'root',
					position: 0
				})
			);

			nextState = layoutDataReducer(
				nextState,
				addItem({
					itemId: 'position2',
					itemType: LAYOUT_DATA_ITEM_TYPES.row,
					parentId: 'root',
					position: 2
				})
			);

			expect(nextState.layoutData.items.root.children).toEqual([
				'position0',
				'frag',
				'position2',
				'position3'
			]);
		});

		it('throws an error if parent does not exist', () => {
			expect(() =>
				layoutDataReducer(state, addItem({parentId: 'no-parent'}))
			).toThrow('There is no item with id "no-parent"');
		});

		it('throws an error if item cannot be placed inside parent', () => {
			expect(() =>
				layoutDataReducer(
					state,
					addItem({
						itemId: 'new',
						itemType: LAYOUT_DATA_ITEM_TYPES.column,
						parentId: 'frag'
					})
				)
			).toThrow('Cannot put item of type "column" inside "fragment');
		});

		it('does nothing if there is an item with same id', () => {
			const nextState = layoutDataReducer(
				state,
				addItem({itemId: 'root'})
			);

			expect(nextState).toEqual(state);
		});
	});

	describe('REMOVE_ITEM', () => {
		it('removes an item from layoutData.items', () => {
			const nextState = layoutDataReducer(state, removeItem('frag'));

			expect(nextState.layoutData.items).toEqual({
				root: {
					children: [],
					config: {},
					itemId: 'root',
					type: LAYOUT_DATA_ITEM_TYPES.root
				}
			});
		});

		it('does nothing if item does not exist', () => {
			const nextState = layoutDataReducer(state, removeItem('no-ex'));

			expect(nextState.layoutData.items).toEqual(state.layoutData.items);
		});
	});
});
