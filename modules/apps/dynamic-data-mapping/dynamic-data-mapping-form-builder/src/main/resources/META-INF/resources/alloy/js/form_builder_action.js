AUI.add(
	'liferay-ddm-form-builder-action',
	function(A) {
		var listType = ['select', 'radio', 'options', 'checkbox'];

		var FormBuilderAction = A.Component.create(
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
					}
				},

				AUGMENTS: [],

				NAME: 'liferay-ddm-form-builder-action',

				prototype: {
					conditionChange: function() {},

					createSelectField: function(context) {
						var instance = this;

						var config = A.merge(
							context,
							{
								bubbleTargets: [instance],
								context: A.clone(context)
							}
						);

						return new Liferay.DDM.Field.Select(config);
					},

					createTextField: function(context) {
						var instance = this;

						var config = A.merge(
							context,
							{
								bubbleTargets: [instance],
								context: A.clone(context)
							}
						);

						return new Liferay.DDM.Field.Text(config);
					},

					getFieldsByType: function(type) {
						var instance = this;

						var fields = instance.get('fields');

						var fieldsFiltered = [];

						for (var i = 0; i < fields.length; i++) {
							if (type === 'list') {
								if (listType.indexOf(fields[i].type) !== -1) {
									fieldsFiltered.push(fields[i]);
								}
							}
							else if (type) {
								fieldsFiltered.push(fields[i]);
							}
						}

						return fieldsFiltered;
					},

					getValue: function() {},

					hideMessageField: function(index) {
						var instance = this;

						var boundingBox = instance.get('boundingBox');

						var fieldMessageContainer = boundingBox.one('.target-message-' + index);

						fieldMessageContainer.hide();
						fieldMessageContainer.html('');
					}
				}
			}
		);

		Liferay.namespace('DDM').FormBuilderAction = FormBuilderAction;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field']
	}
);