/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useResource} from '@clayui/data-provider';
import React, {useContext, useEffect, useState} from 'react';

import {DefinitionBuilderContext} from '../../../../../DefinitionBuilderContext';
import {contextUrl} from '../../../../../constants';
import {
	headers,
	retrieveAccountRoles,
	userBaseURL,
} from '../../../../../util/fetchUtil';
import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import NotificationsInfo from './NotificationsInfo';

const Notifications = (props) => {
	const {accountEntryId} = useContext(DefinitionBuilderContext);
	const {selectedItem} = useContext(DiagramBuilderContext);

	const {notifications} = selectedItem?.data;

	const [accountRoles, setAccountRoles] = useState([]);
	const [networkStatus, setNetworkStatus] = useState(4);
	const [sections, setSections] = useState([{identifier: `${Date.now()}-0`}]);

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

		const sectionsData = [];

		if (notifications) {
			for (let i = 0; i < notifications.name.length; i++) {
				let notificationTypes = notifications.notificationTypes[i];

				if (notificationTypes === undefined) {
					notificationTypes = '';
				}

				sectionsData.push({
					description: notifications.description[i],
					executionType: notifications.executionType[i],
					identifier: `${Date.now()}-${i}`,
					name: notifications.name[i],
					notificationTypes,
					recipients: notifications.recipients[i],
					template: notifications.template[i],
					templateLanguage: notifications.templateLanguage[i],
				});
			}

			setSections(sectionsData);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, []);

	return sections.map(({identifier}, index) => (
		<NotificationsInfo
			{...props}
			accountRoles={accountRoles}
			identifier={identifier}
			index={index}
			key={`section-${identifier}`}
			networkStatus={networkStatus}
			resource={resource}
			sectionsLength={sections?.length}
			setSections={setSections}
		/>
	));
};

export default Notifications;
