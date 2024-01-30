/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import BaseRoleType from '../shared-components/BaseRoleType';

const RoleType = ({notificationIndex, updateSelectedItem, ...restProps}) => {
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
