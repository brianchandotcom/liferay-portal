/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext} from 'react';

import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import BaseRoleType from '../shared-components/BaseRoleType';

const RoleType = ({notificationIndex, updateSelectedItem: _, ...restProps}) => {
	const {setSelectedItem} = useContext(DiagramBuilderContext);

	const updateSelectedItem = (values) => {
		setSelectedItem((previousItem) => {
			previousItem.data.notifications.recipients[notificationIndex] = {
				assignmentType: ['roleType'],
				autoCreate: values.map(({autoCreate}) => autoCreate),
				roleKey: values.map(({roleKey}) => roleKey),
				roleName: values.map(({roleName}) => roleName),
				roleType: values.map(({roleType}) => roleType),
			};

			return previousItem;
		});
	};

	return (
		<BaseRoleType
			buttonName={Liferay.Language.get('new-role-type')}
			inputLabel={Liferay.Language.get('role-type')}
			notificationIndex={notificationIndex}
			updateSelectedItem={updateSelectedItem}
			{...restProps}
		/>
	);
};

export default RoleType;
