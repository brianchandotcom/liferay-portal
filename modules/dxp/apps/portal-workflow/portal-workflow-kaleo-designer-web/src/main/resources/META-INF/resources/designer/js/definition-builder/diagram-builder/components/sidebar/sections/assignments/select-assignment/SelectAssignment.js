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
import ClayIcon from '@clayui/icon';
import React from 'react';

import SidebarPanel from '../../../SidebarPanel';

const options = [
	{
		assignmentType: 'assetCreator',
		label: Liferay.Language.get('asset-creator'),
	},
	{
		assignmentType: 'resourceActions',
		label: Liferay.Language.get('resource-actions'),
	},
	{
		assignmentType: 'roleId',
		label: Liferay.Language.get('role'),
	},
	{
		assignmentType: 'user',
		label: Liferay.Language.get('user'),
	},
	{
		assignmentType: 'roleType',
		label: Liferay.Language.get('role-type'),
	},
	{
		assignmentType: 'scriptedAssignment',
		label: Liferay.Language.get('scripted-assignment'),
	},
];

const SelectAssignment = ({section, setSection, setSections}) => {
	return (
		<SidebarPanel panelTitle={Liferay.Language.get('select-assignment')}>
			<ClayForm.Group>
				<label htmlFor="assignment-type">
					{Liferay.Language.get('assignment-type')}

					<span
						className="ml-2"
						title={Liferay.Language.get(
							'select-the-assignment-type'
						)}
					>
						<ClayIcon
							className="text-muted"
							symbol="question-circle-full"
						/>
					</span>
				</label>

				<ClaySelect
					aria-label="Select"
					id="assignment-type"
					onChange={(event) => {
						setSection(event.target.value);
						setSections([{identifier: `${Date.now()}-0`}]);
					}}
				>
					{options.map((item) => (
						<ClaySelect.Option
							disabled={item?.disabled}
							key={item.assignmentType}
							label={item.label}
							selected={item.assignmentType === section}
							value={item.assignmentType}
						/>
					))}
				</ClaySelect>
			</ClayForm.Group>
		</SidebarPanel>
	);
};

export {SelectAssignment, options};
