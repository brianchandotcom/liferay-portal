/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {sub} from 'frontend-js-web';
import React from 'react';

import './RightSidebarObjectRelationshipDetails.scss';

import {
	Input,
	InputLocalized,
	SingleSelect,
} from '@liferay/object-js-components-web';

import {firstLetterUppercase} from '../../../utils/string';
import {TDeletionType} from '../../ObjectRelationship/EditRelationship';
import {useFolderContext} from '../objectFolderContext';

interface RightSidebarObjectRelationshipDetailsProps {
	deletionTypes: TDeletionType[];
}

export function RightSidebarObjectRelationshipDetails({
	deletionTypes,
}: RightSidebarObjectRelationshipDetailsProps) {
	const [
		{selectedDefinitionNode, selectedObjectRelationship},
	] = useFolderContext();

	const readOnly =
		!selectedDefinitionNode.hasUpdateObjectDefinitionPermission ||
		selectedObjectRelationship.reverse;

	return (
		<>
			<div className="lfr-objects__model-builder-right-sidebar-relationship-title-container">
				<div className="lfr-objects__model-builder-right-sidebar-relationship-title">
					<span>
						{sub(
							Liferay.Language.get('x-details'),
							Liferay.Language.get('relationship')
						)}
					</span>
				</div>

				<ClayButtonWithIcon
					aria-label={Liferay.Language.get('delete-relationship')}
					displayType="secondary"
					symbol="trash"
					title={Liferay.Language.get('delete-relationship')}
				/>
			</div>

			<div className="lfr-objects__model-builder-right-sidebar-relationship-content">
				<InputLocalized
					disableFlag={readOnly}
					disabled={readOnly}
					error=""
					label={Liferay.Language.get('label')}
					onChange={() => {}}
					required
					translations={
						selectedObjectRelationship.label as LocalizedValue<
							string
						>
					}
				/>

				<Input
					disabled={readOnly}
					error=""
					label={Liferay.Language.get('name')}
					onChange={() => {}}
					required
					value={selectedObjectRelationship.name}
				/>

				<Input
					disabled={readOnly}
					error=""
					label={
						selectedObjectRelationship.type === 'manyToMany'
							? Liferay.Language.get('many-records-of')
							: Liferay.Language.get('one-record-of')
					}
					onChange={() => {}}
					required
					value={selectedDefinitionNode.name}
				/>

				<Input
					disabled={readOnly}
					error=""
					label={Liferay.Language.get('many-records-of')}
					onChange={() => {}}
					required
					value={selectedObjectRelationship.objectDefinitionName2}
				/>

				<SingleSelect
					disabled={readOnly}
					label={Liferay.Language.get('deletion-type')}
					onChange={() => {}}
					options={deletionTypes}
					required
					value={firstLetterUppercase(
						selectedObjectRelationship.deletionType as string
					)}
				/>
			</div>
		</>
	);
}
