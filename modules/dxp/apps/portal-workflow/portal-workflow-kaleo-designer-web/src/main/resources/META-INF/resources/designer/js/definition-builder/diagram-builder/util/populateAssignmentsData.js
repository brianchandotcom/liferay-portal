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

import {retrieveRolesBy, retrieveUsersBy} from '../../util/fetchUtil';
import {getAssignmentType} from '../components/sidebar/sections/assignments/utils';

const populateAssignmentsData = (initialElements, setElements) => {
	for (let index = 0; index < initialElements.length; index++) {
		const element = initialElements[index];

		if (
			isNode(element) &&
			element.type === 'task' &&
			element.data.assignments
		) {
			const assignmentType = getAssignmentType(element.data.assignments);

			if (assignmentType === 'user') {
				const sectionsData = [];

				retrieveUsersBy(
					'emailAddress',
					element.data.assignments.emailAddress
				)
					.then((response) => response.json())
					.then(({items}) => {
						items.forEach((item, index) => {
							sectionsData.push({
								emailAddress: item.emailAddress,
								identifier: `${Date.now()}-${index}`,
								name: item.name,
								screenName: item.alternateName,
								userId: item.id,
							});
						});
					})
					.then(() => {
						initialElements[
							index
						].data.assignments.sectionsData = sectionsData;
						setElements([...initialElements]);
					});
			}
			else if (assignmentType === 'roleId') {
				retrieveRolesBy('roleId', element.data.assignments.roleId)
					.then((response) => response.json())
					.then((response) => {
						initialElements[index].data.assignments.sectionsData = {
							id: response.id,
							name: response.name,
							roleType: response.roleType,
						};
						setElements([...initialElements]);
					});
			}
		}
	}
};

export default populateAssignmentsData;
