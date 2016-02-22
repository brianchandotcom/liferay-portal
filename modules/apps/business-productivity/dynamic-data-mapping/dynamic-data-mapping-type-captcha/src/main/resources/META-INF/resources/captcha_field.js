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
					initializer: function() {
						var instance = this;

						instance._eventHandlers.push(
							instance.bindContainerEvent('click', instance._onClickRefresh, '.icon-refresh')
						);
					},

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
					},

					_onClickRefresh: function() {
						var instance = this;

						var container = instance.get('container');

						var captchaNode = container.one('.captcha');

						var baseURL = captchaNode.attr('src');

						var url = Liferay.Util.addParams('t=' + Date.now(), baseURL);

						captchaNode.attr('src', url);
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