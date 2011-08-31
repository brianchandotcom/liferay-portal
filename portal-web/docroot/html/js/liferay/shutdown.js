AUI.add(
	'liferay-shutdown',
	function(A) {
		var Shutdown = {
			init: function(portletId) {
				var instance = this;

				instance._portletId = portletId;

				instance._shutdownText = Liferay.Language.get('the-portal-will-shutdown-for-maintenance-in-x-minutes');

				instance._startPolling();
			},

			send: function(options, id) {
				var instance = this;

				Liferay.Poller.submitRequest(instance._portletId, options, id);
			},

			_formatNumber: function(num) {
				var instance = this;

				if (!Liferay.Util.isArray(num)) {
					if (num <= 9) {
						num = '0' + num;
					}
				}
				else {
					num = A.Array.map(num, instance._formatNumber);
				}

				return num;
			},

			_setTime: function() {
				var instance = this;

				var amount = instance._currentTime;

				if (amount > 0) {
					var days=0, hours=0, minutes=0, seconds=0, output='';

					amount = Math.floor(amount / 1000);

					hours = Math.floor(amount / 3600);
					amount = amount % 3600;

					minutes = Math.floor(amount / 60);
					amount = amount % 60;

					seconds = Math.floor(amount);

					return instance._formatNumber([hours, minutes, seconds]).join(':');
				}
			},

			_onPollerUpdate: function(response, chunkId) {
				var instance = this;

				if (!instance._isShutdown && response.shutdown.process > 0) {
					instance._currentTime = response.shutdown.process;

					var shutdownText = A.substitute(instance._shutdownText, ['<span class="countdown-timer"></span>', response.shutdown.process]);

					if (response.shutdown.message != '') {
						shutdownText = shutdownText + '<span class="custom-shutdown-message"> ' + response.shutdown.message + ' </span>';
					}

					instance._warningShutdown(shutdownText);

					instance._isShutdown = true;
				}
			},

			_shutdownCounter: function(shutdownTime) {
				var instance = this;

				var banner = instance.banner;

				if (banner) {
					var interval = 1000;

					instance._originalTitle = document.title;

					instance._counterText = banner.one('.countdown-timer');

					instance._counterText.text(instance._setTime());

					document.title = instance.banner.text();

					instance._countdownTimer = setInterval(
						function() {
							var time = instance._setTime();

							instance._currentTime = instance._currentTime - interval;

							if (instance._currentTime > 0) {
								instance._counterText.text(time);

								document.title = instance.banner.text();
							}
							else {
								if (instance._countdownTimer) {
									clearInterval(instance._countdownTimer);
								}
							}
						},
						interval
					);
				}
			},

			_startPolling: function() {
				var instance = this;

				Liferay.Poller.addListener(instance._portletId, instance._onPollerUpdate, instance);

				Liferay.on(
					'sessionExpired',
					function(event) {
						Liferay.Poller.removeListener(instance._portletId);
					}
				);
			},

			_warningShutdown: function(shutdownText) {
				var instance = this;

				instance.banner = new Liferay.Notice(
					{
						content: shutdownText,
						onClose: function() {
							if (instance._countdownTimer) {
								clearInterval(instance._countdownTimer);
							}
						},
						toggleText: false
					}
				);

				instance._shutdownCounter();
			},

			_currentTime: 0,
			_originalTitle: '',
			_timeout: 0
		};

		Liferay.Shutdown = Shutdown;
	},
	'',
	{
		requires: ['aui-base', 'collection', 'liferay-notice', 'liferay-poller', 'substitute']
	}
);