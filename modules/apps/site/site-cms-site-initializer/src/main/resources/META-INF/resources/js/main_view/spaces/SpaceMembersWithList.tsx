/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../../css/spaces/SpaceMembersWithList.scss';

import LoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import {openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {
	useCallback,
	useEffect,
	useId,
	useMemo,
	useRef,
	useState,
} from 'react';

import SpaceService from '../../common/services/SpaceService';
import {UserAccount, UserGroup} from '../../common/types/UserAccount';
import {MembersListItem} from './MemberListItem';
import {
	SelectOptions,
	SpaceMembersInputWithSelect,
} from './SpaceMembersInputWithSelect';
import {useSpaceMembers} from './hooks/useSpaceMembers';
export interface SpaceMembersWithListProps {
	assetLibraryCreatorUserId: string;
	className?: string;
	externalReferenceCode: string;
	hasAssignMembersPermission: boolean;
	onHasSelectedMembersChange?: (hasSelectedMembers: boolean) => void;
	pageSize?: number;
}

const DEFAULT_PAGE_SIZE = 20;

export function SpaceMembersWithList({
	assetLibraryCreatorUserId,
	externalReferenceCode,
	hasAssignMembersPermission,
	className,
	onHasSelectedMembersChange,
	pageSize = DEFAULT_PAGE_SIZE,
}: SpaceMembersWithListProps) {
	const listLabelId = useId();
	const currentUserId = Liferay.ThemeDisplay.getUserId();
	const {state} = useSpaceMembers(externalReferenceCode, pageSize);
	const {
		groups,
		isFetching: isFetchingMembers,
		roles: spacePermissionsRoles,
		users,
	} = state;

	const [selectedOption, setSelectedOption] = useState(SelectOptions.USERS);
	const [selectedUsers, setSelectedUsers] = useState<UserAccount[]>(
		users.items
	);
	const [selectedUserGroups, setSelectedUserGroups] = useState<UserGroup[]>(
		groups.items
	);
	const [usersPage, setUsersPage] = useState(1);
	const [userGroupsPage, setUserGroupsPage] = useState(1);
	const sentinelRef = useRef(null);

	useEffect(() => {
		const loadMoreItems = async () => {
			if (selectedOption === SelectOptions.USERS) {
				const newUsersPage = usersPage + 1;

				if (newUsersPage > users.lastPage) {
					return;
				}

				const spaceUsers = await SpaceService.getSpaceUsers({
					externalReferenceCode,
					nestedFields: 'roles',
					page: newUsersPage,
					pageSize,
				});

				setSelectedUsers((currentSelectedUsers) => [
					...currentSelectedUsers,
					...spaceUsers.items,
				]);
				setUsersPage(newUsersPage);

				return;
			}

			const newUserGroupsPage = userGroupsPage + 1;
			if (newUserGroupsPage > groups.lastPage) {
				return;
			}

			const spaceUserGroups = await SpaceService.getSpaceUserGroups({
				externalReferenceCode,
				nestedFields: 'numberOfUserAccounts,roles',
				page: newUserGroupsPage,
				pageSize,
			});

			setSelectedUserGroups((currentSelectedUserGroups) => [
				...currentSelectedUserGroups,
				...spaceUserGroups.items,
			]);
			setUserGroupsPage(newUserGroupsPage);
		};

		loadMoreItems();
	}, [
		externalReferenceCode,
		groups.lastPage,
		pageSize,
		selectedOption,
		userGroupsPage,
		usersPage,
		users.lastPage,
	]);

	useEffect(() => {
		if (!sentinelRef.current) {
			return;
		}

		const observer = new IntersectionObserver(
			(entries) => {
				if (entries[0].isIntersecting) {

					// loadMoreItems();

				}
			},
			{
				threshold: 1,
			}
		);

		observer.observe(sentinelRef.current);

		return () => {
			observer.disconnect();
		};
	}, [sentinelRef]);

	useEffect(() => {
		setSelectedUsers(users.items);
	}, [users.items]);

	useEffect(() => {
		setSelectedUserGroups(groups.items);
	}, [groups.items]);

	useEffect(() => {
		const hasMembers =
			selectedUsers.length > 0 || selectedUserGroups.length > 0;
		onHasSelectedMembersChange?.(hasMembers);
	}, [onHasSelectedMembersChange, selectedUsers, selectedUserGroups]);

	const onAutocompleteItemSelected = async (
		item: UserAccount | UserGroup
	) => {
		const itemWithEmptyRoles = {
			...item,
			roles: [],
		};

		if (selectedOption === SelectOptions.USERS) {
			if (selectedUsers.some((user) => user.id === item.id)) {
				return;
			}

			setSelectedUsers([
				itemWithEmptyRoles as UserAccount,
				...selectedUsers,
			]);

			const {error} = await SpaceService.linkUserToSpace({
				spaceExternalReferenceCode: externalReferenceCode,
				userExternalReferenceCode: item.externalReferenceCode,
			});

			if (error) {
				openToast({
					message: sub(
						Liferay.Language.get('failed-to-add-user-x-to-space'),
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

			return;
		}

		if (selectedUserGroups.some((group) => group.id === item.id)) {
			return;
		}

		setSelectedUserGroups([
			itemWithEmptyRoles as UserGroup,
			...selectedUserGroups,
		]);

		const {error} = await SpaceService.linkUserGroupToSpace({
			spaceExternalReferenceCode: externalReferenceCode,
			userGroupExternalReferenceCode: item.externalReferenceCode,
		});

		if (error) {
			openToast({
				message: sub(
					Liferay.Language.get('failed-to-add-group-x-to-space'),
					[`<strong>${item.name}</strong>`]
				),
				type: 'danger',
			});
		}
		else {
			openToast({
				message: sub(
					Liferay.Language.get('group-x-successfully-added-to-space'),
					[`<strong>${item.name}</strong>`]
				),
				type: 'success',
			});
		}
	};

	const onRemoveItem = async (item: UserAccount | UserGroup) => {
		if (selectedOption === SelectOptions.USERS) {
			setSelectedUsers(selectedUsers.filter((u) => u.id !== item.id));

			const {error} = await SpaceService.unlinkUserFromSpace({
				spaceExternalReferenceCode: externalReferenceCode,
				userExternalReferenceCode: item.externalReferenceCode,
			});

			if (error) {
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

			return;
		}

		setSelectedUserGroups(
			selectedUserGroups.filter((u) => u.id !== item.id)
		);

		const {error} = await SpaceService.unlinkUserGroupFromSpace({
			spaceExternalReferenceCode: externalReferenceCode,
			userGroupExternalReferenceCode: item.externalReferenceCode,
		});

		if (error) {
			openToast({
				message: sub(
					Liferay.Language.get('unable-to-remove-group-x-from-space'),
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
	};

	const onUpdateItemRoles = useCallback(
		async (itemToUpdate: UserAccount | UserGroup, newRoles: string[]) => {
			const isUser = selectedOption === SelectOptions.USERS;
			const originalRoles = itemToUpdate.roles;

			const newRoleObjects = spacePermissionsRoles.filter((role) =>
				newRoles.includes(role.name)
			);

			const stateUpdater = isUser
				? setSelectedUsers
				: setSelectedUserGroups;

			const setStateUpdater = stateUpdater as React.Dispatch<
				React.SetStateAction<(UserAccount | UserGroup)[]>
			>;

			setStateUpdater((current) =>
				current.map((item) =>
					item.name === itemToUpdate.name
						? {...item, roles: newRoleObjects}
						: item
				)
			);

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
				setStateUpdater((current) =>
					current.map((item) =>
						item.id === itemToUpdate.id
							? {...item, roles: originalRoles}
							: item
					)
				);

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
		[externalReferenceCode, selectedOption, spacePermissionsRoles]
	);

	const hasMembersSelected = useMemo(() => {
		if (selectedOption === SelectOptions.USERS) {
			return selectedUsers.length;
		}

		return selectedUserGroups.length;
	}, [selectedOption, selectedUsers, selectedUserGroups]);

	return (
		<div className={classNames('space-members-with-list', className)}>
			<SpaceMembersInputWithSelect
				disabled={!hasAssignMembersPermission}
				onAutocompleteItemSelected={onAutocompleteItemSelected}
				onSelectChange={(value) => {
					setSelectedOption(value);
				}}
				selectValue={selectedOption}
			/>

			{!hasMembersSelected ? (
				<div className="border-top c-ml-n4 c-mr-n4 c-p-4 c-pb-0 text-center">
					<p className="c-mb-1 c-mt-2 font-weight-semi-bold text-4">
						{Liferay.Language.get('no-members-yet')}
					</p>

					<p className="c-m-0 text-3 text-secondary">
						{Liferay.Language.get('add-members-to-this-space')}
					</p>
				</div>
			) : (
				<>
					<label className="d-block" id={listLabelId}>
						{Liferay.Language.get('who-has-access')}
					</label>
					<ul
						aria-labelledby={listLabelId}
						className="c-mt-3 c-p-0 list-unstyled members-list"
					>
						{selectedOption === SelectOptions.USERS ? (
							<MembersListItem
								assetLibraryCreatorUserId={
									assetLibraryCreatorUserId
								}
								currentUserId={currentUserId}
								hasAssignMembersPermission={
									hasAssignMembersPermission
								}
								itemType="user"
								items={selectedUsers}
								onRemoveItem={onRemoveItem}
								onUpdateItemRoles={onUpdateItemRoles}
								roles={spacePermissionsRoles}
							/>
						) : (
							<MembersListItem
								hasAssignMembersPermission={
									hasAssignMembersPermission
								}
								itemType="group"
								items={selectedUserGroups}
								onRemoveItem={onRemoveItem}
								onUpdateItemRoles={onUpdateItemRoles}
								roles={spacePermissionsRoles}
							/>
						)}

						{isFetchingMembers && (
							<li className="d-flex justify-content-center">
								<LoadingIndicator
									displayType="secondary"
									size="sm"
								/>
							</li>
						)}

						<div ref={sentinelRef} />
					</ul>
				</>
			)}
		</div>
	);
}
