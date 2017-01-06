AUI.add(
	'liferay-ddl-form-builder-action-property',
	function(A) {
		var FormBuilderActionProperty = A.Component.create(
			{
				ATTRS: {
					action: {
						value: ''
					},

					index: {
						value: ''
					},

					options: {
						value: []
					},

					type: {
						value: ''
					}
				},

				AUGMENTS: [],

				NAME: 'liferay-ddl-form-builder-action-property',

				prototype: {
					getValue: function() {
						var instance = this;

						var type = instance.get('type');

						return {
							action: type,
							target: instance._field.getValue()
						};
					},

					render: function() {
						var instance = this;

						instance._createField().render(instance.get('boundingBox'));
					},

					_createField: function() {
						var instance = this;

						var value;

						var action = instance.get('action');

						if (action && action.target) {
							value = action.target;
						}

						instance._field = new Liferay.DDM.Field.Select(
							{
								fieldName: instance.get('index') + '-action',
								label: Liferay.Language.get('the'),
								options: instance.get('options'),
								showLabel: false,
								value: value,
								visible: true
							}
						);

						return instance._field;
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderActionProperty = FormBuilderActionProperty;
	},
	'',
	{
		requires: ['liferay-ddl-form-builder-action']
	}
);