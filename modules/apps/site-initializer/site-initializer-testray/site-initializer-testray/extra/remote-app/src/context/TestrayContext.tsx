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

import {ReactNode, createContext, useEffect, useMemo, useReducer} from 'react';
import {KeyedMutator} from 'swr';

import {useFetch} from '../hooks/useFetch';
import useStorage from '../hooks/useStorage';
import {UserAccount} from '../services/rest';
import {ActionMap} from '../types';

export type RunId = number | null | undefined;

export type CompareRuns = {
	runA?: RunId;
	runB?: RunId;
	runId?: RunId;
};

type InitialState = {
	compareRuns: CompareRuns;
	myUserAccount?: UserAccount;
};

const initialState: InitialState = {
	compareRuns: {
		runA: undefined,
		runB: undefined,
		runId: undefined,
	},
	myUserAccount: undefined,
};

export const enum TestrayTypes {
	SET_MY_USER_ACCOUNT = 'SET_MY_USER_ACCOUNT',
	SET_RUN_A = 'SET_RUN_A',
	SET_RUN_B = 'SET_RUN_B',
	SET_RUN_ID = 'SET_RUN_ID',
}

type TestrayPayload = {
	[TestrayTypes.SET_MY_USER_ACCOUNT]: {
		account: UserAccount;
	};
	[TestrayTypes.SET_RUN_A]: RunId;
	[TestrayTypes.SET_RUN_B]: RunId;
	[TestrayTypes.SET_RUN_ID]: RunId;
};

type AppActions = ActionMap<TestrayPayload>[keyof ActionMap<TestrayPayload>];

export const TestrayContext = createContext<
	[
		InitialState,
		(param: AppActions) => void,
		KeyedMutator<UserAccount> | null
	]
>([initialState, () => null, null]);

const reducer = (state: InitialState, action: AppActions) => {
	switch (action.type) {
		case TestrayTypes.SET_MY_USER_ACCOUNT:
			const {account} = action.payload;

			return {
				...state,
				myUserAccount: account,
			};

		case TestrayTypes.SET_RUN_A:
			return {
				...state,
				compareRuns: {...state.compareRuns, runA: action.payload},
			};

		case TestrayTypes.SET_RUN_B:
			return {
				...state,
				compareRuns: {...state.compareRuns, runB: action.payload},
			};

		case TestrayTypes.SET_RUN_ID:
			return {
				...state,
				compareRuns: {...state.compareRuns, runId: action.payload},
			};

		default:
			return state;
	}
};

const TestrayContextProvider: React.FC<{
	children: ReactNode;
}> = ({children}) => {
	const [storageValue, setStorageValue] = useStorage<{
		compareRuns: CompareRuns;
	}>('compareRuns', undefined, sessionStorage);

	const [state, dispatch] = useReducer(reducer, {
		...initialState,
		compareRuns: storageValue.compareRuns,
	});

	const {data: myUserAccount, mutate} = useFetch(
		'/my-user-account',
		(user: UserAccount) => ({
			additionalName: user?.additionalName,
			alternateName: user?.alternateName,
			emailAddress: user?.emailAddress,
			familyName: user?.familyName,
			givenName: user?.givenName,
			id: user?.id,
			image: user.image,
			roleBriefs: user?.roleBriefs,
			userGroupBriefs: user?.userGroupBriefs,
			uuid: user?.uuid,
		})
	);

	const compareRuns = useMemo(() => state.compareRuns, [state.compareRuns]);

	useEffect(() => {
		if (compareRuns) {
			setStorageValue({compareRuns});
		}
	}, [setStorageValue, compareRuns]);

	useEffect(() => {
		if (myUserAccount) {
			dispatch({
				payload: {
					account: myUserAccount,
				},
				type: TestrayTypes.SET_MY_USER_ACCOUNT,
			});
		}
	}, [myUserAccount]);

	return (
		<TestrayContext.Provider value={[state, dispatch, mutate]}>
			{children}
		</TestrayContext.Provider>
	);
};

export default TestrayContextProvider;
