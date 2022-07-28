/* eslint-disable no-case-declarations */
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

import {ReactNode, createContext, useEffect, useReducer} from 'react';

import {UserAccount} from '../graphql/queries';
import {useFetch} from '../hooks/useFetch';
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
	[AccountTypes.SET_MY_USER_ACCOUNT]: {
		account: UserAccount;
	};
};

type AppActions = ActionMap<AccountPayload>[keyof ActionMap<AccountPayload>];

export const AccountContext = createContext<
	[InitialState, (param: AppActions) => void]
>([initialState, () => null]);

const reducer = (state: InitialState, action: AppActions) => {
	switch (action.type) {
		case AccountTypes.SET_MY_USER_ACCOUNT:
			const {account} = action.payload;

			return {
				...state,
				myUserAccount: account,
			};

		default:
			return state;
	}
};

const AccountContextProvider: React.FC<{
	children: ReactNode;
}> = ({children}) => {
	const [state, dispatch] = useReducer(reducer, initialState);
	const {data: myUserAccount} = useFetch(
		'/my-user-account',
		(user: UserAccount) => ({
			additionalName: user?.additionalName,
			alternateName: user?.alternateName,
			emailAddress: user?.emailAddress,
			familyName: user?.familyName,
			givenName: user?.givenName,
			id: user?.id,
			image: '',
			roleBriefs: user?.roleBriefs,
			uuid: user?.uuid,
		})
	);

	useEffect(() => {
		if (myUserAccount) {
			dispatch({
				payload: {
					account: myUserAccount,
				},
				type: AccountTypes.SET_MY_USER_ACCOUNT,
			});
		}
	}, [myUserAccount]);

	return (
		<AccountContext.Provider value={[state, dispatch]}>
			{children}
		</AccountContext.Provider>
	);
};

export default AccountContextProvider;
