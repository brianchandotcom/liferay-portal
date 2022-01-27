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

export default function parseAssignments(node) {
	const assignments = {};
	const users = [];

	node.assignments.forEach((item) => {
		for (const [key, value] of Object.entries(item)) {
			if (key === 'user') {
				assignments.assignmentType = ['user'];
			}
			else if (key === 'resource-action') {
				assignments.assignmentType = ['resourceActions'];
				assignments.resourceAction = value;
			}
			else if (key === 'role') {
				assignments.assignmentType = ['roleId'];
				assignments.roleId = parseInt(value, 10);
			}
			else if (key === 'email-address') {
				if (!assignments.assignmentType) {
					assignments.assignmentType = ['user'];
				}
				users.push(value);
			}
		}
	});

	if (users.length) {
		assignments.emailAddress = users;
	}

	return assignments;
}
