/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
