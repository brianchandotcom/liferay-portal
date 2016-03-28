AUI.add(
	'liferay-ddl-form-builder',
	function(A) {
		var AArray = A.Array;

		var FieldTypes = Liferay.DDM.Renderer.FieldTypes;

		var FormBuilderUtil = Liferay.DDL.FormBuilderUtil;

		var Lang = A.Lang;

		var CSS_FORM_BUILDER_TABS = A.getClassName('form', 'builder', 'tabs');

		var CSS_PAGE_HEADER = A.getClassName('form', 'builder', 'pages', 'header');

		var CSS_PAGES = A.getClassName('form', 'builder', 'pages', 'lexicon');

		var CSS_ROW_CONTAINER_ROW = A.getClassName('layout', 'row', 'container', 'row');

		var TPL_REQURIED_FIELDS = '<label class="hide required-warning">{message}</label>';

		var FormBuilder = A.Component.create(
			{
				ATTRS: {
					container: {
						getter: function() {
							var instance = this;

							return instance.get('contentBox');
						}
					},

					dataProviders: {
					},

					definition: {
						validator: Lang.isObject
					},

					deserializer: {
						valueFn: '_valueDeserializer'
					},

					evaluatorURL: {
					},

					fieldTypes: {
						setter: '_setFieldTypes',
						valueFn: '_valueFieldTypes'
					},

					layouts: {
						valueFn: '_valueLayouts'
					},

					pagesJSON: {
						validator: Array.isArray,
						value: []
					},

					portletNamespace: {
					},

					strings: {
						value: {
							addColumn: Liferay.Language.get('add-column'),
							addField: Liferay.Language.get('add-field'),
							cancelRemoveRow: Liferay.Language.get('cancel'),
							confirmRemoveRow: Liferay.Language.get('yes-delete'),
							formTitle: Liferay.Language.get('build-your-form'),
							modalHeader: Liferay.Language.get('remove-confirmation'),
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

				AUGMENTS: [Liferay.DDL.FormBuilderLayoutBuilderSupport, Liferay.DDM.Renderer.NestedFieldsSupport],

				CSS_PREFIX: 'form-builder',

				EXTENDS: A.FormBuilder,

				NAME: 'liferay-ddl-form-builder',

				prototype: {
					TPL_PAGES: '<div class="' + CSS_PAGES + '" ></div>',

					initializer: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						var settingsModal = instance.getFieldSettingModal();

						instance._eventHandlers = [
							boundingBox.delegate('click', instance._onClickPaginationItem, '.pagination li a'),
							instance.after('liferay-ddl-form-builder-field-list:fieldsChange', instance._afterFieldListChange, instance),
							instance.after('render', instance._afterFormBuilderRender, instance),
							instance.after(instance._afterRemoveField, instance, 'removeField'),
							settingsModal.after('hide', A.bind(instance._afterFieldSettingsModalHide, instance)),
							settingsModal.after('save', A.bind(instance._afterFieldSettingsModalSave, instance))
						];

						instance._overwriteFieldToolbar();
					},

					destructor: function() {
						var instance = this;

						var visitor = instance.get('visitor');

						visitor.set('fieldHandler', instance.destroyField);

						visitor.visit();

						(new A.EventHandle(instance._eventHandlers)).detach();
					},

					contains: function(field) {
						var instance = this;

						var contains = false;

						var visitor = instance.get('visitor');

						visitor.set('pages', instance.get('layouts'));

						visitor.set(
							'fieldHandler',
							function(currentField) {
								if (currentField === field) {
									contains = true;
								}
							}
						);

						visitor.visit();

						return contains;
					},

					createField: function(fieldType) {
						var instance = this;

						var fieldClass = FormBuilderUtil.getFieldClass(fieldType.get('name'));

						return new fieldClass(
							A.merge(
								fieldType.get('defaultConfig'),
								{
									builder: instance,
									dataProviders: instance.get('dataProviders'),
									evaluatorURL: instance.get('evaluatorURL'),
									portletNamespace: instance.get('portletNamespace'),
									readOnly: true
								}
							)
						);
					},

					destroyField: function(field) {
						var instance = this;

						field.destroy();
					},

					editField: function(field) {
						var instance = this;

						var fieldType = instance.findTypeOfField(field);

						instance.showFieldSettingsPanel(
							field,
							Lang.sub(
								Liferay.Language.get('edit-x'),
								[fieldType.get('label')]
							)
						);
					},

					findTypeOfField: function(field) {
						var instance = this;

						return FieldTypes.get(field.get('type'));
					},

					getFieldSettingModal: function() {
						var instance = this;

						if (!instance._fieldSettingsModal) {
							instance._fieldSettingsModal = new Liferay.DDL.FormBuilderFieldSettingsModal(
								{
									portletNamespace: instance.get('portletNamespace')
								}
							);
						}

						return instance._fieldSettingsModal;
					},

					showFieldSettingsPanel: function(field, typeName) {
						var instance = this;

						var settingsModal = instance.getFieldSettingModal();

						settingsModal.show(field, typeName);
					},

					_addFieldsChangeListener: function(layouts) {
						var instance = this;

						layouts.forEach(
							function(layout) {
								instance._fieldsChangeHandles.push(
									layout.after(
										'liferay-ddl-form-builder-field-list:fieldsChange',
										A.bind(instance._afterFieldsChange, instance)
									)
								);
							}
						);
					},

					_afterActivePageNumberChange: function() {
						var instance = this;

						FormBuilder.superclass._afterActivePageNumberChange.apply(instance, arguments);

						instance._syncRequiredFieldsWarning();
						instance._syncRowsLastColumnUI();
					},

					_afterFieldListChange: function() {
						var instance = this;

						instance._syncRequiredFieldsWarning();
					},

					_afterFieldSettingsModalSave: function(event) {
						var instance = this;

						FormBuilder.superclass._afterFieldSettingsModalSave.apply(instance, arguments);

						var field = event.field;

						instance.appendChild(field);

						instance._syncRequiredFieldsWarning();

						var row = instance.getFieldRow(field);

						instance.getActiveLayout().normalizeColsHeight(new A.NodeList(row));
					},

					_afterFormBuilderRender: function() {
						var instance = this;

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
					},

					_afterSelectFieldType: function(event) {
						var instance = this;

						var fieldType = event.fieldType;

						var field = instance.createField(fieldType);

						instance.hideFieldsPanel();

						instance.showFieldSettingsPanel(
							field,
							Lang.sub(
								Liferay.Language.get('add-x'),
								[fieldType.get('label')]
							)
						);
					},

					_getPageManagerInstance: function(config) {
						var instance = this;

						var contentBox = instance.get('contentBox');

						if (!instance._pageManager) {
							instance._pageManager = new Liferay.DDL.FormBuilderPagesManager(
								A.merge(
									{
										builder: instance,
										mode: 'wizard',
										pageHeader: contentBox.one('.' + CSS_PAGE_HEADER),
										pagesQuantity: instance.get('layouts').length,
										paginationContainer: contentBox.one('.' + CSS_PAGES),
										tabviewContainer: contentBox.one('.' + CSS_FORM_BUILDER_TABS)
									},
									config
								)
							);
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

					_insertRemoveRowButton: function(layoutRow, row) {
						var instance = this;

						var deleteButton = row.ancestor('.' + CSS_ROW_CONTAINER_ROW).all('.layout-builder-remove-row-button');

						if (deleteButton) {
							deleteButton.empty();
							deleteButton.insert(Liferay.Util.getLexiconIconTpl('trash'));
						}
					},

					_makeEmptyFieldList: function(col) {
						col.set('value', new Liferay.DDL.FormBuilderFieldList());
					},

					_onClickPaginationItem: function(event) {
						var instance = this;

						event.halt();
					},

					_overwriteFieldToolbar: function() {
						var instance = this;

						instance._fieldToolbar.destroy();

						instance._fieldToolbar = new Liferay.DDL.FormBuilderFieldToolbar(instance.get('fieldToolbarConfig'));
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

						field.set('builder', instance);

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

						var deserializer = instance.get('deserializer');

						var pages = instance.get('pages');

						pages.set('descriptions', deserializer.get('descriptions'));
						pages.set('titles', deserializer.get('titles'));

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

					_setFieldToolbarConfig: function() {
						var instance = this;

						return A.merge(
							FormBuilder.superclass._setFieldToolbarConfig.apply(instance, arguments),
							{
								items: [
									Liferay.DDL.FormBuilderFieldToolbar.ITEM_EDIT,
									Liferay.DDL.FormBuilderFieldToolbar.ITEM_MOVE,
									Liferay.DDL.FormBuilderFieldToolbar.ITEM_REMOVE,
									Liferay.DDL.FormBuilderFieldToolbar.ITEM_CLOSE
								]
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

						visitor.set(
							'fieldHandler',
							function(field) {
								var fieldVisible = boundingBox.contains(field.get('container'));

								if (fieldVisible && field.get('required')) {
									hasRequiredField = true;
								}
							}
						);

						visitor.visit();

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

						return new Liferay.DDL.LayoutDeserializer(
							{
								builder: instance,
								definition: instance.get('definition')
							}
						);
					},

					_valueFieldTypes: function() {
						var instance = this;

						return FieldTypes.getAll();
					},

					_valueFieldTypesModal: function() {
						var instance = this;

						var strings = A.merge(
							instance.get('strings'),
							{
								addField: Liferay.Language.get('choose-a-field-type')
							}
						);

						var fieldTypesModal = new Liferay.DDL.FormBuilderFieldTypesModal(
							{
								draggable: false,
								fieldTypes: instance.get('fieldTypes'),
								modal: true,
								portletNamespace: instance.get('portletNamespace'),
								resizable: false,
								strings: strings,
								visible: false
							}
						);

						fieldTypesModal.addTarget(this);

						return fieldTypesModal;
					},

					_valueLayouts: function() {
						var instance = this;

						var deserializer = instance.get('deserializer');

						deserializer.set('pages', instance.get('pagesJSON'));

						return deserializer.deserialize();
					},

					_valueVisitor: function() {
						var instance = this;

						return new Liferay.DDL.LayoutVisitor(
							{
								pages: instance.get('layouts')
							}
						);
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilder = FormBuilder;
	},
	'',
	{
		requires: ['aui-form-builder', 'aui-form-builder-pages', 'aui-popover', 'liferay-ddl-form-builder-field-settings-modal', 'liferay-ddl-form-builder-field-support', 'liferay-ddl-form-builder-field-type', 'liferay-ddl-form-builder-field-types-modal', 'liferay-ddl-form-builder-layout-deserializer', 'liferay-ddl-form-builder-layout-visitor', 'liferay-ddl-form-builder-pages-manager', 'liferay-ddl-form-builder-util', 'liferay-ddm-form-field-types', 'liferay-ddm-form-renderer']
	}
);