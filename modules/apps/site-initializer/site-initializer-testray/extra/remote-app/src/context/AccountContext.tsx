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

import {createContext, useEffect, useReducer} from 'react';

import apolloClient from '../graphql/apolloClient';
import {UserAccount, getLiferayMyUserAccount} from '../graphql/queries';
import {ActionMap} from '../types';

type InitialState = {
	myUserAccount?: UserAccount;
};

const initialState: InitialState = {
	myUserAccount: undefined,
};

export enum AccountTypes {
	SET_MY_USER_ACCOUNT = 'SET_MY_USER_ACCOUNT',
}

type AccountPayload = {
	[AccountTypes.SET_MY_USER_ACCOUNT]: UserAccount;
};

type AppActions = ActionMap<AccountPayload>[keyof ActionMap<AccountPayload>];

export const AccountContext = createContext<
	[InitialState, (param: AppActions) => void]
>([initialState, () => null]);

const reducer = (state: InitialState, action: AppActions) => {
	switch (action.type) {
		case AccountTypes.SET_MY_USER_ACCOUNT:
			return {
				...state,
				myUserAccount: action.payload,
			};

		default:
			return state;
	}
};

const AccountContextProvider: React.FC = ({children}) => {
	const [state, dispatch] = useReducer(reducer, initialState);

	useEffect(() => {
		apolloClient
			.query({query: getLiferayMyUserAccount})
			.then((response) =>
				dispatch({
					payload: response.data.myUserAccount as UserAccount,
					type: AccountTypes.SET_MY_USER_ACCOUNT,
				})
			)
			.catch(console.error);
	}, []);

	return (
		<AccountContext.Provider value={[state, dispatch]}>
			{children}
		</AccountContext.Provider>
	);
};

export default AccountContextProvider;
