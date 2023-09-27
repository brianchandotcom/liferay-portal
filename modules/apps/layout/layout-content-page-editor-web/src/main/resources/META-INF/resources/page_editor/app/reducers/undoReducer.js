/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DELETE_SEGMENTS_EXPERIENCE} from '../../plugins/experience/actions';
import {
	ADD_REDO_ACTION,
	ADD_UNDO_ACTION,
	UPDATE_REDO_ACTIONS,
	UPDATE_UNDO_ACTIONS,
} from '../actions/types';
import {getDerivedStateForUndo} from '../components/undo/undoActions';

const MAX_UNDO_ACTIONS = 100;

let actionId = 0;

export default function undoReducer(state, action) {
	switch (action.type) {
		case ADD_REDO_ACTION: {
			const {actionType, originalType} = action;

			const nextRedoHistory = state.redoHistory || [];

			return {
				...state,
				redoHistory: [
					{
						...getDerivedStateForUndo({
							action,
							state,
							type: actionType,
						}),
						actionId: actionId++,
						originalType,
					},
					...nextRedoHistory,
				],
			};
		}
		case ADD_UNDO_ACTION: {
			const {actionType, originalType} = action;

			const nextUndoHistory = state.undoHistory || [];

			const nextRedoHistory = action.isRedo ? state.redoHistory : [];

			return {
				...state,
				redoHistory: nextRedoHistory,
				undoHistory: [
					{
						...getDerivedStateForUndo({
							action,
							state,
							type: actionType,
						}),
						actionId: actionId++,
						originalType,
					},
					...nextUndoHistory.slice(0, MAX_UNDO_ACTIONS - 1),
				],
			};
		}
		case DELETE_SEGMENTS_EXPERIENCE: {
			const {segmentsExperienceId} = action.payload;

			const nextUndoHistory = state.undoHistory || [];
			const nextRedoHistory = state.redoHistory || [];

			return {
				...state,
				redoHistory: nextRedoHistory.filter(
					(action) =>
						action.segmentsExperienceId !== segmentsExperienceId &&
						action.nextSegmentsExperienceId !== segmentsExperienceId
				),
				undoHistory: nextUndoHistory.filter(
					(action) =>
						action.segmentsExperienceId !== segmentsExperienceId &&
						action.nextSegmentsExperienceId !== segmentsExperienceId
				),
			};
		}
		case UPDATE_REDO_ACTIONS: {
			return {
				...state,
				redoHistory: action.redoHistory,
			};
		}
		case 'UPDATE_STORE': {
			return {
				...action.store,
			};
		}
		case UPDATE_UNDO_ACTIONS: {
			return {
				...state,
				undoHistory: action.undoHistory,
			};
		}
		default:
			return state;
	}
}
