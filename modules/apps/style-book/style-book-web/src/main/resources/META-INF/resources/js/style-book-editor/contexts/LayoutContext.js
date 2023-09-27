/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useCallback, useContext, useReducer} from 'react';

import {
	LOADING,
	SET_PREVIEW_LAYOUT,
	SET_PREVIEW_LAYOUT_TYPE,
} from '../constants/actionTypes';
import layoutReducer from '../reducers/layoutReducer';

const LayoutDispatchContext = React.createContext(() => {});
export const LayoutStoreContext = React.createContext({
	loading: true,
	previewLayout: {},
	previewLayoutType: null,
});

export function LayoutContextProvider({children, initialState}) {
	const [state, dispatch] = useReducer(layoutReducer, initialState);

	return (
		<LayoutDispatchContext.Provider value={dispatch}>
			<LayoutStoreContext.Provider value={state}>
				{children}
			</LayoutStoreContext.Provider>
		</LayoutDispatchContext.Provider>
	);
}

export function useDispatch() {
	return useContext(LayoutDispatchContext);
}

export function useDraftStatus() {
	return useContext(LayoutStoreContext).draftStatus;
}

export function useLoading() {
	return useContext(LayoutStoreContext).loading;
}

export function usePreviewLayout() {
	return useContext(LayoutStoreContext).previewLayout;
}

export function usePreviewLayoutType() {
	return useContext(LayoutStoreContext).previewLayoutType;
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
