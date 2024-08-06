/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {reducer} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext';

import '@testing-library/jest-dom/extend-expect';

const ACTION = {
	itemId: null,
	itemType: null,
	origin: 'layout',
};
const HOVER_ITEM = 'HOVER_ITEM';
const MULTI_SELECT = 'MULTI_SELECT';
const SELECT_ITEM = 'SELECT_ITEM';
const STATE = {
	activationOrigin: 'layout',
	activeItemIds: [],
	activeItemType: null,
	hoveredItemId: null,
	hoveredItemType: null,
};

describe('Reducer', () => {
	describe('Hover action', () => {
		it('hovers a fragment', () => {
			const action = {
				...ACTION,
				itemId: 'item-1',
				itemType: 'layoutItem',
				type: HOVER_ITEM,
			};
			const state = {
				...STATE,
				activeItemType: 'layoutItem',
				hoveredItemType: 'layoutItem',
			};

			expect(reducer(state, action)).toEqual({
				...state,
				activeItemType: 'layoutItem',
				hoveredItemId: 'item-1',
				hoveredItemType: 'layoutItem',
			});
		});

		it('hovers an editable when a fragment is selected', () => {
			const action = {
				...ACTION,
				itemId: 'editable-1',
				itemType: 'editable',
				type: HOVER_ITEM,
			};
			const state = {
				...STATE,
				activeItemIds: 'item-1',
				activeItemType: 'layoutItem',
				hoveredItemId: 'editable-1',
				hoveredItemType: 'editable',
			};

			expect(reducer(state, action)).toEqual({
				...state,
				activeItemIds: 'item-1',
				activeItemType: 'layoutItem',
				hoveredItemId: 'editable-1',
				hoveredItemType: 'editable',
			});
		});

		it('hovers a mapped content', () => {
			const action = {
				...ACTION,
				itemId: 'mapped-content-1',
				itemType: 'mappedContent',
				type: HOVER_ITEM,
			};
			const state = {
				...STATE,
				activationOrigin: null,
				hoveredItemId: 'mapped-content-1',
				hoveredItemType: 'mappedContent',
			};

			expect(reducer(state, action)).toEqual({
				...state,
				activationOrigin: null,
				hoveredItemId: 'mapped-content-1',
				hoveredItemType: 'mappedContent',
			});
		});
	});

	describe('Hover out action', () => {
		it('hovers a fragment', () => {
			const action = {
				...ACTION,
				itemType: 'layoutItem',
				type: HOVER_ITEM,
			};
			const state = {
				...STATE,
				hoveredItemId: 'item-1',
				hoveredItemType: 'layoutItem',
			};

			expect(reducer(state, action)).toEqual({
				...state,
				hoveredItemId: null,
				hoveredItemType: 'layoutItem',
			});
		});

		it('hovers out an editable', () => {
			const action = {
				...ACTION,
				itemType: 'layoutItem',
				type: HOVER_ITEM,
			};
			const state = {
				...STATE,
				activeItemIds: 'item-1',
				activeItemType: 'layoutItem',
				hoveredItemId: 'editable-1',
				hoveredItemType: 'editable',
			};

			expect(reducer(state, action)).toEqual({
				...state,
				activeItemIds: 'item-1',
				activeItemType: 'layoutItem',
				hoveredItemId: null,
				hoveredItemType: 'layoutItem',
			});
		});
	});

	describe('Select action', () => {
		it('selects a fragment which is hovered', () => {
			const action = {
				...ACTION,
				itemId: 'item-1',
				itemType: 'layoutItem',
				type: SELECT_ITEM,
			};
			const state = {
				...STATE,
				activeItemType: 'layoutItem',
				hoveredItemId: 'item-1',
				hoveredItemType: 'layoutItem',
			};

			expect(reducer(state, action)).toEqual({
				...state,
				activeItemIds: ['item-1'],
				activeItemType: 'layoutItem',
				hoveredItemId: 'item-1',
				hoveredItemType: 'layoutItem',
			});
		});

		it('selects a fragment which is already selected', () => {
			const action = {
				...ACTION,
				itemId: 'item-1',
				itemType: 'layoutItem',
				type: SELECT_ITEM,
			};
			const state = {
				...STATE,
				activeItemIds: ['item-1'],
				activeItemType: 'layoutItem',
				hoveredItemId: 'item-1',
				hoveredItemType: 'layoutItem',
			};

			expect(reducer(state, action)).toEqual({
				...state,
				activeItemIds: ['item-1'],
				activeItemType: 'layoutItem',
				hoveredItemId: 'item-1',
				hoveredItemType: 'layoutItem',
			});
		});

		it('selects an editable when a fragment is selected', () => {
			const action = {
				...ACTION,
				itemId: 'editable-1',
				itemType: 'editable',
				type: SELECT_ITEM,
			};
			const state = {
				...STATE,
				activeItemIds: ['item-1'],
				hoveredItemId: 'editable-1',
				hoveredItemType: 'editable',
			};

			expect(reducer(state, action)).toEqual({
				...state,
				activeItemIds: ['editable-1'],
				activeItemType: 'editable',
				hoveredItemId: 'editable-1',
				hoveredItemType: 'editable',
			});
		});

		it('selects an item in page structure tree', () => {
			const action = {
				...ACTION,
				itemId: 'item-1',
				itemType: 'layoutItem',
				origin: 'structureTreeNode',
				type: SELECT_ITEM,
			};
			const state = {
				...STATE,
				hoveredItemId: 'item-1',
			};

			expect(reducer(state, action)).toEqual({
				...state,
				activationOrigin: 'structureTreeNode',
				activeItemIds: ['item-1'],
				activeItemType: 'layoutItem',
				hoveredItemId: 'item-1',
			});
		});
	});

	describe('Deselect action', () => {
		it('Deselects a fragment', () => {
			const action = {
				...ACTION,
				itemType: 'layoutItem',
				type: SELECT_ITEM,
			};
			const state = {
				...STATE,
				activeItemIds: ['item-1'],
				activeItemType: 'layoutItem',
			};

			expect(reducer(state, action)).toEqual({
				...state,
				activeItemIds: [],
				activeItemType: 'layoutItem',
			});
		});

		it('deselects a fragment when other fragment is selected', () => {
			const action = {
				...ACTION,
				itemId: 'item-2',
				itemType: 'layoutItem',
				type: SELECT_ITEM,
			};
			const state = {
				...STATE,
				activeItemIds: ['item-1'],
				activeItemType: 'layoutItem',
				hoveredItemId: 'item-2',
				hoveredItemType: 'layoutDataItem',
			};

			expect(reducer(state, action)).toEqual({
				...state,
				activeItemIds: ['item-2'],
				activeItemType: 'layoutItem',
				hoveredItemId: 'item-2',
				hoveredItemType: 'layoutDataItem',
			});
		});

		it('deselects an editable', () => {
			const action = {
				...ACTION,
				itemType: 'layoutItem',
				type: SELECT_ITEM,
			};
			const state = {
				...STATE,
				activeItemIds: ['editable-1'],
				activeItemType: 'editable',
			};

			expect(reducer(state, action)).toEqual({
				...state,
				activeItemIds: [],
				activeItemType: 'layoutItem',
			});
		});
	});

	describe('Multiselect action', () => {
		it('activates multiselect', async () => {
			const state = {...STATE, multiSelectIsActive: false};
			const action = {
				...ACTION,
				...{
					multiSelectIsActive: true,
					type: MULTI_SELECT,
				},
			};

			expect(reducer(state, action)).toEqual({
				...state,
				multiSelectIsActive: true,
			});
		});

		it('selects multiple fragments', async () => {
			const state = {...STATE, activeItemIds: []};
			const action = {
				...ACTION,
				activeItemIds: ['item-1', 'item-2'],
				type: MULTI_SELECT,
			};

			expect(reducer(state, action)).toEqual({
				...state,
				activeItemIds: ['item-1', 'item-2'],
			});
		});
	});
});
