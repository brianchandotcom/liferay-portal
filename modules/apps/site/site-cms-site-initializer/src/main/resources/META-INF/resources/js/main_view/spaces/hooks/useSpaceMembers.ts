/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useCallback, useEffect, useReducer} from 'react';

import AdminUserService from '../../../common/services/AdminUserService';
import SpaceService from '../../../common/services/SpaceService';
import {Role} from '../../../common/types/Role';
import {UserAccount, UserGroup} from '../../../common/types/UserAccount';
import {SelectOptions} from '../SpaceMembersInputWithSelect';

interface State {
	error: Error | null;
	groups: {
		items: UserGroup[];
		lastPage: number;
		page: number;
	};
	isFetching: boolean;
	roles: Role[];
	users: {
		items: UserAccount[];
		lastPage: number;
		page: number;
	};
}

type Action =
	| {type: 'FETCH_START' | 'LOAD_MORE_START'}
	| {
			payload: {
				groups: {items: UserGroup[]; lastPage: number};
				roles: Role[];
				users: {items: UserAccount[]; lastPage: number};
			};
			type: 'FETCH_SUCCESS';
	  }
	| {payload: Error; type: 'FETCH_ERROR' | 'LOAD_MORE_ERROR'}
	| {
			payload: {
				items: UserAccount[];
				page: number;
			};
			type: 'LOAD_MORE_USERS_SUCCESS';
	  }
	| {
			payload: {
				items: UserGroup[];
				page: number;
			};
			type: 'LOAD_MORE_GROUPS_SUCCESS';
	  };

const initialState: State = {
	error: null,
	groups: {
		items: [],
		lastPage: 1,
		page: 1,
	},
	isFetching: false,
	roles: [],
	users: {
		items: [],
		lastPage: 1,
		page: 1,
	},
};

function reducer(state: State, action: Action): State {
	switch (action.type) {
		case 'FETCH_START':
		case 'LOAD_MORE_START':
			return {...state, error: null, isFetching: true};
		case 'FETCH_SUCCESS':
			return {
				...state,
				groups: {
					...state.groups,
					items: action.payload.groups.items,
					lastPage: action.payload.groups.lastPage,
				},
				isFetching: false,
				roles: action.payload.roles,
				users: {
					...state.users,
					items: action.payload.users.items,
					lastPage: action.payload.users.lastPage,
				},
			};
		case 'LOAD_MORE_USERS_SUCCESS':
			return {
				...state,
				isFetching: false,
				users: {
					...state.users,
					items: [...state.users.items, ...action.payload.items],
					page: action.payload.page,
				},
			};
		case 'LOAD_MORE_GROUPS_SUCCESS':
			return {
				...state,
				groups: {
					...state.groups,
					items: [...state.groups.items, ...action.payload.items],
					page: action.payload.page,
				},
				isFetching: false,
			};
		case 'FETCH_ERROR':
		case 'LOAD_MORE_ERROR':
			return {...state, error: action.payload, isFetching: false};
		default:
			return state;
	}
}

export function useSpaceMembers(
	externalReferenceCode: string,
	pageSize: number
) {
	const [state, dispatch] = useReducer(reducer, initialState);

	useEffect(() => {
		const fetchMembers = async () => {
			dispatch({type: 'FETCH_START'});

			try {
				const [spaceUsers, spaceUserGroups, userRoles] =
					await Promise.all([
						SpaceService.getSpaceUsers({
							externalReferenceCode,
							nestedFields: 'roles',
							page: 1,
							pageSize,
						}),
						SpaceService.getSpaceUserGroups({
							externalReferenceCode,
							nestedFields: 'numberOfUserAccounts,roles',
							page: 1,
							pageSize,
						}),
						AdminUserService.getUserRoles({
							filter: "name ne 'Asset Library Connected Site Member' and type eq 5",
						}),
					]);

				dispatch({
					payload: {
						groups: spaceUserGroups,
						roles: userRoles.items,
						users: spaceUsers,
					},
					type: 'FETCH_SUCCESS',
				});
			}
			catch (error) {
				console.error(error);
				dispatch({payload: error as Error, type: 'FETCH_ERROR'});
			}
		};

		fetchMembers();
	}, [externalReferenceCode, pageSize]);

	const loadMore = useCallback(
		async (type: SelectOptions) => {
			if (state.isFetching) {
				return;
			}

			dispatch({type: 'LOAD_MORE_START'});

			try {
				if (type === SelectOptions.USERS) {
					const newPage = state.users.page + 1;

					if (newPage > state.users.lastPage) {
						return;
					}

					const spaceUsers = await SpaceService.getSpaceUsers({
						externalReferenceCode,
						nestedFields: 'roles',
						page: newPage,
						pageSize,
					});

					dispatch({
						payload: {items: spaceUsers.items, page: newPage},
						type: 'LOAD_MORE_USERS_SUCCESS',
					});
				}
				else {
					const newPage = state.groups.page + 1;

					if (newPage > state.groups.lastPage) {
						return;
					}

					const spaceUserGroups =
						await SpaceService.getSpaceUserGroups({
							externalReferenceCode,
							nestedFields: 'numberOfUserAccounts,roles',
							page: newPage,
							pageSize,
						});

					dispatch({
						payload: {items: spaceUserGroups.items, page: newPage},
						type: 'LOAD_MORE_GROUPS_SUCCESS',
					});
				}
			}
			catch (error) {
				dispatch({payload: error as Error, type: 'LOAD_MORE_ERROR'});
			}
		},
		[externalReferenceCode, pageSize, state]
	);

	return {loadMore, state};
}
