/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-portlet-kaleo-designer',
	(A) => {
		const DiagramBuilder = A.DiagramBuilder;
		const Lang = A.Lang;

		const DefinitionDiagramController =
			Liferay.KaleoDesignerDefinitionDiagramController;
		const KaleoDesignerEditors = Liferay.KaleoDesignerEditors;
		const KaleoDesignerStrings = Liferay.KaleoDesignerStrings;
		const XMLUtil = Liferay.XMLUtil;

		const isObject = Lang.isObject;

		const STR_BLANK = '';

		const PropertyListFormatter =
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

		const KaleoDesigner = A.Component.create({
			ATTRS: {
				aceEditorConfig: {
					setter: '_setAceEditor',
					validator: isObject,
					value: null,
				},

				availableFields: {
					validator: isObject,
					valueFn() {
						return KaleoDesigner.AVAILABLE_FIELDS.DEFAULT;
					},
				},

				availablePropertyModels: {
					validator: isObject,
					valueFn() {
						return KaleoDesigner.AVAILABLE_PROPERTY_MODELS.DEFAULT;
					},
				},

				contentTabView: {
					setter: '_setContentTabView',
					validator: isObject,
					value: null,
					writeOnce: true,
				},

				data: {
					validator: isObject,
					value: {},
				},

				definition: {
					lazyAdd: false,
					setter: '_setDefinition',
				},

				portletNamespace: {
					value: STR_BLANK,
				},

				portletResourceNamespace: {
					value: STR_BLANK,
				},

				propertyList: {
					value: {
						strings: {
							propertyName: Liferay.Language.get('property-name'),
							value: Liferay.Language.get('value'),
						},
					},
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
						settings: Liferay.Language.get('settings'),
					},
				},
			},

			EXTENDS: DiagramBuilder,

			NAME: 'diagram-builder',

			UI_ATTRS: ['definition'],

			prototype: {
				_afterRenderKaleoDesigner() {
					const instance = this;

					instance.connectDefinitionFields();

					instance.canvasRegion = instance.canvas.get('region');

					A.one('.property-builder').insertBefore(
						A.one('.property-builder-tabs'),
						A.one('.property-builder-canvas')
					);
				},

				_afterRenderSettings() {
					const instance = this;

					const dataTable = instance.propertyList;

					dataTable.after(
						A.bind(
							instance._afterRenderSettingsTableBody,
							instance
						),
						dataTable,
						'_onUITriggerSort'
					);

					// Dynamically removes unnecessary icons from editor toolbar buttons

					const defaultGetEditorFn = dataTable.getEditor;

					dataTable.getEditor = function () {
						const editor = defaultGetEditorFn.apply(
							this,
							arguments
						);

						if (editor) {
							const defaultSetToolbarFn = A.bind(
								editor._setToolbar,
								editor
							);

							editor._setToolbar = function (val) {
								const toolbar = defaultSetToolbarFn(val);

								if (toolbar && toolbar.children) {
									toolbar.children = toolbar.children.map(
										(children) => {
											children = children.map((item) => {
												item.cssClass = 'btn-secondary';

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
					const instance = this;

					instance._fixTableWidth();
				},

				_afterSelectionChangeKaleoDesigner(event) {
					const instance = this;
					const tabContentNode = event.newVal.get('boundingBox');

					if (instance.get('rendered')) {
						instance.stopEditing();

						if (tabContentNode === instance.sourceNode) {
							instance.showEditor();
						}
						else {
							if (
								!XMLUtil.validateDefinition(
									instance.getEditorContent()
								)
							) {
								instance.showErrorMessage(
									Liferay.Language.get(
										'please-enter-valid-content'
									)
								);
							}
						}
					}
				},

				_fixTableWidth() {
					const instance = this;

					instance.propertyList._tableNode.setStyle('width', '100%');
				},

				_onDestroyPortlet() {
					const instance = this;

					const baseCellEditor = document.querySelector(
						'.basecelleditor'
					);

					if (baseCellEditor) {
						while (baseCellEditor.hasChildNodes()) {
							baseCellEditor.removeChild(
								baseCellEditor.lastChild
							);
						}
						baseCellEditor.remove();
					}

					instance.destroy(true);
				},

				_renderContentTabs() {
					const instance = this;

					instance.closeEditProperties();

					if (!instance.contentTabView) {
						const contentTabView = new A.TabView(
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
					const instance = this;

					const portletNamespace = instance.get('portletNamespace');

					const canvasRegion = instance.canvasRegion;

					return {
						boundingBox: '#' + portletNamespace + 'editorWrapper',
						height: canvasRegion.height,
						mode: 'xml',
						tabSize: 4,
						width: canvasRegion.width,
						...val,
					};
				},

				_setContentTabView(val) {
					const instance = this;

					const boundingBox = instance.get('boundingBox');

					const contentTabListNode = boundingBox.one(
						'.tabbable .nav-tabs'
					);

					const defaultValue = {
						after: {
							selectionChange: A.bind(
								instance._afterSelectionChangeKaleoDesigner,
								instance
							),
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
						listNode: contentTabListNode,
					};

					if (!contentTabListNode) {
						const strings = instance.getStrings();

						defaultValue.items = [
							{
								label: strings.view,
							},
							{
								label: strings.source,
							},
						];
					}

					return {...defaultValue, ...val};
				},

				_setDefinition(val) {
					const instance = this;

					instance.definitionController = new DefinitionDiagramController(
						encodeURIComponent(val),
						instance.canvas
					);

					return val;
				},

				_uiSetAvailableFields() {
					const instance = this;

					const disabled = instance.get('disabled');
					const fieldsNode = instance.fieldsNode;

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
					const instance = this;

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
					const instance = this;

					const connectors = instance.definitionController.getConnectors();

					instance.connectAll(connectors);
				},

				createField(val) {
					const instance = this;

					const field = KaleoDesigner.superclass.createField.call(
						instance,
						val
					);

					const controlsToolbar = field.get('controlsToolbar');

					controlsToolbar.children[0].icon = 'times';

					field.set('controlsToolbar', controlsToolbar);

					return field;
				},

				destructor() {
					const instance = this;

					const dataTable = instance.propertyList;

					if (dataTable) {
						const data = dataTable.get('data');

						for (let i = 0; i < data.size(); i++) {
							const editor = data.item(i).get('editor');

							if (editor) {
								editor.destroy();
							}
						}
					}
				},

				editNode(diagramNode) {
					const instance = this;

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
					const instance = this;

					const draftVersionInput = document.getElementById(
						instance.get('portletNamespace') + 'draftVersion'
					);

					return instance.definitionController.serializeDefinition(
						draftVersionInput.value,
						instance.toJSON()
					);
				},

				getEditorContent() {
					const instance = this;

					const editor = instance.editor;

					return editor.get('value');
				},

				initializer(config) {
					const instance = this;

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

					document.addEventListener('keydown', (event) => {
						const baseCellEditorPopup = document.querySelector(
							'.basecelleditor'
						);

						if (
							baseCellEditorPopup &&
							!baseCellEditorPopup.contains(event.target) &&
							event.key === 'Enter'
						) {
							baseCellEditorPopup.classList.add(
								'base-cell-editor-hidden'
							);
						}

						const scriptEditorPopup = document.querySelector(
							'.script-cell-editor'
						);

						if (
							scriptEditorPopup &&
							!scriptEditorPopup.contains(event.target) &&
							event.key === 'Enter'
						) {
							scriptEditorPopup.classList.add(
								'script-cell-editor-hidden'
							);
						}
					});
				},

				setEditorContent(content) {
					const instance = this;

					const editor = instance.editor;

					editor.set('value', content);
				},

				showEditor() {
					const instance = this;

					let editor = instance.editor;

					if (!editor) {
						editor = new A.AceEditor(
							instance.get('aceEditorConfig')
						).render();

						instance.editor = editor;
					}

					let content = instance.get('definition');

					if (!content || XMLUtil.validateDefinition(content)) {
						content = instance.getContent();
					}

					editor.set('value', content);

					if (instance.get('readOnly')) {
						editor.set('readOnly', true);
					}
				},

				showErrorMessage(message) {
					Liferay.Util.openToast({
						container: document.querySelector(
							'.lfr-alert-container'
						),
						message,
						type: 'danger',
					});
				},
			},
		});

		KaleoDesigner.AVAILABLE_FIELDS = {
			DEFAULT: [
				{
					iconClass: 'diamond',
					label: Liferay.Language.get('condition-node'),
					type: 'condition',
				},
				{
					iconClass: 'arrow-end',
					label: Liferay.Language.get('end-node'),
					type: 'end',
				},
				{
					iconClass: 'arrow-split',
					label: Liferay.Language.get('fork-node'),
					type: 'fork',
				},
				{
					iconClass: 'arrow-join',
					label: Liferay.Language.get('join-node'),
					type: 'join',
				},
				{
					iconClass: 'arrow-xor',
					label: Liferay.Language.get('join-xor-node'),
					type: 'join-xor',
				},
				{
					iconClass: 'arrow-start',
					label: Liferay.Language.get('start-node'),
					type: 'start',
				},
				{
					iconClass: 'circle',
					label: Liferay.Language.get('state-node'),
					type: 'state',
				},
				{
					iconClass: 'square',
					label: Liferay.Language.get('task-node'),
					type: 'task',
				},
			],
		};

		KaleoDesigner.AVAILABLE_PROPERTY_MODELS = {
			DEFAULT: {},

			KALEO_FORMS_EDIT: {
				task(model, parentModel) {
					const instance = this;

					const strings = instance.getStrings();

					return parentModel.concat(model).concat([
						{
							attributeName: 'assignments',
							editor: new KaleoDesignerEditors.AssignmentsEditor(),
							formatter: PropertyListFormatter.assignmentsType,
							name: strings.assignments,
						},
					]);
				},
			},
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
			'liferay-util-window',
		],
	}
);
