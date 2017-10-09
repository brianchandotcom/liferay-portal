AUI.add(
	'liferay-portlet-kaleo-designer',
	function(A) {
		var AArray = A.Array;
		var DiagramBuilder = A.DiagramBuilder;
		var Lang = A.Lang;
		var XMLDefinition = Liferay.KaleoDesignerXMLDefinition;
		var XMLFormatter = new Liferay.XMLFormatter();
		var XMLUtil = Liferay.XMLUtil;
		var FieldNormalizer = Liferay.KaleoDesignerFieldNormalizer;

		var KaleoDesignerEditors = Liferay.KaleoDesignerEditors;
		var KaleoDesignerRemoteServices = Liferay.KaleoDesignerRemoteServices;
		var KaleoDesignerStrings = Liferay.KaleoDesignerStrings;

		var isNull = Lang.isNull;
		var isObject = Lang.isObject;
		var isValue = Lang.isValue;

		var jsonParse = Liferay.KaleoDesignerUtils.jsonParse;
		var uniformRandomInt = Liferay.KaleoDesignerUtils.uniformRandomInt;
		var serializeDefinition = Liferay.KaleoDesignerXMLDefinitionSerializer;

		var COL_TYPES_ASSIGNMENT = ['address', 'receptionType', 'resourceActions', 'roleId', 'roleType', 'scriptedAssignment', 'scriptedRecipient', 'taskAssignees', 'user', 'userId'];

		var DEFAULT_LANGUAGE = 'groovy';

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

					portletNamespace: {
						value: STR_BLANK
					},

					portletResourceNamespace: {
						value: STR_BLANK
					}
				},

				EXTENDS: DiagramBuilder,

				NAME: 'diagram-builder',

				UI_ATTRS: ['definition'],

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance.definition = new XMLDefinition(
							{
								value: config.definition
							}
						);

						instance.after('render', instance._afterRenderKaleoDesigner);

						instance.after(instance._renderContentTabs, instance, '_renderTabs');

						instance.after(instance._afterRenderSettings, instance, '_renderSettings');
					},

					connectDefinitionFields: function() {
						var instance = this;

						var connectors = [];

						instance.definition.forEachField(
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

					getContent: function() {
						var instance = this;

						var json = instance.toJSON();

						return instance.definitionController.serializeDefinition(json);
					},

					showEditor: function() {
						var instance = this;

						var editor = instance.editor;

						if (!editor) {
							editor = new A.AceEditor(instance.get('aceEditorConfig')).render();

							instance.editor = editor;
						}

						var content = instance.get('definition');

						var definition = instance.definition;

						if (!content || instance.validateDefinition(content)) {
							content = serializeDefinition(
								definition.get('xmlNamespace'),
								definition.getAttrs(['description', 'name', 'version']),
								instance.toJSON()
							);
						}

						editor.set('value', content);
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

						if (assignments.assignmentType == 'roleId') {
							instance._populateRole(assignments);
						}
						else if (assignments.assignmentType == 'user') {
							instance._populateUser(assignments);
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
												if (item2[0] && item2[0].receptionType) {
													instance._put(notifications,'receptionType', item2[0].receptionType);
												}

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

					_populateRole: function(assignments) {
						KaleoDesignerRemoteServices.getRole(
							assignments.roleId,
							function(data) {
								AArray.each(
									data,
									function(item) {
										if (item) {
											var index = assignments.roleId.indexOf(item.roleId);

											assignments.roleNameAC[index] = item.name;
										}
									}
								);
							}
						);
					},

					_populateUser: function(assignments) {
						KaleoDesignerRemoteServices.getUser(
							assignments.userId,
							function(data) {
								AArray.each(
									data,
									function(item) {
										if (item) {
											var index = assignments.userId.indexOf(item.userId);

											assignments.emailAddress[index] = item.emailAddress;
											assignments.fullName[index] = item.fullName;
											assignments.screenName[index] = item.screenName;
										}
									}
								);
							}
						);
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

						instance.definition = new XMLDefinition(
							{
								value: val
							}
						);

						return val;
					},

					_translateXMLtoFields: function() {
						var instance = this;

						var fields = [];

						instance.definition.forEachField(
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
		requires: ['aui-ace-editor', 'aui-ace-editor-mode-xml', 'aui-tpl-snippets-deprecated', 'datasource', 'datatype-xml', 'event-valuechange', 'io-form', 'liferay-kaleo-designer-editors', 'liferay-kaleo-designer-nodes', 'liferay-kaleo-designer-remote-services', 'liferay-kaleo-designer-utils', 'liferay-kaleo-designer-xml-definition', 'liferay-kaleo-designer-xml-util', 'liferay-util-window', 'liferay-xml-formatter']
	}
);