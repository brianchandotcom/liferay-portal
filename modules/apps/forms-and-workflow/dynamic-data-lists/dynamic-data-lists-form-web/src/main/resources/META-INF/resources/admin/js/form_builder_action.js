AUI.add(
	'liferay-ddl-form-builder-action',
	function(A) {
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

				NAME: 'liferay-ddl-form-builder-action',

				prototype: {
					getValue: function() {}
				}
			}
		);

		Liferay.namespace('DDL').FormBuilderAction = FormBuilderAction;
	},
	'',
	{
		requires: ['liferay-ddl-form-builder-render-template', 'liferay-ddm-form-renderer-field']
	}
);