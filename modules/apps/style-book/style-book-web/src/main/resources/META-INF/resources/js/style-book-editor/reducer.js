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
	SET_TOKEN_VALUE,
	UPDATE_UNDO_REDO_HISTORY,
} from './constants/actionTypes';

export default function reducer(state, action) {
	switch (action.type) {
		case LOADING: {
			const {value} = action;

			return {...state, loading: value};
		}

		case SET_DRAFT_STATUS: {
			const {value} = action;

			return {
				...state,
				draftStatus: value,
			};
		}

		case SET_PREVIEW_LAYOUT: {
			const {layout} = action;

			return {...state, previewLayout: layout};
		}

		case SET_PREVIEW_LAYOUT_TYPE: {
			const {layoutType} = action;

			return {...state, previewLayoutType: layoutType};
		}

		case SET_TOKEN_VALUE: {
			const {name, value} = action;

			return {
				...state,
				frontendTokensValues: {
					...state.frontendTokensValues,
					[name]: value,
				},
			};
		}

		case ADD_UNDO_ACTION: {
			const {isRedo = false, name, value} = action;

			const nextRedoHistory = isRedo ? state.redoHistory : [];

			return {
				...state,
				redoHistory: nextRedoHistory,
				undoHistory: [
					{
						name,
						value,
					},
					...state.undoHistory,
				],
			};
		}

		case ADD_REDO_ACTION: {
			const {name, value} = action;

			return {
				...state,
				redoHistory: [
					{
						name,
						value,
					},
					...state.redoHistory,
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
