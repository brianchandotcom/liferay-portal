AUI.add(
	'liferay-portlet-kaleo-designer',
	function(A) {
		var AArray = A.Array;
		var AObject = A.Object;
		var DiagramBuilder = A.DiagramBuilder;
		var Lang = A.Lang;
		var XMLFormatter = new Liferay.XMLFormatter();
		var XMLUtil = Liferay.XMLUtil;

		var KaleoDesignerEditors = Liferay.KaleoDesignerEditors;
		var KaleoDesignerStrings = Liferay.KaleoDesignerStrings;

		var isNull = Lang.isNull;
		var isNumber = Lang.isNumber;
		var isObject = Lang.isObject;
		var isString = Lang.isString;
		var isValue = Lang.isValue;

		var cdata = Liferay.KaleoDesignerUtils.cdata;
		var jsonParse = Liferay.KaleoDesignerUtils.jsonParse;
		var jsonStringify = Liferay.KaleoDesignerUtils.jsonStringify;
		var uniformRandomInt = Liferay.KaleoDesignerUtils.uniformRandomInt;

		var COL_TYPES_ASSIGNMENT = ['address', 'resourceActions', 'roleId', 'roleType', 'scriptedAssignment', 'scriptedRecipient', 'taskAssignees', 'user', 'userId'];

		var COL_TYPES_FIELD = ['condition', 'fork', 'join', 'join-xor', 'state', 'task'];

		var DEFAULT_LANGUAGE = 'groovy';

		var SCHEMA_FIELDS_PATH = ['results', '0', 'name'];

		var SCHEMA_MAP_RESULTS = {
			resultFields: [
				{
					key: 'name',
					locator: 'Name[@language-id="' + themeDisplay.getLanguageId() + '"]'
				}
			],
			resultListLocator: 'root'
		};

		var STR_BLANK = '';

		var PropertyListFormatter = Liferay.KaleoDesignerUtils.PropertyListFormatter;

		var KaleoDesigner = A.Component.create(
			{
				ATTRS: {
					aceEditorConfig: {
						setter: '_setAceEditor',
						validator: isObject,
						value: null
					},

					availableFields: {
						validator: isObject,
						valueFn: function() {
							return KaleoDesigner.AVAILABLE_FIELDS.DEFAULT;
						}
					},

					availablePropertyModels: {
						validator: isObject,
						valueFn: function() {
							return KaleoDesigner.AVAILABLE_PROPERTY_MODELS.DEFAULT;
						}
					},

					contentTabView: {
						setter: '_setContentTabView',
						validator: isObject,
						value: null,
						writeOnce: true
					},

					data: {
						validator: isObject,
						value: {}
					},

					definition: {
						lazyAdd: false,
						setter: '_setDefinition'
					},

					definitionDescription: {
						validator: isString,
						value: STR_BLANK
					},

					definitionName: {
						validator: isString,
						valueFn: function() {
							return KaleoDesignerStrings.definition + uniformRandomInt(0, 100);
						}
					},

					definitionVersion: {
						validator: isNumber,
						value: 1
					},

					portletNamespace: {
						value: STR_BLANK
					},

					portletResourceNamespace: {
						value: STR_BLANK
					},

					xmlNamespace: {
						value: {
							'xmlns': 'urn:liferay.com:liferay-workflow_7.0.0',
							'xmlns:xsi': 'http://www.w3.org/2001/XMLSchema-instance',
							'xsi:schemaLocation': 'urn:liferay.com:liferay-workflow_7.0.0 http://www.liferay.com/dtd/liferay-workflow-definition_7_0_0.xsd'
						}
					}
				},

				EXTENDS: DiagramBuilder,

				NAME: 'diagram-builder',

				UI_ATTRS: ['definition'],

				prototype: {
					initializer: function() {
						var instance = this;

						var metadata = instance.getDefinitionMetadata();

						if (metadata) {
							instance.setAttrs(metadata);
						}

						instance.after('render', instance._afterRenderKaleoDesigner);

						instance.after(instance._renderContentTabs, instance, '_renderTabs');

						instance.after(instance._afterRenderSettings, instance, '_renderSettings');
					},

					connectDefinitionFields: function() {
						var instance = this;

						var connectors = [];

						instance.forEachDefinitionField(
							function(tagName, fieldData) {
								fieldData.results.forEach(
									function(item1, index1, collection1) {
										item1.transitions.forEach(
											function(item2, index2, collection2) {
												connectors.push(
													{
														connector: {
															'default': item2.default,
															name: item2.name
														},
														source: item1.name,
														target: item2.target
													}
												);
											}
										);
									}
								);
							}
						);

						instance.connectAll(connectors);
					},

					editNode: function(diagramNode) {
						var instance = this;

						if (diagramNode.getProperties()) {
							KaleoDesigner.superclass.editNode.apply(this, arguments);
						}
						else {
							instance.closeEditProperties();
						}

						instance._fixTableWidth();
					},

					forEachDefinitionField: function(fn) {
						var instance = this;

						COL_TYPES_FIELD.forEach(
							function(item, index, collection) {
								var fieldData = instance._translateXMLToJSON(item);

								if (fn && !fieldData.error) {
									fn.call(instance, item, fieldData);
								}
							}
						);
					},

					getDefinitionMetadata: function() {
						var instance = this;

						var output = A.DataSchema.XML.apply(
							{
								metaFields: {
									definitionDescription: '//workflow-definition/description',
									definitionName: '//workflow-definition/name',
									definitionVersion: '//workflow-definition/version'
								}
							},
							instance.definitionDoc
						);

						return output.meta;
					},

					getLocalizedName: function(name) {
						var doc = A.DataType.XML.parse(name);

						var schema = A.DataSchema.XML.apply(SCHEMA_MAP_RESULTS, doc);

						return A.Object.getValue(schema, SCHEMA_FIELDS_PATH);
					},

					sanitizeDefinitionXML: function(val) {
						var instance = this;

						val = decodeURIComponent(val);

						val = val.replace(/\s*(<!\[CDATA\[)/g, '$1');
						val = val.replace(/(\]\]>)\s*/g, '$1');

						instance._updateXMLNamespace(val);

						return val.replace(/(<workflow-definition)[^>]*(>)/, '$1$2');
					},

					showEditor: function() {
						var instance = this;

						var editor = instance.editor;

						if (!editor) {
							editor = new A.AceEditor(instance.get('aceEditorConfig')).render();

							instance.editor = editor;
						}

						var content = instance.get('definition');

						if (!content || instance.validateDefinition(content)) {
							content = instance.toFormattedXML();
						}

						editor.set('value', content);
					},

					toFormattedXML: function() {
						var instance = this;

						var xml = instance.toXML();

						xml = XMLUtil.format(xml);

						xml = XMLFormatter.format(xml);

						xml = xml.trim();

						return xml;
					},

					toXML: function() {
						var instance = this;

						var json = instance.toJSON();

						var definitionDescription = instance.get('definitionDescription');
						var definitionName = A.Escape.html(instance.get('definitionName'));
						var definitionVersion = instance.get('definitionVersion');
						var xmlNamespace = instance.get('xmlNamespace');

						var buffer = [];

						var xmlWorkflowDefinition = XMLUtil.createObj('workflow-definition', xmlNamespace);

						buffer.push('<?xml version="1.0"?>', xmlWorkflowDefinition.open);

						buffer.push(
							XMLUtil.create('name', definitionName),
							XMLUtil.create('description', definitionDescription),
							XMLUtil.create('version', definitionVersion)
						);

						json.nodes.forEach(
							function(item, index, collection) {
								var description = item.description;
								var initial = item.initial;
								var metadata = item.metadata;
								var name = item.name;
								var script = item.script;
								var scriptLanguage = item.scriptLanguage;

								var xmlNode = XMLUtil.createObj(item.xmlType);

								buffer.push(xmlNode.open, XMLUtil.create('name', name));

								if (description) {
									buffer.push(XMLUtil.create('description', cdata(jsonStringify(description))));
								}

								if (metadata) {
									buffer.push(XMLUtil.create('metadata', cdata(jsonStringify(metadata))));
								}

								instance._appendXMLActions(buffer, item.actions, item.notifications);

								if (initial) {
									buffer.push(XMLUtil.create('initial', initial));
								}

								if (script) {
									buffer.push(XMLUtil.create('script', cdata(script)));
								}

								if (scriptLanguage) {
									buffer.push(XMLUtil.create('scriptLanguage', scriptLanguage));
								}

								instance._appendXMLAssignments(buffer, item.assignments);
								instance._appendXMLTransitions(buffer, item.transitions);
								instance._appendXMLTaskTimers(buffer, item.taskTimers);

								buffer.push(xmlNode.close);
							}
						);

						buffer.push(xmlWorkflowDefinition.close);

						return buffer.join(STR_BLANK);
					},

					validateDefinition: function(definition) {
						var instance = this;

						var doc = A.DataType.XML.parse(definition);

						var valid = true;

						if (isNull(doc) || isNull(doc.documentElement) || A.one(doc).one('parsererror')) {
							valid = false;
						}

						return valid;
					},

					_afterRenderKaleoDesigner: function() {
						var instance = this;

						instance.connectDefinitionFields();

						instance.canvasRegion = instance.canvas.get('region');
					},

					_afterRenderSettings: function() {
						var instance = this;

						var dataTable = instance.propertyList;

						dataTable.after(A.bind(instance._afterRenderSettingsTableBody, instance), dataTable, '_onUITriggerSort');
					},

					_afterRenderSettingsTableBody: function() {
						var instance = this;

						instance._fixTableWidth();
					},

					_afterSelectionChangeKaleoDesigner: function(event) {
						var instance = this;
						var tabContentNode = event.newVal.get('boundingBox');

						if (instance.get('rendered')) {
							instance.stopEditing();

							if (tabContentNode === instance.sourceNode) {
								instance.showEditor();
							}
						}
					},

					_appendXMLActions: function(buffer, actions, notifications, assignments, wrapperNodeName, actionNodeName, notificationNodeName, assignmentNodeName) {
						var instance = this;

						var hasAction = isObject(actions) && !AObject.isEmpty(actions);
						var hasAssignment = isObject(assignments) && !AObject.isEmpty(assignments);
						var hasNotification = isObject(notifications) && !AObject.isEmpty(notifications);
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

							actions.name.forEach(
								function(item, index, collection) {
									buffer.push(xmlAction.open, XMLUtil.create('name', item));

									if (description) {
										buffer.push(XMLUtil.create('description', description[index]));
									}

									if (script) {
										buffer.push(XMLUtil.create('script', cdata(script[index])));
									}

									if (language) {
										buffer.push(XMLUtil.create('scriptLanguage', language[index]));
									}

									if (executionType) {
										buffer.push(XMLUtil.create('executionType', executionType[index]));
									}

									buffer.push(xmlAction.close);
								}
							);
						}

						if (hasNotification) {
							instance._appendXMLNotifications(buffer, notifications, notificationNodeName);
						}

						if (hasAssignment) {
							instance._appendXMLAssignments(buffer, assignments, assignmentNodeName);
						}

						if (hasAction || hasNotification || hasAssignment) {
							buffer.push(xmlActions.close);
						}
					},

					_appendXMLAssignments: function(buffer, dataAssignments, wrapperNodeName) {
						var instance = this;

						if (dataAssignments) {
							var assignmentType = AArray(dataAssignments.assignmentType)[0];

							var xmlAssignments = XMLUtil.createObj(wrapperNodeName || 'assignments');

							buffer.push(xmlAssignments.open);

							if (dataAssignments.address) {
								dataAssignments.address.forEach(
									function(item, index, collection) {
										if (isValue(item)) {
											buffer.push(XMLUtil.create('address', item));
										}
									}
								);
							}

							var xmlRoles = XMLUtil.createObj('roles');

							if (assignmentType === 'resourceActions') {
								var xmlResourceAction = XMLUtil.create('resourceAction', dataAssignments.resourceAction);

								buffer.push(XMLUtil.create('resourceActions', xmlResourceAction));
							}
							else if (assignmentType === 'roleId') {
								var xmlRoleId = XMLUtil.create('roleId', dataAssignments.roleId);

								buffer.push(xmlRoles.open, XMLUtil.create('role', xmlRoleId), xmlRoles.close);
							}
							else if (assignmentType === 'roleType') {
								buffer.push(xmlRoles.open);

								var xmlRole = XMLUtil.createObj('role');

								dataAssignments.roleType.forEach(
									function(item, index, collection) {
										var roleName = dataAssignments.roleName[index];

										if (roleName) {
											buffer.push(
												xmlRole.open,
												XMLUtil.create('roleType', item),
												XMLUtil.create('name', roleName),
												XMLUtil.create('autoCreate', dataAssignments.autoCreate[index] || String(false)),
												xmlRole.close
											);
										}
									}
								);

								buffer.push(xmlRoles.close);
							}
							else if (assignmentType === 'scriptedAssignment') {
								var xmlScriptedAssignment = XMLUtil.createObj('scriptedAssignment');

								dataAssignments.script.forEach(
									function(item, index, collection) {
										buffer.push(
											xmlScriptedAssignment.open,
											XMLUtil.create('script', cdata(item)),
											XMLUtil.create('scriptLanguage', dataAssignments.scriptLanguage[index]),
											xmlScriptedAssignment.close
										);
									}
								);
							}
							else if (assignmentType === 'scriptedRecipient') {
								var xmlScriptedRecipient = XMLUtil.createObj('scriptedRecipient');

								dataAssignments.script.forEach(
									function(item, index, collection) {
										buffer.push(
											xmlScriptedRecipient.open,
											XMLUtil.create('script', cdata(item)),
											XMLUtil.create('scriptLanguage', dataAssignments.scriptLanguage[index]),
											xmlScriptedRecipient.close
										);
									}
								);
							}
							else if (assignmentType === 'user') {
								var xmlUser = XMLUtil.createObj('user');

								dataAssignments.userId.forEach(
									function(item, index, collection) {
										var userContent = null;

										if (isValue(item)) {
											userContent = XMLUtil.create('userId', item);
										}
										else if (isValue(dataAssignments.emailAddress[index])) {
											userContent = XMLUtil.create('emailAddress', dataAssignments.emailAddress[index]);
										}
										else if (isValue(dataAssignments.screenName[index])) {
											userContent = XMLUtil.create('screenName', dataAssignments.screenName[index]);
										}

										if (userContent) {
											buffer.push(
												xmlUser.open,
												userContent,
												xmlUser.close
											);
										}
									}
								);
							}
							else if (assignmentType === 'taskAssignees') {
								buffer.push('<assignees/>');
							}
							else if (!dataAssignments.address || dataAssignments.address.filter(isValue).length === 0) {
								buffer.push('<user/>');
							}

							buffer.push(xmlAssignments.close);
						}
					},

					_appendXMLNotifications: function(buffer, notifications, nodeName) {
						var instance = this;

						if (notifications && notifications.name && notifications.name.length > 0) {
							var description = notifications.description;
							var executionType = notifications.executionType;
							var notificationType = notifications.notificationType;
							var recipients = notifications.recipients;
							var template = notifications.template;
							var templateLanguage = notifications.templateLanguage;

							var xmlNotification = XMLUtil.createObj(nodeName || 'notification');

							notifications.name.forEach(
								function(item, index, collection) {
									buffer.push(xmlNotification.open, XMLUtil.create('name', item));

									if (description) {
										buffer.push(XMLUtil.create('description', description[index]));
									}

									if (template) {
										buffer.push(XMLUtil.create('template', cdata(template[index])));
									}

									if (templateLanguage) {
										buffer.push(XMLUtil.create('templateLanguage', templateLanguage[index]));
									}

									if (notificationType) {
										buffer.push(XMLUtil.create('notificationType', notificationType[index]));
									}

									instance._appendXMLAssignments(
										buffer,
										recipients[index],
										'recipients'
									);

									if (executionType) {
										buffer.push(XMLUtil.create('executionType', executionType[index]));
									}

									buffer.push(xmlNotification.close);
								}
							);
						}
					},

					_appendXMLTaskTimers: function(buffer, taskTimers) {
						var instance = this;

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

							taskTimers.name.forEach(
								function(item, index, collection) {
									buffer.push(xmlTaskTimer.open, XMLUtil.create('name', item));

									if (description) {
										buffer.push(XMLUtil.create('description', description[index]));
									}

									var xmlDelay = XMLUtil.createObj('delay');

									buffer.push(xmlDelay.open);

									buffer.push(XMLUtil.create('duration', delay[index].duration[0]));
									buffer.push(XMLUtil.create('scale', delay[index].scale[0]));

									buffer.push(xmlDelay.close);

									for (var i = 1; i < delay[index].duration.length; i++) {
										var xmlRecurrence = XMLUtil.createObj('recurrence');

										buffer.push(xmlRecurrence.open);

										buffer.push(XMLUtil.create('duration', delay[index].duration[i]));
										buffer.push(XMLUtil.create('scale', delay[index].scale[i]));

										buffer.push(xmlRecurrence.close);
									}

									if (blocking && isValue(blocking[index])) {
										buffer.push(XMLUtil.create('blocking', blocking[index]));
									}
									else {
										buffer.push(XMLUtil.create('blocking', String(false)));
									}

									instance._appendXMLActions(
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
								}
							);

							buffer.push(xmlTaskTimers.close);
						}
					},

					_appendXMLTransitions: function(buffer, transitions) {
						var instance = this;

						if (transitions && transitions.length > 0) {
							var xmlTransition = XMLUtil.createObj('transition');
							var xmlTransitions = XMLUtil.createObj('transitions');

							buffer.push(xmlTransitions.open);

							var pickDefault = transitions.some(
								function(item, index, collection) {
									return item.connector.default === true;
								}
							);

							pickDefault = !pickDefault;

							transitions.forEach(
								function(item, index, collection) {
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
								}
							);

							buffer.push(xmlTransitions.close);
						}
					},

					_fixTableWidth: function() {
						var instance = this;

						instance.propertyList._tableNode.setStyle('width', '100%');
					},

					_getRandomXY: function() {
						var instance = this;

						var region = instance.canvas.get('region');

						return [
							uniformRandomInt(0, region.width - 100),
							uniformRandomInt(0, region.height - 100)
						];
					},

					_getSchemaActions: function(key, tagName) {
						var instance = this;

						return {
							key: key || 'actions',
							schema: {
								resultFields: [
									{
										key: 'description',
										locator: 'description'
									},
									{
										key: 'executionType',
										locator: 'execution-type'
									},
									{
										key: 'name',
										locator: 'name'
									},
									{
										key: 'priority',
										locator: 'priority'
									},
									{
										key: 'script',
										locator: 'script'
									},
									{
										key: 'scriptLanguage',
										locator: 'script-language'
									}
								],
								resultListLocator: tagName || 'action'
							}
						};
					},

					_getSchemaAssignments: function(key, tagName) {
						var instance = this;

						return {
							key: key || 'assignments',
							schema: {
								resultFields: [
									{
										key: 'address',
										locator: 'address'
									},
									{
										key: 'resourceActions',
										schema: {
											resultFields: [
												{
													key: 'resourceAction',
													locator: 'resource-action'
												}
											],
											resultListLocator: 'resource-actions'
										}
									},
									{
										key: 'roleId',
										schema: {
											resultFields: [
												{
													key: 'roleId',
													locator: 'role-id'
												}
											],
											resultListLocator: 'role'
										}
									},
									{
										key: 'roleType',
										schema: {
											resultFields: [
												{
													key: 'autoCreate',
													locator: 'auto-create'
												},
												{
													key: 'roleName',
													locator: 'name'
												},
												{
													key: 'roleType',
													locator: 'role-type'
												}
											],
											resultListLocator: 'role'
										}
									},
									{
										key: 'scriptedAssignment',
										schema: {
											resultFields: [
												{
													key: 'script',
													locator: 'script'
												},
												{
													key: 'scriptLanguage',
													locator: 'script-language'
												}
											],
											resultListLocator: 'scripted-assignment'
										}
									},
									{
										key: 'scriptedRecipient',
										schema: {
											resultFields: [
												{
													key: 'script',
													locator: 'script'
												},
												{
													key: 'scriptLanguage',
													locator: 'script-language'
												}
											],
											resultListLocator: 'scripted-recipient'
										}
									},
									{
										key: 'taskAssignees',
										locator: 'assignees'
									},
									{
										key: 'user',
										schema: {
											resultFields: [
												{
													key: 'emailAddress',
													locator: 'email-address'
												},
												{
													key: 'screenName',
													locator: 'screen-name'
												},
												{
													key: 'userId',
													locator: 'user-id'
												}
											],
											resultListLocator: 'user'
										}
									}
								],
								resultListLocator: tagName || 'assignments'
							}
						};
					},

					_getSchemaNotifications: function(key, tagName, assignmentKey, assignmentTagName) {
						var instance = this;

						assignmentKey = assignmentKey || 'recipients';
						assignmentTagName = assignmentTagName || 'recipients';

						return {
							key: key || 'notifications',
							schema: {
								resultFields: [
									{
										key: 'description',
										locator: 'description'
									},
									{
										key: 'executionType',
										locator: 'execution-type'
									},
									{
										key: 'name',
										locator: 'name'
									},
									{
										key: 'notificationType',
										locator: 'notification-type'
									},
									{
										key: 'template',
										locator: 'template'
									},
									{
										key: 'templateLanguage',
										locator: 'template-language'
									},
									instance._getSchemaAssignments(assignmentKey, assignmentTagName)
								],
								resultListLocator: tagName || 'notification'
							}
						};
					},

					_getSchemaTaskTimers: function(key, tagNode) {
						var instance = this;

						return {
							key: key || 'taskTimers',
							schema: {
								resultFields: [
									{
										key: 'blocking',
										locator: 'blocking'
									},
									{
										key: 'delay',
										schema: {
											resultFields: [
												{
													key: 'duration',
													locator: 'duration'
												},
												{
													key: 'scale',
													locator: 'scale'
												}
											],
											resultListLocator: 'delay'
										}
									},
									{
										key: 'description',
										locator: 'description'
									},
									{
										key: 'name',
										locator: 'name'
									},
									{
										key: 'recurrence',
										schema: {
											resultFields: [
												{
													key: 'duration',
													locator: 'duration'
												},
												{
													key: 'scale',
													locator: 'scale'
												}
											],
											resultListLocator: 'recurrence'
										}
									},
									instance._getSchemaActions('timerActions', 'timer-action'),
									instance._getSchemaAssignments('reassignments', 'reassignments'),
									instance._getSchemaNotifications('timerNotifications', 'timer-notification')
								],
								resultListLocator: tagNode || 'task-timer'
							}
						};
					},

					_getSchemaTransitions: function(key, tagName) {
						var instance = this;

						return {
							key: key || 'transitions',
							schema: {
								resultFields: [
									{
										key: 'default',
										locator: 'default'
									},
									{
										key: 'name',
										locator: 'name'
									},
									{
										key: 'target',
										locator: 'target'
									}
								],
								resultListLocator: tagName || 'transition'
							}
						};
					},

					_normalizeToActions: function(data) {
						var instance = this;

						var actions = {};

						data = data || {};

						data.forEach(
							function(item1, index1, collection1) {
								A.each(
									item1,
									function(item2, index2, collection2) {
										if (isValue(item2)) {
											if (index2 === 'script') {
												item2 = Lang.trim(item2);
											}

											instance._put(actions, index2, item2);
										}
									}
								);
							}
						);

						return actions;
					},

					_normalizeToAssignments: function(data) {
						var instance = this;

						var assignments = {
							assignmentType: [STR_BLANK]
						};

						if (data && data.length) {
							COL_TYPES_ASSIGNMENT.forEach(
								function(item1, index1, collection1) {
									var assignmentValue = AArray(data[0][item1]);

									assignmentValue.forEach(
										function(item2, index2, collection2) {
											if (isObject(item2)) {
												A.each(
													item2,
													function(item3, index3, collection3) {
														instance._put(assignments, index3, item3);

														if (isValue(item3)) {
															assignments.assignmentType = AArray(item1);
														}
													}
												);
											}
											else {
												if (isValue(item2)) {
													assignments.assignmentType = AArray(item1);
												}

												instance._put(assignments, item1, item2);
											}
										}
									);
								}
							);
						}

						return assignments;
					},

					_normalizeToDelays: function(data) {
						var instance = this;

						var delays = {};

						data = data || {};

						data.forEach(
							function(item1, index1, collection1) {
								A.each(
									item1,
									function(item2, index2, collection2) {
										if (isValue(item2)) {
											instance._put(delays, index2, item2);
										}
									}
								);
							}
						);

						return delays;
					},

					_normalizeToNotifications: function(data) {
						var instance = this;

						var notifications = {};

						data = data || {};

						data.forEach(
							function(item1, index1, collection1) {
								A.each(
									item1,
									function(item2, index2, collection2) {
										if (isValue(item2)) {
											if (index2 === 'recipients') {
												item2 = instance._normalizeToAssignments(item2);
											}

											instance._put(notifications, index2, item2);
										}
									}
								);
							}
						);

						return notifications;
					},

					_normalizeToTaskTimers: function(data) {
						var instance = this;

						var taskTimers = {};

						data = data || {};

						data.forEach(
							function(item1, index1, collection1) {
								A.each(
									item1,
									function(item2, index2, collection2) {
										if (isValue(item2)) {
											if (index2 === 'delay' || index2 === 'recurrence') {
												return;
											}
											else if (index2 === 'timerNotifications') {
												item2 = instance._normalizeToNotifications(item2);
											}
											else if (index2 === 'timerActions') {
												item2 = instance._normalizeToActions(item2);
											}
											else if (index2 === 'reassignments') {
												item2 = instance._normalizeToAssignments(item2);
											}

											instance._put(taskTimers, index2, item2);
										}
									}
								);

								var delays = item1.delay.concat(item1.recurrence);

								instance._put(taskTimers, 'delay', instance._normalizeToDelays(delays));
							}
						);

						return taskTimers;
					},

					_put: function(obj, key, value) {
						var instance = this;

						obj[key] = obj[key] || [];

						obj[key].push(value);
					},

					_renderContentTabs: function() {
						var instance = this;

						if (!instance.contentTabView) {
							var contentTabView = new A.TabView(instance.get('contentTabView'));

							contentTabView.render();

							instance.viewNode = contentTabView.item(0).get('boundingBox');
							instance.sourceNode = contentTabView.item(1).get('boundingBox');

							instance.contentTabView = contentTabView;
						}
					},

					_setAceEditor: function(val) {
						var instance = this;

						var portletNamespace = instance.get('portletNamespace');

						var canvasRegion = instance.canvasRegion;

						return A.merge(
							{
								boundingBox: '#' + portletNamespace + 'editorWrapper',
								height: canvasRegion.height,
								mode: 'xml',
								tabSize: 4,
								width: canvasRegion.width
							},
							val
						);
					},

					_setContentTabView: function(val) {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						var contentTabListNode = boundingBox.one('.tabbable .nav-tabs');

						var defaultValue = {
							after: {
								selectionChange: A.bind(instance._afterSelectionChangeKaleoDesigner, instance)
							},
							boundingBox: boundingBox.one('.tabbable'),
							bubbleTargets: instance,
							contentBox: boundingBox.one('.tabbable .tabbable-content'),
							contentNode: boundingBox.one('.tabbable .tabbable-content .tabview-content'),
							cssClass: 'tabbable',
							listNode: contentTabListNode
						};

						if (!contentTabListNode) {
							var strings = instance.getStrings();

							defaultValue.items = [
								{
									label: strings.view
								},
								{
									label: strings.source
								}
							];
						}

						return A.merge(defaultValue, val);
					},

					_setDefinition: function(val) {
						var instance = this;

						val = instance.sanitizeDefinitionXML(val);

						instance.definitionDoc = A.DataType.XML.parse(val);

						return val;
					},

					_translateXMLtoFields: function() {
						var instance = this;

						var fields = [];

						instance.forEachDefinitionField(
							function(tagName, fieldData) {
								fieldData.results.forEach(
									function(item, index, collection) {
										var description = jsonParse(item.description);

										var type = tagName;

										if (item.initial) {
											type = 'start';
										}

										var metadata = jsonParse(item.metadata);

										if (metadata) {
											if (metadata.terminal) {
												type = 'end';
											}
										}
										else {
											metadata = {
												xy: instance._getRandomXY()
											};
										}

										fields.push(
											{
												actions: instance._normalizeToActions(item.actions),
												assignments: instance._normalizeToAssignments(item.assignments),
												description: description,
												fields: [{}],
												initial: item.initial,
												metadata: metadata,
												name: item.name,
												notifications: instance._normalizeToNotifications(item.notifications),
												script: item.script,
												scriptLanguage: item.scriptLanguage || DEFAULT_LANGUAGE,
												taskTimers: instance._normalizeToTaskTimers(item.taskTimers),
												type: type,
												xy: metadata.xy
											}
										);
									}
								);
							}
						);

						return fields;
					},

					_translateXMLToJSON: function(tagName) {
						var instance = this;

						var schema = {
							resultFields: [
								'description',
								'initial',
								'metadata',
								'name',
								'script',
								{
									key: 'scriptLanguage',
									locator: 'script-language'
								},
								instance._getSchemaActions(),
								instance._getSchemaAssignments(),
								instance._getSchemaNotifications(),
								instance._getSchemaTaskTimers(),
								instance._getSchemaTransitions()
							],
							resultListLocator: tagName
						};

						return A.DataSchema.XML.apply(schema, instance.definitionDoc);
					},

					_uiSetAvailableFields: function(val) {
						var instance = this;

						var disabled = instance.get('disabled');
						var fieldsNode = instance.fieldsNode;

						if (fieldsNode) {
							if (disabled) {
								fieldsNode.html('<div class="alert alert-info">' + KaleoDesignerStrings.inspectTaskMessage + '</div>');
							}
							else {
								KaleoDesigner.superclass._uiSetAvailableFields.apply(this, arguments);
							}
						}
					},

					_uiSetDefinition: function(val) {
						var instance = this;

						var fields = instance._translateXMLtoFields();

						instance.clearFields();

						instance.set('fields', fields);

						if (instance.get('rendered')) {
							instance.connectDefinitionFields();
						}
					},

					_updateXMLNamespace: function(definition) {
						var instance = this;

						var workflowDefinition = /(<workflow-definition)[^>]*(>)/.exec(definition);

						if (workflowDefinition) {
							var xmlns = /xmlns="([^"]*)"/.exec(workflowDefinition);
							var xmlnsXsi = /xmlns:xsi="([^"]*)"/.exec(workflowDefinition);
							var xsiSchemaLocation = /xsi:schemaLocation="([^"]*)"/.exec(workflowDefinition);

							if (xmlns && xmlnsXsi && xsiSchemaLocation) {
								instance.set(
									'xmlNamespace',
									{
										'xmlns': xmlns[1],
										'xmlns:xsi': xmlnsXsi[1],
										'xsi:schemaLocation': xsiSchemaLocation[1]
									}
								);
							}
						}
					}
				}
			}
		);

		KaleoDesigner.AVAILABLE_FIELDS = {
			DEFAULT: [
				{
					iconClass: 'icon-db-condition',
					label: Liferay.Language.get('condition-node'),
					type: 'condition'
				},
				{
					iconClass: 'icon-db-end',
					label: Liferay.Language.get('end-node'),
					type: 'end'
				},
				{
					iconClass: 'icon-db-fork',
					label: Liferay.Language.get('fork-node'),
					type: 'fork'
				},
				{
					iconClass: 'icon-db-join',
					label: Liferay.Language.get('join-node'),
					type: 'join'
				},
				{
					iconClass: 'icon-db-joinxor',
					label: Liferay.Language.get('join-xor-node'),
					type: 'join-xor'
				},
				{
					iconClass: 'icon-db-start',
					label: Liferay.Language.get('start-node'),
					type: 'start'
				},
				{
					iconClass: 'icon-db-state',
					label: Liferay.Language.get('state-node'),
					type: 'state'
				},
				{
					iconClass: 'icon-db-task',
					label: Liferay.Language.get('task-node'),
					type: 'task'
				}
			]
		};

		KaleoDesigner.AVAILABLE_PROPERTY_MODELS = {
			DEFAULT: {},

			KALEO_FORMS_EDIT: {
				task: function(model, parentModel) {
					var instance = this;

					var strings = instance.getStrings();

					return parentModel.concat(model).concat(
						[
							{
								attributeName: 'assignments',
								editor: new KaleoDesignerEditors.AssignmentsEditor(),
								formatter: PropertyListFormatter.assignmentsType,
								name: strings.assignments
							}
						]
					);
				}
			}
		};

		Liferay.KaleoDesigner = KaleoDesigner;
	},
	'',
	{
		requires: ['aui-ace-editor', 'aui-ace-editor-mode-xml', 'aui-tpl-snippets-deprecated', 'dataschema-xml', 'datasource', 'datatype-xml', 'event-valuechange', 'io-form', 'liferay-kaleo-designer-autocomplete-util', 'liferay-kaleo-designer-editors', 'liferay-kaleo-designer-nodes', 'liferay-kaleo-designer-utils', 'liferay-kaleo-designer-xml-util', 'liferay-util-window', 'liferay-xml-formatter']
	}
);