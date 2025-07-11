/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayLayout from '@clayui/layout';
import {ILearnResourceContext} from 'frontend-js-components-web';
import {navigate, sub} from 'frontend-js-web';
import React, {useState} from 'react';

import {getImage} from '../../common/utils/getImage';
import {NewSpaceFormSection} from './NewSpaceFormSection';
import {SpaceMembersWithList} from './SpaceMembersWithList';

export interface AddSpaceMembersProps {
	assetLibraryCreatorUserId: string;
	assetLibraryId: string;
	assetLibraryName: string;
	baseAssetLibraryURL: string;
	learnResources: ILearnResourceContext;
}

export function AddSpaceMembers({
	assetLibraryCreatorUserId,
	assetLibraryId,
	assetLibraryName,
	baseAssetLibraryURL,
	learnResources,
}: AddSpaceMembersProps) {
	const [hasSelectedMembers, setHasSelectedMembers] = useState(false);

	const onContinueBtnClick = () => {
		navigate(baseAssetLibraryURL + assetLibraryId);
	};

	return (
		<ClayLayout.Row className="add-space-members">
			<ClayLayout.Col className="mw-50 px-9 w-50">
				<NewSpaceFormSection
					description={Liferay.Language.get(
						'add-team-members-to-this-space-to-start-collaborating'
					)}
					learnResourceKey="add-space-members"
					learnResources={learnResources}
					step={2}
					title={sub(
						Liferay.Language.get('add-members-to-x'),
						assetLibraryName
					)}
					withForm={false}
				>
					<SpaceMembersWithList
						assetLibraryCreatorUserId={assetLibraryCreatorUserId}
						assetLibraryId={assetLibraryId}
						onHasSelectedMembersChange={setHasSelectedMembers}
					/>

					<ClayButton.Group className="mb-0 w-100" spaced vertical>
						<ClayButton
							className="mt-4"
							onClick={onContinueBtnClick}
						>
							{hasSelectedMembers
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
