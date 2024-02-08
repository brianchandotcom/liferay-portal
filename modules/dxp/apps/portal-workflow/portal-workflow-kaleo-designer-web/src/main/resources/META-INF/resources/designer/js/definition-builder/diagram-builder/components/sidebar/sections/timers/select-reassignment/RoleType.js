/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useResource} from '@clayui/data-provider';
import React, {useState} from 'react';

import {contextUrl} from '../../../../../../constants';
import {headers, userBaseURL} from '../../../../../../util/fetchUtil';
import SidebarPanel from '../../../SidebarPanel';
import BaseRoleType from '../../shared-components/BaseRoleType';

const RoleType = (props) => {
	const [networkStatus, setNetworkStatus] = useState(4);

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

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('selected-role')}>
			<BaseRoleType
				buttonName={Liferay.Language.get('new-section')}
				inputLabel={Liferay.Language.get('role-type')}
				networkStatus={networkStatus}
				{...props}
				resource={resource}
			/>
		</SidebarPanel>
	);
};

export default RoleType;
