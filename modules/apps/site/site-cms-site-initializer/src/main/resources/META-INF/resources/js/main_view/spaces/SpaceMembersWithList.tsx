/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../../css/spaces/SpaceMembersWithList.scss';

import LoadingIndicator from '@clayui/loading-indicator';
import classNames from 'classnames';
import {openToast} from 'frontend-js-components-web';
import {sub} from 'frontend-js-web';
import React, {useCallback, useEffect, useId, useRef, useState} from 'react';

import SpaceService from '../../common/services/SpaceService';
import {UserAccount, UserGroup} from '../../common/types/UserAccount';
import {MembersListItem} from './MemberListItem';
import {
	SelectOptions,
	SpaceMembersInputWithSelect,
} from './SpaceMembersInputWithSelect';

export interface SpaceMembersWithListProps {
	assetLibraryCreatorUserId: string;
	assetLibraryId: string;
	className?: string;
	hasAssignMembersPermission: boolean;
	onHasSelectedMembersChange?: (hasSelectedMembers: boolean) => void;
	pageSize?: number;
}

const DEFAULT_PAGE_SIZE = 20;

export function SpaceMembersWithList({
	assetLibraryCreatorUserId,
	assetLibraryId,
	hasAssignMembersPermission,
	className,
	onHasSelectedMembersChange,
	pageSize = DEFAULT_PAGE_SIZE,
}: SpaceMembersWithListProps) {
	const currentUserId = Liferay.ThemeDisplay.getUserId();
	const [isFetchingMembers, setIsFetchingMembers] = useState(false);
	const [selectedOption, setSelectedOption] = useState(SelectOptions.USERS);
	const [selectedUsers, setSelectedUsers] = useState<UserAccount[]>([]);
	const [selectedUserGroups, setSelectedUserGroups] = useState<UserGroup[]>(
		[]
	);
	const [usersLastPage, setUsersLastPage] = useState(0);
	const [usersPage, setUsersPage] = useState(1);
	const [userGroupsLastPage, setUserGroupsLastPage] = useState(0);
	const [userGroupsPage, setUserGroupsPage] = useState(1);
	const sentinelRef = useRef(null);

	useEffect(() => {
		const fetchMembers = async () => {
			setIsFetchingMembers(true);

			try {
				const [spaceUsers, spaceUserGroups] = await Promise.all([
					SpaceService.getSpaceUsers({
						page: 1,
						pageSize,
						spaceId: assetLibraryId,
					}),
					SpaceService.getSpaceUserGroups({
						nestedFields: 'numberOfUserAccounts',
						page: 1,
						pageSize,
						spaceId: assetLibraryId,
					}),
				]);

				setSelectedUsers(spaceUsers.items);
				setSelectedUserGroups(spaceUserGroups.items);
				setUserGroupsLastPage(spaceUserGroups.lastPage);
				setUsersLastPage(spaceUsers.lastPage);
			}
			catch (error) {
				console.error(error);
			}
			finally {
				setIsFetchingMembers(false);
			}
		};

		fetchMembers();
	}, [assetLibraryId, pageSize]);

	useEffect(() => {
		const hasMembers =
			selectedUsers?.length > 1 || selectedUserGroups?.length;
		onHasSelectedMembersChange?.(Boolean(hasMembers));
	}, [onHasSelectedMembersChange, selectedUsers, selectedUserGroups]);

	const loadMoreItems = useCallback(async () => {
		if (selectedOption === SelectOptions.USERS) {
			const newUsersPage = usersPage + 1;

			if (newUsersPage > usersLastPage) {
				return;
			}

			setIsFetchingMembers(true);

			try {
				const spaceUsers = await SpaceService.getSpaceUsers({
					page: newUsersPage,
					pageSize,
					spaceId: assetLibraryId,
				});

				setSelectedUsers((currentSelectedUsers) => [
					...currentSelectedUsers,
					...spaceUsers.items,
				]);
				setUsersPage(newUsersPage);
				setUsersLastPage(spaceUsers.lastPage);
			}
			catch (error) {
				console.error(error);
			}
			finally {
				setIsFetchingMembers(false);
			}

			return;
		}

		const newUserGroupsPage = userGroupsPage + 1;
		if (newUserGroupsPage > userGroupsLastPage) {
			return;
		}

		setIsFetchingMembers(true);

		try {
			const spaceUserGroups = await SpaceService.getSpaceUserGroups({
				page: newUserGroupsPage,
				pageSize,
				spaceId: assetLibraryId,
			});

			setSelectedUserGroups((currentSelectedUserGroups) => [
				...currentSelectedUserGroups,
				...spaceUserGroups.items,
			]);
			setUserGroupsPage(newUserGroupsPage);
			setUserGroupsLastPage(spaceUserGroups.lastPage);
		}
		catch (error) {
			console.error(error);
		}
		finally {
			setIsFetchingMembers(false);
		}
	}, [
		assetLibraryId,
		pageSize,
		selectedOption,
		userGroupsPage,
		usersPage,
		userGroupsLastPage,
		usersLastPage,
	]);

	useEffect(() => {
		if (!sentinelRef.current) {
			return;
		}

		const observer = new IntersectionObserver(
			(entries) => {
				if (entries[0].isIntersecting) {
					loadMoreItems();
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
	}, [sentinelRef, loadMoreItems]);

	const onAutocompleteItemSelected = async (
		item: UserAccount | UserGroup
	) => {
		if (selectedOption === SelectOptions.USERS) {
			if (selectedUsers.some((user) => user.id === item.id)) {
				return;
			}

			setSelectedUsers([item as UserAccount, ...selectedUsers]);

			const {error} = await SpaceService.linkUserToSpace({
				spaceId: assetLibraryId,
				userId: item.id,
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

		setSelectedUserGroups([item, ...selectedUserGroups]);

		const {error} = await SpaceService.linkUserGroupToSpace({
			spaceId: assetLibraryId,
			userGroupId: item.id,
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
				spaceId: assetLibraryId,
				userId: item.id,
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
			spaceId: assetLibraryId,
			userGroupId: item.id,
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

	const listLabelId = useId();

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

			<label className="d-block" id={listLabelId}>
				{Liferay.Language.get('who-has-access')}
			</label>

			<ul
				aria-labelledby={listLabelId}
				className="c-mt-3 c-p-0 list-unstyled members-list"
			>
				{selectedOption === SelectOptions.USERS ? (
					<MembersListItem
						assetLibraryCreatorUserId={assetLibraryCreatorUserId}
						currentUserId={currentUserId}
						emptyMessage={Liferay.Language.get(
							'this-space-has-no-user-yet'
						)}
						hasAssignMembersPermission={hasAssignMembersPermission}
						itemType="user"
						items={selectedUsers}
						onRemoveItem={onRemoveItem}
					/>
				) : (
					<MembersListItem
						emptyMessage={Liferay.Language.get(
							'this-space-has-no-group-yet'
						)}
						hasAssignMembersPermission={hasAssignMembersPermission}
						itemType="group"
						items={selectedUserGroups}
						onRemoveItem={onRemoveItem}
					/>
				)}

				{isFetchingMembers && (
					<li className="d-flex justify-content-center">
						<LoadingIndicator displayType="secondary" size="sm" />
					</li>
				)}

				<div ref={sentinelRef} />
			</ul>
		</div>
	);
}
