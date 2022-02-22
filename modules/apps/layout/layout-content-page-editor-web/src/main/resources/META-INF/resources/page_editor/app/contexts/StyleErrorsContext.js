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

const DEFAULT_ID = 'defaultId';

const INITIAL_STATE = {
	setState: () => {},
	state: {},
};

const StyleErrorsStateContext = React.createContext(INITIAL_STATE);

export function StyleErrorsContextProvider({children, initialState = {}}) {
	const [state, setState] = useState(initialState);

	return (
		<StyleErrorsStateContext.Provider value={{setState, state}}>
			{children}
		</StyleErrorsStateContext.Provider>
	);
}

export function useDeleteStyleError() {
	const {setState, state} = useContext(StyleErrorsStateContext);

	return useCallback(
		(fieldName, itemId = DEFAULT_ID) => {
			if (state[itemId]?.[fieldName]) {
				const filteredErrors = {};
				const {[itemId]: itemErrors, ...rest} = state;

				for (const key in itemErrors) {
					if (key !== fieldName) {
						filteredErrors[key] = itemErrors[key];
					}
				}

				const nextState = {
					...rest,
					...(Object.keys(filteredErrors).length > 0 && {
						[itemId]: filteredErrors,
					}),
				};

				setState(nextState);
			}
		},
		[setState, state]
	);
}

export function useHasStyleErrors() {
	const {state} = useContext(StyleErrorsStateContext);

	return Object.keys(state).length > 0;
}

export function useSetStyleError() {
	const {setState, state} = useContext(StyleErrorsStateContext);

	return useCallback(
		(fieldName, value, itemId = DEFAULT_ID) => {
			setState({
				...state,
				[itemId]: {
					...state[itemId],
					[fieldName]: value,
				},
			});
		},
		[setState, state]
	);
}

export function useStyleErrors() {
	const {state} = useContext(StyleErrorsStateContext);

	return state;
}
