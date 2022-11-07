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

import React, {createContext, useContext, useReducer} from 'react';

const initialState = {
	accountsCount: 0,
	organizationsCount: 0,
	usersCount: 0,
};

type TState = {
	accountsCount: number;
	organizationsCount: number;
	usersCount: number;
};

const PeopleContextData = createContext<TState>(initialState);
const PeopleContextDispatch = createContext<any>(null);

const useData = () => useContext(PeopleContextData);
const useDispatch = () => useContext(PeopleContextDispatch);

export enum Events {
	AccountsCount = 'ACCOUNTS_COUNT',
	OrganizationsCount = 'ORGANIZATIONS_COUNT',
	UsersCount = 'USERS_COUNT',
}

function reducer(state: TState, action: {payload: number; type: Events}) {
	switch (action.type) {
		case Events.AccountsCount: {
			return {
				...state,
				accountsCount: action.payload,
			};
		}
		case Events.OrganizationsCount: {
			return {
				...state,
				organizationsCount: action.payload,
			};
		}
		case Events.UsersCount: {
			return {
				...state,
				usersCount: action.payload,
			};
		}
		default:
			throw new Error();
	}
}

const PeopleContextProvider: React.FC = ({children}) => {
	const [state, dispatch] = useReducer(reducer, initialState);

	return (
		<PeopleContextData.Provider value={state}>
			<PeopleContextDispatch.Provider value={dispatch}>
				{children}
			</PeopleContextDispatch.Provider>
		</PeopleContextData.Provider>
	);
};

export {useData, useDispatch};
export default PeopleContextProvider;
