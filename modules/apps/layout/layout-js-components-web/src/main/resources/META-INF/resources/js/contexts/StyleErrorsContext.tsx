/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {
	Dispatch,
	ReactNode,
	SetStateAction,
	useCallback,
	useContext,
	useState,
} from 'react';

const DEFAULT_ID = 'defaultId';

interface Error {
	error: string;
	value: string;
}

type State = Record<string, Record<string, Error>>;

const StyleErrorsStateContext = React.createContext<{
	setState: Dispatch<SetStateAction<State>>;
	state: State;
}>({
	setState: () => {},
	state: {},
});

interface Props {
	children: ReactNode;
	initialState?: State;
}

export function StyleErrorsContextProvider({
	children,
	initialState = {},
}: Props) {
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
		(fieldName: string, itemId: string = DEFAULT_ID) => {
			if (state[itemId]?.[fieldName]) {
				const filteredErrors: Record<string, Error> = {};
				const {[itemId]: itemErrors, ...rest} = state;

				for (const key in itemErrors) {
					if (key !== fieldName) {
						filteredErrors[key] = itemErrors[key];
					}
				}

				const nextState = {
					...rest,
					...(!!Object.keys(filteredErrors).length && {
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

	return !!Object.keys(state).length;
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
