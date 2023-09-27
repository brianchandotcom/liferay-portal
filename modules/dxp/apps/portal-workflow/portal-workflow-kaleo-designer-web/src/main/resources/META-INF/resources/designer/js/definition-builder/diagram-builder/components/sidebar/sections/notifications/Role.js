/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
					]?.[0]?.sectionsData?.id ||
					selectedItem.data.notifications?.recipients?.[
						notificationIndex
					]?.sectionsData?.id ||
					'',
				name:
					selectedItem.data.notifications?.recipients?.[
						notificationIndex
					]?.[0]?.sectionsData?.name ||
					selectedItem.data.notifications?.recipients?.[
						notificationIndex
					]?.sectionsData?.name ||
					'',
			}}
			inputLabel={Liferay.Language.get('role-id')}
			selectLabel={Liferay.Language.get('role-name')}
			updateSelectedItem={updateSelectedItem}
		/>
	);
};

export default Role;
