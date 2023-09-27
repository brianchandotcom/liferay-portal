/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';

import {DiagramBuilderContext} from '../../../../../DiagramBuilderContext';
import SidebarPanel from '../../../SidebarPanel';
import BaseRoleType from '../../shared-components/BaseRoleType';

const RoleType = (props) => {
	const {setSelectedItem} = useContext(DiagramBuilderContext);

	const updateSelectedItem = (values) => {
		setSelectedItem((previousItem) => ({
			...previousItem,
			data: {
				...previousItem.data,
				assignments: {
					assignmentType: ['roleType'],
					autoCreate: values.map(({autoCreate}) => autoCreate),
					roleKey: values.map(({roleKey}) => roleKey),
					roleName: values.map(({roleName}) => roleName),
					roleType: values.map(({roleType}) => roleType),
				},
			},
		}));
	};

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('selected-role')}>
			<BaseRoleType
				buttonName={Liferay.Language.get('new-section')}
				inputLabel={Liferay.Language.get('role-type')}
				updateSelectedItem={updateSelectedItem}
				{...props}
			/>
		</SidebarPanel>
	);
};

export default RoleType;
