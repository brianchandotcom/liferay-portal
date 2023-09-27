/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ADD_REDO_ACTION,
	ADD_UNDO_ACTION,
	SET_DRAFT_STATUS,
	SET_TOKEN_VALUES,
	UPDATE_UNDO_REDO_HISTORY,
} from '../../../src/main/resources/META-INF/resources/js/style-book-editor/constants/actionTypes';
import editorReducer from '../../../src/main/resources/META-INF/resources/js/style-book-editor/reducers/editorReducer';

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

describe('editorReducer', () => {
	it('saves needed state when dispatching SET_DRAFT_STATUS action', () => {
		const {draftStatus} = editorReducer(STATE, {
			type: SET_DRAFT_STATUS,
			value: 'saving',
		});

		expect(draftStatus).toBe('saving');
	});

	it('saves needed state when dispatching SET_TOKEN_VALUES action', () => {
		const tokens = {
			testColor2: {
				cssVariableMapping: 'test-color-2',
				label: 'Test Color 2',
				value: '#BBBBBB',
			},
		};

		const {frontendTokensValues} = editorReducer(STATE, {
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

		const {redoHistory, undoHistory} = editorReducer(STATE, {
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

		const {redoHistory, undoHistory} = editorReducer(STATE, {
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

		const {redoHistory} = editorReducer(STATE, {
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

		const {redoHistory, undoHistory} = editorReducer(STATE, {
			redoHistory: nextRedoHistory,
			type: UPDATE_UNDO_REDO_HISTORY,
			undoHistory: nextUndoHistory,
		});

		expect(redoHistory).toEqual(nextRedoHistory);
		expect(undoHistory).toEqual(nextUndoHistory);
	});

	it('saves needed state when dispatching UPDATE_UNDO_REDO_HISTORY action and there are no previous redo/undo history', () => {
		const {redoHistory, undoHistory} = editorReducer(STATE, {
			type: UPDATE_UNDO_REDO_HISTORY,
		});

		expect(redoHistory).toEqual(STATE.redoHistory);
		expect(undoHistory).toEqual(STATE.undoHistory);
	});
});
