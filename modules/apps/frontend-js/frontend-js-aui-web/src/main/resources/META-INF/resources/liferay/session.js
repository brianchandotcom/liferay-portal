/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-session',
	(A) => {
		const Lang = A.Lang;

		const BUFFER_TIME = [];

		const CONFIG = A.config;

		const DOC = CONFIG.doc;

		const MAP_SESSION_STATE_EVENTS = {
			active: 'activated',
		};

		const SESSION_STATE_CHECK_INTERVAL = 1000;

		const SRC = {};

		const SRC_EVENT_OBJ = {
			src: SRC,
		};

		const TOAST_ID = 'sessionToast';

		const URL_BASE = themeDisplay.getPathMain() + '/portal/';

		const SessionBase = A.Component.create({
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
					const instance = this;

					const details = event.details;
					const newVal = event.newVal;

					let src = null;

					if ('src' in event && details.length) {
						src = details[0];
					}

					instance.fire(
						MAP_SESSION_STATE_EVENTS[newVal] || newVal,
						src
					);
				},

				_defActivatedFn(event) {
					const instance = this;

					instance.set('timestamp');

					if (event.src === SRC) {
						Liferay.Util.fetch(URL_BASE + 'extend_session').then(
							(response) => {
								if (response.status === 500) {
									instance.expire();
								}
							}
						);
					}
				},

				_defExpiredFn(event) {
					const instance = this;

					A.clearInterval(instance._intervalId);

					if (event.src === SRC) {
						instance._expireSession();
					}
				},

				_expireSession() {
					const instance = this;

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
					const instance = this;

					return (
						Liferay.Util.Cookie.get(
							instance._cookieKey,
							Liferay.Util.Cookie.TYPES.NECESSARY
						) || instance._initTimestamp
					);
				},

				_getWarningTime() {
					const instance = this;

					return (
						instance.get('sessionLength') -
						instance.get('warningLength')
					);
				},

				_initEvents() {
					const instance = this;

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
					const instance = this;

					const newVal = event.newVal;
					const prevVal = event.prevVal;

					if (prevVal === 'expired' && prevVal !== newVal) {
						event.preventDefault();
					}
					else if (prevVal === 'active' && prevVal === newVal) {
						instance._afterSessionStateChange(event);
					}
				},

				_setTimestamp(value) {
					const instance = this;

					value = String(value || Date.now());

					instance._initTimestamp = value;

					if (navigator.cookieEnabled) {
						Liferay.Util.Cookie.set(
							instance._cookieKey,
							value,
							Liferay.Util.Cookie.TYPES.NECESSARY,
							instance._cookieOptions
						);
					}
				},

				_setWarningLength(value) {
					const instance = this;

					return Math.min(instance.get('sessionLength'), value);
				},

				_startTimer() {
					const instance = this;

					const sessionLength = instance.get('sessionLength');
					const sessionTimeoutOffset = instance.get(
						'sessionTimeoutOffset'
					);
					const warningTime = instance.get('warningTime');

					instance._intervalId = A.setInterval(() => {
						const sessionState = instance.get('sessionState');
						const timestamp = instance.get('timestamp');

						// LPS-82336 Maintain session state in multiple tabs

						if (instance._initTimestamp !== timestamp) {
							instance.set('timestamp', timestamp);

							if (sessionState !== 'active') {
								instance.set(
									'sessionState',
									'active',
									SRC_EVENT_OBJ
								);
							}
						}

						const elapsed =
							Math.floor((Date.now() - timestamp) / 1000) * 1000;

						const autoExtend = instance.get('autoExtend');

						const hasExpired = elapsed >= sessionLength;
						const hasExpiredTimeoutOffset =
							elapsed >= sessionLength - sessionTimeoutOffset;
						const hasWarned = elapsed >= warningTime;

						if (hasExpired && sessionState !== 'expired') {
							instance.expire();
						}
						else if (autoExtend && hasExpiredTimeoutOffset) {
							instance.extend();
						}
						else if (
							!autoExtend &&
							hasWarned &&
							sessionState !== 'warned'
						) {
							instance.warn();
						}

						const registered = instance._registered;

						for (const i in registered) {
							registered[i](elapsed, hasWarned, hasExpired);
						}
					}, SESSION_STATE_CHECK_INTERVAL);
				},

				_stopTimer() {
					const instance = this;

					A.clearInterval(instance._intervalId);
				},

				destructor() {
					const instance = this;

					new A.EventHandle(instance._eventHandlers).detach();

					instance._stopTimer();
				},

				expire() {
					const instance = this;

					instance.set('sessionState', 'expired', SRC_EVENT_OBJ);
				},

				extend() {
					const instance = this;

					instance.set('sessionState', 'active', SRC_EVENT_OBJ);
				},

				initializer() {
					const instance = this;

					instance._cookieKey =
						'LFR_SESSION_STATE_' + themeDisplay.getRealUserId();

					instance._cookieOptions = {
						path: '/',
						secure: A.UA.secure,
					};

					instance._registered = {};

					instance.set('timestamp');

					instance._initEvents();

					instance._startTimer();

					Liferay.fire('sessionInitialized', {
						session: instance,
					});
				},

				registerInterval(fn) {
					const instance = this;

					let fnId;
					const registered = instance._registered;

					if (Lang.isFunction(fn)) {
						fnId = A.stamp(fn);

						registered[fnId] = fn;
					}

					return fnId;
				},

				resetInterval() {
					const instance = this;

					instance._stopTimer();
					instance._startTimer();
				},

				unregisterInterval(fnId) {
					const instance = this;

					const registered = instance._registered;

					if (
						Object.prototype.hasOwnProperty.call(registered, fnId)
					) {
						delete registered[fnId];
					}

					return fnId;
				},

				warn() {
					const instance = this;

					instance.set('sessionState', 'warned', SRC_EVENT_OBJ);
				},
			},
		});

		SessionBase.SRC = SRC;

		const SessionDisplay = A.Component.create({
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
					const instance = this;

					instance._uiSetActivated();
				},

				_afterDefExpiredFn() {
					const instance = this;

					instance._host.unregisterInterval(instance._intervalId);

					instance._uiSetExpired();
				},

				_beforeHostWarned() {
					const instance = this;

					const host = instance._host;

					const sessionLength = host.get('sessionLength');
					const timestamp = host.get('timestamp');
					const warningLength = host.get('warningLength');

					let elapsed = sessionLength;

					if (Lang.toInt(timestamp)) {
						elapsed =
							Math.floor((Date.now() - timestamp) / 1000) * 1000;
					}

					let remainingTime = sessionLength - elapsed;

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
						(elapsed, hasWarned, hasExpired) => {
							if (!hasWarned) {
								instance._uiSetActivated();
							}
							else if (!hasExpired) {
								instance._uiSetRemainingTime(
									sessionLength - elapsed,
									counterTextNode
								);
							}
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
					const instance = this;

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
					const instance = this;

					let banner = instance._banner;

					if (!banner) {
						const openToast = instance.get('openToast');

						const toastDefaultConfig = {
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

						const toastComponent = Liferay.component(TOAST_ID);

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
					const instance = this;

					if (event.newVal === 'warned') {
						instance._beforeHostWarned(event);
					}
				},

				_uiSetActivated() {
					const instance = this;

					DOC.title = instance.reset('pageTitle').get('pageTitle');

					instance._host.unregisterInterval(instance._intervalId);

					if (instance._banner) {
						instance._destroyBanner();
					}
				},

				_uiSetExpired() {
					const instance = this;

					const banner = instance._getBanner();

					banner.open({
						message: instance._expiredText,
						title: Liferay.Language.get('danger'),
						type: 'danger',
					});

					DOC.title = instance.get('pageTitle');
				},

				_uiSetRemainingTime(remainingTime, counterTextNode) {
					const instance = this;

					remainingTime = instance._formatTime(remainingTime);

					if (!instance._alertClosed) {
						const alert = counterTextNode.closest(
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
					const instance = this;

					if (instance._banner) {
						instance._destroyBanner();
					}
				},

				initializer() {
					const instance = this;

					const host = instance.get('host');

					if (Liferay.Util.getTop() === CONFIG.win) {
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
								'<a class="alert-link" href="javascript:void(0);">' +
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
