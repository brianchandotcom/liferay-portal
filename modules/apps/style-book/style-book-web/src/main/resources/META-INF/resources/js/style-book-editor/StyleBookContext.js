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

import {openToast} from 'frontend-js-web';
import React, {useCallback, useContext, useReducer} from 'react';

import {
	LOADING,
	SET_DRAFT_STATUS,
	SET_PREVIEW_LAYOUT,
	SET_PREVIEW_LAYOUT_TYPE,
	SET_TOKEN_VALUE,
} from './constants/actionTypes';
import {DRAFT_STATUS} from './constants/draftStatusConstants';
import reducer from './reducer';
import saveDraft from './saveDraft';

const StyleBookDispatchContext = React.createContext(() => {});
export const StyleBookStoreContext = React.createContext({
	draftStatus: null,
	frontendTokensValues: {},
	loading: true,
	previewLayout: {},
	previewLayoutType: null,
});

export function StyleBookContextProvider({children, initialState}) {
	const [state, dispatch] = useReducer(reducer, initialState);

	return (
		<StyleBookDispatchContext.Provider value={dispatch}>
			<StyleBookStoreContext.Provider value={state}>
				{children}
			</StyleBookStoreContext.Provider>
		</StyleBookDispatchContext.Provider>
	);
}

export function useDispatch() {
	return useContext(StyleBookDispatchContext);
}

export function useDraftStatus() {
	return useContext(StyleBookStoreContext).draftStatus;
}

export function useFrontendTokensValues() {
	return useContext(StyleBookStoreContext).frontendTokensValues;
}

export function useLoading() {
	return useContext(StyleBookStoreContext).loading;
}

export function usePreviewLayout() {
	return useContext(StyleBookStoreContext).previewLayout;
}

export function usePreviewLayoutType() {
	return useContext(StyleBookStoreContext).previewLayoutType;
}

export function useSaveTokenValue() {
	const dispatch = useDispatch();
	const frontendTokensValues = useFrontendTokensValues();

	return (name, value) => {
		dispatch({
			name,
			type: SET_TOKEN_VALUE,
			value,
		});

		saveDraft({...frontendTokensValues, [name]: value})
			.then(() => {
				dispatch({
					type: SET_DRAFT_STATUS,
					value: DRAFT_STATUS.draftSaved,
				});
			})
			.catch((error) => {
				if (process.env.NODE_ENV === 'development') {
					console.error(error);
				}

				dispatch({
					type: SET_DRAFT_STATUS,
					value: DRAFT_STATUS.notSaved,
				});

				openToast({
					message: error.message,
					type: 'danger',
				});
			});
	};
}

export function useSetLoading() {
	const dispatch = useDispatch();

	return useCallback((value) => dispatch({type: LOADING, value}), [dispatch]);
}

export function useSetPreviewLayout() {
	const dispatch = useDispatch();

	return useCallback(
		(layout) => dispatch({layout, type: SET_PREVIEW_LAYOUT}),
		[dispatch]
	);
}

export function useSetPreviewLayoutType() {
	const dispatch = useDispatch();

	return (layoutType) =>
		dispatch({layoutType, type: SET_PREVIEW_LAYOUT_TYPE});
}
