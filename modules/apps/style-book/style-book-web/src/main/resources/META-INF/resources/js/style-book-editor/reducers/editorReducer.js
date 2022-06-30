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
	SET_DRAFT_STATUS,
	SET_TOKEN_VALUES,
	UPDATE_UNDO_REDO_HISTORY,
} from '../constants/actionTypes';

const MAX_UNDO_ACTIONS = 100;

export default function editorReducer(state, action) {
	switch (action.type) {
		case SET_DRAFT_STATUS: {
			const {value} = action;

			return {
				...state,
				draftStatus: value,
			};
		}

		case SET_TOKEN_VALUES: {
			const {tokens} = action;

			return {
				...state,
				frontendTokensValues: {
					...state.frontendTokensValues,
					...tokens,
				},
			};
		}

		case ADD_UNDO_ACTION: {
			const {isRedo = false, label, name, value} = action;

			const nextRedoHistory = isRedo ? state.redoHistory : [];
			const nextUndoHistory = state.undoHistory || [];

			return {
				...state,
				redoHistory: nextRedoHistory,
				undoHistory: [
					{
						label,
						name,
						value,
					},
					...nextUndoHistory.slice(0, MAX_UNDO_ACTIONS - 1),
				],
			};
		}

		case ADD_REDO_ACTION: {
			const {label, name, value} = action;
			const nextRedoHistory = state.redoHistory || [];

			return {
				...state,
				redoHistory: [
					{
						label,
						name,
						value,
					},
					...nextRedoHistory,
				],
			};
		}

		case UPDATE_UNDO_REDO_HISTORY: {
			return {
				...state,
				redoHistory: action.redoHistory ?? state.redoHistory,
				undoHistory: action.undoHistory ?? state.undoHistory,
			};
		}

		default:
			return state;
	}
}
