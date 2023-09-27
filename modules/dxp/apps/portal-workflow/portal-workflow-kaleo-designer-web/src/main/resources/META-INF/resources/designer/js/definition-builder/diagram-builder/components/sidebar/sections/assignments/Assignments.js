/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useResource} from '@clayui/data-provider';
import React, {useContext, useEffect, useState} from 'react';

import {DefinitionBuilderContext} from '../../../../../DefinitionBuilderContext';
import {contextUrl} from '../../../../../constants';
import {
	headers,
	retrieveAccountRoles,
	userBaseURL,
} from '../../../../../util/fetchUtil';
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
	const {accountEntryId} = useContext(DefinitionBuilderContext);
	const assignments = selectedItem?.data?.assignments;

	const assignmentType = getAssignmentType(assignments);

	const [accountRoles, setAccountRoles] = useState([]);
	const [networkStatus, setNetworkStatus] = useState(4);
	const [section, setSection] = useState(assignmentType || 'assetCreator');
	const [sections, setSections] = useState([{identifier: `${Date.now()}-0`}]);

	const AssignmentSectionComponent = assignmentSectionComponents[section];

	const {resource} = useResource({
		fetchOptions: {
			headers: {
				...headers,
				'accept': `application/json`,
				'x-csrf-token': Liferay.authToken,
			},
		},
		fetchPolicy: 'cache-first',
		link: `${window.location.origin}${contextUrl}${userBaseURL}/roles`,
		onNetworkStatusChange: setNetworkStatus,
		variables: {
			pageSize: -1,
		},
	});

	useEffect(() => {
		retrieveAccountRoles(accountEntryId)
			.then((response) => response.json())
			.then(({items}) => {
				const accountRoleItems = items.map(({displayName, name}) => {
					return {
						roleKey: name,
						roleName: displayName,
						roleType: 'Account',
					};
				});

				setAccountRoles(accountRoleItems);
			});

		if (assignmentType === 'roleType') {
			const sectionsData = [];

			for (let i = 0; i < assignments.roleType.length; i++) {
				sectionsData.push({
					autoCreate: assignments?.autoCreate?.[i],
					identifier: `${Date.now()}-${i}`,
					roleKey: assignments.roleKey[i],
					roleName: assignments.roleName?.[i],
					roleType: assignments.roleType[i],
				});
			}

			setSections(sectionsData);
		}
		else if (assignmentType === 'user') {
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

			{sections.map(({identifier, ...restProps}, index) => {
				return (
					AssignmentSectionComponent && (
						<AssignmentSectionComponent
							{...props}
							{...restProps}
							accountRoles={accountRoles}
							identifier={identifier}
							index={index}
							key={`section-${identifier}`}
							networkStatus={networkStatus}
							resource={resource}
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
