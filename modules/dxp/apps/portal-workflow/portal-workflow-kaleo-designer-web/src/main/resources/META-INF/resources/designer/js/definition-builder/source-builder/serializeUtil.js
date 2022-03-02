/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 */

import {isObject, isObjectEmpty} from '../util/utils';
import {
	DEFAULT_LANGUAGE,
	STR_CDATA_CLOSE,
	STR_CDATA_OPEN,
	STR_CHAR_CRLF,
	STR_CHAR_TAB,
} from './constants';
import XMLUtil from './xmlUtil';

function cdata(value) {
	value = value.replace(STR_CDATA_OPEN, '').replace(STR_CDATA_CLOSE, '');

	return (
		STR_CHAR_CRLF + STR_CDATA_OPEN + value + STR_CDATA_CLOSE + STR_CHAR_CRLF
	);
}

function isValidValue(array, index) {
	return array && array[index] !== undefined;
}

function jsonStringify(value) {
	let jsonString = null;

	try {
		jsonString =
			STR_CHAR_CRLF +
			JSON.stringify(value, null, STR_CHAR_TAB) +
			STR_CHAR_CRLF;
	}
	catch (error) {}

	return jsonString;
}

function appendXMLActions(
	buffer,
	actions,
	notifications,
	assignments,
	wrapperNodeName,
	actionNodeName,
	notificationNodeName,
	assignmentNodeName
) {
	const hasAction = isObject(actions) && !isObjectEmpty(actions);
	const hasAssignment = isObject(assignments) && !isObjectEmpty(assignments);
	const hasNotification =
		isObject(notifications) &&
		!isObjectEmpty(notifications) &&
		!isObjectEmpty(notifications.recipients);
	const xmlActions = XMLUtil.createObj(wrapperNodeName || 'actions');

	if (hasAction || hasNotification || hasAssignment) {
		buffer.push(xmlActions.open);
	}

	if (hasAction) {
		const {description, executionType, priority, script} = actions;

		const xmlAction = XMLUtil.createObj(actionNodeName || 'action');

		actions.name.forEach((item, index) => {
			buffer.push(xmlAction.open, XMLUtil.create('name', item));

			if (isValidValue(description, index)) {
				buffer.push(XMLUtil.create('description', description[index]));
			}

			if (isValidValue(script, index)) {
				buffer.push(XMLUtil.create('script', cdata(script[index])));
			}

			buffer.push(XMLUtil.create('scriptLanguage', DEFAULT_LANGUAGE));

			if (isValidValue(priority, index)) {
				buffer.push(XMLUtil.create('priority', priority[index]));
			}

			if (isValidValue(executionType, index)) {
				buffer.push(
					XMLUtil.create('executionType', executionType[index])
				);
			}

			buffer.push(xmlAction.close);
		});
	}

	if (hasNotification) {
		appendXMLNotifications(buffer, notifications, notificationNodeName);
	}

	if (hasAssignment) {
		appendXMLAssignments(buffer, assignments, assignmentNodeName);
	}

	if (hasAction || hasNotification || hasAssignment) {
		buffer.push(xmlActions.close);
	}
}

