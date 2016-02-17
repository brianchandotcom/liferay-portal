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

					visitor: {
						getter: '_getVisitor',
						valueFn: '_valueVisitor'
					}
				},

				AUGMENTS: [Liferay.DDM.Renderer.NestedFieldsSupport],

				CSS_PREFIX: 'form-builder',

				EXTENDS: A.FormBuilder,

				NAME: 'liferay-ddl-form-builder',

				prototype: {
					TPL_PAGES: '<div class="' + CSS_PAGES + '" ></div>',

					initializer: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						instance._eventHandlers = [
							boundingBox.delegate('click', instance._onClickPaginationItem, '.pagination li a')
						];
					},

					renderUI: function() {
						var instance = this;

						FormBuilder.superclass.renderUI.apply(instance, arguments);

						instance._renderFields();
						instance._renderPages();
						instance._syncRowsLastColumnUI();
					},

					destructor: function() {
						var instance = this;

						var visitor = instance.get('visitor');

						visitor.set('fieldHandler', instance.destroyField);

						visitor.visit();
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
								Liferay.Language.get('edit-x-field'),
								[fieldType.get('label')]
							)
						);
					},

					findTypeOfField: function(field) {
						var instance = this;

						return FieldTypes.get(field.get('type'));
					},

					_afterActivePageNumberChange: function() {
						var instance = this;

						FormBuilder.superclass._afterActivePageNumberChange.apply(instance, arguments);

						instance._syncRowsLastColumnUI();
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

						instance._syncRowsLastColumnUI();
					},

					_afterSelectFieldType: function(event) {
						var instance = this;

						var fieldType = event.fieldType;

						var field = instance.createField(fieldType);

						instance.hideFieldsPanel();

						field.set(
							'toolbarFooter',
							[
								{
									label: Liferay.Language.get('add')
								}
							]
						);

						instance.showFieldSettingsPanel(
							field,
							Lang.sub(
								Liferay.Language.get('add-x-field'),
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

					_onClickPaginationItem: function(event) {
						var instance = this;

						event.halt();
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

					_setFieldToolbarConfig: function() {
						var instance = this;

						return A.merge(
							FormBuilder.superclass._setFieldToolbarConfig.apply(instance, arguments),
							{
								items: [
									A.FormBuilderFieldToolbar.ITEM_EDIT,
									A.FormBuilderFieldToolbar.ITEM_MOVE,
									A.FormBuilderFieldToolbar.ITEM_REMOVE,
									A.FormBuilderFieldToolbar.ITEM_CLOSE
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

						var fieldTypesModal = new Liferay.DDL.FormBuilderFieldTypesModal(
							{
								draggable: false,
								fieldTypes: instance.get('fieldTypes'),
								modal: true,
								portletNamespace: instance.get('portletNamespace'),
								resizable: false,
								strings: {
									addField: Liferay.Language.get('choose-a-field-type')
								},
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
		requires: ['aui-form-builder', 'aui-form-builder-pages', 'liferay-ddl-form-builder-field-support', 'liferay-ddl-form-builder-field-types-modal', 'liferay-ddl-form-builder-layout-deserializer', 'liferay-ddl-form-builder-layout-visitor', 'liferay-ddl-form-builder-pages-manager', 'liferay-ddl-form-builder-util', 'liferay-ddm-form-field-types', 'liferay-ddm-form-renderer']
	}
);