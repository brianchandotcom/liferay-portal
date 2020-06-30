AUI.add(
	'liferay-ddm-form-field-password',
	function(A) {
		var PasswordField = A.Component.create(
			{
				ATTRS: {
					type: {
						value: 'password'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-password',

				prototype: {
				}
			}
		);

		Liferay.namespace('DDM.Field').Password = PasswordField;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field']
	}
);