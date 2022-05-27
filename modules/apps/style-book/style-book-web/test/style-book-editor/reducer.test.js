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
	ADD_REDO_ACTION,
	ADD_UNDO_ACTION,
	LOADING,
	SET_DRAFT_STATUS,
	SET_PREVIEW_LAYOUT,
	SET_PREVIEW_LAYOUT_TYPE,
	SET_TOKEN_VALUES,
	UPDATE_UNDO_REDO_HISTORY,
} from '../../src/main/resources/META-INF/resources/js/style-book-editor/constants/actionTypes';
import reducer from '../../src/main/resources/META-INF/resources/js/style-book-editor/reducer';

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
		const {loading} = reducer(STATE, {
			type: LOADING,
			value: false,
		});

		expect(loading).toBe(false);
	});

	it('saves needed state when dispatching SET_DRAFT_STATUS action', () => {
		const {draftStatus} = reducer(STATE, {
			type: SET_DRAFT_STATUS,
			value: 'saving',
		});

		expect(draftStatus).toBe('saving');
	});

	it('saves needed state when dispatching SET_PREVIEW_LAYOUT action', () => {
		const layout = {
			name: 'hello world',
			private: false,
			url: 'my-url',
		};

		const {previewLayout} = reducer(STATE, {
			layout,
			type: SET_PREVIEW_LAYOUT,
		});

		expect(previewLayout).toBe(layout);
	});

	it('saves needed state when dispatching SET_PREVIEW_LAYOUT_TYPE action', () => {
		const {previewLayoutType} = reducer(STATE, {
			layoutType: 'layoutTest',
			type: SET_PREVIEW_LAYOUT_TYPE,
		});

		expect(previewLayoutType).toBe('layoutTest');
	});

	it('saves needed state when dispatching SET_TOKEN_VALUES action', () => {
		const tokens = {
			testColor2: {
				cssVariableMapping: 'test-color-2',
				label: 'Test Color 2',
				value: '#BBBBBB',
			},
		};

		const {frontendTokensValues} = reducer(STATE, {
			tokens,
			type: SET_TOKEN_VALUES,
		});

		expect(frontendTokensValues).toEqual({
			...frontendTokensValues,
			...tokens,
		});
	});

	it('saves needed state when dispatching ADD_UNDO_ACTION action when is a redo action', () => {
		const token = {
			name: 'testColor2',
			value: {
				cssVariableMapping: 'test-color-2',
				label: 'Test Color 2',
				value: '#CCCCCC',
			},
		};

		const {redoHistory, undoHistory} = reducer(STATE, {
			isRedo: true,
			type: ADD_UNDO_ACTION,
			...token,
		});

		expect(redoHistory).toEqual(STATE.redoHistory);
		expect(undoHistory).toEqual([token, ...STATE.undoHistory]);
	});

	it('saves needed state when dispatching ADD_UNDO_ACTION action when there is no a redo action', () => {
		const token = {
			name: 'testColor2',
			value: {
				cssVariableMapping: 'test-color-2',
				label: 'Test Color 2',
				value: '#CCCCCC',
			},
		};

		const {redoHistory, undoHistory} = reducer(STATE, {
			type: ADD_UNDO_ACTION,
			...token,
		});

		expect(redoHistory).toEqual([]);
		expect(undoHistory).toEqual([token, ...STATE.undoHistory]);
	});

	it('saves needed state when dispatching ADD_REDO_ACTION action', () => {
		const token = {
			name: 'testColor2',
			value: {
				cssVariableMapping: 'test-color-2',
				label: 'Test Color 2',
				value: '#DDDDDD',
			},
		};

		const {redoHistory} = reducer(STATE, {
			type: ADD_REDO_ACTION,
			...token,
		});

		expect(redoHistory).toEqual([token, ...STATE.redoHistory]);
	});

	it('saves needed state when dispatching UPDATE_UNDO_REDO_HISTORY action', () => {
		const nextRedoHistory = [
			{
				name: 'testColor2',
				value: {
					cssVariableMapping: 'test-color-2',
					label: 'Test Color 2',
					value: '#DDDDDD',
				},
			},
		];

		const nextUndoHistory = [
			{
				name: 'testColor3',
				value: {
					cssVariableMapping: 'test-color-3',
					label: 'Test Color 2',
					value: '#333333',
				},
			},
		];

		const {redoHistory, undoHistory} = reducer(STATE, {
			redoHistory: nextRedoHistory,
			type: UPDATE_UNDO_REDO_HISTORY,
			undoHistory: nextUndoHistory,
		});

		expect(redoHistory).toEqual(nextRedoHistory);
		expect(undoHistory).toEqual(nextUndoHistory);
	});

	it('saves needed state when dispatching UPDATE_UNDO_REDO_HISTORY action and there are no previous redo/undo history', () => {
		const {redoHistory, undoHistory} = reducer(STATE, {
			type: UPDATE_UNDO_REDO_HISTORY,
		});

		expect(redoHistory).toEqual(STATE.redoHistory);
		expect(undoHistory).toEqual(STATE.undoHistory);
	});
});
