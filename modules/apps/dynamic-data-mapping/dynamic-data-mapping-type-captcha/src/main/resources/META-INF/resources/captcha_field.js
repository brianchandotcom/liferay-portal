AUI.add(
	'liferay-ddm-form-field-captcha',
	function(A) {
		var Lang = A.Lang;

		var CaptchaField = A.Component.create(
			{
				ATTRS: {
					context: {
						getter: '_getContext'
					},

					html: {
						value: ''
					},

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

					getChangeEventName: function() {
						return 'input';
					},

					getInputSelector: function() {
						var instance = this;

						var container = instance.get('container');

						return '#' + container.one('input').attr('id');
					},

					hasErrors: function() {
						var instance = this;

						var hasErrors = CaptchaField.superclass.hasErrors.apply(instance, arguments);

						var inputNode = instance.getInputNode();

						return inputNode && !inputNode.val() || hasErrors;
					},

					render: function() {
						var instance = this;

						var container = instance.get('container');

						container.plug(A.Plugin.ParseContent);

						return CaptchaField.superclass.render.apply(instance, arguments);
					},

					showErrorMessage: Lang.emptyFn,

					_getContext: function(context) {
						var instance = this;

						if (!context) {
							return {};
						}
						else if (context.html) {
							return A.merge(
								context,
								{
									html: window.DDMCaptcha.render.Soy.toIncDom(context.html.content)
								}
							);
						}
						return context;
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
		requires: ['aui-parse-content', 'liferay-ddm-form-renderer-field']
	}
);