AUI.add(
	'liferay-kaleo-designer-xml-definition-serializer',
	function(A) {
		var AArray = A.Array;
		var AObject = A.Object;
		var Lang = A.Lang;

		var XMLFormatter = new Liferay.XMLFormatter();
		var XMLUtil = Liferay.XMLUtil;

		var isArray = Lang.isArray;
		var isObject = Lang.isObject;
		var isValue = Lang.isValue;

		var cdata = Liferay.KaleoDesignerUtils.cdata;
		var jsonStringify = Liferay.KaleoDesignerUtils.jsonStringify;

		var STR_BLANK = '';

		var isNotEmptyValue = function(item) {
			return isValue(item) && item !== STR_BLANK;
		};

		var serializeDefinition = function(xmlNamespace, metadata, json) {
			var xml = toXML(xmlNamespace, metadata, json);

			xml = XMLUtil.format(xml);

			xml = XMLFormatter.format(xml);

			xml = xml.trim();

			return xml;
		};

		var toXML = function(xmlNamespace, metadata, json) {
			var description = metadata.description;
			var name = metadata.name;
			var version = metadata.version;

			var buffer = [];

			var xmlWorkflowDefinition = XMLUtil.createObj(
				'workflow-definition',
				xmlNamespace
			);

			buffer.push('<?xml version="1.0"?>', xmlWorkflowDefinition.open);

			if (name) {
				buffer.push(XMLUtil.create('name', A.Escape.html(name)));
			}

			if (description) {
				buffer.push(XMLUtil.create('description', description));
			}

			if (version) {
				buffer.push(XMLUtil.create('version', version));
			}

			json.nodes.forEach(function(item, index, collection) {
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

			return buffer.join(STR_BLANK);
		};

		var appendXMLActions = function(
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

				actions.name.forEach(function(item, index, collection) {
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
		};

		var appendXMLAssignments = function(
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
					dataAssignments.address.forEach(function(
						item,
						index,
						collection
					) {
						if (isValue(item)) {
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
				} else if (assignmentType === 'roleId') {
					var xmlRoleId = XMLUtil.create(
						'roleId',
						dataAssignments.roleId
					);

					buffer.push(
						xmlRoles.open,
						XMLUtil.create('role', xmlRoleId),
						xmlRoles.close
					);
				} else if (assignmentType === 'roleType') {
					buffer.push(xmlRoles.open);

					var xmlRole = XMLUtil.createObj('role');

					dataAssignments.roleType.forEach(function(
						item,
						index,
						collection
					) {
						var roleName = dataAssignments.roleName[index];

						if (roleName) {
							buffer.push(
								xmlRole.open,
								XMLUtil.create('roleType', item),
								XMLUtil.create('name', roleName),
								XMLUtil.create(
									'autoCreate',
									dataAssignments.autoCreate[index] ||
										String(false)
								),
								xmlRole.close
							);
						}
					});

					buffer.push(xmlRoles.close);
				} else if (assignmentType === 'scriptedAssignment') {
					var xmlScriptedAssignment = XMLUtil.createObj(
						'scriptedAssignment'
					);

					dataAssignments.script.forEach(function(
						item,
						index,
						collection
					) {
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
				} else if (assignmentType === 'scriptedRecipient') {
					var xmlScriptedRecipient = XMLUtil.createObj(
						'scriptedRecipient'
					);

					dataAssignments.script.forEach(function(
						item,
						index,
						collection
					) {
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
				} else if (assignmentType === 'user') {
					if (
						isArray(dataAssignments.userId) &&
						dataAssignments.userId.filter(isValue).length !== 0
					) {
						var xmlUser = XMLUtil.createObj('user');

						dataAssignments.userId.forEach(function(
							item,
							index,
							collection
						) {
							buffer.push(xmlUser.open);

							var userContent = null;

							if (isValue(item)) {
								userContent = XMLUtil.create('userId', item);
							} else if (
								isValue(dataAssignments.emailAddress[index])
							) {
								userContent = XMLUtil.create(
									'emailAddress',
									dataAssignments.emailAddress[index]
								);
							} else if (
								isValue(dataAssignments.screenName[index])
							) {
								userContent = XMLUtil.create(
									'screenName',
									dataAssignments.screenName[index]
								);
							}

							if (userContent) {
								buffer.push(userContent);
							}

							buffer.push(xmlUser.close);
						});
					} else {
						buffer.push('<user/>');
					}
				} else if (assignmentType === 'taskAssignees') {
					buffer.push('<assignees/>');
				} else if (
					!dataAssignments.address ||
					dataAssignments.address.filter(isValue).length === 0
				) {
					buffer.push('<user/>');
				}

				buffer.push(xmlAssignments.close);
			}
		};

		var appendXMLNotifications = function(buffer, notifications, nodeName) {
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

				notifications.name.forEach(function(item, index, collection) {
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
						notificationTypes[index].forEach(function(item) {
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

					appendXMLAssignments(
						buffer,
						recipients[index],
						'recipients',
						recipientsAttrs
					);

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
		};

		var appendXMLTaskTimers = function(buffer, taskTimers) {
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

				taskTimers.name.forEach(function(item, index, collection) {
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

					for (var i = 1; i < delay[index].duration.length; i++) {
						var xmlRecurrence = XMLUtil.createObj('recurrence');

						buffer.push(xmlRecurrence.open);

						buffer.push(
							XMLUtil.create('duration', delay[index].duration[i])
						);
						buffer.push(
							XMLUtil.create('scale', delay[index].scale[i])
						);

						buffer.push(xmlRecurrence.close);
					}

					if (blocking && isValue(blocking[index])) {
						buffer.push(
							XMLUtil.create('blocking', blocking[index])
						);
					} else {
						buffer.push(XMLUtil.create('blocking', String(false)));
					}

					if (
						reassignments &&
						reassignments[index] &&
						reassignments[index].assignmentType[0] === STR_BLANK
					) {
						reassignments[index] = null;
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
		};

		var appendXMLTransitions = function(buffer, transitions) {
			if (transitions && transitions.length > 0) {
				var xmlTransition = XMLUtil.createObj('transition');
				var xmlTransitions = XMLUtil.createObj('transitions');

				buffer.push(xmlTransitions.open);

				var pickDefault = transitions.some(function(
					item,
					index,
					collection
				) {
					return item.connector.default === true;
				});

				pickDefault = !pickDefault;

				transitions.forEach(function(item, index, collection) {
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
		};

		var isValidValue = function(array, index) {
			return array && array[index] !== undefined;
		};

		Liferay.KaleoDesignerXMLDefinitionSerializer = serializeDefinition;
	},
	'',
	{
		requires: [
			'escape',
			'liferay-kaleo-designer-utils',
			'liferay-kaleo-designer-xml-util',
			'liferay-xml-formatter'
		]
	}
);
