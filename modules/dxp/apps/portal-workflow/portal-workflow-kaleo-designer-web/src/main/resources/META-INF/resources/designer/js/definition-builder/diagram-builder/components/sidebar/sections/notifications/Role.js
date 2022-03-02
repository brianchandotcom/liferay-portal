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

import React, {useContext} from 'react';

import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import BaseRole from '../shared-components/BaseRole';

const Role = ({notificationIndex}) => {
	const {selectedItem, setSelectedItem} = useContext(DiagramBuilderContext);

	const updateSelectedItem = (role) => {
		setSelectedItem((previousItem) => {
			previousItem.data.notifications.recipients[notificationIndex] = {
				assignmentType: ['roleId'],
				roleId: role.id,
				sectionsData: {
					id: role.id,
					name: role.name,
					roleType: role.roleType,
				},
			};

			return previousItem;
		});
	};

	return (
		<BaseRole
			defaultFieldValue={{
				id:
					selectedItem.data.notifications?.recipients?.[
						notificationIndex
					]?.sectionsData?.id || '',
				name:
					selectedItem.data.notifications?.recipients?.[
						notificationIndex
					]?.sectionsData?.name || '',
			}}
			inputLabel={Liferay.Language.get('role-id')}
			selectLabel={Liferay.Language.get('role-name')}
			updateSelectedItem={updateSelectedItem}
		/>
	);
};

export default Role;
