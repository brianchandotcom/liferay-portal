AUI.add(
	'liferay-ddm-form-field-select',
	function(A) {
		var AArray = A.Array;
		var Lang = A.Lang;

		var SelectField = A.Component.create(
			{
				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-select',

				prototype: {
					getTemplateContext: function() {
						var instance = this;

						return A.merge(
								SelectField.superclass.getTemplateContext.apply(instance, arguments),
							{
								options: []
							}
						);
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Select = SelectField;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field']
	}
);