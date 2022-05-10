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
export const StyleBookContext = React.createContext({
	frontendTokensValues: {},
	loading: true,
	previewLayout: {},
	previewLayoutType: null,
	setFrontendTokensValues: () => {},
	setPreviewLayoutType: () => {},
});

export function StyleBookContextProvider({children, value}) {
	const [state, dispatch] = useReducer(reducer, value);

	return (
		<StyleBookDispatchContext.Provider value={dispatch}>
			<StyleBookContext.Provider value={state}>
				{children}
			</StyleBookContext.Provider>
		</StyleBookDispatchContext.Provider>
	);
}

export function useDispatch() {
	return useContext(StyleBookDispatchContext);
}

export function useLoading() {
	return useContext(StyleBookContext).loading;
}

export function usePreviewLayout() {
	return useContext(StyleBookContext).previewLayout;
}
