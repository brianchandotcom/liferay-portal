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

import {ADD_UNDO_ACTION, UPDATE_UNDO_ACTIONS} from '../actions/types';
import {getDerivedStateForUndo} from '../components/undo/undoActions';

export default function undoReducer(state, action) {
	switch (action.type) {
		case ADD_UNDO_ACTION: {
			const {actionType} = action;

			return {
				...state,
				undoHistory: [
					getDerivedStateForUndo({action, state, type: actionType}),
					...(state.undoHistory || []),
				],
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
