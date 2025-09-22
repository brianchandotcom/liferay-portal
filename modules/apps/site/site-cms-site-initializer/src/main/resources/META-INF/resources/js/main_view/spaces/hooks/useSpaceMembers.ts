/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
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
	| {type: 'FETCH_START' | 'LOAD_MORE_START' | 'ADD_MEMBER_START'}
	| {
			payload: {
				groups: {items: UserGroup[]; lastPage: number};
				roles: Role[];
				users: {items: UserAccount[]; lastPage: number};
			};
			type: 'FETCH_SUCCESS';
	  }
	| {
			payload: Error;
			type: 'FETCH_ERROR' | 'LOAD_MORE_ERROR' | 'ADD_MEMBER_ERROR';
	  }
	| {payload: UserAccount; type: 'ADD_USER_SUCCESS'}
	| {payload: UserGroup; type: 'ADD_GROUP_SUCCESS'}
	| {payload: {id: number | string}; type: 'ADD_USER_FAILURE'}
	| {payload: {id: number | string}; type: 'ADD_GROUP_FAILURE'}
	| {payload: {id: number | string}; type: 'REMOVE_USER_SUCCESS'}
	| {payload: {id: number | string}; type: 'REMOVE_GROUP_SUCCESS'}
	| {payload: UserAccount; type: 'REMOVE_USER_FAILURE'}
	| {payload: UserGroup; type: 'REMOVE_GROUP_FAILURE'}
	| {
			payload: {id: number | string; roles: Role[]};
			type: 'UPDATE_ROLES_SUCCESS';
	  }
	| {
			payload: {id: number | string; originalRoles: Role[]};
			type: 'UPDATE_ROLES_FAILURE';
	  }
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
		case 'ADD_MEMBER_START':
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
		case 'ADD_USER_SUCCESS':
			return {
				...state,
				isFetching: false,
				users: {
					...state.users,
					items: [action.payload, ...state.users.items],
				},
			};
		case 'ADD_GROUP_SUCCESS':
			return {
				...state,
				groups: {
					...state.groups,
					items: [action.payload, ...state.groups.items],
				},
				isFetching: false,
			};
		case 'ADD_USER_FAILURE':
			return {
				...state,
				isFetching: false,
				users: {
					...state.users,
					items: state.users.items.filter(
						(user) => user.id !== action.payload.id
					),
				},
			};
		case 'ADD_GROUP_FAILURE':
			return {
				...state,
				groups: {
					...state.groups,
					items: state.groups.items.filter(
						(group) => group.id !== action.payload.id
					),
				},
				isFetching: false,
			};
		case 'REMOVE_USER_SUCCESS':
			return {
				...state,
				users: {
					...state.users,
					items: state.users.items.filter(
						(user) => user.id !== action.payload.id
					),
				},
			};
		case 'REMOVE_GROUP_SUCCESS':
			return {
				...state,
				groups: {
					...state.groups,
					items: state.groups.items.filter(
						(group) => group.id !== action.payload.id
					),
				},
			};
		case 'REMOVE_USER_FAILURE':
			return {
				...state,
				users: {
					...state.users,
					items: [...state.users.items, action.payload],
				},
			};
		case 'REMOVE_GROUP_FAILURE':
			return {
				...state,
				groups: {
					...state.groups,
					items: [...state.groups.items, action.payload],
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
		case 'UPDATE_ROLES_SUCCESS':
			return {
				...state,
				groups: {
					...state.groups,
					items: state.groups.items.map((item) =>
						item.id === action.payload.id
							? {...item, roles: action.payload.roles}
							: item
					),
				},
				users: {
					...state.users,
					items: state.users.items.map((item) =>
						item.id === action.payload.id
							? {...item, roles: action.payload.roles}
							: item
					),
				},
			};
		case 'UPDATE_ROLES_FAILURE':
			return {
				...state,
				users: {
					...state.users,
					items: state.users.items.map((item) =>
						item.id === action.payload.id
							? {...item, roles: action.payload.originalRoles}
							: item
					),
				},
			};
		case 'FETCH_ERROR':
		case 'LOAD_MORE_ERROR':
		case 'ADD_MEMBER_ERROR':
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

	const addMember = useCallback(
		async (item: UserAccount | UserGroup, type: SelectOptions) => {
			const itemWithEmptyRoles = {
				...item,
				roles: [],
			};

			if (type === SelectOptions.USERS) {
				if (state.users.items.some((user) => user.id === item.id)) {
					return;
				}

				dispatch({
					payload: itemWithEmptyRoles as UserAccount,
					type: 'ADD_USER_SUCCESS',
				});

				const {error} = await SpaceService.linkUserToSpace({
					spaceExternalReferenceCode: externalReferenceCode,
					userExternalReferenceCode: item.externalReferenceCode,
				});

				if (error) {
					dispatch({
						payload: {id: item.id},
						type: 'ADD_USER_FAILURE',
					});

					openToast({
						message: sub(
							Liferay.Language.get(
								'failed-to-add-user-x-to-space'
							),
							[`<strong>${item.name}</strong>`]
						),
						type: 'danger',
					});
				}
				else {
					openToast({
						message: sub(
							Liferay.Language.get(
								'user-x-successfully-added-to-space'
							),
							[`<strong>${item.name}</strong>`]
						),
						type: 'success',
					});
				}
			}
			else {
				if (state.groups.items.some((group) => group.id === item.id)) {
					return;
				}

				dispatch({
					payload: itemWithEmptyRoles as UserGroup,
					type: 'ADD_GROUP_SUCCESS',
				});

				const {error} = await SpaceService.linkUserGroupToSpace({
					spaceExternalReferenceCode: externalReferenceCode,
					userGroupExternalReferenceCode: item.externalReferenceCode,
				});

				if (error) {
					dispatch({
						payload: {id: item.id},
						type: 'ADD_GROUP_FAILURE',
					});

					openToast({
						message: sub(
							Liferay.Language.get(
								'failed-to-add-group-x-to-space'
							),
							[`<strong>${item.name}</strong>`]
						),
						type: 'danger',
					});
				}
				else {
					openToast({
						message: sub(
							Liferay.Language.get(
								'group-x-successfully-added-to-space'
							),
							[`<strong>${item.name}</strong>`]
						),
						type: 'success',
					});
				}
			}
		},
		[externalReferenceCode, state.users.items, state.groups.items]
	);

	const removeMember = useCallback(
		async (item: UserAccount | UserGroup, type: SelectOptions) => {
			if (type === SelectOptions.USERS) {
				dispatch({payload: {id: item.id}, type: 'REMOVE_USER_SUCCESS'});

				const {error} = await SpaceService.unlinkUserFromSpace({
					spaceExternalReferenceCode: externalReferenceCode,
					userExternalReferenceCode: item.externalReferenceCode,
				});

				if (error) {
					dispatch({
						payload: item as UserAccount,
						type: 'REMOVE_USER_FAILURE',
					});

					openToast({
						message: sub(
							Liferay.Language.get(
								'unable-to-remove-user-x-from-space'
							),
							[`<strong>${item.name}</strong>`]
						),
						type: 'danger',
					});
				}
				else {
					openToast({
						message: sub(
							Liferay.Language.get(
								'user-x-successfully-removed-from-space'
							),
							[`<strong>${item.name}</strong>`]
						),
						type: 'success',
					});
				}
			}
			else {
				dispatch({
					payload: {id: item.id},
					type: 'REMOVE_GROUP_SUCCESS',
				});

				const {error} = await SpaceService.unlinkUserGroupFromSpace({
					spaceExternalReferenceCode: externalReferenceCode,
					userGroupExternalReferenceCode: item.externalReferenceCode,
				});

				if (error) {
					dispatch({
						payload: item as UserGroup,
						type: 'REMOVE_GROUP_FAILURE',
					});

					openToast({
						message: sub(
							Liferay.Language.get(
								'unable-to-remove-group-x-from-space'
							),
							[`<strong>${item.name}</strong>`]
						),
						type: 'danger',
					});
				}
				else {
					openToast({
						message: sub(
							Liferay.Language.get(
								'group-x-successfully-removed-from-space'
							),
							[`<strong>${item.name}</strong>`]
						),
						type: 'success',
					});
				}
			}
		},
		[externalReferenceCode]
	);

	const updateMemberRoles = useCallback(
		async (
			itemToUpdate: UserAccount | UserGroup,
			newRoles: string[],
			type: SelectOptions
		) => {
			const isUser = type === SelectOptions.USERS;
			const originalRoles = itemToUpdate.roles;

			const newRoleObjects = state.roles.filter((role) =>
				newRoles.includes(role.name)
			);

			dispatch({
				payload: {id: itemToUpdate.id, roles: newRoleObjects},
				type: 'UPDATE_ROLES_SUCCESS',
			});

			const {error} = isUser
				? await SpaceService.updateUserRoles({
						roleNames: newRoles,
						spaceExternalReferenceCode: externalReferenceCode,
						userExternalReferenceCode:
							itemToUpdate.externalReferenceCode,
					})
				: await SpaceService.updateUserGroupRoles({
						roleNames: newRoles,
						spaceExternalReferenceCode: externalReferenceCode,
						userGroupExternalReferenceCode:
							itemToUpdate.externalReferenceCode,
					});

			if (error) {
				dispatch({
					payload: {
						id: itemToUpdate.id,
						originalRoles:
							originalRoles?.map(
								(originalRole) =>
									state.roles.find(
										(role) => role.id === originalRole.id
									)!
							) || [],
					},
					type: 'UPDATE_ROLES_FAILURE',
				});

				openToast({
					message: sub(
						Liferay.Language.get(
							isUser
								? 'unable-to-update-roles-for-user-x'
								: 'unable-to-update-roles-for-group-x'
						),
						[`<strong>${itemToUpdate.name}</strong>`]
					),
					type: 'danger',
				});
			}
			else {
				openToast({
					message: sub(
						Liferay.Language.get('x-role-was-successfully-updated'),
						[`<strong>${itemToUpdate.name}</strong>`]
					),
					type: 'success',
				});
			}
		},
		[externalReferenceCode, state.roles]
	);

	return {
		addMember,
		loadMore,
		removeMember,
		state,
		updateMemberRoles,
	};
}
