AUI.add(
	'liferay-admin',
	function(A) {
		var AObject = A.Object;
		var Lang = A.Lang;
		var Poller = Liferay.Poller;

		var ERROR_THRESHOLD = 10;

		var STR_CLICK = 'click';

		var STR_DISABLED = 'disabled';

		var STR_PORTLET_MSG_ERROR = 'portlet-msg-error';

		var STR_PORTLET_MSG_PROGRESS = 'portlet-msg-progress';

		var STR_PORTLET_MSG_SUCCESS = 'portlet-msg-success';

		var MESSAGES = {
			'downloading-xuggler': Liferay.Language.get('downloading-xuggler'),
			'copying-xuggler': Liferay.Language.get('copying-xuggler'),
			'completed': Liferay.Language.get('completed'),
			'an-unexpected-error-occurred-while-installing-xuggler': Liferay.Language.get('an-unexpected-error-occurred-while-installing-xuggler')
		};

		var Admin = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				ATTRS: {
					form: {
						setter: A.one,
						value: null
					},

					url: {
						value: null
					}
				},

				EXTENDS: A.Base,

				NAME: 'admin',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._errorCount = 0;

						var eventHandles = [];

						var installXugglerButton = instance.one('#installXugglerButton');

						if (installXugglerButton) {
							eventHandles.push(
								installXugglerButton.on(STR_CLICK, instance._installXuggler, instance)
							);

							instance._installXugglerButton = installXugglerButton;

							instance._xugglerProgressInfo = instance.one('#xugglerProgressInfo');

							instance._eventHandles = eventHandles;
						}
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');

						Poller.removeListener(instance.ID);
					},

					_installXuggler: function() {
						var instance = this;

						var xugglerProgressInfo = instance._xugglerProgressInfo;

						Liferay.Util.toggleDisabled(instance._installXugglerButton, true);

						var form = instance.get('form');

						form.get(instance.ns('cmd')).val('installXuggler');

						var ioRequest = A.io.request(
							instance.get('url'),
							{
								autoLoad: false,
								dataType: 'json',
								form: form.getDOM()
							}
						);

						ioRequest.on(['failure', 'success'], instance._onIOResponse, instance);

						A.config.win[instance.ns('xugglerProgressInfo')].startProgress();

						ioRequest.start();
					},

					_onIOResponse: function(event) {
						var instance = this;

						var responseData = event.currentTarget.get('responseData');

						var progressBar = instance.one('#xugglerProgressInfoBar');

						progressBar.hide();

						if (responseData.success) {
							var xugglerProgressInfo = instance._xugglerProgressInfo;

							xugglerProgressInfo.html(Liferay.Language.get('xuggler-has-been-installed-you-need-to-reboot-your-server-to-apply-changes'));

							xugglerProgressInfo.addClass(STR_PORTLET_MSG_SUCCESS);
						}
						else {
							var xugglerProgressInfo = instance._xugglerProgressInfo;

							xugglerProgressInfo.html(Liferay.Language.get('an-unexpected-error-occurred-while-installing-xuggler') + ': ' + responseData.exception);

							xugglerProgressInfo.addClass(STR_PORTLET_MSG_ERROR);
						}
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