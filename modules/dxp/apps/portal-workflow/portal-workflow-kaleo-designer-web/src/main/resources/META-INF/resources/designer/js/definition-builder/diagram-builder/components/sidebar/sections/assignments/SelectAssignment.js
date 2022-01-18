/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayForm, {ClaySelect} from '@clayui/form';
import React, {useState} from 'react';

import SidebarPanel from '../../SidebarPanel';
import AssetCreator from './select-assignment/AssetCreator';
import ResourceActions from './select-assignment/ResourceActions';

const options = [
	{
		label: '',
		value: '',
	},
	{
		label: Liferay.Language.get('asset-creator'),
		value: 'assetCreator',
	},
	{
		label: Liferay.Language.get('resource-actions'),
		value: 'resourceActions',
	},
	{
		disabled: true,
		label: Liferay.Language.get('role'),
		value: 'role',
	},
	{
		disabled: true,
		label: Liferay.Language.get('user'),
		value: 'user',
	},
	{
		disabled: true,
		label: Liferay.Language.get('role-type'),
		value: 'roleType',
	},
	{
		disabled: true,
		label: Liferay.Language.get('scripted-assignment'),
		value: 'scriptedAssignment',
	},
];

const AssignmentSectionComponents = {
	assetCreator: AssetCreator,
	resourceActions: ResourceActions,
};

const Assignments = (props) => {
	const [section, setSection] = useState('');

	const AssignmentSectionComponent = AssignmentSectionComponents[section];

	return (
		<>
			<SidebarPanel
				panelTitle={Liferay.Language.get('select-assignment')}
			>
				<ClayForm.Group>
					<label htmlFor="assignment-type">
						{Liferay.Language.get('assignment-type')}

						<span className="ml-1 mr-1 text-warning">*</span>
					</label>

					<ClaySelect
						aria-label="Select"
						id="assignment-type"
						onChange={(event) => {
							setSection(event.target.value);
						}}
					>
						{options.map((item) => (
							<ClaySelect.Option
								disabled={item?.disabled}
								key={item.value}
								label={item.label}
								value={item.value}
							/>
						))}
					</ClaySelect>
				</ClayForm.Group>
			</SidebarPanel>

			{AssignmentSectionComponent && (
				<AssignmentSectionComponent {...props} />
			)}
		</>
	);
};

export default Assignments;
