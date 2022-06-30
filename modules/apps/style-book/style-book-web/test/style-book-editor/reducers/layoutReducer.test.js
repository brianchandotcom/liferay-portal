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

import {
	LOADING,
	SET_PREVIEW_LAYOUT,
	SET_PREVIEW_LAYOUT_TYPE,
} from '../../../src/main/resources/META-INF/resources/js/style-book-editor/constants/actionTypes';
import layoutReducer from '../../../src/main/resources/META-INF/resources/js/style-book-editor/reducers/layoutReducer';

const STATE = {
	draftStatus: null,
	frontendTokensValues: {
		testColor1: {
			cssVariableMapping: 'test-color-1',
			label: 'Test Color 1',
			value: '#AAAAAA',
		},
	},
	loading: true,
	previewLayout: {},
	previewLayoutType: null,
	redoHistory: [
		{
			name: 'testColor3',
			value: {
				cssVariableMapping: 'test-color-3',
				label: 'Test Color 3',
				value: '#111111',
			},
		},
	],
	undoHistory: [
		{
			name: 'testColor1',
			value: {
				cssVariableMapping: 'test-color-1',
				label: 'Test Color 1',
				value: '#BBBBBB',
			},
		},
	],
};

describe('reducer', () => {
	it('saves needed state when dispatching LOADING action', () => {
		const {loading} = layoutReducer(STATE, {
			type: LOADING,
			value: false,
		});

		expect(loading).toBe(false);
	});

	it('saves needed state when dispatching SET_PREVIEW_LAYOUT action', () => {
		const layout = {
			name: 'hello world',
			private: false,
			url: 'my-url',
		};

		const {previewLayout} = layoutReducer(STATE, {
			layout,
			type: SET_PREVIEW_LAYOUT,
		});

		expect(previewLayout).toBe(layout);
	});

	it('saves needed state when dispatching SET_PREVIEW_LAYOUT_TYPE action', () => {
		const {previewLayoutType} = layoutReducer(STATE, {
			layoutType: 'layoutTest',
			type: SET_PREVIEW_LAYOUT_TYPE,
		});

		expect(previewLayoutType).toBe('layoutTest');
	});
});
