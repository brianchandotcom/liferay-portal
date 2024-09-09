/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import {BaseRoleType} from '../shared-components/BaseRoleType';

const RoleType = ({
	accountRoles,
	notificationIndex,
	resource,
	updateSelectedItem,
	...restProps
}) => {
	return (
		<BaseRoleType
			accountRoles={accountRoles}
			buttonName={Liferay.Language.get('new-role-type')}
			inputLabel={Liferay.Language.get('role-type')}
			notificationIndex={notificationIndex}
			resource={resource}
			updateSelectedItem={updateSelectedItem}
			{...restProps}
		/>
	);
};

export default RoleType;
