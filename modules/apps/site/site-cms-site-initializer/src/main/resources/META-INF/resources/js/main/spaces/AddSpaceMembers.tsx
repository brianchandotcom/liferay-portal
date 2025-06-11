/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../../css/spaces/AddSpaceMembers.scss';

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClaySticker from '@clayui/sticker';
import {openToast} from 'frontend-js-components-web';
import {navigate, sub} from 'frontend-js-web';
import React, {useEffect, useId, useState} from 'react';

import SpaceService from '../../services/SpaceService';
import {UserAccount, UserGroup} from '../../types/UserAccount';
import {getImage} from '../util/getImage';
import {NewSpaceFormSection} from './NewSpaceFormSection';
import {
	SelectOptions,
	SpaceMembersInputWithSelect,
} from './SpaceMembersInputWithSelect';

export interface AddSpaceMembersProps {
	assetLibraryCreatorUserId: string;
	assetLibraryId: string;
	assetLibraryName: string;
	baseSpaceUrl: string;
	depotEntryName: string;
}

export function AddSpaceMembers({
	assetLibraryCreatorUserId,
	assetLibraryId,
	assetLibraryName,
	baseSpaceUrl,
}: AddSpaceMembersProps) {
	const currentUserId = Liferay.ThemeDisplay.getUserId();
	const [selectedOption, setSelectedOption] = useState(SelectOptions.USERS);
	const [selectedUsers, setSelectedUsers] = useState<UserAccount[]>([]);
	const [selectedUserGroups, setSelectedUserGroups] = useState<UserGroup[]>(
		[]
	);

	useEffect(() => {
		const fetchSpaceUsers = async () => {
			const spaceUsers = await SpaceService.getSpaceUsers({
				spaceId: assetLibraryId,
			});
			setSelectedUsers(spaceUsers);
		};

		fetchSpaceUsers();
	}, [assetLibraryId]);

	useEffect(() => {
		const fetchSpaceUserGroups = async () => {
			const spaceUserGroups = await SpaceService.getSpaceUserGroups({
				spaceId: assetLibraryId,
			});
			setSelectedUserGroups(spaceUserGroups);
		};

		fetchSpaceUserGroups();
	}, [assetLibraryId]);

	const onAutocompleteItemSelected = async (
		item: UserAccount | UserGroup
	) => {
		if (selectedOption === SelectOptions.USERS) {
			if (selectedUsers.some((user) => user.id === item.id)) {
				return;
			}

			setSelectedUsers([...selectedUsers, item as UserAccount]);

			const {error} = await SpaceService.linkUserToSpace({
				spaceId: assetLibraryId,
				userId: item.id,
			});

			if (error) {
				openToast({
					message: sub(
						Liferay.Language.get('failed-to-add-user-x-to-space-x'),
						[`<strong>${item.name}</strong>`]
					),
					type: 'danger',
				});
			}
			else {
				openToast({
					message: sub(
						Liferay.Language.get(
							'user-x-successfully-added-to-space-x'
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

		setSelectedUserGroups([...selectedUserGroups, item]);

		const {error} = await SpaceService.linkUserGroupToSpace({
			spaceId: assetLibraryId,
			userGroupId: item.id,
		});

		if (error) {
			openToast({
				message: sub(
					Liferay.Language.get('failed-to-add-group-x-to-space-x'),
					[`<strong>${item.name}</strong>`]
				),
				type: 'danger',
			});
		}
		else {
			openToast({
				message: sub(
					Liferay.Language.get(
						'group-x-successfully-added-to-space-x'
					),
					[`<strong>${item.name}</strong>`]
				),
				type: 'success',
			});
		}
	};

	const onRemoveUser = async (user: UserAccount) => {
		setSelectedUsers(selectedUsers.filter((u) => u.id !== user.id));

		const {error} = await SpaceService.unlinkUserFromSpace({
			spaceId: assetLibraryId,
			userId: user.id,
		});

		if (error) {
			openToast({
				message: sub(
					Liferay.Language.get(
						'unable-to-remove-user-x-from-space-x'
					),
					[`<strong>${user.name}</strong>`]
				),
				type: 'success',
			});
		}
		else {
			openToast({
				message: sub(
					Liferay.Language.get(
						'user-x-successfully-removed-from-space-x'
					),
					[`<strong>${user.name}</strong>`]
				),
				type: 'success',
			});
		}
	};

	const onRemoveUserGroup = async (group: UserGroup) => {
		setSelectedUserGroups(
			selectedUserGroups.filter((g) => g.id !== group.id)
		);

		const {error} = await SpaceService.unlinkUserGroupFromSpace({
			spaceId: assetLibraryId,
			userGroupId: group.id,
		});

		if (error) {
			openToast({
				message: sub(
					Liferay.Language.get(
						'unable-to-remove-group-x-from-space-x'
					),
					[`<strong>${group.name}</strong>`]
				),
				type: 'success',
			});
		}
		else {
			openToast({
				message: sub(
					Liferay.Language.get(
						'group-x-successfully-removed-from-space-x'
					),
					[`<strong>${group.name}</strong>`]
				),
				type: 'success',
			});
		}
	};

	const onContinueBtnClick = () => {
		navigate(baseSpaceUrl + assetLibraryId);
	};

	const hasMembers = selectedUsers?.length > 1 || selectedUserGroups?.length;

	const renderUsersList = () => {
		if (!selectedUsers?.length) {
			return (
				<li className="d-flex justify-content-center">
					{Liferay.Language.get('this-space-has-no-user-yet')}
				</li>
			);
		}

		return selectedUsers.map((user) => (
			<li
				className="align-items-center d-flex justify-content-between"
				key={user.id}
			>
				<div className="align-items-center d-flex">
					<ClaySticker displayType="primary" shape="circle" size="sm">
						<img
							alt={user.name}
							className="sticker-img"
							src={user.image || '/image/user_portrait'}
						/>
					</ClaySticker>

					<span className="ml-2">{user.name}</span>

					{String(user.id) === currentUserId && (
						<span className="ml-1 text-lowercase text-secondary">
							({Liferay.Language.get('you')})
						</span>
					)}
				</div>

				{assetLibraryCreatorUserId === String(user.id) ? (
					<span className="text-3 text-capitalize text-secondary">
						({Liferay.Language.get('owner')})
					</span>
				) : (
					<ClayButtonWithIcon
						aria-label={sub(
							Liferay.Language.get('remove-x'),
							Liferay.Language.get('user')
						)}
						borderless
						displayType="secondary"
						onClick={async () => {
							await onRemoveUser(user);
						}}
						symbol="times-circle"
						translucent
					/>
				)}
			</li>
		));
	};

	const renderUserGroupsList = () => {
		if (!selectedUserGroups?.length) {
			return (
				<li className="d-flex justify-content-center">
					{Liferay.Language.get('this-space-has-no-group-yet')}
				</li>
			);
		}

		return selectedUserGroups.map((group) => (
			<li
				className="align-items-center d-flex justify-content-between"
				key={group.id}
			>
				<div className="align-items-center d-flex">
					<ClaySticker displayType="primary" shape="circle" size="sm">
						<ClayIcon
							className="text-secondary"
							fontSize="24px"
							symbol="users"
						/>
					</ClaySticker>

					<span className="ml-2 text-truncate">{group.name}</span>
				</div>

				<ClayButtonWithIcon
					aria-label={sub(
						Liferay.Language.get('remove-x'),
						Liferay.Language.get('group')
					)}
					borderless
					displayType="secondary"
					onClick={async () => {
						await onRemoveUserGroup(group);
					}}
					symbol="times-circle"
					translucent
				/>
			</li>
		));
	};

	const listLabelId = useId();

	return (
		<ClayLayout.Row className="add-space-members">
			<ClayLayout.Col className="mw-50 px-9 w-50">
				<NewSpaceFormSection
					description={Liferay.Language.get(
						'add-team-members-to-this-space-to-start-collaborating'
					)}
					step={2}
					title={sub(
						Liferay.Language.get('add-members-to-x'),
						assetLibraryName
					)}
					withForm={false}
				>
					<SpaceMembersInputWithSelect
						onAutocompleteItemSelected={onAutocompleteItemSelected}
						onSelectChange={setSelectedOption}
						selectValue={selectedOption}
					/>

					<label className="d-block" id={listLabelId}>
						{Liferay.Language.get('who-has-access')}
					</label>

					<ul aria-labelledby={listLabelId} className="members-list">
						{selectedOption === SelectOptions.USERS
							? renderUsersList()
							: renderUserGroupsList()}
					</ul>

					<ClayButton.Group className="mb-0 w-100" spaced vertical>
						<ClayButton
							className="mt-4"
							onClick={onContinueBtnClick}
						>
							{hasMembers
								? Liferay.Language.get('continue')
								: Liferay.Language.get(
										'continue-without-members'
									)}
						</ClayButton>
					</ClayButton.Group>
				</NewSpaceFormSection>
			</ClayLayout.Col>

			<ClayLayout.Col>
				<img
					aria-hidden="true"
					src={getImage('add_space_members_illustration.svg')}
				></img>
			</ClayLayout.Col>
		</ClayLayout.Row>
	);
}

export default AddSpaceMembers;
