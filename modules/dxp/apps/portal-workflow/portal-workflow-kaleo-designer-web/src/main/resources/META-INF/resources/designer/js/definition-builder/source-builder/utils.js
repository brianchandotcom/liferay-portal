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

import {removeNewLine, replaceTabSpaces} from '../util/utils';
import {DEFAULT_LANGUAGE} from './constants';

export function parseActions(node) {
	const actions = {};

	node.actions.forEach((item) => {
		actions.name = parseProperty(actions, item, 'name');
		actions.description = parseProperty(actions, item, 'description');
		actions.executionType = parseProperty(actions, item, 'execution-type');
		actions.priority = parseProperty(actions, item, 'priority');
		actions.script = parseProperty(actions, item, 'script');
	});

	return actions;
}

export function parseAssignments(node) {
	const assignments = {};
	const autoCreateValues = [];
	const roleNames = [];
	const roleTypes = [];
	const users = [];

	node.assignments.forEach((item) => {
		const itemKeys = Object.keys(item);

		if (itemKeys.includes('resource-action')) {
			assignments.assignmentType = ['resourceActions'];
			assignments.resourceAction = item['resource-action'];
		}
		else if (itemKeys.includes('role-id')) {
			assignments.assignmentType = ['roleId'];
			assignments.roleId = parseInt(item['role-id'], 10);
		}
		else if (itemKeys.includes('role-type')) {
			assignments.assignmentType = ['roleType'];
			autoCreateValues.push(item['auto-create']);
			roleNames.push(item.name);
			roleTypes.push(item['role-type']);
		}
		else if (itemKeys.includes('script')) {
			assignments.assignmentType = ['scriptedAssignment'];
			assignments.script = [item.script];
			assignments.scriptLanguage = item['script-language'];
		}
		else if (itemKeys.includes('user')) {
			assignments.assignmentType = ['user'];
		}
		else if (itemKeys.includes('email-address')) {
			assignments.assignmentType = ['user'];
			users.push(item['email-address']);
		}
	});

	if (users.length) {
		assignments.emailAddress = users;
	}

	if (assignments.assignmentType[0] === 'roleType') {
		assignments.autoCreate = autoCreateValues[0];
		assignments.roleName = roleNames[0];
		assignments.roleType = roleTypes[0];
	}

	return assignments;
}

export function parseNotifications(node) {
	const notifications = {notificationTypes: [], recipients: []};

	node.notifications.forEach((item, index) => {
		notifications.description = parseProperty(
			notifications,
			item,
			'description'
		);
		notifications.executionType = parseProperty(
			notifications,
			item,
			'execution-type'
		);
		notifications.name = parseProperty(notifications, item, 'name');

		let notificationTypes = parseProperty(
			notifications,
			item,
			'notification-type'
		);

		if (Array.isArray(notificationTypes[0])) {
			notificationTypes = notificationTypes[0];
		}

		notifications.notificationTypes[index] = notificationTypes;

		notifications.template = parseProperty(notifications, item, 'template');
		notifications.templateLanguage = parseProperty(
			notifications,
			item,
			'template-language'
		);

		if (item.assignees) {
			notifications.recipients[index] = {
				assignmentType: ['taskAssignees'],
			};
		}
		else if (item.roles) {
			notifications.recipients[index] = {
				assignmentType: ['roleId'],
				roleId: replaceTabSpaces(removeNewLine(item.roles[0])),
			};
		}
		else if (item['scripted-recipient']) {
			let script = item['scripted-recipient'][0];

			script = replaceTabSpaces(
				removeNewLine(script.substring(0, script.length - 13))
			);

			notifications.recipients[index] = {
				assignmentType: ['scriptedRecipient'],
				script: [script],
				scriptLanguage: [DEFAULT_LANGUAGE],
			};
		}
		else if (item.user) {
			notifications.recipients[index] = {
				assignmentType: ['user'],
			};
		}
	});

	return notifications;
}

function parseProperty(data, item, property) {
	let newProperty = property;

	if (property === 'execution-type') {
		newProperty = 'executionType';
	}
	else if (property === 'template-language') {
		newProperty = 'templateLanguage';
	}

	if (Array.isArray(data[newProperty])) {
		data[newProperty].push(item[property]);

		return data[newProperty];
	}

	return new Array(item[property]);
}
