AUI.add(
	'liferay-ddl-form-builder',
	function(A) {
		var AArray = A.Array;
		var FieldTypes = Liferay.DDM.Renderer.FieldTypes;
		var FormBuilderUtil = Liferay.DDL.FormBuilderUtil;
		var Lang = A.Lang;

		var FormBuilder = A.Component.create(
			{
				ATTRS: {
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

					visitor: {
						getter: '_getVisitor',
						valueFn: '_valueVisitor'
					}
				},

				CSS_PREFIX: 'form-builder',

				EXTENDS: A.FormBuilder,

				NAME: 'liferay-ddl-form-builder',

				prototype: {
					initializer: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						boundingBox.delegate('click', instance._onClickPaginationItem, '.pagination li a');
					},

					renderUI: function() {
						var instance = this;

						FormBuilder.superclass.renderUI.apply(instance, arguments);

						instance._renderFields();
						instance._renderPages();
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
									builder: instance
								}
							)
						);
					},

					destroyField: function(field) {
						var instance = this;

						field.destroy();
					},

					findTypeOfField: function(field) {
						var instance = this;

						return FieldTypes.get(field.get('type'));
					},

					getField: function(name) {
						var instance = this;

						return AArray.find(
							instance.getFields(),
							function(item) {
								return item.get('name') === name;
							}
						);
					},

					getFields: function() {
						var instance = this;

						var fields = [];

						var visitor = instance.get('visitor');

						visitor.set(
							'fieldHandler',
							function(field) {
								fields.push(field);
							}
						);

						visitor.visit();

						return fields;
					},

					_getVisitor: function(visitor) {
						var instance = this;

						visitor.set('pages', instance.get('layouts'));

						return visitor;
					},

					_onClickFieldType: function(event) {
						var instance = this;

						var fieldType = event.currentTarget.getData('fieldType');

						instance.hideFieldsPanel();

						instance.showFieldSettingsPanel(
							instance.createField(fieldType),
							fieldType.get('label')
						);
					},

					_onClickPaginationItem: function(event) {
						var instance = this;

						event.halt();
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

					_setFieldTypes: function(fieldTypes) {
						var instance = this;

						return AArray.filter(
							fieldTypes,
							function(item) {
								return !item.get('system');
							}
						);
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
		requires: ['aui-form-builder', 'aui-form-builder-pages', 'liferay-ddl-form-builder-field', 'liferay-ddl-form-builder-layout-deserializer', 'liferay-ddl-form-builder-layout-visitor', 'liferay-ddl-form-builder-util', 'liferay-ddm-form-field-types', 'liferay-ddm-form-renderer']
	}
);