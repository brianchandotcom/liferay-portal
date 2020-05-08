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
	ADD_UNDO_ACTION,
	CLEAN_UNDO_ACTIONS,
	UPDATE_UNDO_ACTIONS,
} from '../actions/types';
import {getDerivedStateForUndo} from '../components/undo/undoActions';

const MAX_UNDO_ACTIONS = 20;

export default function undoReducer(state, action) {
	switch (action.type) {
		case ADD_UNDO_ACTION: {
			const {actionType} = action;

			const nextUndoHistory = state.undoHistory || [];

			return {
				...state,
				undoHistory: [
					getDerivedStateForUndo({action, state, type: actionType}),
					...nextUndoHistory.slice(0, MAX_UNDO_ACTIONS - 1),
				],
			};
		}
		case CLEAN_UNDO_ACTIONS: {
			let nextUndoHistory = [...(state.undoHistory || [])];

			const itemId = action.itemId;

			const item = state.layoutData.items[itemId];

			if (item.config && item.config.fragmentEntryLinkId) {
				nextUndoHistory = nextUndoHistory.filter(
					({fragmentEntryLinkId}) =>
						fragmentEntryLinkId !== item.config.fragmentEntryLinkId
				);
			}

			nextUndoHistory = nextUndoHistory.filter(
				({itemId}) => itemId !== action.itemId
			);

			return {
				...state,
				undoHistory: nextUndoHistory,
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
