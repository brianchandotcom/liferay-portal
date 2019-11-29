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
	'liferay-portlet-kaleo-designer',
	A => {
		var DiagramBuilder = A.DiagramBuilder;
		var Lang = A.Lang;

		var DefinitionDiagramController =
			Liferay.KaleoDesignerDefinitionDiagramController;
		var KaleoDesignerEditors = Liferay.KaleoDesignerEditors;
		var KaleoDesignerStrings = Liferay.KaleoDesignerStrings;
		var XMLUtil = Liferay.XMLUtil;

		var isObject = Lang.isObject;

		var STR_BLANK = '';

		var PropertyListFormatter =
			Liferay.KaleoDesignerUtils.PropertyListFormatter;

		// Updates icons to produce lexicon SVG markup instead of default glyphicon

		A.PropertyBuilderAvailableField.prototype.FIELD_ITEM_TEMPLATE = A.PropertyBuilderAvailableField.prototype.FIELD_ITEM_TEMPLATE.replace(
			/<\s*span[^>]*>(.*?)<\s*\/\s*span>/,
			Liferay.Util.getLexiconIconTpl(
				'{iconClass}',
				'property-builder-field-icon'
			)
		);

		A.ToolbarRenderer.prototype.TEMPLATES.icon = Liferay.Util.getLexiconIconTpl(
			'{cssClass}'
		);

		var KaleoDesigner = A.Component.create({
			ATTRS: {
				aceEditorConfig: {
					setter: '_setAceEditor',
					validator: isObject,
					value: null
				},

				availableFields: {
					validator: isObject,
					valueFn() {
						return KaleoDesigner.AVAILABLE_FIELDS.DEFAULT;
					}
				},

				availablePropertyModels: {
					validator: isObject,
					valueFn() {
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
				},

				propertyList: {
					value: {
						strings: {
							propertyName: Liferay.Language.get('property-name'),
							value: Liferay.Language.get('value')
						}
					}
				},

				strings: {
					value: {
						addNode: Liferay.Language.get('add-node'),
						cancel: Liferay.Language.get('cancel'),
						close: Liferay.Language.get('close'),
						deleteConnectorsMessage: Liferay.Language.get(
							'are-you-sure-you-want-to-delete-the-selected-connectors'
						),
						deleteNodesMessage: Liferay.Language.get(
							'are-you-sure-you-want-to-delete-the-selected-nodes'
						),
						save: Liferay.Language.get('save'),
						settings: Liferay.Language.get('settings')
					}
				}
			},

			EXTENDS: DiagramBuilder,

			NAME: 'diagram-builder',

			UI_ATTRS: ['definition'],

			prototype: {
				_afterRenderKaleoDesigner() {
					var instance = this;

					instance.connectDefinitionFields();

					instance.canvasRegion = instance.canvas.get('region');

					A.one('.property-builder').insertBefore(
						A.one('.property-builder-tabs'),
						A.one('.property-builder-canvas')
					);
				},

				_afterRenderSettings() {
					var instance = this;

					var dataTable = instance.propertyList;

					dataTable.after(
						A.bind(
							instance._afterRenderSettingsTableBody,
							instance
						),
						dataTable,
						'_onUITriggerSort'
					);

					// Dynamically removes unnecessary icons from editor toolbar buttons

					var defaultGetEditorFn = dataTable.getEditor;

					dataTable.getEditor = function() {
						var editor = defaultGetEditorFn.apply(this, arguments);

						if (editor) {
							var defaultSetToolbarFn = A.bind(
								editor._setToolbar,
								editor
							);

							editor._setToolbar = function(val) {
								var toolbar = defaultSetToolbarFn(val);

								if (toolbar && toolbar.children) {
									toolbar.children = toolbar.children.map(
										children => {
											children = children.map(item => {
												delete item.icon;

												return item;
											});

											return children;
										}
									);
								}

								return toolbar;
							};
						}

						return editor;
					};
				},

				_afterRenderSettingsTableBody() {
					var instance = this;

					instance._fixTableWidth();
				},

				_afterSelectionChangeKaleoDesigner(event) {
					var instance = this;
					var tabContentNode = event.newVal.get('boundingBox');

					if (instance.get('rendered')) {
						instance.stopEditing();

						if (tabContentNode === instance.sourceNode) {
							instance.showEditor();
						}
					}
				},

				_fixTableWidth() {
					var instance = this;

					instance.propertyList._tableNode.setStyle('width', '100%');
				},

				_onDestroyPortlet() {
					var instance = this;

					instance.destroy(true);
				},

				_renderContentTabs() {
					var instance = this;

					instance.closeEditProperties();

					if (!instance.contentTabView) {
						var contentTabView = new A.TabView(
							instance.get('contentTabView')
						);

						contentTabView.render();

						instance.viewNode = contentTabView
							.item(0)
							.get('boundingBox');
						instance.sourceNode = contentTabView
							.item(1)
							.get('boundingBox');

						instance.contentTabView = contentTabView;
					}
				},

				_setAceEditor(val) {
					var instance = this;

					var portletNamespace = instance.get('portletNamespace');

					var canvasRegion = instance.canvasRegion;

					return A.merge(
						{
							boundingBox:
								'#' + portletNamespace + 'editorWrapper',
							height: canvasRegion.height,
							mode: 'xml',
							tabSize: 4,
							width: canvasRegion.width
						},
						val
					);
				},

				_setContentTabView(val) {
					var instance = this;

					var boundingBox = instance.get('boundingBox');

					var contentTabListNode = boundingBox.one(
						'.tabbable .nav-tabs'
					);

					var defaultValue = {
						after: {
							selectionChange: A.bind(
								instance._afterSelectionChangeKaleoDesigner,
								instance
							)
						},
						boundingBox: boundingBox.one('.tabbable'),
						bubbleTargets: instance,
						contentBox: boundingBox.one(
							'.tabbable .tabbable-content'
						),
						contentNode: boundingBox.one(
							'.tabbable .tabbable-content .tabview-content'
						),
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

				_setDefinition(val) {
					var instance = this;

					instance.definitionController = new DefinitionDiagramController(
						encodeURIComponent(val),
						instance.canvas
					);

					return val;
				},

				_uiSetAvailableFields() {
					var instance = this;

					var disabled = instance.get('disabled');
					var fieldsNode = instance.fieldsNode;

					if (fieldsNode) {
						if (disabled) {
							fieldsNode.html(
								'<div class="alert alert-info">' +
									KaleoDesignerStrings.inspectTaskMessage +
									'</div>'
							);
						}
						else {
							KaleoDesigner.superclass._uiSetAvailableFields.apply(
								this,
								arguments
							);
						}
					}
				},

				_uiSetDefinition() {
					var instance = this;

					instance.clearFields();

					instance.set(
						'fields',
						instance.definitionController.getFields()
					);

					if (instance.get('rendered')) {
						instance.connectDefinitionFields();
					}
				},

				connectDefinitionFields() {
					var instance = this;

					var connectors = instance.definitionController.getConnectors();

					instance.connectAll(connectors);
				},

				createField(val) {
					var instance = this;

					var field = KaleoDesigner.superclass.createField.call(
						instance,
						val
					);

					var controlsToolbar = field.get('controlsToolbar');

					controlsToolbar.children[0].icon = 'times';

					field.set('controlsToolbar', controlsToolbar);

					return field;
				},

				destructor() {
					var instance = this;

					var dataTable = instance.propertyList;

					if (dataTable) {
						var data = dataTable.get('data');

						for (var i = 0; i < data.size(); i++) {
							var editor = data.item(i).get('editor');

							if (editor) {
								editor.destroy();
							}
						}
					}
				},

				editNode(diagramNode) {
					var instance = this;

					if (diagramNode.getProperties()) {
						KaleoDesigner.superclass.editNode.apply(
							this,
							arguments
						);
					}
					else {
						instance.closeEditProperties();
					}

					instance._fixTableWidth();
				},

				getContent() {
					var instance = this;

					var json = instance.toJSON();

					return instance.definitionController.serializeDefinition(
						json
					);
				},

				getEditorContent() {
					var instance = this;

					var editor = instance.editor;

					return editor.get('value');
				},

				initializer(config) {
					var instance = this;

					instance.definitionController = new DefinitionDiagramController(
						encodeURIComponent(config.definition),
						instance.canvas
					);

					instance.after(
						'render',
						instance._afterRenderKaleoDesigner
					);

					instance.after(
						instance._renderContentTabs,
						instance,
						'_renderTabs'
					);

					instance.after(
						instance._afterRenderSettings,
						instance,
						'_renderSettings'
					);

					instance.destroyPortletHandler = Liferay.on(
						'destroyPortlet',
						A.bind(instance._onDestroyPortlet, instance)
					);
				},

				setEditorContent(content) {
					var instance = this;

					var editor = instance.editor;

					editor.set('value', content);
				},

				showEditor() {
					var instance = this;

					var editor = instance.editor;

					if (!editor) {
						editor = new A.AceEditor(
							instance.get('aceEditorConfig')
						).render();

						instance.editor = editor;
					}

					var content = instance.get('definition');

					if (!content || XMLUtil.validateDefinition(content)) {
						content = instance.getContent();
					}

					editor.set('value', content);

					if (instance.get('readOnly')) {
						editor.set('readOnly', true);
					}
				},

				showSuccessMessage() {
					var instance = this;

					var successMessage = Liferay.Language.get(
						'definition-imported-sucessfully'
					);

					var alert = instance._alert;

					if (alert) {
						alert.destroy();
					}

					alert = new Liferay.Alert({
						closeable: true,
						delay: {
							hide: 3000,
							show: 0
						},
						icon: 'check',
						message: successMessage,
						type: 'success'
					});

					if (!alert.get('rendered')) {
						alert.render('.portlet-column');
					}

					alert.show();

					instance._alert = alert;
				}
			}
		});

		KaleoDesigner.AVAILABLE_FIELDS = {
			DEFAULT: [
				{
					iconClass: 'diamond',
					label: Liferay.Language.get('condition-node'),
					type: 'condition'
				},
				{
					iconClass: 'arrow-end',
					label: Liferay.Language.get('end-node'),
					type: 'end'
				},
				{
					iconClass: 'arrow-split',
					label: Liferay.Language.get('fork-node'),
					type: 'fork'
				},
				{
					iconClass: 'arrow-join',
					label: Liferay.Language.get('join-node'),
					type: 'join'
				},
				{
					iconClass: 'arrow-xor',
					label: Liferay.Language.get('join-xor-node'),
					type: 'join-xor'
				},
				{
					iconClass: 'arrow-start',
					label: Liferay.Language.get('start-node'),
					type: 'start'
				},
				{
					iconClass: 'circle',
					label: Liferay.Language.get('state-node'),
					type: 'state'
				},
				{
					iconClass: 'square',
					label: Liferay.Language.get('task-node'),
					type: 'task'
				}
			]
		};

		KaleoDesigner.AVAILABLE_PROPERTY_MODELS = {
			DEFAULT: {},

			KALEO_FORMS_EDIT: {
				task(model, parentModel) {
					var instance = this;

					var strings = instance.getStrings();

					return parentModel.concat(model).concat([
						{
							attributeName: 'assignments',
							editor: new KaleoDesignerEditors.AssignmentsEditor(),
							formatter: PropertyListFormatter.assignmentsType,
							name: strings.assignments
						}
					]);
				}
			}
		};

		Liferay.KaleoDesigner = KaleoDesigner;
	},
	'',
	{
		requires: [
			'aui-ace-editor',
			'aui-ace-editor-mode-xml',
			'aui-tpl-snippets-deprecated',
			'event-valuechange',
			'io-form',
			'liferay-kaleo-designer-definition-diagram-controller',
			'liferay-kaleo-designer-editors',
			'liferay-kaleo-designer-nodes',
			'liferay-kaleo-designer-utils',
			'liferay-kaleo-designer-xml-util',
			'liferay-util-window'
		]
	}
);
