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

const populateAssignmentsData = (
	initialElements,
	setElements,
	setBlockingErrors
) => {
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

				let filterTypeRetrieveUsersBy = Object.keys(
					element.data.assignments
				)[1];
				const keywordRetrieveUsersBy = Object.values(
					element.data.assignments
				)[1];

				const verifySectionsData = () => {
					if (sectionsData.length) {
						element.data.assignments.sectionsData = sectionsData;
						setElements([...initialElements]);
					}
					else {
						delete element.data.assignments.sectionsData;

						if (element.data.assignments.emailAddress) {
							delete element.data.assignments.emailAddress;
							setBlockingErrors((prev) => {
								return {
									...prev,
									errorMessage: Liferay.Language.get(
										'please-enter-a-valid-email-address'
									),
									errorType: 'assignment',
								};
							});
						}
						else if (element.data.assignments.screenName) {
							delete element.data.assignments.screenName;
							setBlockingErrors((prev) => {
								return {
									...prev,
									errorMessage: Liferay.Language.get(
										'please-enter-a-valid-screen-name'
									),
									errorType: 'assignment',
								};
							});
						}
						else if (element.data.assignments.userId) {
							delete element.data.assignments.userId;
							setBlockingErrors((prev) => {
								return {
									...prev,
									errorMessage: Liferay.Language.get(
										'please-enter-a-valid-user-id'
									),
									errorType: 'assignment',
								};
							});
						}
					}
				};

				if (filterTypeRetrieveUsersBy === 'screenName') {
					filterTypeRetrieveUsersBy = 'alternateName';
				}
				else if (filterTypeRetrieveUsersBy === 'userId') {
					filterTypeRetrieveUsersBy = filterTypeRetrieveUsersBy
						.toLocaleLowerCase()
						.replace('user', '');
				}

				retrieveUsersBy(
					filterTypeRetrieveUsersBy,
					keywordRetrieveUsersBy
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
					.then(() => verifySectionsData());
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
