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

import React, {useContext, useReducer} from 'react';

import reducer from './reducer';

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
