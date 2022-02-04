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

import React, {useContext, useEffect, useState} from 'react';

import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import AssetCreator from './select-assignment/AssetCreator';
import ResourceActions from './select-assignment/ResourceActions';
import Role from './select-assignment/Role';
import RoleType from './select-assignment/RoleType';
import ScriptedAssignment from './select-assignment/ScriptedAssignment';
import {SelectAssignment} from './select-assignment/SelectAssignment';
import User from './select-assignment/User';
import {getAssignmentType} from './utils';

const assignmentSectionComponents = {
	assetCreator: AssetCreator,
	resourceActions: ResourceActions,
	roleId: Role,
	roleType: RoleType,
	scriptedAssignment: ScriptedAssignment,
	user: User,
};

const Assignments = (props) => {
	const {selectedItem} = useContext(DiagramBuilderContext);
	const assignments = selectedItem?.data?.assignments;

	const assignmentType = getAssignmentType(assignments);

	const [section, setSection] = useState(assignmentType || 'assetCreator');
	const [sections, setSections] = useState([{identifier: `${Date.now()}-0`}]);

	const AssignmentSectionComponent = assignmentSectionComponents[section];

	useEffect(() => {
		if (assignmentType === 'user') {
			setSections(assignments.sectionsData);
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<>
			<SelectAssignment
				section={section}
				setSection={setSection}
				setSections={setSections}
			/>

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

export default Assignments;