function appendXMLAssignments(
	buffer,
	dataAssignments,
	wrapperNodeName,
	wrapperNodeAttrs
) {
	if (dataAssignments) {
		const assignmentType = Array.from(dataAssignments.assignmentType)[0];

		const xmlAssignments = XMLUtil.createObj(
			wrapperNodeName || 'assignments',
			wrapperNodeAttrs
		);

		buffer.push(xmlAssignments.open);

		if (dataAssignments.address) {
			dataAssignments.address.forEach((item) => {
				if (item !== '') {
					buffer.push(XMLUtil.create('address', item));
				}
			});
		}

		const xmlRoles = XMLUtil.createObj('roles');

		if (assignmentType === 'resourceActions') {
			const xmlResourceAction = XMLUtil.create(
				'resourceAction',
				dataAssignments.resourceAction
			);

			buffer.push(XMLUtil.create('resourceActions', xmlResourceAction));
		}
		else if (assignmentType === 'roleId') {
			const xmlRoleId = XMLUtil.create('roleId', dataAssignments.roleId);

			buffer.push(
				xmlRoles.open,
				XMLUtil.create('role', xmlRoleId),
				xmlRoles.close
			);
		}
		else if (assignmentType === 'roleType') {
			buffer.push(xmlRoles.open);

			const xmlRole = XMLUtil.createObj('role');

			dataAssignments.roleType.forEach((item, index) => {
				const roleName = dataAssignments.roleName[index];

				if (roleName) {
					buffer.push(
						xmlRole.open,
						XMLUtil.create('roleType', item),
						XMLUtil.create('name', roleName)
					);

					if (dataAssignments.autoCreate?.[index]) {
						buffer.push(
							XMLUtil.create(
								'autoCreate',
								dataAssignments.autoCreate[index]
							)
						);
					}

					buffer.push(xmlRole.close);
				}
			});

			buffer.push(xmlRoles.close);
		}
		else if (assignmentType === 'scriptedAssignment') {
			const xmlScriptedAssignment = XMLUtil.createObj(
				'scriptedAssignment'
			);

			dataAssignments.script.forEach((item, index) => {
				buffer.push(
					xmlScriptedAssignment.open,
					XMLUtil.create('script', cdata(item)),
					XMLUtil.create(
						'scriptLanguage',
						dataAssignments.scriptLanguage[index]
					),
					xmlScriptedAssignment.close
				);
			});
		}
		else if (assignmentType === 'scriptedRecipient') {
			const xmlScriptedRecipient = XMLUtil.createObj('scriptedRecipient');

			dataAssignments.script.forEach((item, index) => {
				buffer.push(
					xmlScriptedRecipient.open,
					XMLUtil.create('script', cdata(item)),
					XMLUtil.create(
						'scriptLanguage',
						dataAssignments.scriptLanguage[index]
					),
					xmlScriptedRecipient.close
				);
			});
		}
		else if (assignmentType === 'user') {
			if (
				Array.isArray(dataAssignments.emailAddress) &&
				dataAssignments.emailAddress.filter(
					(emailAddress) => emailAddress !== ''
				).length !== 0
			) {
				const xmlUser = XMLUtil.createObj('user');

				dataAssignments.emailAddress.forEach((item) => {
					buffer.push(xmlUser.open);

					if (item !== '') {
						buffer.push(XMLUtil.create('emailAddress', item));
					}

					buffer.push(xmlUser.close);
				});
			}
			else if (
				Array.isArray(dataAssignments.screenName) &&
				dataAssignments.screenName.filter(
					(screenName) => screenName !== ''
				).length !== 0
			) {
				const xmlUser = XMLUtil.createObj('user');

				dataAssignments.screenName.forEach((item) => {
					buffer.push(xmlUser.open);

					if (item !== '') {
						buffer.push(XMLUtil.create('screenName', item));
					}

					buffer.push(xmlUser.close);
				});
			}
			else if (
				Array.isArray(dataAssignments.userId) &&
				dataAssignments.userId.filter((userId) => userId !== '')
					.length !== 0
			) {
				const xmlUser = XMLUtil.createObj('user');

				dataAssignments.userId.forEach((item) => {
					buffer.push(xmlUser.open);

					if (item !== '') {
						buffer.push(XMLUtil.create('userId', item));
					}

					buffer.push(xmlUser.close);
				});
			}
			else {
				buffer.push('<user />');
			}
		}
		else if (assignmentType === 'taskAssignees') {
			buffer.push('<assignees />');
		}
		else if (
			!dataAssignments.address ||
			dataAssignments.address.filter((address) => address !== '')
				.length === 0
		) {
			buffer.push('<user />');
		}

		buffer.push(xmlAssignments.close);
	}
}

