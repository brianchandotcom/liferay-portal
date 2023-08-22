/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext, useReducer} from 'react';

import {
	Action,
	LOAD_DATA,
	SET_DATA,
	SET_ERROR,
	SET_ISSUES,
	SET_LANGUAGE_ID,
	SET_SELECTED_ITEM,
} from '../constants/actionTypes';
import {LayoutReportsData} from '../constants/types/LayoutReports';
import {SelectedItem} from '../constants/types/SelectedItem';

interface State {
	data?: LayoutReportsData;
	error?: string | null;
	languageId?: string;
	loading: boolean;
	selectedItem?: SelectedItem | null;
}

const INITIAL_STATE: State = {
	loading: false,
};

const noop = () => {};

export const StoreDispatchContext = createContext<React.Dispatch<Action>>(noop);
export const StoreStateContext = createContext<State>(INITIAL_STATE);

function reducer(state = INITIAL_STATE, action: Action) {
	let nextState: State = state;

	switch (action.type) {
		case LOAD_DATA:
			nextState = {
				...state,
				error: null,
				loading: true,
			};
			break;

		case SET_ERROR:
			nextState = {
				...state,
				error: action.error,
				loading: false,
			};
			break;

		case SET_DATA:
			nextState = {
				data: action.data,
				error: action.data?.error,
				languageId: state.languageId || action.data?.defaultLanguageId,
				loading: action.loading || false,
			};
			break;

		case SET_ISSUES:
			nextState = {
				...state,
				data: {
					...state.data,
					layoutReportsIssues: {
						...state.data?.layoutReportsIssues,
						[action.languageId]: action.layoutReportsIssues,
					},
				},
				loading: false,
			};
			break;

		case SET_LANGUAGE_ID:
			nextState = {
				...state,
				languageId: action.languageId,
			};
			break;

		case SET_SELECTED_ITEM:
			nextState = {
				...state,
				selectedItem: action.item,
			};
			break;

		default:
			return state;
	}

	return nextState;
}

export function StoreContextProvider({
	children,
	value,
}: {
	children: React.ReactNode;
	value: State;
}) {
	const [state, dispatch] = useReducer(reducer, {...INITIAL_STATE, ...value});

	return (
		<StoreDispatchContext.Provider value={dispatch}>
			<StoreStateContext.Provider value={state}>
				{children}
			</StoreStateContext.Provider>
		</StoreDispatchContext.Provider>
	);
}
