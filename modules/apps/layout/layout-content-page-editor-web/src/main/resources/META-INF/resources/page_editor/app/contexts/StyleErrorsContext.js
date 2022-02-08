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

import React, {useCallback, useContext, useState} from 'react';

import {useActiveItemId} from './ControlsContext';

const StyleErrorsDispatchContext = React.createContext(() => {});
const StyleErrorsStateContext = React.createContext({});

export function StyleErrorsContextProvider({children}) {
	const [styleErrors, setStyleErrors] = useState({});

	return (
		<StyleErrorsDispatchContext.Provider value={setStyleErrors}>
			<StyleErrorsStateContext.Provider value={styleErrors}>
				{children}
			</StyleErrorsStateContext.Provider>
		</StyleErrorsDispatchContext.Provider>
	);
}

export function useHasStyleErrors() {
	const state = useContext(StyleErrorsStateContext);

	return Object.keys(state).length > 0;
}

export function useSetStyleError() {
	const activeItemId = useActiveItemId() || 'defaultId';
	const setState = useContext(StyleErrorsDispatchContext);
	const state = useContext(StyleErrorsStateContext);

	return useCallback(
		(fieldName, value) => {
			let nextState;

			if (state[activeItemId]) {
				nextState = {
					...state,
					[activeItemId]: {
						...state[activeItemId],
						[fieldName]: value,
					},
				};
			}
			else {
				nextState = {
					...state,
					[activeItemId]: {[fieldName]: value},
				};
			}

			setState(nextState);
		},
		[activeItemId, setState, state]
	);
}

export function useStyleErrors() {
	return useContext(StyleErrorsStateContext);
}
