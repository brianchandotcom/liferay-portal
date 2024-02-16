/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useResource} from '@clayui/data-provider';
import React, {useContext, useEffect, useState} from 'react';

import {DefinitionBuilderContext} from '../../../../../../DefinitionBuilderContext';
import {contextUrl} from '../../../../../../constants';
import {
	headers,
	retrieveAccountRoles,
	userBaseURL,
} from '../../../../../../util/fetchUtil';
import SidebarPanel from '../../../SidebarPanel';
import BaseRoleType from '../../shared-components/BaseRoleType';

const RoleType = ({subSectionIdentifier, subSectionsLength, ...otherProps}) => {
	const {accountEntryId} = useContext(DefinitionBuilderContext);

	const [accountRoles, setAccountRoles] = useState([]);
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

	const {autoCreate, roleKey, roleName, roleType} = otherProps?.restProps;

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

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return (
		<SidebarPanel panelTitle={Liferay.Language.get('selected-role')}>
			<BaseRoleType
				{...otherProps}
				accountRoles={accountRoles}
				autoCreate={autoCreate}
				buttonName={Liferay.Language.get('new-section')}
				identifier={subSectionIdentifier}
				inputLabel={Liferay.Language.get('role-type')}
				networkStatus={networkStatus}
				resource={resource}
				roleKey={roleKey}
				roleName={roleName}
				roleType={roleType}
				sectionsLength={subSectionsLength}
			/>
		</SidebarPanel>
	);
};

export default RoleType;
