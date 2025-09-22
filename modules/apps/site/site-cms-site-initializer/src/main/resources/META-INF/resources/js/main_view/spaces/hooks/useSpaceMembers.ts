/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useEffect, useReducer} from 'react';

import AdminUserService from '../../../common/services/AdminUserService';
import SpaceService from '../../../common/services/SpaceService';
import {Role} from '../../../common/types/Role';
import {UserAccount, UserGroup} from '../../../common/types/UserAccount';

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
	| {type: 'FETCH_START'}
	| {
			payload: {
				groups: {items: UserGroup[]; lastPage: number};
				roles: Role[];
				users: {items: UserAccount[]; lastPage: number};
			};
			type: 'FETCH_SUCCESS';
	  }
	| {payload: Error; type: 'FETCH_ERROR'};

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
		case 'FETCH_ERROR':
			return {...state, error: action.payload, isFetching: false};
		default:
			return state;
	}
}

export function useSpaceMembers(assetLibraryId: string, pageSize: number) {
	const [state, dispatch] = useReducer(reducer, initialState);

	useEffect(() => {
		const fetchMembers = async () => {
			dispatch({type: 'FETCH_START'});

			try {
				const [spaceUsers, spaceUserGroups, userRoles] =
					await Promise.all([
						SpaceService.getSpaceUsers({
							nestedFields: 'roles',
							page: 1,
							pageSize,
							spaceId: assetLibraryId,
						}),
						SpaceService.getSpaceUserGroups({
							nestedFields: 'numberOfUserAccounts,roles',
							page: 1,
							pageSize,
							spaceId: assetLibraryId,
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
	}, [assetLibraryId, pageSize]);

	return {state};
}
