AUI.add(
	'liferay-ddm-form-field-fieldset',
	function(A) {
		var Lang = A.Lang;

		var Renderer = Liferay.DDM.Renderer;

		var FieldSetUtil = Liferay.DDM.Field.FieldSetUtil;

		var Util = Renderer.Util;

		var FieldSetField = A.Component.create(
			{
				ATTRS: {
					context: {
						getter: '_getContext',
						value: {}
					},

					nestedFields: {
						setter: '_setNestedFields',
						state: true,
						validator: Array.isArray,
						value: []
					},

					showLabel: {
						value: true
					},

					type: {
						value: 'fieldset'
					},

					valid: {
						getter: '_getValid'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-fieldset',

				prototype: {

					initializer: function() {
						var instance = this;

						instance._eventHandlers.push(
							instance.after('contextChange', instance._afterContextChange)
						);
					},

					copyConfiguration: function() {
						var instance = this;

						var config = FieldSetField.superclass.copyConfiguration.apply(instance, arguments);

						config.context.nestedFields.forEach(
							function(field) {
								delete field.name;

								field.instanceId = Util.generateInstanceId(8);

								if (Lang.isArray(field.value)) {
									field.value = [];
								}
								else if (Lang.isObject(field.value)) {
									field.value = {};
								}
								else {
									field.value = '';
								}
							}
						);

						config.nestedFields = config.context.nestedFields;

						return config;
					},

					getTemplateContext: function() {
						var instance = this;

						var repeatedIndex = instance.get('repeatedIndex');

						var siblings = instance.getRepeatedSiblings();

						var showBorderBottom = repeatedIndex === siblings.length - 1;

						var showBorderTop = repeatedIndex === 0;

						return A.merge(
							FieldSetField.superclass.getTemplateContext.apply(instance, arguments),
							{
								showBorderBottom: showBorderBottom,
								showBorderTop: showBorderTop,
								showLabel: repeatedIndex === 0
							}
						);
					},

					getValue: function() {
						return '';
					},

					removeChild: function(field) {
						var instance = this;

						var context = instance.get('context');

						var nestedFields = context.nestedFields;

						var index = instance.indexOf(field);

						if (index > -1) {
							nestedFields.splice(index, 1);

							instance.set('nestedFields', nestedFields);
						}
					},

					render: function() {
						var instance = this;

						FieldSetField.superclass.render.apply(instance, arguments);

						instance.eachNestedField(
							function(field) {
								field.render();
							}
						);

						return instance;
					},

					repeat: function() {
						var instance = this;

						var siblings = instance.getRepeatedSiblings();

						var newField = FieldSetField.superclass.repeat.apply(instance, arguments);

						if (newField.get('repeatedIndex') === 1) {
							siblings[0].render();
						}
						else if (newField.get('repeatedIndex') === siblings.length - 1) {
							var index = siblings.indexOf(newField);

							siblings[index - 1].render();
						}

						return newField;
					},

					setValue: function() {
					},

					_afterContextChange: function(event) {
						var instance = this;

						var nestedFields = event.newVal.nestedFields;

						nestedFields.forEach(A.bind(instance._setNewNestedFieldContext, instance));
					},

					_createNestedField: function(config) {
						var instance = this;

						var FieldSetNestedField = FieldSetUtil.createFieldSetNestedField(config);

						var nestedFieldContext = A.merge(
							config,
							{
								context: A.clone(config),
								fieldName: Util.getFieldNameFromQualifiedName(config.name),
								parent: instance,
								portletNamespace: instance.get('portletNamespace'),
								repeatedIndex: 0
							}
						);

						return new FieldSetNestedField(nestedFieldContext);
					},

					_getContext: function(context) {
						var instance = this;

						if (context && context.nestedFields) {
							context.nestedFields.forEach(
								function(fieldContext) {
									var field = instance.getField(fieldContext.fieldName, fieldContext.instanceId);

									if (field) {
										A.mix(fieldContext, field.get('context'), true);
									}
								}
							);
						}

						return context;
					},

					_getValid: function() {
						return true;
					},

					_setNestedFields: function(nestedFields) {
						var instance = this;

						var componentFields = nestedFields.map(A.bind(instance._createNestedField, instance));

						instance.set('fields', componentFields);
					},

					_setNewNestedFieldContext: function(nestedField) {
						var instance = this;

						if (nestedField.name) {
							var name = Util.getFieldNameFromQualifiedName(nestedField.name);

							var field = instance.getField(name);

							if (field) {
								field.set('context', nestedField);
							}
						}
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').FieldSet = FieldSetField;
	},
	'',
	{
		requires: ['liferay-ddm-form-field-fieldset-util', 'liferay-ddm-form-renderer-field']
	}
);