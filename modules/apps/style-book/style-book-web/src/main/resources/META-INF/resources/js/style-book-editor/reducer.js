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
			const {isUndoAction, name, value} = action;
			const nextUndoHistory = state.undoHistory || [];

			const previousValue = {
				...value,
				name: state.frontendTokensValues[name]?.name,
				value: state.frontendTokensValues[name].value,
			};

			return {
				...state,
				frontendTokensValues: {
					...state.frontendTokensValues,
					[name]: value,
				},
				...(!isUndoAction && {
					undoHistory: [
						...nextUndoHistory,
						{name, value: previousValue},
					],
				}),
			};
		}

		case ADD_UNDO_ACTION: {
			const {name, value} = action;
			const {frontendTokensValues, redoHistory, undoHistory} = state;

			return {
				...state,
				redoHistory: [
					...redoHistory,
					{
						name,
						value: {
							...value,
							name: frontendTokensValues[name]?.name,
							value: frontendTokensValues[name].value,
						},
					},
				],
				undoHistory: undoHistory.slice(0, undoHistory.length - 1),
			};
		}

		case ADD_REDO_ACTION: {
			const {name, value} = action;
			const {frontendTokensValues, redoHistory, undoHistory} = state;

			return {
				...state,
				redoHistory: redoHistory.slice(0, redoHistory.length - 1),
				undoHistory: [
					...undoHistory,
					{
						name,
						value: {
							...value,
							name: frontendTokensValues[name]?.name,
							value: frontendTokensValues[name].value,
						},
					},
				],
			};
		}

		default:
			return state;
	}
}
