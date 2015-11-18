AUI.add(
	'liferay-ddm-form-field-captcha',
	function(A) {
		var CaptchaField = A.Component.create(
			{
				ATTRS: {
					type: {
						value: 'captcha'
					}
				},

				EXTENDS: Liferay.DDM.Renderer.Field,

				NAME: 'liferay-ddm-form-field-captcha',

				prototype: {
					getTemplateRenderer: function() {
						var instance = this;

						return A.bind('renderTemplate', instance);
					},

					getValue: function() {
						return '';
					},

					renderTemplate: function() {
						var instance = this;

						return instance._valueContainer().html();
					}
				}
			}
		);

		Liferay.namespace('DDM.Field').Captcha = CaptchaField;
	},
	'',
	{
		requires: ['liferay-ddm-form-renderer-field']
	}
);