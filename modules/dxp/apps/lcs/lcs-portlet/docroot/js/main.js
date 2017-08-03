AUI.add(
	'lcs',
	function(A) {
		var Lang = A.Lang;

		var padNumber = A.rbind('padNumber', Lang.String, 2);

		var CSS_ALERT_DANGER = 'alert-danger';

		var CSS_ALERT_SUCCESS = 'alert-success';

		var CSS_ALERT_WARNING = 'alert-warning';

		var STR_BLANK = '';

		var STR_DOUBLE_ZERO = '00';

		var LCS = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'lcs',

				prototype: {
					initializeConnectionPage: function(config) {
						var instance = this;

						var ready = config.ready;

						instance._connectionStatusURL = config.connectionStatusURL;
						instance._handshakeTime = config.handshakeTime;
						instance._labels = config.labels;
						instance._lcsConstants = config.lcsConstants;
						instance._ready = ready;

						instance._connectionAlertContainer = instance.byId('connectionAlertContainer');
						instance._durationContainer = instance.byId('duration');
						instance._gatewayUnavailableAlert = instance.byId('lcsGatewayUnavailable');
						instance._spinner = instance.byId('spinner');

						var connectionStatusContainer = instance.byId('connectionStatus');

						if (connectionStatusContainer) {
							instance._connectionStatusContainer = connectionStatusContainer;

							instance._connectionStatusLabel = connectionStatusContainer.one('.lead');
							instance._connectionStatusSpinner = connectionStatusContainer.one('.icon-spin');
						}

						if (ready) {
							instance._updateDuration();
						}
					},

					_getConnectionStatus: function() {
						var instance = this;

						A.io.request(
							instance._connectionStatusURL,
							{
								dataType: 'JSON',
								method: 'GET',
								on: {
									failure: function(event, id, obj) {
										instance._refreshConnectionStatus(
											{
												error: true
											}
										);
									},
									success: function(event, id, obj) {
										var responseData = this.get('responseData');

										var lcsConstants = instance._lcsConstants;

										if (responseData[lcsConstants.JSON_KEY_RESULT] == lcsConstants.JSON_VALUE_FAILURE) {
											instance._refreshConnectionStatus(
												{
													error: true
												}
											);
										}
										else {
											var responseDataReady = responseData.ready;

											instance._ready = responseDataReady;

											instance._refreshConnectionStatus(
												{
													error: false,
													ready: responseDataReady
												}
											);

											var gatewayUnavailableAlert = instance._gatewayUnavailableAlert;

											if (gatewayUnavailableAlert) {
												gatewayUnavailableAlert.toggle(!responseData.lcsGatewayAvailable);
											}

											if (responseDataReady) {
												instance._handshakeTime = Lang.now();

												instance._updateDuration();
											}
										}
									}
								}
							}
						);
					},

					_refreshConnectionStatus: function(config) {
						var instance = this;

						var error = config.error;
						var ready = config.ready;

						var connectionStatusContainer = instance._connectionStatusContainer;

						if (connectionStatusContainer) {
							if (error) {
								connectionStatusContainer.addClass(CSS_ALERT_DANGER);

								connectionStatusContainer.removeClass(CSS_ALERT_SUCCESS);
								connectionStatusContainer.removeClass(CSS_ALERT_WARNING);
							}
							else {
								connectionStatusContainer.removeClass(CSS_ALERT_WARNING);

								if (ready) {
									connectionStatusContainer.addClass(CSS_ALERT_SUCCESS);

									connectionStatusContainer.removeClass(CSS_ALERT_DANGER);
								}
								else {
									connectionStatusContainer.addClass(CSS_ALERT_DANGER);

									connectionStatusContainer.removeClass(CSS_ALERT_SUCCESS);
								}
							}
						}

						var connectionStatusLabel = instance._connectionStatusLabel;
						var labels = instance._labels;

						if (connectionStatusLabel) {
							var label = labels.disconnected;

							if (error) {
								label = labels.unableToDisplayConnectionStatus;
							}
							else if (ready) {
								label = labels.connected;
							}

							connectionStatusLabel.html(label);
						}

						var connectionStatusSpinner = instance._connectionStatusSpinner;

						if (connectionStatusSpinner) {
							connectionStatusSpinner.hide();
						}
					},

					_updateDuration: function() {
						var instance = this;

						var duration = {
							days: STR_BLANK,
							daysSeparator: STR_BLANK,
							hours: STR_DOUBLE_ZERO,
							minutes: STR_DOUBLE_ZERO,
							seconds: STR_DOUBLE_ZERO
						};

						var handshakeTime = instance._handshakeTime;

						if (instance._ready && handshakeTime) {
							var totalSeconds = Math.floor((Lang.now() - handshakeTime) / 1000);

							var days = Math.floor(totalSeconds / 86400);
							var hours = Math.floor(totalSeconds / 3600) % 24;
							var minutes = Math.floor(totalSeconds / 60) % 60;

							var seconds = totalSeconds % 60;

							if (days) {
								duration.days = padNumber(days);

								duration.daysSeparator = ':';
							}

							duration.hours = padNumber(hours);
							duration.minutes = padNumber(minutes);
							duration.seconds = padNumber(seconds);

							setTimeout(
								A.bind('_updateDuration', instance),
								1000
							);
						}

						var durationHTML = Lang.sub('{days}{daysSeparator}{hours}:{minutes}:{seconds}', duration);

						instance._durationContainer.html(durationHTML);
					}
				}
			}
		);

		Liferay.Portlet.LCS = LCS;
	},
	'',
	{
		requires: ['aui-io', 'liferay-portlet-base']
	}
);