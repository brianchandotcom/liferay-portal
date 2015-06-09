AUI.add(
	'liferay-ddl-form-builder',
	function(A) {
		var AArray = A.Array;
		var FieldTypes = Liferay.DDM.Renderer.FieldTypes;
		var FormBuilderField = Liferay.DDL.FormBuilderField;
		var FormBuilderUtil = Liferay.DDL.FormBuilderUtil;
		var Lang = A.Lang;

		var FormBuilder = A.Component.create(
			{
				ATTRS: {
					definition: {
						validator: function(val) {
							return Lang.isObject(val);
						}
					},

					deserializer: {
						valueFn: '_valueDeserializer'
					},

					fieldTypes: {
						getter: function() {
							return FieldTypes.getAll();
						}
					},

					layouts: {
						setter: '_setLayouts',
						validator: function(val) {
							return Lang.isObject(val);
						}
					},

					portletNamespace: {
					}
				},

				CSS_PREFIX: 'form-builder',

				EXTENDS: A.FormBuilder,

				NAME: 'liferay-ddl-form-builder',

				prototype: {
					initializer: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						instance.after('field:saveSettings', instance._afterFieldSettingsModalSave, instance);

						boundingBox.delegate('click', instance._onClickPaginationItem, '.pagination li a');
					},

					renderUI: function() {
						var instance = this;

						var deserializer = instance.get('deserializer');

						FormBuilder.superclass.renderUI.apply(instance, arguments);

						var pages = instance._pages;

						pages.set('descriptions', deserializer.get('descriptions'));
						pages.set('titles', deserializer.get('titles'));

						pages._uiSetActivePageNumber(pages.get('activePageNumber'));
					},

					destructor: function() {
						var instance = this;

						new Liferay.DDL.LayoutVisitor(
							{
								columnHandler: A.bind('_columnDestructor', instance),
								layouts: instance.get('layouts')
							}
						).visit();
					},

					_afterFieldSettingsModalSave: function(event) {
						var instance = this;

						var field = event.field;

						FormBuilder.superclass._afterFieldSettingsModalSave.apply(instance, arguments);

						field.renderTemplate();
					},

					_columnDestructor: function(column) {
						var instance = this;

						var value = column.get('value');

						if (A.instanceOf(value, FormBuilderField)) {
							value.destroy();
						}

						column.destroy();
					},

					_onClickFieldType: function(event) {
						var instance = this;

						var fieldType = event.currentTarget.getData('fieldType');

						if (!fieldType.get('disabled')) {
							instance.hideFieldsPanel();

							var fieldClassName = fieldType.get('className');

							var fieldClass = FormBuilderUtil.getFieldClass(fieldClassName);

							var field = new fieldClass(fieldType.get('defaultConfig'));

							instance.showFieldSettingsPanel(field, fieldType.get('label'));
						}
					},

					_onClickPaginationItem: function(event) {
						var instance = this;

						event.halt();
					},

					_setLayouts: function(val) {
						var instance = this;

						if (val.pages) {
							var deserializer = instance.get('deserializer');

							deserializer.set('layouts', val.pages);

							val = deserializer.deserialize();
						}

						return val;
					},

					_valueDeserializer: function() {
						var instance = this;

						return new Liferay.DDL.LayoutDeserializer(
							{
								definition: instance.get('definition')
							}
						);
					},

					findTypeOfField: function(field) {
						var instance = this;

						return FieldTypes.get(field.get('fieldType'));
					},

					showFieldSettingsPanel: function(field, fieldTypeName) {
						var instance = this;

						field.addTarget(instance);
						field.showSettingsPanel(fieldTypeName);
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilder = FormBuilder;
	},
	'',
	{
		requires: ['aui-form-builder', 'aui-form-builder-pages', 'liferay-ddl-form-builder-field', 'liferay-ddl-form-builder-layout-deserializer', 'liferay-ddl-form-builder-layout-visitor', 'liferay-ddl-form-builder-util', 'liferay-ddm-form-field-types', 'liferay-ddm-form-renderer']
	}
);