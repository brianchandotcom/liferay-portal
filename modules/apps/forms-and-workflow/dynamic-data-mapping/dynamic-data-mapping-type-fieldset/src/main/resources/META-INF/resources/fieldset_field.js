AUI.add(
	'liferay-ddm-form-field-fieldset',
	function(A) {
		var FieldSetField = A.Component.create(
			{
				ATTRS: {
					fields: {
						setter: '_setFields',
						state: true,
						validator: Array.isArray,
						value: []
					},

					type: {
						value: 'fieldset'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-fieldset',

				prototype: {

					getValue: function() {
						return '';
					},

					setValue: function() {
					},

					_createNestedField: function(config) {
						var instance = this;

						var fieldClass = instance.getFieldClass();

						return new fieldClass(config);
					},

					_setFields: function(fields) {
						var instance = this;

						return fields.map(A.bind(instance._createNestedField, instance));
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').FieldSet = FieldSetField;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field']
	}
);