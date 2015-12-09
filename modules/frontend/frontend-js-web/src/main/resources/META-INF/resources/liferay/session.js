AUI.add(
	'liferay-session',
	function(A) {
		var Lang = A.Lang;

		var BUFFER_TIME = [];

		var CONFIG = A.config;

		var DOC = CONFIG.doc;

		var MAP_SESSION_STATE_EVENTS = {
			active: 'activated'
		};

		var SRC = {};

		var SRC_EVENT_OBJ = {
			src: SRC
		};

		var URL_BASE = themeDisplay.getPathMain() + '/portal/';

		var SessionBase = A.Component.create(
			{
				ATTRS: {
					autoExtend: {
						value: false
					},
					redirectOnExpire: {
						value: true
					},
					redirectUrl: {
						value: ''
					},
					sessionLength: {
						getter: '_getLengthInMillis',
						value: 0
					},
					sessionState: {
						value: 'active'
					},
					timestamp: {
						getter: '_getTimestamp',
						setter: '_setTimestamp',
						value: 0
					},
					warningLength: {
						getter: '_getLengthInMillis',
						setter: '_setWarningLength',
						value: 0
					},
					warningTime: {
						getter: '_getWarningTime',
						value: 0
					}
				},
				EXTENDS: A.Base,
				NAME: 'liferaysession',
				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._cookieOptions = {
							path: '/',
							secure: A.UA.secure
						};

						instance._registered = {};

						instance.set('timestamp');

						instance._initEvents();

						instance._startTimer();
					},

					expire: function() {
						var instance = this;

						instance.set('sessionState', 'expired', SRC_EVENT_OBJ);
					},

					extend: function() {
						var instance = this;

						instance.set('sessionState', 'active', SRC_EVENT_OBJ);
					},

					registerInterval: function(fn) {
						var instance = this;

						var fnId;
						var registered = instance._registered;

						if (Lang.isFunction(fn)) {
							fnId = A.stamp(fn);

							registered[fnId] = fn;
						}

						return fnId;
					},

					resetInterval: function() {
						var instance = this;

						instance._stopTimer();
						instance._startTimer();
					},

					unregisterInterval: function(fnId) {
						var instance = this;

						var registered = instance._registered;

						if (registered.hasOwnProperty(fnId)) {
							delete registered[fnId];
						}

						return fnId;
					},

					warn: function() {
						var instance = this;

						instance.set('sessionState', 'warned', SRC_EVENT_OBJ);
					},

					_afterSessionStateChange: function(event) {
						var instance = this;

						var details = event.details;
						var newVal = event.newVal;

						var src = null;

						if ('src' in event && details.length) {
							src = details[0];
						}

						instance.fire(MAP_SESSION_STATE_EVENTS[newVal] || newVal, src);
					},

					_defActivatedFn: function(event) {
						var instance = this;

						instance._elapsed = 0;

						instance.set('timestamp');

						if (event.src == SRC) {
							instance._getExtendIO().start();
						}
					},

					_defExpiredFn: function(event) {
						var instance = this;

						A.clearInterval(instance._intervalId);

						instance.set('timestamp', 'expired');

						if (event.src === SRC) {
							instance._getExpireIO().start();
						}
					},

					_getExpireIO: function() {
						var instance = this;

						var expireIO = instance._expireIO;

						if (!expireIO) {
							expireIO = A.io.request(
								URL_BASE + 'expire_session',
								{
									autoLoad: false,
									on: {
										success: function(event, id, obj) {
											Liferay.fire('sessionExpired');

											if (instance.get('redirectOnExpire')) {
												location.href = instance.get('redirectUrl');
											}
										}
									}
								}
							);

							instance._expireIO = expireIO;
						}

						return expireIO;
					},

					_getExtendIO: function() {
						var instance = this;

						var extendIO = instance._extendIO;

						if (!extendIO) {
							extendIO = A.io.request(
								URL_BASE + 'extend_session',
								{
									autoLoad: false
								}
							);

							instance._extendIO = extendIO;
						}

						return extendIO;
					},

					_getLengthInMillis: function(value) {
						var instance = this;

						return value * 60000;
					},

					_getTimestamp: function(value) {
						var instance = this;

						return A.Cookie.get(instance._cookieKey, instance._cookieOptions) || 0;
					},

					_getWarningTime: function() {
						var instance = this;

						return instance.get('sessionLength') - instance.get('warningLength');
					},

					_initEvents: function() {
						var instance = this;

						instance.on('sessionStateChange', instance._onSessionStateChange);

						instance.after('sessionStateChange', instance._afterSessionStateChange);

						instance.publish(
							'activated',
							{
								defaultFn: A.bind('_defActivatedFn', instance)
							}
						);

						instance.publish(
							'expired',
							{
								defaultFn: A.bind('_defExpiredFn', instance)
							}
						);

						instance.publish('warned');

						A.on(
							'io:complete',
							function(transactionId, response, args) {
								if (!args || args && args.sessionExtend || !Lang.isBoolean(args.sessionExtend)) {
									instance.resetInterval();
								}
							}
						);
					},

					_onSessionStateChange: function(event) {
						var instance = this;

						var newVal = event.newVal;
						var prevVal = event.prevVal;

						if (prevVal == 'expired' && prevVal != newVal) {
							event.preventDefault();
						}
						else if (prevVal == 'active' && prevVal == newVal) {
							instance._afterSessionStateChange(event);
						}
					},

					_setTimestamp: function(value) {
						var instance = this;

						value = String(value || Date.now());

						return A.Cookie.set(instance._cookieKey, value, instance._cookieOptions);
					},

					_setWarningLength: function(value) {
						var instance = this;

						return Math.min(instance.get('sessionLength'), value);
					},

					_startTimer: function() {
						var instance = this;

						var sessionLength = instance.get('sessionLength');
						var warningTime = instance.get('warningTime');

						instance._elapsed = 0;

						var registered = instance._registered;

						var interval = 1000;

						instance._intervalId = A.setInterval(
							function() {
								var elapsed = instance._elapsed += 1000;

								var extend = false;

								var expirationMoment = elapsed == sessionLength;
								var warningMoment = elapsed == warningTime;

								var hasExpired = elapsed >= sessionLength;
								var hasWarned = elapsed >= warningTime;

								var updateSessionState = true;

								if (hasWarned) {
									if (warningMoment || expirationMoment) {
										var timestamp = instance.get('timestamp');

										if (timestamp == 'expired') {
											expirationMoment = true;
											hasExpired = true;
										}
										else if (instance.get('autoExtend')) {
											hasExpired = false;
											hasWarned = false;

											expirationMoment = false;
											warningMoment = false;

											extend = true;
										}
										else {
											var timeOffset = Math.floor((Date.now() - timestamp) / 1000) * 1000;

											if (timeOffset < warningTime) {
												instance._elapsed = timeOffset;

												updateSessionState = false;
												hasWarned = false;
											}
										}
									}

									if (updateSessionState) {
										if (expirationMoment) {
											instance.expire();
										}
										else if (warningMoment) {
											instance.warn();
										}
										else if (extend) {
											instance.extend();
										}
									}
								}

								for (var i in registered) {
									registered[i](elapsed, interval, hasWarned, hasExpired, warningMoment, expirationMoment);
								}
							},
							interval
						);
					},

					_stopTimer: function() {
						var instance = this;

						A.clearInterval(instance._intervalId);
					},

					_cookieKey: 'LFR_SESSION_STATE_' + themeDisplay.getUserId()
				}
			}
		);

		SessionBase.SRC = SRC;

		var SessionDisplay = A.Component.create(
			{
				ATTRS: {
					pageTitle: {
						value: DOC.title
					}
				},
				EXTENDS: A.Plugin.Base,
				NAME: 'liferaysessiondisplay',
				NS: 'display',
				prototype: {
					initializer: function(config) {
						var instance = this;

						var host = instance.get('host');

						if (Liferay.Util.getTop() == CONFIG.win) {
							instance._host = host;

							instance._toggleText = {
								hide: Liferay.Language.get('hide'),
								show: Liferay.Language.get('show')
							};

							instance._expiredText = Liferay.Language.get('warning-your-session-has-expired');
							instance._extendText = Liferay.Language.get('extend');

							instance._warningText = Liferay.Language.get('warning-your-session-will-expire');
							instance._warningText = Lang.sub(
								instance._warningText,
								[
									'<span class="countdown-timer">{0}</span>',
									host.get('sessionLength') / 60000,
									'<a class="alert-link" href="#">' + Liferay.Language.get('extend') + '</a>'
								]
							);

							host.on('sessionStateChange', instance._onHostSessionStateChange, instance);

							instance.afterHostMethod('_defActivatedFn', instance._afterDefActivatedFn);
							instance.afterHostMethod('_defExpiredFn', instance._afterDefExpiredFn);
						}
						else {
							host.unplug(instance);
						}
					},

					_afterDefActivatedFn: function(event) {
						var instance = this;

						instance._uiSetActivated();
					},

					_afterDefExpiredFn: function(event) {
						var instance = this;

						instance._host.unregisterInterval(instance._intervalId);

						instance._uiSetExpired();
					},

					_beforeHostWarned: function(event) {
						var instance = this;

						var host = instance._host;

						var warningLength = host.get('warningLength');

						var remainingTime = warningLength;

						var banner = instance._getBanner();

						var counterTextNode = banner.one('.countdown-timer');

						instance._uiSetRemainingTime(remainingTime, counterTextNode);

						banner.show();

						instance._intervalId = host.registerInterval(
							function(elapsed, interval, hasWarned, hasExpired, warningMoment, expirationMoment) {
								if (!hasWarned) {
									instance._uiSetActivated();
								}
								else if (!hasExpired) {
									if (warningMoment) {
										if (remainingTime <= 0) {
											remainingTime = warningLength;
										}

										banner.show();
									}

									instance._uiSetRemainingTime(remainingTime, counterTextNode);

								}

								remainingTime -= interval;
							}
						);
					},

					_formatNumber: function(value) {
						var instance = this;

						var floor = Math.floor;
						var padNumber = Lang.String.padNumber;

						return Lang.String.padNumber(Math.floor(value), 2);
					},

					_formatTime: function(time) {
						var instance = this;

						time = Number(time);

						if (Lang.isNumber(time) && time > 0) {
							time /= 1000;

							BUFFER_TIME[0] = instance._formatNumber(time / 3600);

							time %= 3600;

							BUFFER_TIME[1] = instance._formatNumber(time / 60);

							time %= 60;

							BUFFER_TIME[2] = instance._formatNumber(time);

							time = BUFFER_TIME.join(':');
						}
						else {
							time = 0;
						}

						return time;
					},

					_getBanner: function() {
						var instance = this;

						var banner = instance._banner;

						if (!banner) {
							banner = new Liferay.Alert(
								{
									closeable: true,
									delay: {
										hide: 0,
										show: 0
									},
									duration: 500,
									message: instance._warningText,
									on: {
										click: function(event) {
											if (event.domEvent.target.test('.alert-link')) {
												event.domEvent.preventDefault();
												instance._host.extend();
											}
										}
									},
									title: Liferay.Language.get('warning'),
									type: 'warning'
								}
							).render('body');

							instance._banner = banner;
						}

						return banner;
					},

					_onHostSessionStateChange: function(event) {
						var instance = this;

						if (event.newVal == 'warned') {
							instance._beforeHostWarned(event);
						}
					},

					_uiSetActivated: function() {
						var instance = this;

						DOC.title = instance.reset('pageTitle').get('pageTitle');

						instance._host.unregisterInterval(instance._intervalId);

						var banner = instance._getBanner();

						if (banner) {
							banner.hide();
						}
					},

					_uiSetExpired: function() {
						var instance = this;

						var banner = instance._getBanner();

						banner.setAttrs(
							{
								bodyContent: instance._expiredText,
								title: Liferay.Language.get('danger'),
								type: 'danger'
							}
						);

						DOC.title = instance.get('pageTitle');
					},

					_uiSetRemainingTime: function(remainingTime, counterTextNode) {
						var instance = this;

						var banner = instance._getBanner();

						banner.set(
							'bodyContent',
							Lang.sub(
								instance._warningText,
								[
									instance._formatTime(remainingTime)
								]
							)
						);

						DOC.title = banner.get('contentBox').text;
					}
				}
			}
		);

		Liferay.SessionBase = SessionBase;
		Liferay.SessionDisplay = SessionDisplay;
	},
	'',
	{
		requires: ['aui-io-request', 'aui-timer', 'cookie', 'liferay-alert']
	}
);