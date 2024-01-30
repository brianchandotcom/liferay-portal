/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';

import BaseRole from '../shared-components/BaseRole';

const Role = ({sectionsData, updateSelectedItem}) => {
	return (
		<BaseRole
			defaultFieldValue={{
				id: sectionsData?.id ? sectionsData?.id : '',
				name: sectionsData?.name ? sectionsData?.name : '',
			}}
			inputLabel={Liferay.Language.get('role-id')}
			selectLabel={Liferay.Language.get('role-name')}
			updateSelectedItem={updateSelectedItem}
		/>
	);
};

export default Role;
