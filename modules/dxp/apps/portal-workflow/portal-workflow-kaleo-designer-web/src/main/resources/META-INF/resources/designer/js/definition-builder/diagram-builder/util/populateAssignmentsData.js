/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	retrieveAccountRoles,
	retrieveRoleById,
	retrieveRoles,
	retrieveUsersBy,
} from '../../util/fetchUtil';
import {getAssignmentType} from '../components/sidebar/sections/assignments/utils';

const verifySectionsData = (
	initialElements,
	sectionsData,
	setBlockingErrors,
	setElements,
	taskNode
) => {
	if (sectionsData.length) {
		taskNode.data.assignments.sectionsData = sectionsData;

		const nodeIndex = initialElements.findIndex(
			(element) => element.id === taskNode.id
		);

		initialElements[nodeIndex] = taskNode;

		setElements([...initialElements]);
	}
	else {
		delete taskNode.data.assignments.sectionsData;

		if (taskNode.data.assignments.emailAddress) {
			delete taskNode.data.assignments.emailAddress;

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
		else if (taskNode.data.assignments.screenName) {
			delete taskNode.data.assignments.screenName;

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
		else if (taskNode.data.assignments.userId) {
			delete taskNode.data.assignments.userId;

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

const populateAssignmentsData = (
	accountEntryId,
	initialElements,
	setElements,
	setBlockingErrors
) => {
	const taskNodes = initialElements.filter((item) => item.type === 'task');

	for (let index = 0; index < taskNodes.length; index++) {
		const taskNode = taskNodes[index];

		const assignmentType = getAssignmentType(taskNode.data.assignments);

		if (assignmentType === 'roleId') {
			retrieveRoleById(taskNode.data.assignments.roleId)
				.then((response) => response.json())
				.then((response) => {
					taskNode.data.assignments.sectionsData = {
						id: response.id,
						name: response.name,
						roleType: response.roleType,
					};

					const nodeIndex = initialElements.findIndex(
						(element) => element.id === taskNode.id
					);

					initialElements[nodeIndex] = taskNode;

					setElements([...initialElements]);
				});
		}
		else if (assignmentType === 'roleType') {
			Promise.all([
				retrieveRoles(),
				retrieveAccountRoles(accountEntryId),
			]).then(([response1, response2]) =>
				Promise.all([response1.json(), response2.json()]).then(
					([roles, accountRoles]) => {
						const items = roles.items.concat(accountRoles.items);

						taskNode.data.assignments.roleKey.forEach((key) => {
							const role = items.find(
								(item) =>
									item.externalReferenceCode === key ||
									item.displayName === key
							);

							if (!taskNode.data.assignments.roleName) {
								taskNode.data.assignments.roleName = [];
							}

							taskNode.data.assignments.roleName.push(role?.name);
						});

						const nodeIndex = initialElements.findIndex(
							(element) => element.id === taskNode.id
						);

						initialElements[nodeIndex] = taskNode;

						setElements([...initialElements]);
					}
				)
			);
		}
		else if (assignmentType === 'user') {
			const sectionsData = [];

			let filterTypeRetrieveUsersBy = Object.keys(
				taskNode.data.assignments
			)[1];
			const keywordRetrieveUsersBy = Object.values(
				taskNode.data.assignments
			)[1];

			if (filterTypeRetrieveUsersBy === 'screenName') {
				filterTypeRetrieveUsersBy = 'alternateName';
			}
			else if (filterTypeRetrieveUsersBy === 'userId') {
				filterTypeRetrieveUsersBy = filterTypeRetrieveUsersBy
					.toLocaleLowerCase()
					.replace('user', '');
			}

			retrieveUsersBy(filterTypeRetrieveUsersBy, keywordRetrieveUsersBy)
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
				.then(() =>
					verifySectionsData(
						initialElements,
						sectionsData,
						setBlockingErrors,
						setElements,
						taskNode
					)
				);
		}
	}
};

export default populateAssignmentsData;
