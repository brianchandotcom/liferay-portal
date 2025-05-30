/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import {sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {getImage} from '../util/getImage';
import {NewSpaceFormSection} from './NewSpaceFormSection';
import {
	SelectOptions,
	SpaceMembersInputWithSelect,
} from './SpaceMembersInputWithSelect';

export interface AddSpaceMembersProps {
	spaceName: string;
}

export function AddSpaceMembers({spaceName}: AddSpaceMembersProps) {
	const [selectedOption, setSelectedOption] = useState(SelectOptions.USERS);
	const [inputValue, setInputValue] = useState('');

	return (
		<ClayLayout.Row className="p-4">
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
					/>

					<ClayButton.Group className="mb-0 w-100" spaced vertical>
						<ClayButton className="mt-4">
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
