AUI.add(
	'liferay-ddl-form-builder-action-jump-to-page',
	function(A) {
		var Lang = A.Lang;

		var TPL_ACTION_FIELD_LABEL = '<label class="lfr-ddm-form-field-container-inline">{message}</label>';

		var FormBuilderActionJumpToPage = A.Component.create(
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

					strings: {
						value: {
							from: Liferay.Language.get('from'),
							to: Liferay.Language.get('to')
						}
					}
				},

				AUGMENTS: [],

				EXTENDS: Liferay.DDL.FormBuilderAction,

				NAME: 'liferay-ddl-form-builder-action-jump-to-page',

				prototype: {
					getValue: function() {
						var instance = this;

						return {
							action: 'jump-to-page',
							source: instance._sourceField.getValue(),
							target: instance._targetField.getValue()
						};
					},

					render: function() {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						var strings = instance.get('strings');

						instance._createLabel(strings['from']);
						instance._createSourceField().render(boundingBox);
						instance._createLabel(strings['to']);
						instance._createTargetField().render(boundingBox);
					},

					_createLabel: function(text) {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						var label =	A.Node.create(
							Lang.sub(
								TPL_ACTION_FIELD_LABEL,
								{
									message: text
								}
							)
						);

						boundingBox.append(label);
					},

					_createSourceField: function() {
						var instance = this;

						var value;

						var action = instance.get('action');

						if (action && action.source) {
							value = action.source;
						}

						instance._sourceField = new Liferay.DDM.Field.Select(
							{
								fieldName: instance.get('index') + '-action',
								label: Liferay.Language.get('the'),
								options: instance.get('options'),
								showLabel: false,
								value: value,
								visible: true
							}
						);

						instance._sourceField.get('container').addClass('lfr-ddm-form-field-container-inline');

						return instance._sourceField;
					},

					_createTargetField: function() {
						var instance = this;

						var value;

						var action = instance.get('action');

						if (action && action.target) {
							value = action.target;
						}

						instance._targetField = new Liferay.DDM.Field.Select(
							{
								fieldName: instance.get('index') + '-action',
								label: Liferay.Language.get('the'),
								options: instance.get('options'),
								showLabel: false,
								value: value,
								visible: true
							}
						);

						instance._targetField.get('container').addClass('lfr-ddm-form-field-container-inline');

						return instance._targetField;
					}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderActionJumpToPage = FormBuilderActionJumpToPage;
	},
	'',
	{
		requires: ['liferay-ddl-form-builder-action']
	}
);