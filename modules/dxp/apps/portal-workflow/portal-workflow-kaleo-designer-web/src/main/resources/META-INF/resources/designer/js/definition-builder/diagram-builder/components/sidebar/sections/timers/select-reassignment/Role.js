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

import React, {useEffect} from 'react';

import {retrieveRoleById} from '../../../../../../util/fetchUtil';
import SidebarPanel from '../../../SidebarPanel';
import BaseRole from '../../shared-components/BaseRole';

const Role = ({actionData, actionSectionsIndex, setActionSections}) => {
	const updateRole = (role) => {
		setActionSections((currentSections) => {
			const updatedSections = [...currentSections];

			updatedSections[actionSectionsIndex].assignmentType = 'roleId';
			updatedSections[actionSectionsIndex].roleId = role.id;
			updatedSections[actionSectionsIndex].name = role.name;
			updatedSections[actionSectionsIndex].roleType = role.roleType;

			return updatedSections;
		});
	};

	useEffect(() => {
		if (actionData.roleId && !actionData.name) {
			retrieveRoleById(actionData.roleId)
				.then((response) => response.json())
				.then((response) => {
					setActionSections((currentSections) => {
						const updatedSections = [...currentSections];
						updatedSections[actionSectionsIndex].name =
							response.name;
						updatedSections[actionSectionsIndex].roleType =
							response.roleType;

						return updatedSections;
					});
				});
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('select-role')}>
			<BaseRole
				defaultFieldValue={{
					id: actionData.roleId || '',
					name: actionData.name || '',
				}}
				inputLabel={Liferay.Language.get('role-id')}
				selectLabel={Liferay.Language.get('role')}
				updateSelectedItem={updateRole}
			/>
		</SidebarPanel>
	);
};

export default Role;