function appendXMLNotifications(buffer, notifications, nodeName) {
	if (notifications && notifications.name && notifications.name.length > 0) {
		const {
			description,
			executionType,
			notificationTypes,
			recipients,
			template,
			templateLanguage,
		} = notifications;

		const xmlNotification = XMLUtil.createObj(nodeName || 'notification');

		notifications.name.forEach((item, index) => {
			buffer.push(xmlNotification.open, XMLUtil.create('name', item));

			if (isValidValue(description, index)) {
				buffer.push(
					XMLUtil.create('description', cdata(description[index]))
				);
			}

			if (isValidValue(template, index)) {
				buffer.push(XMLUtil.create('template', cdata(template[index])));
			}

			if (isValidValue(templateLanguage, index)) {
				buffer.push(
					XMLUtil.create('templateLanguage', templateLanguage[index])
				);
			}

			if (isValidValue(notificationTypes, index)) {
				notificationTypes[index].forEach((item) => {
					buffer.push(
						XMLUtil.create(
							'notificationType',
							item.notificationType
						)
					);
				});
			}

			const recipientsAttrs = {};

			if (
				recipients[index].receptionType &&
				recipients[index].receptionType.some(
					(receptionType) => receptionType !== ''
				)
			) {
				recipientsAttrs.receptionType = recipients[index].receptionType;
			}

			if (
				isObject(recipients[index]) &&
				!isObjectEmpty(recipients[index])
			) {
				appendXMLAssignments(
					buffer,
					recipients[index],
					'recipients',
					recipientsAttrs
				);
			}

			if (executionType) {
				buffer.push(
					XMLUtil.create('executionType', executionType[index])
				);
			}

			buffer.push(xmlNotification.close);
		});
	}
}

function appendXMLTaskTimers(buffer, taskTimers) {
	if (taskTimers && taskTimers.name && taskTimers.name.length > 0) {
		const xmlTaskTimers = XMLUtil.createObj('task-timers');

		buffer.push(xmlTaskTimers.open);

		const blocking = taskTimers.blocking;
		const delay = taskTimers.delay;
		const description = taskTimers.description;
		const reassignments = taskTimers.reassignments;
		const timerActions = taskTimers.timerActions;
		const timerNotifications = taskTimers.timerNotifications;

		const xmlTaskTimer = XMLUtil.createObj('task-timer');

		taskTimers.name.forEach((item, index) => {
			buffer.push(xmlTaskTimer.open, XMLUtil.create('name', item));

			if (isValidValue(description, index)) {
				buffer.push(XMLUtil.create('description', description[index]));
			}

			const xmlDelay = XMLUtil.createObj('delay');

			buffer.push(xmlDelay.open);

			buffer.push(XMLUtil.create('duration', delay[index].duration[0]));
			buffer.push(XMLUtil.create('scale', delay[index].scale[0]));

			buffer.push(xmlDelay.close);

			if (delay[index].duration.length > 1 && delay[index].duration[1]) {
				const xmlRecurrence = XMLUtil.createObj('recurrence');

				buffer.push(xmlRecurrence.open);

				buffer.push(
					XMLUtil.create('duration', delay[index].duration[1])
				);
				buffer.push(XMLUtil.create('scale', delay[index].scale[1]));

				buffer.push(xmlRecurrence.close);
			}

			if (blocking && blocking[index] !== '') {
				buffer.push(XMLUtil.create('blocking', blocking[index]));
			}
			else {
				buffer.push(XMLUtil.create('blocking', String(false)));
			}

			appendXMLActions(
				buffer,
				timerActions[index],
				timerNotifications[index],
				reassignments[index],
				'timer-actions',
				'timer-action',
				'timer-notification',
				'reassignments'
			);

			buffer.push(xmlTaskTimer.close);
		});

		buffer.push(xmlTaskTimers.close);
	}
}

