AUI.add(
	'liferay-portlet-kaleo-designer',
	function(A) {
		var DiagramBuilder = A.DiagramBuilder;
		var Lang = A.Lang;
		var XMLDefinition = Liferay.KaleoDesignerXMLDefinition;
		var XMLFormatter = new Liferay.XMLFormatter();
		var FieldNormalizer = Liferay.KaleoDesignerFieldNormalizer;

		var KaleoDesignerEditors = Liferay.KaleoDesignerEditors;
		var KaleoDesignerStrings = Liferay.KaleoDesignerStrings;
		var XMLUtil = Liferay.XMLUtil;

		var isObject = Lang.isObject;

		var jsonParse = Liferay.KaleoDesignerUtils.jsonParse;
		var uniformRandomInt = Liferay.KaleoDesignerUtils.uniformRandomInt;
		var serializeDefinition = Liferay.KaleoDesignerXMLDefinitionSerializer;

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

						if (!content || XMLUtil.validateDefinition(content)) {
							content = serializeDefinition(
								definition.get('xmlNamespace'),
								definition.getAttrs(['description', 'name', 'version']),
								instance.toJSON()
							);
						}

						editor.set('value', content);
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
												actions: FieldNormalizer.normalizeToActions(item.actions),
												assignments: FieldNormalizer.normalizeToAssignments(item.assignments),
												description: description,
												fields: [{}],
												initial: item.initial,
												metadata: metadata,
												name: item.name,
												notifications: FieldNormalizer.normalizeToNotifications(item.notifications),
												script: item.script,
												scriptLanguage: item.scriptLanguage || DEFAULT_LANGUAGE,
												taskTimers: FieldNormalizer.normalizeToTaskTimers(item.taskTimers),
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
		requires: ['aui-ace-editor', 'aui-ace-editor-mode-xml', 'aui-tpl-snippets-deprecated', 'datasource', 'datatype-xml', 'event-valuechange', 'io-form', 'liferay-kaleo-designer-editors', 'liferay-kaleo-designer-field-normalizer', 'liferay-kaleo-designer-nodes', 'liferay-kaleo-designer-utils', 'liferay-kaleo-designer-xml-definition', 'liferay-kaleo-designer-xml-definition-serializer', 'liferay-kaleo-designer-xml-util', 'liferay-util-window']
	}
);