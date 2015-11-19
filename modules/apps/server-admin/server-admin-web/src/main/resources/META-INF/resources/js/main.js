AUI.add(
	'liferay-admin',
	function(A) {
		var Lang = A.Lang;
		var Poller = Liferay.Poller;

		var CSS_ALERT = 'alert';

		var STR_CLICK = 'click';

		var STR_FORM = 'form';

		var STR_URL = 'url';

		var WIN = A.config.win;

		var CSS_ALERT_DANGER = CSS_ALERT + ' alert-danger';

		var CSS_ALERT_SUCCESS = CSS_ALERT + ' alert-success';

		var Admin = A.Component.create(
			{
				ATTRS: {
					form: {
						setter: A.one,
						value: null
					},

					redirectUrl: {
						validator: Lang.isString,
						value: null
					},

					submitButton: {
						validator: Lang.isString,
						value: null
					},

					url: {
						value: null
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'admin',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._errorCount = 0;

						instance._eventHandles = [];

						var submitButton = instance.one(instance.get('submitButton'));

						if (submitButton) {
							instance._eventHandles.push(
								submitButton.on(STR_CLICK, instance._submitForm, instance)
							);
						}

						var installXugglerButton = instance.one('#installXugglerButton');

						if (installXugglerButton) {
							instance._eventHandles.push(
								installXugglerButton.on(STR_CLICK, instance._installXuggler, instance)
							);

							instance._installXugglerButton = installXugglerButton;

							instance._xugglerProgressInfo = instance.one('#xugglerProgressInfo');
						}
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');

						Poller.removeListener(instance.ID);
					},

					_addInputsFromData: function(node) {
						var instance = this;

						var form = instance.get(STR_FORM);

						var data = node.getData();

						for (var key in data) {
							if (data.hasOwnProperty(key)) {
								var namespaceKey = instance.ns(key);

								form.append('<input id="' + namespaceKey + '" name="' + namespaceKey + '" type="hidden" value="' + data[key] + '" />');
							}
						}
					},

					_installXuggler: function(event) {
						var instance = this;

						Liferay.Util.toggleDisabled(instance._installXugglerButton, true);

						var form = instance.get(STR_FORM);

						instance._addInputsFromData(event.currentTarget);

						var ioRequest = A.io.request(
							instance.get(STR_URL),
							{
								autoLoad: false,
								dataType: 'JSON',
								form: form.getDOM()
							}
						);

						ioRequest.on(['failure', 'success'], instance._onIOResponse, instance);

						WIN[instance.ns('xugglerProgressInfo')].startProgress();

						ioRequest.start();
					},

					_onIOResponse: function(event) {
						var instance = this;

						var responseData = event.currentTarget.get('responseData');

						var progressBar = instance.one('#xugglerProgressInfoBar');

						progressBar.hide();

						WIN[instance.ns('xugglerProgressInfo')].fire('complete');

						var xugglerProgressInfo = instance._xugglerProgressInfo;

						var cssClass = CSS_ALERT_DANGER;

						var message = '';

						if (responseData.success) {
							cssClass = CSS_ALERT_SUCCESS;

							message = Liferay.Language.get('xuggler-has-been-installed-you-need-to-reboot-your-server-to-apply-changes');
						}
						else {
							message = Liferay.Language.get('an-unexpected-error-occurred-while-installing-xuggler') + ': ' + responseData.exception;
						}

						xugglerProgressInfo.html(message);

						xugglerProgressInfo.addClass(cssClass);
					},

					_submitForm: function(event) {
						var instance = this;

						var form = instance.get(STR_FORM);

						var redirect = instance.one('#redirect', form);

						if (redirect) {
							redirect.val(instance.get('redirectURL'));
						}

						instance._addInputsFromData(event.currentTarget);

						submitForm(
							form,
							instance.get(STR_URL)
						);
					}
				}
			}
		);

		Liferay.Portlet.Admin = Admin;
	},
	'',
	{
		requires: ['liferay-poller', 'liferay-portlet-base']
	}
);