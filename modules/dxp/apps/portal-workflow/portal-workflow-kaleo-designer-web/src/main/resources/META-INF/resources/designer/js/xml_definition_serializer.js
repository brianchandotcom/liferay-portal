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

AUI.add(
	'liferay-kaleo-designer-xml-definition-serializer',
	(A) => {
		var AArray = A.Array;
		var AObject = A.Object;
		var Lang = A.Lang;

		var XMLUtil = Liferay.XMLUtil;

		var isArray = Lang.isArray;
		var isObject = Lang.isObject;
		var isValue = Lang.isValue;

		var cdata = Liferay.KaleoDesignerUtils.cdata;
		var jsonStringify = Liferay.KaleoDesignerUtils.jsonStringify;

		var STR_BLANK = '';

		var STR_CHAR_CRLF = '\r\n';

		var isNotEmptyValue = function (item) {
			return isValue(item) && item !== STR_BLANK;
		};

		var serializeDefinition = function (xmlNamespace, metadata, json) {
			var description = metadata.description;
			var name = metadata.name;
			var version = metadata.version;

			var buffer = [];

			var xmlWorkflowDefinition = XMLUtil.createObj(
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
				var description = item.description;
				var initial = item.initial;
				var metadata = item.metadata;
				var name = item.name;
				var script = item.script;
				var scriptLanguage = item.scriptLanguage;

				var xmlNode = XMLUtil.createObj(item.xmlType);

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
			var hasAction = isObject(actions) && !AObject.isEmpty(actions);
			var hasAssignment =
				isObject(assignments) && !AObject.isEmpty(assignments);
			var hasNotification =
				isObject(notifications) &&
				!AObject.isEmpty(notifications) &&
				!AObject.isEmpty(notifications.recipients);
			var xmlActions = XMLUtil.createObj(wrapperNodeName || 'actions');

			if (hasAction || hasNotification || hasAssignment) {
				buffer.push(xmlActions.open);
			}

			if (hasAction) {
				var description = actions.description;
				var executionType = actions.executionType;
				var language = actions.scriptLanguage;
				var script = actions.script;

				var xmlAction = XMLUtil.createObj(actionNodeName || 'action');

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
				var assignmentType = AArray(dataAssignments.assignmentType)[0];

				var xmlAssignments = XMLUtil.createObj(
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

				var xmlRoles = XMLUtil.createObj('roles');

				if (assignmentType === 'resourceActions') {
					var xmlResourceAction = XMLUtil.create(
						'resourceAction',
						dataAssignments.resourceAction
					);

					buffer.push(
						XMLUtil.create('resourceActions', xmlResourceAction)
					);
				}
				else if (assignmentType === 'roleId') {
					var xmlRoleId = XMLUtil.create(
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

					var xmlRole = XMLUtil.createObj('role');

					dataAssignments.roleType.forEach((item, index) => {
						var roleName = dataAssignments.roleName[index];

						if (roleName) {
							buffer.push(
								xmlRole.open,
								XMLUtil.create('roleType', item),
								XMLUtil.create('name', roleName)
							);

							if (dataAssignments.autoCreate[index] != null) {
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
					var xmlScriptedAssignment = XMLUtil.createObj(
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
					var xmlScriptedRecipient = XMLUtil.createObj(
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
					dataAssignments.address.filter(isNotEmptyValue).length === 0
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
				notifications.name.length > 0
			) {
				var description = notifications.description;
				var executionType = notifications.executionType;
				var notificationTypes = notifications.notificationTypes;
				var recipients = notifications.recipients;
				var template = notifications.template;
				var templateLanguage = notifications.templateLanguage;

				var xmlNotification = XMLUtil.createObj(
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

					var recipientsAttrs = {};

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
			if (taskTimers && taskTimers.name && taskTimers.name.length > 0) {
				var xmlTaskTimers = XMLUtil.createObj('task-timers');

				buffer.push(xmlTaskTimers.open);

				var blocking = taskTimers.blocking;
				var delay = taskTimers.delay;
				var description = taskTimers.description;
				var reassignments = taskTimers.reassignments;
				var timerActions = taskTimers.timerActions;
				var timerNotifications = taskTimers.timerNotifications;

				var xmlTaskTimer = XMLUtil.createObj('task-timer');

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

					var xmlDelay = XMLUtil.createObj('delay');

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
						var xmlRecurrence = XMLUtil.createObj('recurrence');

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
			if (transitions && transitions.length > 0) {
				var xmlTransition = XMLUtil.createObj('transition');
				var xmlTransitions = XMLUtil.createObj('transitions');

				buffer.push(xmlTransitions.open);

				var pickDefault = transitions.some((item) => {
					return item.connector.default === true;
				});

				pickDefault = !pickDefault;

				transitions.forEach((item, index) => {
					var defaultValue = item.connector.default;

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
