/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
