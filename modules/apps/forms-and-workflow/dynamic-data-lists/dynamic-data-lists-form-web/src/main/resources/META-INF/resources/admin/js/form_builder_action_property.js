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
					destructor: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						var index = instance.get('index');

						boundingBox.one('.additional-info-' + index).empty();

						boundingBox.one('.target-' + index).empty();
					},

					getValue: function() {
						var instance = this;

						return {
							target: instance._field.getValue()
						};
					},

					render: function() {
						var instance = this;

						var index = instance.get('index');

						var fieldsListContainer = instance.get('boundingBox').one('.target-' + index);

						instance._createField().render(fieldsListContainer);
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
								bubbleTargets: [instance],
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