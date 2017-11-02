AUI.add(
	'${artifactId}-form-field',
	function(A) {
		var ${className}Field = A.Component.create(
			{
				ATTRS: {
					type: {
						value: '${artifactId}-form-field'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: '${artifactId}-form-field'

			}
		);

		Liferay.namespace('DDM.Field').${className} = ${className}Field;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field']
	}
);