AUI.add(
	'liferay-admin',
	function(A) {
		var AObject = A.Object;
		var Lang = A.Lang;

		var STR_CLICK = 'click';

		var CONSECUTIVE_ERROR_THRESHOLD = 10;

		var MESSAGES = {
			'downloading-xuggler': Liferay.Language.get('downloading-xuggler'),
			'copying-xuggler': Liferay.Language.get('copying-xuggler'),
			'completed': Liferay.Language.get('completed'),
			'an-unexpected-error-occurred-while-installing-xuggler': 'an-unexpected-error-occurred-while-installing-xuggler'
		}

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

						var installXugglerButton = container.one('#installXugglerButton');

						if (installXugglerButton) {
							installXugglerButton.on(STR_CLICK, instance._installXugglerButton, instance);
						}
					},

					destructor: function() {
						var instance = this;

						Liferay.Poller.removeListener(instance._portletId);
					},

					_finishPoller: function(json) {
						var instance = this;

						var xugglerProgressInfo = instance._container.one('#xugglerProgressInfo');

						if (json.success) {
							xugglerProgressInfo.html(Liferay.Language.get('xuggler-has-been-installed-you-need-to-reboot-your-server-to-apply-changes'));

							xugglerProgressInfo.removeClass('portlet-msg-progress').addClass("portlet-msg-success");
						}
						else {
							xugglerProgressInfo.html(Liferay.Language.get('an-unexpected-error-occurred-while-installing-xuggler') + ': ' + json.exception);

							xugglerProgressInfo.removeClass('portlet-msg-progress').addClass("portlet-msg-error");
						}

						Liferay.Poller.removeListener(instance._portletId);
					},

					_installXugglerButton: function() {
						var instance = this;

						instance._form.get(instance.ns('cmd')).val('installXuggler');

						var ioRequest = A.io.request(
							instance._url,
							{
								dataType: 'json',
								method: 'post',
								form: instance._form.getDOMNode(),
								on: {
									failure: function(event, id, obj) {
										instance._finishPoller(this.get('responseData'));
									},
									success: function(event, id, obj) {
										instance._finishPoller(this.get('responseData'));
									}
								}
							}
						);

						instance._startMonitoring();
					},

					_onPollerUpdate: function(response, chunkId) {
						var instance = this;

						var xugglerProgressInfo = instance._container.one('#xugglerProgressInfo');

						if (response.status.success) {
							instance._consecutiveError = 0;

							xugglerProgressInfo.html(MESSAGES[(response.status.status)]);
						}
						else {
							instance._consecutiveError = instance._consecutiveError + 1;
						}

						if (instance._consecutiveError > CONSECUTIVE_ERROR_THRESHOLD) {
							var json = {
								'error': 'an-unexpected-error-occurred-while-installing-xuggler'
							};

							instance._finishPoller(json);
						}
					},

					_startMonitoring: function() {
						var instance = this;

						Liferay.Poller.addListener(instance._portletId, instance._onPollerUpdate, instance);

						var xugglerProgressInfo = instance._container.one('#xugglerProgressInfo');

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
		requires: ['aui-base', 'liferay-poller', 'liferay-portlet-base']
	}
);