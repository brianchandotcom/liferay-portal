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

import React, {useContext, useEffect, useState} from 'react';

import {DiagramBuilderContext} from '../../../../DiagramBuilderContext';
import NotificationsInfo from './NotificationsInfo';

const Notifications = (props) => {
	const {selectedItem} = useContext(DiagramBuilderContext);

	const {notifications} = selectedItem?.data;
	const [sections, setSections] = useState([{identifier: `${Date.now()}-0`}]);

	useEffect(() => {
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
			identifier={identifier}
			index={index}
			key={`section-${identifier}`}
			sectionsLength={sections?.length}
			setSections={setSections}
		/>
	));
};

export default Notifications;
