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

import {isNode} from 'react-flow-renderer';

import {retrieveRolesBy} from '../../util/fetchUtil';

const populateNotificationsData = (initialElements, setElements) => {
	for (let i = 0; i < initialElements.length; i++) {
		const element = initialElements[i];

		if (isNode(element) && element.data.notifications) {
			const recipients = element.data.notifications.recipients;

			recipients.map((recipient, index) => {
				if (recipient.assignmentType[0] === 'roleId') {
					retrieveRolesBy('roleId', recipient.roleId)
						.then((response) => response.json())
						.then((response) => {
							initialElements[i].data.notifications.recipients[
								index
							].sectionsData = {
								id: response.id,
								name: response.name,
								roleType: response.roleType,
							};

							setElements([...initialElements]);
						});
				}
			});
		}
	}
};

export default populateNotificationsData;
