/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../../css/spaces/AddSpaceMembers.scss';

import ClayButton, {ClayButtonWithIcon} from '@clayui/button';
import ClayLayout from '@clayui/layout';
import ClaySticker from '@clayui/sticker';
import {fetch, sub} from 'frontend-js-web';
import React, {useEffect, useState} from 'react';

import SpaceService from '../../services/SpaceService';
import {UserAccount, UserGroup} from '../../types/UserAccount';
import {getImage} from '../util/getImage';
import {NewSpaceFormSection} from './NewSpaceFormSection';
import {
	SelectOptions,
	SpaceMembersInputWithSelect,
} from './SpaceMembersInputWithSelect';

export interface AddSpaceMembersProps {
	assetLibraryId: string;
	spaceName: string;
}

export function AddSpaceMembers({
	assetLibraryId,
	spaceName,
}: AddSpaceMembersProps) {
	const [selectedOption, setSelectedOption] = useState(SelectOptions.USERS);
	const [selectedUsers, setSelectedUsers] = useState<UserAccount[]>([]);
	const [selectedUserGroups, setSelectedUserGroups] = useState<UserGroup[]>(
		[]
	);

	useEffect(() => {
		const fetchUsers = async () => {
			const result = await fetch(
				`/o/headless-asset-library/v1.0/asset-libraries/${assetLibraryId}/user-accounts`,
				{
					headers: {
						'x-csrf-token': Liferay.authToken,
					},
				}
			);

			const json = await result.json();

			setSelectedUsers(json.items);
		};

		fetchUsers();
	}, [assetLibraryId]);

	useEffect(() => {
		const fetchUserGroups = async () => {
			const result = await fetch(
				`/o/headless-asset-library/v1.0/asset-libraries/${assetLibraryId}/user-groups`,
				{
					headers: {
						'x-csrf-token': Liferay.authToken,
					},
				}
			);

			const json = await result.json();

			setSelectedUserGroups(json.items);
		};

		fetchUserGroups();
	}, [assetLibraryId]);

	const onAutocompleteItemSelected = async (
		item: UserAccount | UserGroup
	) => {
		if (selectedOption === SelectOptions.USERS) {
			if (selectedUsers.some((user) => user.id === item.id)) {
				return;
			}

			setSelectedUsers([...selectedUsers, item as UserAccount]);

			await SpaceService.linkUserToSpace({
				spaceId: assetLibraryId,
				userId: item.id,
			});

			return;
		}

		if (selectedUserGroups.some((group) => group.id === item.id)) {
			return;
		}

		setSelectedUserGroups([...selectedUserGroups, item]);

		await SpaceService.linkUserGroupToSpace({
			spaceId: assetLibraryId,
			userGroupId: item.id,
		});
	};

	const onRemoveUser = async (user: UserAccount) => {
		setSelectedUsers(selectedUsers.filter((u) => u.id !== user.id));
		await SpaceService.unlinkUserFromSpace({
			spaceId: assetLibraryId,
			userId: user.id,
		});
	};

	const onRemoveUserGroup = async (group: UserGroup) => {
		setSelectedUserGroups(
			selectedUserGroups.filter((g) => g.id !== group.id)
		);
		await SpaceService.unlinkUserGroupFromSpace({
			spaceId: assetLibraryId,
			userGroupId: group.id,
		});
	};

	const onContinueBtnClick = () => {};

	const hasMembers = selectedUsers.length || selectedUserGroups.length;

	return (
		<ClayLayout.Row className="add-space-members">
			<ClayLayout.Col className="mw-50 px-9 w-50">
				<NewSpaceFormSection
					description={Liferay.Language.get(
						'add-team-members-to-this-space-to-start-collaborating'
					)}
					linkLabel={Liferay.Language.get(
						'learn-more-about-memberships'
					)}
					linkUrl="/"
					onSubmit={() => null}
					step={2}
					title={sub(
						Liferay.Language.get('add-members-to-x'),
						spaceName
					)}
				>
					<SpaceMembersInputWithSelect
						onAutocompleteItemSelected={onAutocompleteItemSelected}
						onSelectChange={setSelectedOption}
						selectValue={selectedOption}
					/>

					<label className="d-block" htmlFor="list-of-users">
						{Liferay.Language.get('who-has-access')}
					</label>

					<ul className="members-list" id="list-of-users">
						{selectedUsers.map((user) => (
							<li
								className="align-items-center d-flex justify-content-between"
								key={user.id}
							>
								<div>
									<ClaySticker
										displayType="primary"
										shape="circle"
										size="sm"
									>
										<img
											alt={user.name}
											className="sticker-img"
											src={
												user.image ||
												'/image/user_portrait'
											}
										/>
									</ClaySticker>

									<span className="ml-2">{user.name}</span>
								</div>

								<ClayButtonWithIcon
									aria-label="Remove User"
									borderless
									displayType="secondary"
									onClick={async () => {
										await onRemoveUser(user);
									}}
									symbol="times-circle"
									translucent
								/>
							</li>
						))}

						{selectedUserGroups.map((group) => (
							<li
								className="align-items-center d-flex justify-content-between"
								key={group.id}
							>
								<div>
									<ClaySticker
										displayType="primary"
										shape="circle"
										size="sm"
									>
										<img
											alt={group.name}
											className="sticker-img"
											src="/image/user_portrait"
										/>
									</ClaySticker>

									<span className="ml-2">{group.name}</span>
								</div>

								<ClayButtonWithIcon
									aria-label="Remove User"
									borderless
									displayType="secondary"
									onClick={async () => {
										await onRemoveUserGroup(group);
									}}
									symbol="times-circle"
									translucent
								/>
							</li>
						))}
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
