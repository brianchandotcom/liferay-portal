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

import {cleanup} from '@testing-library/react';

import {reducer} from '../../../../src/main/resources/META-INF/resources/page_editor/app/contexts/ControlsContext';

import '@testing-library/jest-dom/extend-expect';

const ACTION = {
	itemId: null,
	itemType: null,
	origin: 'pageEditor',
};
const HOVER_ITEM = 'HOVER_ITEM';
const SELECT_ITEM = 'SELECT_ITEM';
const STATE = {
	activationOrigin: 'pageEditor',
	activeItemId: null,
	activeItemType: null,
	hoveredItemId: null,
	hoveredItemType: null,
};

describe('Reducer', () => {
	afterEach(cleanup);

	test.each([
		[
			'fragment',
			{
				activeItemType: 'layoutItem',
				hoveredItemType: 'layoutItem',
			},
			{
				itemId: 'item-1',
				itemType: 'layoutItem',
				type: HOVER_ITEM,
			},
			{
				activeItemType: 'layoutItem',
				hoveredItemId: 'item-1',
				hoveredItemType: 'layoutItem',
			},
		],
		[
			'editable when a fragment is selected',
			{
				activeItemId: 'item-1',
				activeItemType: 'layoutItem',
				hoveredItemId: 'editable-1',
				hoveredItemType: 'editable',
			},
			{
				itemId: 'editable-1',
				itemType: 'editable',
				type: HOVER_ITEM,
			},
			{
				activeItemId: 'item-1',
				activeItemType: 'layoutItem',
				hoveredItemId: 'editable-1',
				hoveredItemType: 'editable',
			},
		],
		[
			'mapped content',
			{
				activationOrigin: null,
				hoveredItemId: 'mapped-content-1',
				hoveredItemType: 'mappedContent',
			},
			{
				itemId: 'mapped-content-1',
				itemType: 'mappedContent',
				type: HOVER_ITEM,
			},
			{
				activationOrigin: null,
				hoveredItemId: 'mapped-content-1',
				hoveredItemType: 'mappedContent',
			},
		],
	])('Hover a %p', (item, state, action, expected) => {
		expect(reducer({...STATE, ...state}, {...ACTION, ...action})).toEqual({
			...STATE,
			...expected,
		});
	});

	test.each([
		[
			'fragment',
			{
				hoveredItemId: 'item-1',
				hoveredItemType: 'layoutItem',
			},
			{
				itemType: 'layoutItem',
				type: HOVER_ITEM,
			},
			{
				hoveredItemType: 'layoutItem',
			},
		],
		[
			'editable',
			{
				activeItemId: 'item-1',
				activeItemType: 'layoutItem',
				hoveredItemId: 'editable-1',
				hoveredItemType: 'editable',
			},
			{
				itemType: 'layoutItem',
				type: HOVER_ITEM,
			},
			{
				activeItemId: 'item-1',
				activeItemType: 'layoutItem',
				hoveredItemType: 'layoutItem',
			},
		],
	])('Hover out a %p', (item, state, action, expected) => {
		expect(reducer({...STATE, ...state}, {...ACTION, ...action})).toEqual({
			...STATE,
			...expected,
		});
	});

	test.each([
		[
			'fragment which is hovered',
			{
				activeItemType: 'layoutItem',
				hoveredItemId: 'item-1',
				hoveredItemType: 'layoutItem',
			},
			{
				itemId: 'item-1',
				itemType: 'layoutItem',
				type: SELECT_ITEM,
			},
			{
				activeItemId: 'item-1',
				activeItemType: 'layoutItem',
				hoveredItemId: 'item-1',
				hoveredItemType: 'layoutItem',
			},
		],
		[
			'fragment which is already selected',
			{
				activeItemId: 'item-1',
				activeItemType: 'layoutItem',
				hoveredItemId: 'item-1',
				hoveredItemType: 'layoutItem',
			},
			{
				itemId: 'item-1',
				itemType: 'layoutItem',
				type: SELECT_ITEM,
			},
			{
				activeItemId: 'item-1',
				activeItemType: 'layoutItem',
				hoveredItemId: 'item-1',
				hoveredItemType: 'layoutItem',
			},
		],
		[
			'editable when a fragment is selected',
			{
				activeItemId: 'item-1',
				hoveredItemId: 'editable-1',
				hoveredItemType: 'editable',
			},
			{
				itemId: 'editable-1',
				itemType: 'editable',
				type: SELECT_ITEM,
			},
			{
				activeItemId: 'editable-1',
				activeItemType: 'editable',
				hoveredItemId: 'editable-1',
				hoveredItemType: 'editable',
			},
		],
		[
			'item in page structure tree',
			{
				hoveredItemId: 'item-1',
			},
			{
				itemId: 'item-1',
				itemType: 'layoutItem',
				origin: 'structureTreeNode',
				type: SELECT_ITEM,
			},
			{
				activationOrigin: 'structureTreeNode',
				activeItemId: 'item-1',
				activeItemType: 'layoutItem',
				hoveredItemId: 'item-1',
			},
		],
	])('Select a %p', (item, state, action, expected) => {
		expect(reducer({...STATE, ...state}, {...ACTION, ...action})).toEqual({
			...STATE,
			...expected,
		});
	});

	test.each([
		[
			'fragment',
			{
				activeItemId: 'item-1',
				activeItemType: 'layoutItem',
			},
			{
				itemType: 'layoutItem',
				type: SELECT_ITEM,
			},
			{
				activeItemType: 'layoutItem',
			},
		],
		[
			'fragment when other fragment is selected',
			{
				activeItemId: 'item-1',
				activeItemType: 'layoutItem',
				hoveredItemId: 'item-2',
				hoveredItemType: 'layoutDataItem',
			},
			{
				itemId: 'item-2',
				itemType: 'layoutItem',
				type: SELECT_ITEM,
			},
			{
				activeItemId: 'item-2',
				activeItemType: 'layoutItem',
				hoveredItemId: 'item-2',
				hoveredItemType: 'layoutDataItem',
			},
		],
		[
			'editable',
			{
				activeItemId: 'editable-1',
				activeItemType: 'editable',
			},
			{
				itemType: 'layoutItem',
				type: SELECT_ITEM,
			},
			{
				activeItemType: 'layoutItem',
			},
		],
	])('Deselect a %p', (item, state, action, expected) => {
		expect(reducer({...STATE, ...state}, {...ACTION, ...action})).toEqual({
			...STATE,
			...expected,
		});
	});
});
