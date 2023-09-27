/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-kaleo-designer-xml-definition-serializer',
	(A) => {
		const AArray = A.Array;
		// eslint-disable-next-line @liferay/aui/no-object
		const AObject = A.Object;
		const Lang = A.Lang;

		const XMLUtil = Liferay.XMLUtil;

		const isArray = Lang.isArray;
		const isObject = Lang.isObject;
		const isValue = Lang.isValue;

		const cdata = Liferay.KaleoDesignerUtils.cdata;
		const jsonStringify = Liferay.KaleoDesignerUtils.jsonStringify;

		const STR_BLANK = '';

		const STR_CHAR_CRLF = '\r\n';

		const isNotEmptyValue = function (item) {
			return isValue(item) && item !== STR_BLANK;
		};

		const serializeDefinition = function (xmlNamespace, metadata, json) {
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
				buffer.push(XMLUtil.create('name', A.Escape.html(name)));
			}

			if (description) {
				buffer.push(XMLUtil.create('description', description));
			}

			if (version) {
				buffer.push(XMLUtil.create('version', version));
			}

			json.nodes.forEach((item) => {
				const description = item.description;
				const initial = item.initial;
				const metadata = item.metadata;
				const name = item.name;
				const script = item.script;
				const scriptLanguage = item.scriptLanguage;

				const xmlNode = XMLUtil.createObj(item.xmlType);

				buffer.push(xmlNode.open, XMLUtil.create('name', name));

				if (description) {
					buffer.push(XMLUtil.create('description', description));
				}

				if (metadata) {
					buffer.push(
						XMLUtil.create(
							'metadata',
							cdata(jsonStringify(metadata))
						)
					);
				}

				appendXMLActions(buffer, item.actions, item.notifications);

				if (initial) {
					buffer.push(XMLUtil.create('initial', initial));
				}

				if (script) {
					buffer.push(XMLUtil.create('script', cdata(script)));
				}

				if (scriptLanguage) {
					buffer.push(
						XMLUtil.create('scriptLanguage', scriptLanguage)
					);
				}

				appendXMLAssignments(buffer, item.assignments);
				appendXMLTaskTimers(buffer, item.taskTimers);
				appendXMLTransitions(buffer, item.transitions);

				buffer.push(xmlNode.close);
			});

			buffer.push(xmlWorkflowDefinition.close);

			return XMLUtil.format(buffer);
		};

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
			const hasAction = isObject(actions) && !AObject.isEmpty(actions);
			const hasAssignment =
				isObject(assignments) && !AObject.isEmpty(assignments);
			const hasNotification =
				isObject(notifications) &&
				!AObject.isEmpty(notifications) &&
				!AObject.isEmpty(notifications.recipients);
			const xmlActions = XMLUtil.createObj(wrapperNodeName || 'actions');

			if (hasAction || hasNotification || hasAssignment) {
				buffer.push(xmlActions.open);
			}

			if (hasAction) {
				const description = actions.description;
				const executionType = actions.executionType;
				const language = actions.scriptLanguage;
				const script = actions.script;

				const xmlAction = XMLUtil.createObj(actionNodeName || 'action');

				actions.name.forEach((item, index) => {
					buffer.push(xmlAction.open, XMLUtil.create('name', item));

					if (isValidValue(description, index)) {
						buffer.push(
							XMLUtil.create('description', description[index])
						);
					}

					if (isValidValue(script, index)) {
						buffer.push(
							XMLUtil.create('script', cdata(script[index]))
						);
					}

					if (isValidValue(language, index)) {
						buffer.push(
							XMLUtil.create('scriptLanguage', language[index])
						);
					}

					if (isValidValue(executionType, index)) {
						buffer.push(
							XMLUtil.create(
								'executionType',
								executionType[index]
							)
						);
					}

					buffer.push(xmlAction.close);
				});
			}

			if (hasNotification) {
				appendXMLNotifications(
					buffer,
					notifications,
					notificationNodeName
				);
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
				const assignmentType = AArray(
					dataAssignments.assignmentType
				)[0];

				const xmlAssignments = XMLUtil.createObj(
					wrapperNodeName || 'assignments',
					wrapperNodeAttrs
				);

				buffer.push(xmlAssignments.open);

				if (dataAssignments.address) {
					dataAssignments.address.forEach((item) => {
						if (isNotEmptyValue(item)) {
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

					buffer.push(
						XMLUtil.create('resourceActions', xmlResourceAction)
					);
				}
				else if (assignmentType === 'roleId') {
					const xmlRoleId = XMLUtil.create(
						'roleId',
						dataAssignments.roleId
					);

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

							if (
								dataAssignments.autoCreate[index] !== null &&
								dataAssignments.autoCreate[index] !== undefined
							) {
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
					const xmlScriptedRecipient = XMLUtil.createObj(
						'scriptedRecipient'
					);

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
						isArray(dataAssignments.emailAddress) &&
						dataAssignments.emailAddress.filter(isNotEmptyValue)
							.length !== 0
					) {
						const xmlUser = XMLUtil.createObj('user');

						dataAssignments.emailAddress.forEach((item) => {
							buffer.push(xmlUser.open);

							if (isNotEmptyValue(item)) {
								buffer.push(
									XMLUtil.create('emailAddress', item)
								);
							}

							buffer.push(xmlUser.close);
						});
					}
					else if (
						isArray(dataAssignments.screenName) &&
						dataAssignments.screenName.filter(isNotEmptyValue)
							.length !== 0
					) {
						const xmlUser = XMLUtil.createObj('user');

						dataAssignments.screenName.forEach((item) => {
							buffer.push(xmlUser.open);

							if (isNotEmptyValue(item)) {
								buffer.push(XMLUtil.create('screenName', item));
							}

							buffer.push(xmlUser.close);
						});
					}
					else if (
						isArray(dataAssignments.userId) &&
						dataAssignments.userId.filter(isNotEmptyValue)
							.length !== 0
					) {
						const xmlUser = XMLUtil.createObj('user');

						dataAssignments.userId.forEach((item) => {
							buffer.push(xmlUser.open);

							if (isNotEmptyValue(item)) {
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
					!dataAssignments.address.filter(isNotEmptyValue).length
				) {
					buffer.push('<user />');
				}

				buffer.push(xmlAssignments.close);
			}
		}

		function appendXMLNotifications(buffer, notifications, nodeName) {
			if (
				notifications &&
				notifications.name &&
				!!notifications.name.length
			) {
				const description = notifications.description;
				const executionType = notifications.executionType;
				const notificationTypes = notifications.notificationTypes;
				const recipients = notifications.recipients;
				const template = notifications.template;
				const templateLanguage = notifications.templateLanguage;

				const xmlNotification = XMLUtil.createObj(
					nodeName || 'notification'
				);

				notifications.name.forEach((item, index) => {
					buffer.push(
						xmlNotification.open,
						XMLUtil.create('name', item)
					);

					if (isValidValue(description, index)) {
						buffer.push(
							XMLUtil.create(
								'description',
								cdata(description[index])
							)
						);
					}

					if (isValidValue(template, index)) {
						buffer.push(
							XMLUtil.create('template', cdata(template[index]))
						);
					}

					if (isValidValue(templateLanguage, index)) {
						buffer.push(
							XMLUtil.create(
								'templateLanguage',
								templateLanguage[index]
							)
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
						AArray.some(
							recipients[index].receptionType,
							isNotEmptyValue
						)
					) {
						recipientsAttrs.receptionType =
							recipients[index].receptionType;
					}

					if (
						isObject(recipients[index]) &&
						!AObject.isEmpty(recipients[index])
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
							XMLUtil.create(
								'executionType',
								executionType[index]
							)
						);
					}

					buffer.push(xmlNotification.close);
				});
			}
		}

		function appendXMLTaskTimers(buffer, taskTimers) {
			if (taskTimers && taskTimers.name && !!taskTimers.name.length) {
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
					buffer.push(
						xmlTaskTimer.open,
						XMLUtil.create('name', item)
					);

					if (isValidValue(description, index)) {
						buffer.push(
							XMLUtil.create('description', description[index])
						);
					}

					const xmlDelay = XMLUtil.createObj('delay');

					buffer.push(xmlDelay.open);

					buffer.push(
						XMLUtil.create('duration', delay[index].duration[0])
					);
					buffer.push(XMLUtil.create('scale', delay[index].scale[0]));

					buffer.push(xmlDelay.close);

					if (
						delay[index].duration.length > 1 &&
						delay[index].duration[1]
					) {
						const xmlRecurrence = XMLUtil.createObj('recurrence');

						buffer.push(xmlRecurrence.open);

						buffer.push(
							XMLUtil.create('duration', delay[index].duration[1])
						);
						buffer.push(
							XMLUtil.create('scale', delay[index].scale[1])
						);

						buffer.push(xmlRecurrence.close);
					}

					if (blocking && isNotEmptyValue(blocking[index])) {
						buffer.push(
							XMLUtil.create('blocking', blocking[index])
						);
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

		function appendXMLTransitions(buffer, transitions) {
			if (transitions && !!transitions.length) {
				const xmlTransition = XMLUtil.createObj('transition');
				const xmlTransitions = XMLUtil.createObj('transitions');

				buffer.push(xmlTransitions.open);

				let pickDefault = transitions.some((item) => {
					return item.connector.default === true;
				});

				pickDefault = !pickDefault;

				transitions.forEach((item, index) => {
					let defaultValue = item.connector.default;

					if (pickDefault && index === 0) {
						defaultValue = true;
					}

					buffer.push(
						xmlTransition.open,
						XMLUtil.create('name', item.connector.name),
						XMLUtil.create('target', item.target),
						XMLUtil.create('default', defaultValue),
						xmlTransition.close
					);
				});

				buffer.push(xmlTransitions.close);
			}
		}

		function isValidValue(array, index) {
			return array && array[index] !== undefined;
		}

		Liferay.KaleoDesignerXMLDefinitionSerializer = serializeDefinition;
	},
	'',
	{
		requires: [
			'escape',
			'liferay-kaleo-designer-utils',
			'liferay-kaleo-designer-xml-util',
		],
	}
);