function appendXMLTransitions(buffer, transitions, publishing) {
	if (transitions.length) {
		const xmlTransitions = XMLUtil.createObj('transitions');

		buffer.push(xmlTransitions.open);

		const xmlTransition = XMLUtil.createObj('transition');

		transitions.forEach((item) => {
			buffer.push(xmlTransition.open);

			const xmlLabels = XMLUtil.createObj('labels');

			buffer.push(xmlLabels.open);

			Object.entries(item.data.label).map(([key, value]) => {
				const xmlLabel = XMLUtil.createObj('label', {
					'language-id': `${key}`,
				});
				buffer.push(xmlLabel.open, value);

				buffer.push(xmlLabel.close);
			});

			buffer.push(xmlLabels.close);

			const tagTransitionNameId = publishing ? 'name' : 'id';

			buffer.push(XMLUtil.create(`${tagTransitionNameId}`, item.id));

			buffer.push(
				XMLUtil.create('target', item.target),
				XMLUtil.create('default', `${item.data.defaultEdge}`),
				xmlTransition.close
			);
		});

		buffer.push(xmlTransitions.close);
	}
}

function serializeDefinition(
	xmlNamespace,
	metadata,
	nodes,
	transitions,
	publishing
) {
	const description = metadata.description;
	const name = metadata.name;
	const version = parseInt(metadata.version, 10);

	const buffer = [];

	const xmlWorkflowDefinition = XMLUtil.createObj(
		'workflow-definition',
		xmlNamespace
	);

	buffer.push(
		'<?xml version="1.0"?>',
		STR_CHAR_CRLF,
		xmlWorkflowDefinition.open
	);

	if (name) {
		buffer.push(XMLUtil.create('name', name));
	}

	if (description) {
		buffer.push(XMLUtil.create('description', description));
	}

	if (version) {
		buffer.push(XMLUtil.create('version', version));
	}

	nodes?.forEach((item) => {
		const description = item.data?.description;
		const id = item.id;
		const initial = item.type === 'start';
		const script = item.data?.script;
		let xmlType = item.type;

		if (xmlType === 'start' || xmlType === 'end') {
			xmlType = 'state';
		}

		const xmlNode = XMLUtil.createObj(xmlType);

		const tagNodeNameId = publishing ? 'name' : 'id';

		buffer.push(xmlNode.open, XMLUtil.create(`${tagNodeNameId}`, id));

		if (description) {
			buffer.push(XMLUtil.create('description', description));
		}

		const metadata = {xy: [item.position.x, item.position.y]};

		if (item.type === 'end') {
			metadata.terminal = true;
		}

		buffer.push(XMLUtil.create('metadata', cdata(jsonStringify(metadata))));

		appendXMLActions(buffer, item.data.actions, item.data.notifications);

		appendXMLTaskTimers(buffer, item.data.taskTimers);

		if (initial) {
			buffer.push(XMLUtil.create('initial', initial));
		}

		const xmlLabels = XMLUtil.createObj('labels');

		buffer.push(xmlLabels.open);

		Object.entries(item.data.label).map(([key, value]) => {
			const xmlLabel = XMLUtil.createObj('label', {
				'language-id': `${key}`,
			});
			buffer.push(xmlLabel.open, value);

			buffer.push(xmlLabel.close);
		});

		buffer.push(xmlLabels.close);

		if (script) {
			buffer.push(XMLUtil.create('script', cdata(script)));
		}

		if (xmlType === 'condition') {
			buffer.push(XMLUtil.create('scriptLanguage', DEFAULT_LANGUAGE));
		}

		appendXMLAssignments(buffer, item.data.assignments);

		appendXMLTaskTimers(buffer, item.data.taskTimers);

		const nodeTransitions = transitions.filter(
			(transition) => transition.source === id
		);

		appendXMLTransitions(buffer, nodeTransitions, publishing);

		buffer.push(xmlNode.close);
	});

	buffer.push(xmlWorkflowDefinition.close);

	return XMLUtil.format(buffer);
}

export {serializeDefinition};
