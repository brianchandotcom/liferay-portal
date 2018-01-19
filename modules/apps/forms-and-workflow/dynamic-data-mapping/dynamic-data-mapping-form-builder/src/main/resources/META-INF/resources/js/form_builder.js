AUI.add(
	'liferay-ddm-form-builder',
	function(A) {
		var AArray = A.Array;

		var CSS_DELETE_FIELD_BUTTON = A.getClassName('lfr-delete-field');

		var CSS_FIELD = A.getClassName('form', 'builder', 'field');

		var CSS_FORM_BUILDER_TABS = A.getClassName('form', 'builder', 'tabs');

		var CSS_PAGE_HEADER = A.getClassName('form', 'builder', 'pages', 'header');

		var CSS_PAGES = A.getClassName('form', 'builder', 'pages', 'lexicon');

		var CSS_RESIZE_COL_DRAGGABLE = A.getClassName('layout', 'builder', 'resize', 'col', 'draggable');

		var CSS_RESIZE_COL_DRAGGABLE_BORDER = A.getClassName('layout', 'builder', 'resize', 'col', 'draggable', 'border');

		var CSS_RESIZE_COL_DRAGGABLE_HANDLE = A.getClassName('layout', 'builder', 'resize', 'col', 'draggable', 'handle');

		var CSS_ROW_CONTAINER_ROW = A.getClassName('layout', 'row', 'container', 'row');

		var FormBuilderConfirmDialog = Liferay.DDM.FormBuilderConfirmationDialog;

		var FormBuilderUtil = Liferay.DDM.FormBuilderUtil;

		var FieldSets = Liferay.DDM.FieldSets;

		var FieldTypes = Liferay.DDM.Renderer.FieldTypes;

		var Lang = A.Lang;

		var MOVE_COLUMN_CONTAINER = '<div class="' + CSS_RESIZE_COL_DRAGGABLE_BORDER + '"></div><div class="' + CSS_RESIZE_COL_DRAGGABLE_HANDLE + '">' + Liferay.Util.getLexiconIconTpl('horizontal-scroll') + '</div>';

		var MOVE_COLUMN_TPL = '<div class="' + CSS_RESIZE_COL_DRAGGABLE + ' lfr-tpl">' + MOVE_COLUMN_CONTAINER + '</div>';

		var TPL_CONFIRM_CANCEL_FIELD_EDITION = '<p>' + Liferay.Language.get('are-you-sure-you-want-to-cancel') + '</p>';

		var TPL_CONFIRM_DELETE_FIELD = '<p>' + Liferay.Language.get('are-you-sure-you-want-to-delete-this-field') + '</p>';

		var TPL_REQURIED_FIELDS = '<label class="hide required-warning">{message}</label>';

		var Util = Liferay.DDM.Renderer.Util;

		var FormBuilder = A.Component.create(
			{
				ATTRS: {
					container: {
						getter: function() {
							var instance = this;

							return instance.get('contentBox');
						}
					},

					context: {
						value: {}
					},

					defaultLanguageId: {
						value: themeDisplay.getDefaultLanguageId()
					},

					deserializer: {
						valueFn: '_valueDeserializer'
					},

					editingLanguageId: {
						value: themeDisplay.getDefaultLanguageId()
					},

					fieldSets: {
						valueFn: '_valueFieldSets'
					},

					fieldSettingsPanel: {
						getter: '_getFieldSettingsPanel',
						valueFn: '_valueFieldSettingsPanel'
					},

					fieldTypes: {
						setter: '_setFieldTypes',
						valueFn: '_valueFieldTypes'
					},

					fieldTypesPanel: {
						getter: '_getFieldTypesPanel',
						valueFn: '_valueFieldTypesPanel'
					},

					layouts: {
						valueFn: '_valueLayouts'
					},

					pageManager: {
						value: {}
					},

					formInstanceId: {
						value: 0
					},

					showPagination: {
						value: true
					},

					strings: {
						value: {
							addColumn: Liferay.Language.get('add-column'),
							addField: Liferay.Language.get('add-field'),
							cancelRemoveRow: Liferay.Language.get('cancel'),
							confirmRemoveRow: Liferay.Language.get('delete'),
							formTitle: Liferay.Language.get('build-your-form'),
							modalHeader: Liferay.Language.get('delete-row'),
							pasteHere: Liferay.Language.get('paste-here'),
							removeRowModal: Liferay.Language.get('you-will-also-delete-fields-with-this-row-are-you-sure-you-want-delete-it')
						},
						writeOnce: true
					},

					visitor: {
						getter: '_getVisitor',
						valueFn: '_valueVisitor'
					}
				},

				AUGMENTS: [Liferay.DDM.FormBuilderLayoutBuilderSupport, Liferay.DDM.Renderer.NestedFieldsSupport],

				CSS_PREFIX: 'form-builder',

				EXTENDS: A.FormBuilder,

				NAME: 'liferay-ddm-form-builder',

				prototype: {
					TPL_PAGES: '<div class="' + CSS_PAGES + '" ></div>',

					initializer: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						instance._eventHandlers = [
							boundingBox.delegate('click', A.bind('_afterFieldClick', instance), '.' + CSS_FIELD, instance),
							boundingBox.delegate('click', instance._onClickPaginationItem, '.pagination li a'),
							boundingBox.delegate('click', instance._removeFieldCol, '.' + CSS_DELETE_FIELD_BUTTON, instance),
							instance.after('editingLanguageIdChange', instance._afterEditingLanguageIdChange),
							instance.after('liferay-ddm-form-builder-field-list:fieldsChange', instance._afterFieldListChange, instance),
							instance.after('render', instance._afterFormBuilderRender, instance),
							instance.after(instance._afterRemoveField, instance, 'removeField')
						];
					},

					destructor: function() {
						var instance = this;

						var visitor = instance.get('visitor');

						visitor.set('fieldHandler', instance.destroyField);

						instance._sidebar.destroy();

						visitor.visit();

						(new A.EventHandle(instance._eventHandlers)).detach();
					},

					cancelFieldEdition: function(field) {
						var instance = this;

						var fieldSettingsPanel = instance.getFieldSettingsPanel();

						var fieldContext = fieldSettingsPanel.getPreviousContext();

						if (fieldSettingsPanel.hasChanges()) {
							instance.openConfirmCancelFieldChangesDiolog(
								function() {
									instance.confirmCancelFieldChanges(field, fieldContext, fieldSettingsPanel);

									fieldSettingsPanel.close();
								}
							);
						}
						else {
							fieldSettingsPanel.close();
						}
					},

					confirmCancelFieldChanges: function(field, fieldContext, fieldSettingsPanel) {
						var instance = this;

						var settingForm = fieldSettingsPanel.settingsForm;

						settingForm.set('context', fieldSettingsPanel._previousFormContext);

						field.set('context', fieldContext);
						field.set('context.settingsContet', fieldContext);

						field.render();
					},

					contains: function(field) {
						var instance = this;

						var contains = false;

						instance.eachFields(
							function(currentField) {
								if (currentField === field) {
									contains = true;
								}
							}
						);

						return contains;
					},

					createField: function(fieldType, config) {
						var instance = this;

						var fieldClass = FormBuilderUtil.getFieldClass(fieldType.get('name'));

						return new fieldClass(
							A.merge(
								fieldType.get('defaultConfig'),
								{
									builder: instance,
									evaluatorURL: instance.get('evaluatorURL'),
									readOnly: true
								},
								config
							)
						);
					},

					createFieldFromContext: function(fieldContext) {
						var instance = this;

						var newFieldName = fieldContext.fieldName + Util.generateInstanceId(6);

						var config = A.merge(
							fieldContext,
							{
								fieldName: newFieldName,
								name: newFieldName
							}
						);

						delete config.settingsContext;

						var field = instance.createField(FieldTypes.get(fieldContext.type), config);

						field.set(
							'context',
							A.merge(
								config,
								{
									portletNamespace: Liferay.DDM.Settings.portletNamespace
								}
							)
						);

						var settingsContext = fieldContext.settingsContext;

						FormBuilderUtil.visitLayout(
							settingsContext.pages,
							function(settingsFieldContext) {
								var fieldName = settingsFieldContext.fieldName;

								if (fieldName === 'name') {
									settingsFieldContext.value = newFieldName;
								}
							}
						);

						field.set('context.settingsContext', settingsContext);

						return field;
					},

					createFieldSet: function(fieldSetDefinition) {
						var instance = this;

						var visitor = new Liferay.DDM.LayoutVisitor();

						visitor.set('pages', fieldSetDefinition.pages);

						var fieldColumns = [];

						visitor.set(
							'fieldHandler',
							function(fieldContext) {
								var field = instance.createFieldFromContext(fieldContext);

								fieldColumns.push(field);

								field.render();
							}
						);

						var layoutColumns = [];

						visitor.set(
							'columnHandler',
							function(column) {
								var layoutColumn = new A.LayoutCol(
									{
										size: column.size,
										value: new Liferay.DDM.FormBuilderFieldList(
											{
												fields: fieldColumns
											}
										)
									}
								);

								layoutColumns.push(layoutColumn);

								fieldColumns = [];
							}
						);

						visitor.set(
							'rowHandler',
							function(row) {
								var layout = instance.getActiveLayout();

								layout.addRow(
									instance._currentRowIndex(),
									new A.LayoutRow({cols: layoutColumns})
								);

								layoutColumns = [];
							}
						);

						visitor.visit();
					},

					createNewField: function(fieldType) {
						var instance = this;

						var field = instance.createField(fieldType);

						instance._insertField(field);

						field.newField = true;

						instance.showFieldSettingsPanel(field);
					},

					destroyField: function(field) {
						var instance = this;

						field.destroy();
					},

					duplicateField: function(field) {
						var instance = this;

						var fieldCopy = field.copy();

						fieldCopy.render();

						var fieldList = field.get('container').ancestor('.col').getData('layout-col').get('value');

						fieldList.addField(fieldCopy, fieldList.get('fields').length);

						instance.showFieldSettingsPanel(fieldCopy);

						var activeLayout = instance.getActiveLayout();

						var row = instance.getFieldRow(field);

						activeLayout.normalizeColsHeight(new A.NodeList(row));
					},

					eachFields: function(callback) {
						var instance = this;

						var visitor = instance.get('visitor');

						visitor.set('pages', instance.get('layouts'));

						visitor.set('fieldHandler', callback);

						visitor.visit();
					},

					editField: function(field) {
						var instance = this;

						instance.showFieldSettingsPanel(field);
					},

					findField: function(fieldName, ignoreCase) {
						var instance = this;

						var field;

						var visitor = instance.get('visitor');

						visitor.set(
							'fieldHandler',
							function(currentField) {
								var currentFieldName = currentField.get('context.fieldName');

								if (currentFieldName) {
									if (currentFieldName === fieldName) {
										field = currentField;
									}
									else if (ignoreCase && currentFieldName.toLowerCase() === fieldName.toLowerCase()) {
										field = currentField;
									}
								}
							}
						);

						visitor.visit();

						return field;
					},

					findTypeOfField: function(field) {
						var instance = this;

						return FieldTypes.get(field.get('type'));
					},

					getFieldSettingsPanel: function() {
						var instance = this;

						return instance.get('fieldSettingsPanel');
					},

					getFieldTypesPanel: function() {
						var instance = this;

						return instance.get('fieldTypesPanel');
					},

					getPagesTitle: function() {
						var instance = this;

						return instance._getPageManagerInstance().get('titles');
					},

					getSuccessPageDefinition: function() {
						var instance = this;

						var pageManager = instance._getPageManagerInstance();

						return pageManager.getSuccessPageDefinition();
					},

					isEditMode: function() {
						var instance = this;

						var translating = instance.get('defaultLanguageId') !== instance.get('editingLanguageId');

						return instance.get('formInstanceId') > 0 || translating;
					},

					openConfirmCancelFieldChangesDiolog: function(confirmFn) {
						var instance = this;

						var config = {
							body: TPL_CONFIRM_CANCEL_FIELD_EDITION,
							confirmFn: confirmFn,
							id: 'cancelFieldChangesDialog',
							labelHTML: Liferay.Language.get('yes-cancel'),
							title: Liferay.Language.get('cancel-field-changes-question')
						};

						FormBuilderConfirmDialog.open(config);
					},

					openConfirmDeleteFieldDialog: function(confirmFn) {
						var instance = this;

						var config = {
							body: TPL_CONFIRM_DELETE_FIELD,
							confirmFn: confirmFn,
							id: 'deleteFieldDialog',
							labelHTML: Liferay.Language.get('yes-delete'),
							title: Liferay.Language.get('delete-field-dialog-title'),
							width: 300
						};

						FormBuilderConfirmDialog.open(config);
					},

					showFieldSettingsPanel: function(field) {
						var instance = this;

						if (instance._sidebar) {
							instance._sidebar.close();
						}

						var settingsPanel = instance.getFieldSettingsPanel();

						settingsPanel.set('field', field);

						settingsPanel.open();
					},

					showFieldTypesPanel: function() {
						var instance = this;

						var fieldTypesPanel = instance.getFieldTypesPanel();

						fieldTypesPanel.open();
					},

					_addFieldsChangeListener: function(layouts) {
						var instance = this;

						layouts.forEach(
							function(layout) {
								instance._fieldsChangeHandles.push(
									layout.after(
										'liferay-ddm-form-builder-field-list:fieldsChange',
										A.bind(instance._afterFieldsChange, instance)
									)
								);
							}
						);
					},

					_afterActivePageNumberChange: function(event) {
						var instance = this;

						if (event.newVal > instance.get('layouts').length) {
							instance.fire(
								'successPageVisibility',
								{
									visible: true
								}
							);
						}
						else {
							instance.fire(
								'successPageVisibility',
								{
									visible: false
								}
							);

							FormBuilder.superclass._afterActivePageNumberChange.apply(instance, arguments);

							instance._syncRequiredFieldsWarning();
							instance._syncRowsLastColumnUI();
						}
					},

					_afterEditingLanguageIdChange: function(event) {
						var instance = this;

						instance.eachFields(
							function(field) {
								field.set('locale', event.newVal);

								field.saveSettings();
							}
						);

						var pageManager = instance.get('pageManager');

						pageManager.set('editingLanguageId', event.newVal);
					},

					_afterFieldClick: function(event) {
						var instance = this;

						var field = event.currentTarget.getData('field-instance');

						if (event.target.ancestor('.lfr-ddm-field-actions-container')) {
							return;
						}
						instance.editField(field);
					},

					_afterFieldListChange: function() {
						var instance = this;

						instance._syncRequiredFieldsWarning();
					},

					_afterFormBuilderRender: function() {
						var instance = this;

						instance._fieldToolbar.destroy();

						instance.getFieldSettingsPanel();
						instance._renderArrowActions();
						instance._renderFields();
						instance._renderPages();
						instance._renderRequiredFieldsWarning();
						instance._syncRequiredFieldsWarning();
						instance._syncRowsLastColumnUI();
						instance._syncRowIcons();
					},

					_afterLayoutColsChange: function(event) {
						var instance = this;

						FormBuilder.superclass._afterLayoutColsChange.apply(instance, arguments);

						instance._syncRowLastColumnUI(event.target);
					},

					_afterLayoutRowsChange: function(event) {
						var instance = this;

						FormBuilder.superclass._afterLayoutRowsChange.apply(instance, arguments);

						event.newVal.forEach(instance._syncRowLastColumnUI);
					},

					_afterLayoutsChange: function() {
						var instance = this;

						FormBuilder.superclass._afterLayoutsChange.apply(instance, arguments);

						instance._syncRequiredFieldsWarning();
						instance._syncRowsLastColumnUI();
					},

					_afterRemoveField: function(field) {
						var instance = this;

						instance.removeChild(field);

						instance.getFieldSettingsPanel().close();
					},

					_afterSelectFieldType: function(event) {
						var instance = this;

						instance.createNewField(event.fieldType);
					},

					_createFieldActions: function() {
						var instance = this;

						instance.eachFields(
							function(field) {
								var container = field.get('container');

								container.append(instance._getFieldActionsLayout());
							}
						);
					},

					_currentRowIndex: function() {
						var instance = this;

						var layout = instance.getActiveLayout();

						var rows = layout.get('rows');

						if (A.instanceOf(instance._newFieldContainer.get('value'), A.FormBuilderFieldList)) {
							var row = instance._newFieldContainer.get('value').get('content').ancestor('.row').getData();

							return A.Array.indexOf(rows, row);
						}

						if (rows.length > 0) {
							return rows.length - 1;
						}

						return 0;
					},

					_getFieldActionsLayout: function() {
						var instance = this;

						return '<div class="lfr-ddm-field-actions-container"> ' +
							'<button class="btn btn-monospaced btn-sm label-primary lfr-duplicate-field" type="button">' + Liferay.Util.getLexiconIconTpl('paste') + '</button>' +
							'<button class="btn btn-monospaced btn-sm label-primary lfr-delete-field" type="button">' + Liferay.Util.getLexiconIconTpl('trash') + '</button>' +
							'</div>';
					},

					_getFieldSettingsPanel: function(fieldSettingsPanel) {
						var instance = this;

						instance._sidebar = fieldSettingsPanel;

						return fieldSettingsPanel;
					},

					_getFieldTypesPanel: function(fieldTypesPanel) {
						var instance = this;

						instance._sidebar = fieldTypesPanel;

						return fieldTypesPanel;
					},

					_getPageManagerInstance: function(config) {
						var instance = this;

						var contentBox = instance.get('contentBox');

						var deserializer = instance.get('deserializer');

						var layouts = instance.get('layouts');

						if (!instance._pageManager) {
							var context = instance.get('context');

							instance._pageManager = new Liferay.DDM.FormBuilderPagesManager(
								A.merge(
									{
										builder: instance,
										defaultLanguageId: instance.get('defaultLanguageId'),
										editingLanguageId: instance.get('editingLanguageId'),
										localizedDescriptions: deserializer.get('descriptions'),
										localizedTitles: deserializer.get('titles'),
										mode: context.paginationMode,
										pageHeader: contentBox.one('.' + CSS_PAGE_HEADER),
										pagesQuantity: layouts.length,
										paginationContainer: contentBox.one('.' + CSS_PAGES),
										showPagination: instance.get('showPagination'),
										tabviewContainer: contentBox.one('.' + CSS_FORM_BUILDER_TABS)
									},
									config
								)
							);

							instance._pageManager.setSuccessPage(context.successPageSettings);

							instance.set('pageManager', instance._pageManager);
						}

						return instance._pageManager;
					},

					_getVisitor: function(visitor) {
						var instance = this;

						visitor.set('pages', instance.get('layouts'));

						return visitor;
					},

					_insertCutRowIcon: function(row) {
						var instance = this;

						var cutButton = row.ancestor('.' + CSS_ROW_CONTAINER_ROW).one('.layout-builder-move-cut-button');

						if (cutButton) {
							cutButton.insert(Liferay.Util.getLexiconIconTpl('cut'));
						}
					},

					_insertField: function(field) {
						var instance = this;

						field.set(
							'context',
							{
								label: '',
								placeholder: '',
								portletNamespace: Liferay.DDM.Settings.portletNamespace,
								readOnly: true,
								showLabel: true,
								type: field.get('type'),
								value: '',
								visible: true
							}
						);

						if (this._newFieldContainer) {
							if (A.instanceOf(this._newFieldContainer.get('value'), A.FormBuilderFieldList)) {
								this._newFieldContainer.get('value').addField(field);
								this._newFieldContainer.set('removable', false);
							}
							else {
								this._addNestedField(
									this._newFieldContainer,
									field,
									this._newFieldContainer.get('nestedFields').length
								);
							}
							this._newFieldContainer = null;
						}

						instance._syncRequiredFieldsWarning();

						instance._renderField(field);
					},

					_insertRemoveRowButton: function(layoutRow, row) {
						var instance = this;

						var deleteButton = row.ancestor('.' + CSS_ROW_CONTAINER_ROW).all('.layout-builder-remove-row-button');

						if (deleteButton) {
							deleteButton.empty();
							deleteButton.insert(Liferay.Util.getLexiconIconTpl('trash'));
						}
					},

					_makeEmptyFieldList: function(col) {
						col.set('value', new Liferay.DDM.FormBuilderFieldList());
					},

					_onClickPaginationItem: function(event) {
						var instance = this;

						event.halt();
					},

					_openNewFieldPanel: function(target) {
						var instance = this;

						var ancestorCol = target.ancestor('.col');

						instance._newFieldContainer = ancestorCol.getData('layout-col');
						instance.showFieldTypesPanel();
					},

					_removeFieldCol: function(event) {
						var instance = this;

						var fieldNode = event.currentTarget.ancestor('.' + CSS_FIELD);

						var field = fieldNode.getData('field-instance');

						if (field) {
							var content = field.get('content');

							var ancestor = content.ancestor('.col');

							field._col = ancestor.getData('layout-col');

							instance.openConfirmDeleteFieldDialog(
								function() {
									field._col.get('value').removeField(field);

									var layout = instance.getActiveLayout();

									layout.normalizeColsHeight(new A.NodeList(content.ancestor('.layout-row')));

									fieldNode.remove();

									var fieldSettingsPanel = instance.getFieldSettingsPanel();

									fieldSettingsPanel.close();
								}
							);
						}
					},

					_renderArrowActions: function() {
						var instance = this;

						var layoutBuilder = instance._layoutBuilder;

						layoutBuilder.TPL_RESIZE_COL_DRAGGABLE = MOVE_COLUMN_TPL;
						layoutBuilder._uiSetEnableResizeCols(layoutBuilder.get('enableResizeCols'));

						var boundingBox = instance.get('boundingBox');

						boundingBox.all('.' + CSS_RESIZE_COL_DRAGGABLE + ':not(.lfr-tpl)').each(
							function(handler) {
								handler.html(MOVE_COLUMN_CONTAINER);
							}
						);
					},

					_renderContentBox: function() {
						var instance = this;

						var contentBox = instance.get('contentBox');

						var strings = instance.get('strings');

						var headerTemplate = A.Lang.sub(
							instance.TPL_HEADER,
							{
								formTitle: strings.formTitle
							}
						);

						contentBox.append(instance.TPL_TABVIEW);
						contentBox.append(instance.TPL_PAGE_HEADER);
						contentBox.append(headerTemplate);
						contentBox.append(instance.TPL_LAYOUT);
						contentBox.append(instance.TPL_PAGES);
					},

					_renderField: function(field) {
						var instance = this;

						var activeLayout = instance.getActiveLayout();

						field.set('builder', instance);

						field.after(
							'render',
							function() {
								var row = instance.getFieldRow(field);

								activeLayout.normalizeColsHeight(new A.NodeList(row));
								field.get('container').append(instance._getFieldActionsLayout());
							}
						);

						field.render();
					},

					_renderFields: function() {
						var instance = this;

						var visitor = instance.get('visitor');

						visitor.set('fieldHandler', A.bind('_renderField', instance));

						visitor.visit();
					},

					_renderPages: function() {
						var instance = this;

						var pages = instance.get('pages');

						pages._uiSetActivePageNumber(pages.get('activePageNumber'));
					},

					_renderRequiredFieldsWarning: function() {
						var instance = this;

						var pageManager = instance._getPageManagerInstance();

						if (!instance._requiredFieldsWarningNode) {
							instance._requiredFieldsWarningNode = A.Node.create(
								Lang.sub(
									TPL_REQURIED_FIELDS,
									{
										message: Lang.sub(
											Liferay.Language.get('all-fields-marked-with-x-are-required'),
											['<i class="icon-asterisk text-warning"></i>']
										)
									}
								)
							);
						}

						instance._requiredFieldsWarningNode.appendTo(pageManager.get('pageHeader'));
					},

					_renderRowIcons: function() {
						var instance = this;

						var rows = A.all('.layout-row');

						rows.each(
							function(row) {
								instance._insertCutRowIcon(row);
								instance._insertRemoveRowButton(null, row);
							}
						);
					},

					_setFieldTypes: function(fieldTypes) {
						var instance = this;

						return AArray.filter(
							fieldTypes,
							function(item) {
								return !item.get('system');
							}
						);
					},

					_syncRequiredFieldsWarning: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						var hasRequiredField = false;

						var visitor = instance.get('visitor');

						visitor.set('pages', instance.get('layouts'));

						instance.eachFields(
							function(field) {
								var fieldVisible = boundingBox.contains(field.get('container'));

								if (fieldVisible && field.get('required')) {
									hasRequiredField = true;
								}
							}
						);

						instance._requiredFieldsWarningNode.toggle(hasRequiredField);
					},

					_syncRowIcons: function() {
						var instance = this;

						instance._renderRowIcons();

						instance._layoutBuilder.after(instance._insertCutRowIcon, instance._layoutBuilder, '_insertCutButtonOnRow');

						instance._layoutBuilder.after(instance._insertRemoveRowButton, instance._layoutBuilder, '_insertRemoveButtonBeforeRow');
					},

					_syncRowLastColumnUI: function(row) {
						var lastColumn = row.get('node').one('.last-col');

						if (lastColumn) {
							lastColumn.removeClass('last-col');
						}

						var cols = row.get('cols');

						cols[cols.length - 1].get('node').addClass('last-col');
					},

					_syncRowsLastColumnUI: function() {
						var instance = this;

						var rows = instance.getActiveLayout().get('rows');

						rows.forEach(instance._syncRowLastColumnUI);
					},

					_valueDeserializer: function() {
						var instance = this;

						return new Liferay.DDM.LayoutDeserializer(
							{
								builder: instance
							}
						);
					},

					_valueFieldSets: function() {
						var instance = this;

						return FieldSets.getAll();
					},

					_valueFieldSettingsPanel: function() {
						var instance = this;

						var fieldSettingsPanel = new Liferay.DDM.FormBuilderFieldsSettingsSidebar(
							{
								builder: instance
							}
						);

						fieldSettingsPanel.render('#wrapper');

						return fieldSettingsPanel;
					},

					_valueFieldTypes: function() {
						var instance = this;

						return FieldTypes.getAll();
					},

					_valueFieldTypesPanel: function() {
						var instance = this;

						var fieldTypesPanel = new Liferay.DDM.FormBuilderFieldTypesSidebar(
							{
								builder: instance,
								fieldSets: instance.get('fieldSets'),
								fieldTypes: instance.get('fieldTypes')
							}
						);

						fieldTypesPanel.render('#wrapper');

						return fieldTypesPanel;
					},

					_valueLayouts: function() {
						var instance = this;

						var deserializer = instance.get('deserializer');

						var context = instance.get('context');

						deserializer.set('pages', context.pages);

						return deserializer.deserialize();
					},

					_valueVisitor: function() {
						var instance = this;

						return new Liferay.DDM.FormBuilderLayoutVisitor(
							{
								pages: instance.get('layouts')
							}
						);
					}
				}
			}
		);

		Liferay.namespace('DDM').FormBuilder = FormBuilder;
	},
	'',
	{
		requires: ['aui-form-builder', 'aui-form-builder-pages', 'aui-popover', 'liferay-ddm-form-builder-confirmation-dialog', 'liferay-ddm-form-builder-field-list', 'liferay-ddm-form-builder-field-options-toolbar', 'liferay-ddm-form-builder-field-settings-sidebar', 'liferay-ddm-form-builder-field-support', 'liferay-ddm-form-builder-field-type', 'liferay-ddm-form-builder-field-types-sidebar', 'liferay-ddm-form-builder-fieldset', 'liferay-ddm-form-builder-layout-deserializer', 'liferay-ddm-form-builder-layout-visitor', 'liferay-ddm-form-builder-pages-manager', 'liferay-ddm-form-builder-util', 'liferay-ddm-form-field-types', 'liferay-ddm-form-renderer', 'liferay-ddm-form-renderer-layout-visitor', 'liferay-ddm-form-renderer-util']
	}
);