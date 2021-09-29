/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

AUI.add(
	'liferay-session',
	(A) => {
		var Lang = A.Lang;

		var BUFFER_TIME = [];

		var CONFIG = A.config;

		var DOC = CONFIG.doc;

		var MAP_SESSION_STATE_EVENTS = {
			active: 'activated',
		};

		var SRC = {};

		var SRC_EVENT_OBJ = {
			src: SRC,
		};

		const TOAST_ID = 'sessionToast';

		var URL_BASE = themeDisplay.getPathMain() + '/portal/';

		var SessionBase = A.Component.create({
			ATTRS: {
				autoExtend: {
					value: false,
				},
				redirectOnExpire: {
					value: true,
				},
				redirectUrl: {
					value: '',
				},
				sessionLength: {
					getter: '_getLengthInMillis',
					value: 0,
				},
				sessionState: {
					value: 'active',
				},
				sessionTimeoutOffset: {
					getter: '_getLengthInMillis',
					value: 0,
				},
				timestamp: {
					getter: '_getTimestamp',
					setter: '_setTimestamp',
					value: 0,
				},
				warningLength: {
					getter: '_getLengthInMillis',
					setter: '_setWarningLength',
					value: 0,
				},
				warningTime: {
					getter: '_getWarningTime',
					value: 0,
				},
			},
			EXTENDS: A.Base,
			NAME: 'liferaysession',
			prototype: {
				_afterSessionStateChange(event) {
					var instance = this;

					var details = event.details;
					var newVal = event.newVal;

					var src = null;

					if ('src' in event && details.length) {
						src = details[0];
					}

					instance.fire(
						MAP_SESSION_STATE_EVENTS[newVal] || newVal,
						src
					);
				},

				_defActivatedFn(event) {
					var instance = this;

					instance.set('timestamp');

					if (event.src == SRC) {
						Liferay.Util.fetch(URL_BASE + 'extend_session');
					}
				},

				_defExpiredFn(event) {
					var instance = this;

					A.clearInterval(instance._intervalId);

					instance.set('timestamp', 'expired');

					if (event.src === SRC) {
						instance._expireSession();
					}
				},

				_expireSession() {
					var instance = this;

					Liferay.Util.fetch(URL_BASE + 'expire_session').then(
						(response) => {
							if (response.ok) {
								Liferay.fire('sessionExpired');

								if (instance.get('redirectOnExpire')) {
									location.href = instance.get('redirectUrl');
								}
							}
							else {
								A.setTimeout(() => {
									instance._expireSession();
								}, 1000);
							}
						}
					);
				},

				_getLengthInMillis(value) {
					return value * 1000;
				},

				_getTimestamp() {
					var instance = this;

					return (
						A.Cookie.get(
							instance._cookieKey,
							instance._cookieOptions
						) || instance._initTimestamp
					);
				},

				_getWarningTime() {
					var instance = this;

					return (
						instance.get('sessionLength') -
						instance.get('warningLength')
					);
				},

				_initEvents() {
					var instance = this;

					instance.publish('activated', {
						defaultFn: A.bind('_defActivatedFn', instance),
					});

					instance.publish('expired', {
						defaultFn: A.bind('_defExpiredFn', instance),
					});

					instance.publish('warned');

					instance._eventHandlers = [
						instance.on(
							'sessionStateChange',
							instance._onSessionStateChange
						),
						instance.after(
							'sessionStateChange',
							instance._afterSessionStateChange
						),
						A.on('io:complete', (transactionId, response, args) => {
							if (
								!args ||
								(args && args.sessionExtend) ||
								!Lang.isBoolean(args.sessionExtend)
							) {
								instance.resetInterval();
							}
						}),
						Liferay.once('screenLoad', () => {
							instance.destroy();
						}),
					];
				},

				_onSessionStateChange(event) {
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

				_setTimestamp(value) {
					var instance = this;

					value = String(value || Date.now());

					instance._initTimestamp = value;

					if (navigator.cookieEnabled) {
						A.Cookie.set(
							instance._cookieKey,
							value,
							instance._cookieOptions
						);
					}
				},

				_setWarningLength(value) {
					var instance = this;

					return Math.min(instance.get('sessionLength'), value);
				},

				_startTimer() {
					var instance = this;

					var sessionLength = instance.get('sessionLength');

					var sessionTimeoutOffset = instance.get(
						'sessionTimeoutOffset'
					);

					var warningTime = instance.get('warningTime');

					var registered = instance._registered;

					var interval = 1000;

					instance._intervalId = A.setInterval(() => {
						var sessionState = instance.get('sessionState');

						var timeOffset;

						var timestamp = instance.get('timestamp');

						var elapsed = sessionLength;

						if (Lang.toInt(timestamp)) {
							timeOffset =
								Math.floor((Date.now() - timestamp) / 1000) *
								1000;

							elapsed = timeOffset;

							if (instance._initTimestamp !== timestamp) {
								instance.set('timestamp', timestamp);

								if (sessionState != 'active') {
									instance.set(
										'sessionState',
										'active',
										SRC_EVENT_OBJ
									);
								}
							}
						}
						else {
							timestamp = 'expired';
						}

						var extend = instance.get('autoExtend');

						var expirationMoment = false;
						var warningMoment = false;

						var hasExpired = elapsed >= sessionLength;
						var hasExpiredTimeoutOffset =
							elapsed >= sessionLength - sessionTimeoutOffset;
						var hasWarned = elapsed >= warningTime;

						if (hasExpiredTimeoutOffset || hasWarned) {
							if (timestamp == 'expired') {
								expirationMoment = true;
								extend = false;
								hasExpired = true;
								hasExpiredTimeoutOffset = true;
							}

							if (
								hasExpiredTimeoutOffset &&
								sessionState != 'expired'
							) {
								if (extend && !hasExpired) {
									expirationMoment = false;
									hasExpired = false;
									hasExpiredTimeoutOffset = false;
									hasWarned = false;
									warningMoment = false;

									instance.extend();
								}
								else {
									instance.expire();

									expirationMoment = true;
								}
							}
							else if (
								hasWarned &&
								!hasExpiredTimeoutOffset &&
								!extend &&
								sessionState != 'warned'
							) {
								instance.warn();

								warningMoment = true;
							}
						}

						for (var i in registered) {
							registered[i](
								elapsed,
								interval,
								hasWarned,
								hasExpired,
								hasExpiredTimeoutOffset,
								warningMoment,
								expirationMoment
							);
						}
					}, interval);
				},

				_stopTimer() {
					var instance = this;

					A.clearInterval(instance._intervalId);
				},

				destructor() {
					var instance = this;

					new A.EventHandle(instance._eventHandlers).detach();

					instance._stopTimer();
				},

				expire() {
					var instance = this;

					instance.set('sessionState', 'expired', SRC_EVENT_OBJ);
				},

				extend() {
					var instance = this;

					instance.set('sessionState', 'active', SRC_EVENT_OBJ);
				},

				initializer() {
					var instance = this;

					instance._cookieKey =
						'LFR_SESSION_STATE_' + themeDisplay.getUserId();

					instance._cookieOptions = {
						path: '/',
						secure: A.UA.secure,
					};

					instance._registered = {};

					instance.set('timestamp');

					instance._initEvents();

					instance._startTimer();
				},

				registerInterval(fn) {
					var instance = this;

					var fnId;
					var registered = instance._registered;

					if (Lang.isFunction(fn)) {
						fnId = A.stamp(fn);

						registered[fnId] = fn;
					}

					return fnId;
				},

				resetInterval() {
					var instance = this;

					instance._stopTimer();
					instance._startTimer();
				},

				unregisterInterval(fnId) {
					var instance = this;

					var registered = instance._registered;

					if (
						Object.prototype.hasOwnProperty.call(registered, fnId)
					) {
						delete registered[fnId];
					}

					return fnId;
				},

				warn() {
					var instance = this;

					instance.set('sessionState', 'warned', SRC_EVENT_OBJ);
				},
			},
		});

		SessionBase.SRC = SRC;

		var SessionDisplay = A.Component.create({
			ATTRS: {
				openToast: {
					validator: Lang.isFunction,
				},
				pageTitle: {
					value: DOC.title,
				},
			},
			EXTENDS: A.Plugin.Base,
			NAME: 'liferaysessiondisplay',
			NS: 'display',
			prototype: {
				_afterDefActivatedFn() {
					var instance = this;

					instance._uiSetActivated();
				},

				_afterDefExpiredFn() {
					var instance = this;

					instance._host.unregisterInterval(instance._intervalId);

					instance._uiSetExpired();
				},

				_beforeHostWarned() {
					var instance = this;

					var host = instance._host;

					var sessionLength = host.get('sessionLength');
					var timestamp = host.get('timestamp');
					var warningLength = host.get('warningLength');

					var elapsed = sessionLength;

					if (Lang.toInt(timestamp)) {
						elapsed =
							Math.floor((Date.now() - timestamp) / 1000) * 1000;
					}

					var remainingTime = sessionLength - elapsed;

					if (remainingTime > warningLength) {
						remainingTime = warningLength;
					}

					instance._getBanner();

					const counterTextNode = document.querySelector(
						`#${TOAST_ID} .countdown-timer`
					);

					instance._uiSetRemainingTime(
						remainingTime,
						counterTextNode
					);

					instance._intervalId = host.registerInterval(
						(
							elapsed,
							interval,
							hasWarned,
							hasExpired,
							warningMoment
						) => {
							if (!hasWarned) {
								instance._uiSetActivated();
							}
							else if (!hasExpired) {
								if (warningMoment) {
									if (remainingTime <= 0) {
										remainingTime = warningLength;
									}
								}

								elapsed =
									Math.floor(
										(Date.now() - timestamp) / 1000
									) * 1000;

								remainingTime = sessionLength - elapsed;

								instance._uiSetRemainingTime(
									remainingTime,
									counterTextNode
								);
							}

							remainingTime -= interval;
						}
					);
				},

				_destroyBanner() {
					const instance = this;

					const toast = document.getElementById(TOAST_ID);

					const toastRootElement = toast?.parentElement;

					Liferay.destroyComponent(TOAST_ID);

					if (toastRootElement) {
						toastRootElement.remove();
					}

					instance._banner = false;
				},

				_formatNumber(value) {
					return Lang.String.padNumber(Math.floor(value), 2);
				},

				_formatTime(time) {
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

				_getBanner() {
					var instance = this;

					var banner = instance._banner;

					if (!banner) {
						var openToast = instance.get('openToast');

						var toastDefaultConfig = {
							onClick({event}) {
								if (
									event.target.classList.contains(
										'alert-link'
									)
								) {
									instance._host.extend();
								}
							},
							renderData: {
								componentId: TOAST_ID,
							},
							toastProps: {
								autoClose: false,
								id: TOAST_ID,
								role: 'alert',
							},
						};

						openToast({
							message: instance._warningText,
							type: 'warning',
							...toastDefaultConfig,
						});

						var toastComponent = Liferay.component(TOAST_ID);

						banner = {
							open(props) {
								instance._destroyBanner();

								openToast({
									...props,
									...toastDefaultConfig,
								});
							},
							...toastComponent,
						};

						instance._banner = banner;
					}

					return banner;
				},

				_onHostSessionStateChange(event) {
					var instance = this;

					if (event.newVal == 'warned') {
						instance._beforeHostWarned(event);
					}
				},

				_uiSetActivated() {
					var instance = this;

					DOC.title = instance.reset('pageTitle').get('pageTitle');

					instance._host.unregisterInterval(instance._intervalId);

					if (instance._banner) {
						instance._destroyBanner();
					}
				},

				_uiSetExpired() {
					var instance = this;

					var banner = instance._getBanner();

					banner.open({
						message: instance._expiredText,
						title: Liferay.Language.get('danger'),
						type: 'danger',
					});

					DOC.title = instance.get('pageTitle');
				},

				_uiSetRemainingTime(remainingTime, counterTextNode) {
					var instance = this;

					remainingTime = instance._formatTime(remainingTime);

					if (!instance._alertClosed) {
						var alert = counterTextNode.closest(
							'div[role="alert"]'
						);

						// Prevent screen reader from rereading alert

						if (alert) {
							alert.removeAttribute('role');

							instance._alert = alert;
						}

						counterTextNode.innerHTML = remainingTime;
					}

					DOC.title =
						Lang.sub(Liferay.Language.get('session-expires-in-x'), [
							remainingTime,
						]) +
						' | ' +
						instance.get('pageTitle');
				},

				destructor() {
					var instance = this;

					if (instance._banner) {
						instance._destroyBanner();
					}
				},

				initializer() {
					var instance = this;

					var host = instance.get('host');

					if (Liferay.Util.getTop() == CONFIG.win) {
						instance._host = host;

						instance._toggleText = {
							hide: Liferay.Language.get('hide'),
							show: Liferay.Language.get('show'),
						};

						instance._expiredText = Liferay.Language.get(
							'due-to-inactivity-your-session-has-expired'
						);

						instance._warningText = Liferay.Language.get(
							'due-to-inactivity-your-session-will-expire'
						);
						instance._warningText = Lang.sub(
							instance._warningText,
							[
								'<span class="countdown-timer">{0}</span>',
								host.get('sessionLength') / 60000,
								'<a class="alert-link" href="javascript:;">' +
									Liferay.Language.get('extend') +
									'</a>',
							]
						);

						host.on(
							'sessionStateChange',
							instance._onHostSessionStateChange,
							instance
						);

						instance.afterHostMethod(
							'_defActivatedFn',
							instance._afterDefActivatedFn
						);
						instance.afterHostMethod(
							'_defExpiredFn',
							instance._afterDefExpiredFn
						);
					}
					else {
						host.unplug(instance);
					}
				},
			},
		});

		Liferay.SessionBase = SessionBase;
		Liferay.SessionDisplay = SessionDisplay;
	},
	'',
	{
		requires: [
			'aui-base',
			'aui-component',
			'aui-timer',
			'cookie',
			'plugin',
		],
	}
);
