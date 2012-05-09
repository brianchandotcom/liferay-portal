AUI.add(
	'liferay-admin',
	function(A) {
		var AObject = A.Object;
		var Lang = A.Lang;
		var Poller = Liferay.Poller;

		var CONSECUTIVE_ERROR_THRESHOLD = 10;

		var CSS_BUTTON_DISABLED = 'aui-button-disabled';

		var SELECTOR_PROGRESS_INFO = '#xugglerProgressInfo';

		var STR_CLICK = 'click';

		var STR_DISABLED = 'disabled';

		var STR_PORTLET_MSG_ERROR = 'portlet-msg-error';

		var STR_PORTLET_MSG_PROGRESS = 'portlet-msg-progress';

		var STR_PORTLET_MSG_SUCCESS = 'portlet-msg-success';

		var MESSAGES = {
			'downloading-xuggler': Liferay.Language.get('downloading-xuggler'),
			'copying-xuggler': Liferay.Language.get('copying-xuggler'),
			'completed': Liferay.Language.get('completed'),
			'an-unexpected-error-occurred-while-installing-xuggler': 'an-unexpected-error-occurred-while-installing-xuggler'
		};

		var Admin = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'admin',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._portletId = config.portletId;

						instance._consecutiveError = 0;

						var container = A.one('#p_p_id_' + instance._portletId + '_');

						instance._container = container;

						instance._form = A.one(config.form);
						instance._url = config.url;

						var eventHandles = [];

						var installXugglerButton = container.one('#installXugglerButton');

						if (installXugglerButton) {
							eventHandles.push(
								installXugglerButton.on(STR_CLICK, instance._installXuggler, instance)
							);
						}

						instance._installXugglerButton = installXugglerButton;

						instance._xugglerProgressInfo = container.one(SELECTOR_PROGRESS_INFO);

						instance._installXugglerButtonWrapper = installXugglerButton.ancestor('.aui-button');

						instance._eventHandles = eventHandles;
					},

					destructor: function() {
						var instance = this;

						A.Array.invoke(instance._eventHandles, 'detach');

						Poller.removeListener(instance._portletId);
					},

					_finishPoller: function(json) {
						var instance = this;

						var xugglerProgressInfo = instance._xugglerProgressInfo;

						if (json.success) {
							xugglerProgressInfo.html(Liferay.Language.get('xuggler-has-been-installed-you-need-to-reboot-your-server-to-apply-changes'));

							xugglerProgressInfo.removeClass(STR_PORTLET_MSG_PROGRESS).addClass(STR_PORTLET_MSG_SUCCESS);
						}
						else {
							xugglerProgressInfo.html(Liferay.Language.get('an-unexpected-error-occurred-while-installing-xuggler') + ': ' + json.exception);

							xugglerProgressInfo.removeClass(STR_PORTLET_MSG_PROGRESS).addClass(STR_PORTLET_MSG_ERROR);
						}

						Poller.removeListener(instance._portletId);

						instance._installXugglerButton.removeAttribute(STR_DISABLED, true);

						instance._installXugglerButtonWrapper.removeClass(CSS_BUTTON_DISABLED);
					},

					_installXuggler: function() {
						var instance = this;

						var xugglerProgressInfo = instance._xugglerProgressInfo;

						xugglerProgressInfo.removeClass(STR_PORTLET_MSG_SUCCESS).removeClass(STR_PORTLET_MSG_ERROR);

						xugglerProgressInfo.addClass(STR_PORTLET_MSG_PROGRESS);

						instance._installXugglerButton.setAttribute(STR_DISABLED, true);

						instance._installXugglerButtonWrapper.addClass(CSS_BUTTON_DISABLED);

						instance._form.get(instance.ns('cmd')).val('installXuggler');

						var ioRequest = A.io.request(
							instance._url,
							{
								dataType: 'json',
								form: instance._form.getDOMNode(),
								autoLoad: false
							}
						);

						var onIOResponse = A.bind(instance._onIOResponse, instance, ioRequest);

						ioRequest.on(['failure', 'success'], onIOResponse);

						instance._startMonitoring();

						ioRequest.start();
					},

					_onIOResponse: function(ioRequest, event) {
						var instance = this;

						var response = ioRequest.get('responseData');

						instance._finishPoller(response);
					},

					_onPollerUpdate: function(response, chunkId) {
						var instance = this;

						var xugglerProgressInfo = instance._xugglerProgressInfo;

						if (response.status.success) {
							instance._consecutiveError = 0;

							xugglerProgressInfo.html(MESSAGES[(response.status.status)]);
						}
						else {
							instance._consecutiveError = instance._consecutiveError + 1;
						}

						if (instance._consecutiveError > CONSECUTIVE_ERROR_THRESHOLD) {
							instance._finishPoller(
								{
									error: 'an-unexpected-error-occurred-while-installing-xuggler'
								}
							);
						}
					},

					_startMonitoring: function() {
						var instance = this;

						Poller.addListener(instance._portletId, instance._onPollerUpdate, instance);

						var xugglerProgressInfo = instance._xugglerProgressInfo;

						xugglerProgressInfo.html(Liferay.Language.get('starting-the-installation'));

						xugglerProgressInfo.show();
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