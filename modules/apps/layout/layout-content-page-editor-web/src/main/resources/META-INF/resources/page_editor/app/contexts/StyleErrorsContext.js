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

const StyleErrorsStateContext = React.createContext({});

export function StyleErrorsContextProvider({children, initialState = {}}) {
	const [state, setState] = useState(initialState);

	return (
		<StyleErrorsStateContext.Provider value={{setState, state}}>
			{children}
		</StyleErrorsStateContext.Provider>
	);
}

export function useDeleteStyleError() {
	const activeItemId = useActiveItemId() || 'defaultId';

	const {setState, state} = useContext(StyleErrorsStateContext);

	return useCallback(
		(fieldName) => {
			if (state[activeItemId]?.[fieldName]) {
				delete state[activeItemId][fieldName];

				if (Object.keys(state[activeItemId]).length === 0) {
					delete state[activeItemId];
				}

				setState(state);
			}
		},
		[activeItemId, setState, state]
	);
}

export function useHasStyleErrors() {
	const {state} = useContext(StyleErrorsStateContext);

	return Object.keys(state).length > 0;
}

export function useSetStyleError() {
	const activeItemId = useActiveItemId() || 'defaultId';
	const {setState, state} = useContext(StyleErrorsStateContext);

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
	const {state} = useContext(StyleErrorsStateContext);

	return state;
}
