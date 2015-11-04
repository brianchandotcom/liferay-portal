AUI.add(
	'liferay-admin',
	function(A) {
		var AObject = A.Object;
		var Lang = A.Lang;
		var Poller = Liferay.Poller;

		var STR_CLICK = 'click';

		var STR_PORTLET_MSG_ERROR = 'alert alert-danger';

		var STR_PORTLET_MSG_SUCCESS = 'alert alert-success';

		var WIN = A.config.win;

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

					submitButtonSelector: {
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

						var submitButton = instance.one(instance.get('submitButtonSelector'));

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

						var form = instance.get('form');

						var data = node.getData();

						for (var key in data) {
							if (data.hasOwnProperty(key)) {
								form.append('<input id="' + instance.ns(key) + '" name="' + instance.ns(key) + '" type="hidden" value="' + data[key] + '" />');
							}
						}
					},

					_installXuggler: function(event) {
						var instance = this;

						var xugglerProgressInfo = instance._xugglerProgressInfo;

						Liferay.Util.toggleDisabled(instance._installXugglerButton, true);

						var form = instance.get('form');

						var currentTarget = event.currentTarget;

						instance._addInputsFromData(currentTarget);

						var ioRequest = A.io.request(
							instance.get('url'),
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

						var cssClass = STR_PORTLET_MSG_ERROR;

						var message = '';

						if (responseData.success) {
							cssClass = STR_PORTLET_MSG_SUCCESS;

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

						var currentTarget = event.currentTarget;

						var form = instance.get('form');

						form.one('#' + instance.ns('redirect')).val(instance.get('redirectURL'));

						instance._addInputsFromData(currentTarget);

						submitForm(form, instance.get('url'));
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