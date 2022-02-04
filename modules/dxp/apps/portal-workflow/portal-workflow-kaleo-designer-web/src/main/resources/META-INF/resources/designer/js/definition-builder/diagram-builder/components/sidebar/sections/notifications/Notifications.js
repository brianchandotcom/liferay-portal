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

import React, {useState} from 'react';

import NotificationsInfo from './NotificationsInfo';

const Notifications = (props) => {
	const [sections, setSections] = useState([{identifier: `${Date.now()}-0`}]);

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
