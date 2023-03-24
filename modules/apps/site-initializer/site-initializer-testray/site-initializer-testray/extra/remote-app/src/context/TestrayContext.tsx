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
import {STORAGE_KEYS} from '~/core/Storage';

import {useFetch} from '../hooks/useFetch';
import useStorage from '../hooks/useStorage';
import {
	APIResponse,
	TestrayDispatchTrigger,
	UserAccount,
} from '../services/rest';
import {testrayDispatchTriggerImpl} from '../services/rest/TestrayDispatchTrigger';
import {ActionMap} from '../types';

export type RunId = number | null;

export type CompareRuns = {
	runA?: RunId;
	runB?: RunId;
	runId?: RunId;
};

type InitialState = {
	compareRuns: CompareRuns;
	myUserAccount?: UserAccount;
	testrayDispatchTriggers: APIResponse<TestrayDispatchTrigger>;
};

const initialState: InitialState = {
	compareRuns: {
		runA: null,
		runB: null,
	},
	myUserAccount: undefined,
	testrayDispatchTriggers: {
		actions: {},
		facets: [],
		items: [],
		lastPage: 1,
		page: 1,
		pageSize: 1,
		totalCount: 1,
	},
};

export const enum TestrayTypes {
	SET_MY_USER_ACCOUNT = 'SET_MY_USER_ACCOUNT',
	SET_RUN_A = 'SET_RUN_A',
	SET_RUN_B = 'SET_RUN_B',
}

type TestrayPayload = {
	[TestrayTypes.SET_MY_USER_ACCOUNT]: {
		account: UserAccount;
	};
	[TestrayTypes.SET_RUN_A]: RunId;
	[TestrayTypes.SET_RUN_B]: RunId;
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

		default:
			return state;
	}
};

const TestrayContextProvider: React.FC<{
	children: ReactNode;
}> = ({children}) => {
	const [storageValue, setStorageValue] = useStorage<{
		compareRuns: CompareRuns;
	}>(STORAGE_KEYS.COMPARE_RUNS, {
		initialValue: initialState,
		storageType: 'temporary',
	});

	const [state, dispatch] = useReducer(reducer, {
		...initialState,
		compareRuns: storageValue?.compareRuns,
	});

	const {data: testrayDispatchTriggers} = useFetch<
		APIResponse<TestrayDispatchTrigger>
	>(testrayDispatchTriggerImpl.resource, {
		params: {
			aggregationTerms: 'dueStatus',
			pageSize: 10,
			sort: 'dateCreated:asc',
		},
	});

	const {data: myUserAccount, mutate} = useFetch('/my-user-account', {
		transformData: (user: UserAccount) => ({
			additionalName: user?.additionalName,
			alternateName: user?.alternateName,
			emailAddress: user?.emailAddress,
			familyName: user?.familyName,
			givenName: user?.givenName,
			id: user?.id,
			image: user.image,
			name: user.name,
			roleBriefs: user?.roleBriefs,
			userGroupBriefs: user?.userGroupBriefs,
			uuid: user?.uuid,
		}),
	});

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
		<TestrayContext.Provider
			value={[
				{
					...state,
					testrayDispatchTriggers: testrayDispatchTriggers as APIResponse<
						TestrayDispatchTrigger
					>,
				},
				dispatch,
				mutate,
			]}
		>
			{children}
		</TestrayContext.Provider>
	);
};

export default TestrayContextProvider;
