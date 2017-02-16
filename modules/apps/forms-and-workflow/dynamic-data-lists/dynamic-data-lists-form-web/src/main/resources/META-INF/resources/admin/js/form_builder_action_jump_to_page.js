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
							to: Liferay.Language.get('to')
						}
					},

					type: {
						value: 'jump-to-page'
					}
				},

				AUGMENTS: [],

				EXTENDS: Liferay.DDL.FormBuilderAction,

				NAME: 'liferay-ddl-form-builder-action-jump-to-page',

				prototype: {
					getValue: function() {
						var instance = this;

						return {
							source: instance._sourceField.getValue(),
							target: instance._targetField.getValue()
						};
					},

					render: function() {
						var instance = this;

						var strings = instance.get('strings');

						instance._createSourceField().render(boundingBox);
						instance._createLabel(strings.to);
						instance._createTargetField().render(boundingBox);
					},

					updateSource: function(pages) {
						var instance = this;

						instance._setSourceField(String(Math.max(pages)));
					},

					_createLabel: function(text) {
						var instance = this;

						var label =	A.Node.create(
							Lang.sub(
								TPL_ACTION_FIELD_LABEL,
								{
									message: text
								}
							)
						);

						return label;
					},

					_createSourceField: function() {
						var instance = this;

						var value;

						var action = instance.get('action');

						if (action && action.source) {
							if (action.source.value) {
								value = action.source.value;
							}
							else {
								var options = instance.get('options');

								value = options[action.source].value;
							}
						}

						instance._sourceField = new Liferay.DDM.Field.Select(
							{
								bubbleTargets: [instance],
								fieldName: instance.get('index') + '-action',
								label: Liferay.Language.get('the'),
								options: instance.get('options'),
								showLabel: false,
								value: value,
								visible: false
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
							if (action.target.value) {
								value = action.target.value;
							}
							else {
								var options = instance.get('options');

								value = options[action.target].value;
							}
						}

						instance._targetField = new Liferay.DDM.Field.Select(
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

						instance._targetField.get('container').addClass('lfr-ddm-form-field-container-inline');

						return instance._targetField;
					},

					_setSourceField: function(pageIndex) {
						var instance = this;

						instance._sourceField.setValue(String(pageIndex));
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