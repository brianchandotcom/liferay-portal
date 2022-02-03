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
import React, {useContext, useEffect, useState} from 'react';

import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import SidebarPanel from '../../SidebarPanel';
import AssetCreator from './select-assignment/AssetCreator';
import ResourceActions from './select-assignment/ResourceActions';
import Role from './select-assignment/Role';
import RoleType from './select-assignment/RoleType';
import ScriptedAssignment from './select-assignment/ScriptedAssignment';
import User from './select-assignment/User';
import {getAssignmentType} from './utils';

const options = [
	{
		label: '',
		value: '',
	},
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

const AssignmentSectionComponents = {
	assetCreator: AssetCreator,
	resourceActions: ResourceActions,
	roleId: Role,
	roleType: RoleType,
	scriptedAssignment: ScriptedAssignment,
	user: User,
};

const SelectAssignment = (props) => {
	const {selectedItem} = useContext(DiagramBuilderContext);
	const assignments = selectedItem?.data?.assignments;

	const assignmentType = getAssignmentType(assignments);

	const [section, setSection] = useState(assignmentType || '');
	const [sections, setSections] = useState([{identifier: `${Date.now()}-0`}]);

	const AssignmentSectionComponent = AssignmentSectionComponents[section];

	useEffect(() => {
		if (assignmentType === 'user') {
			setSections(assignments.sectionsData);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

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

			{sections.map(({identifier}, index) => {
				return (
					AssignmentSectionComponent && (
						<AssignmentSectionComponent
							{...props}
							identifier={identifier}
							index={index}
							key={`section-${identifier}`}
							sectionsLength={sections?.length}
							setSections={setSections}
						/>
					)
				);
			})}
		</>
	);
};

export {SelectAssignment, options};
