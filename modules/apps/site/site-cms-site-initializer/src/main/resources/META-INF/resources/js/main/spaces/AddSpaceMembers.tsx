/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '../../../css/spaces/AddSpaceMembers.scss';

import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';
import ClaySticker from '@clayui/sticker';

import {getImage} from '../util/getImage';
import {NewSpaceFormSection} from './NewSpaceFormSection';
import {
	SelectOptions,
	SpaceMembersInputWithSelect,
} from './SpaceMembersInputWithSelect';
import { UserAccount, UserGroup } from '../../types/UserAccount';

export interface AddSpaceMembersProps {
	spaceName: string;
}

export function AddSpaceMembers({spaceName}: AddSpaceMembersProps) {
	const [selectedOption, setSelectedOption] = useState(SelectOptions.USERS);
	const [inputValue, setInputValue] = useState('');
	const [selectedUsers, setSelectedUsers] = useState<UserAccount[]>([]);
	const [selectedUserGroups, setSelectedUserGroups] = useState<UserGroup[]>([]);

	const onAutocompleteItemSelected = (item: UserAccount | UserGroup) => {
		if (selectedOption === SelectOptions.USERS) {
			if(selectedUsers.some((user) => user.id === item.id)) {
				return;
			}

			setSelectedUsers([...selectedUsers, item as UserAccount]);
		} else {
			if(selectedUserGroups.some((group) => group.id === item.id)){
				return;
			}

			setSelectedUserGroups([...selectedUserGroups, item]);
		}
	}

	const onContinueBtnClick = () => {
		if(selectedUsers.length) {
			// Call endpoint for users
		}

		if(selectedUserGroups.length) {
			// Call endpoint for groups
		}
	}

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
						inputValue={inputValue}
						onInputChange={setInputValue}
						onSelectChange={setSelectedOption}
						selectValue={selectedOption}
						onAutocompleteItemSelected={onAutocompleteItemSelected}
					/>

					<label className="d-block" htmlFor="list-of-users">Who has access</label>
					<ul className="members-list" id="list-of-users">
						{selectedUsers.map((user) => (
							<li>
								<ClaySticker
									displayType="primary"
									shape="circle"
									size="sm"
								>
									<img
										alt={user.name}
										className="sticker-img"
										src={user.image || '/image/user_portrait'}
									/>
								</ClaySticker>
								<span className="ml-2">
									{user.name} ({user.emailAddress?.split('@')[0]})
								</span>
							</li>
						))}
						{selectedUserGroups.map((group) => (
							<li>
								<ClaySticker
									displayType="primary"
									shape="circle"
									size="sm"
								>
									<img
										alt={group.name}
										className="sticker-img"
										src={'/image/user_portrait'}
									/>
								</ClaySticker>
								<span className="ml-2">{group.name}</span>
							</li>
						))}
					</ul>
					<ClayButton.Group className="mb-0 w-100" spaced vertical>
						<ClayButton className="mt-4" onClick={onContinueBtnClick}>
							{Liferay.Language.get('continue-without-members')}
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
