/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

interface SessionConfig {
	autoExtend: boolean;
	redirectOnExpire: boolean;
	redirectUrl: string;
	sessionLength: number;
	sessionTimeoutOffset: number;
	warningLength: number;
}

type TSessionState = 'active' | 'expired' | 'warned';

const TOAST_ID = 'sessionToast';

const BUFFER_TIME: Array<any> = [];

export class Session {
	autoExtend: boolean;
	redirectOnExpire: boolean;
	redirectUrl: string;
	sessionLength: number;
	sessionTimeoutOffset: number;

	private _alertClosed: any;
	private _banner: any;
	private _cookieKey: string;
	private _cookieOptions: {path: string; secure: boolean};
	private _expiredText: string;
	private _initPageTitle: string;
	private _initTimestamp?: string;
	private _intervalId?: number | NodeJS.Timeout;
	private _pageTitle: string;
	private _timestamp: string;
	private _warningLength: number;
	private _warningText: string;
	private sessionState: TSessionState;

	constructor(config: SessionConfig) {
		this.autoExtend = config.autoExtend || false;
		this.redirectOnExpire = config.redirectOnExpire || true;
		this.redirectUrl = config.redirectUrl || '';
		this.sessionLength = config.sessionLength * 1000 || 0;
		this.sessionTimeoutOffset = config.sessionTimeoutOffset * 1000 || 0;

		this._alertClosed = '';
		this._banner = null;
		this._cookieKey =
			'LFR_SESSION_STATE_' + Liferay.ThemeDisplay.getRealUserId();
		this._cookieOptions = {
			path: Liferay.ThemeDisplay.getPathContext() || '/',
			secure: window.location.protocol === 'https:',
		};
		this._expiredText = Liferay.Language.get(
			'due-to-inactivity-your-session-has-expired'
		);
		this._initPageTitle = document.title;
		this._initTimestamp = Date.now().toString();
		this._pageTitle = document.title;
		this._timestamp = '';
		this._warningLength = config.warningLength * 1000 || this.sessionLength;
		this._warningText = Liferay.Util.sub(
			Liferay.Language.get('due-to-inactivity-your-session-will-expire'),
			[
				'<span class="countdown-timer">{0}</span>',
				(this.sessionLength / 60000).toString(),
				`<a class="alert-link" href="javascript:void(0);">${Liferay.Language.get('extend')}</a>`,
			]
		);
		this.sessionState = 'active';

		this._startTimer();
	}

	destructor() {
		clearInterval(this._intervalId);

		this._destroyBanner();
	}

	expire() {
		this.setSessionState('expired');
	}

	extend() {
		this.setSessionState('active');
	}

	async openToast(args: any) {
		const {openToast} = await import(
			Liferay.ThemeDisplay.getPathContext() +
				'/o/frontend-js-components-web/__liferay__/index.js'
		);

		openToast(args);
	}

	setSessionState(newVal: TSessionState) {
		const prevVal = this.sessionState;

		if (newVal === 'warned') {
			this._uiSetWarned();
		}
		else if (prevVal === 'expired' && prevVal !== newVal) {
			return;
		}
		else if (prevVal === 'active') {
			if (newVal === 'active') {
				this._uiSetActivated();
				this._extendSession();
			}
			else if (newVal === 'expired') {
				this._expireSession();
				this._uiSetExpired();
			}
		}

		this.sessionState = newVal;
	}

	warn() {
		this.setSessionState('warned');
	}

	private _destroyBanner() {
		const toast = document.getElementById(TOAST_ID);

		const toastRootElement = toast?.parentElement;

		Liferay.destroyComponent(TOAST_ID);

		if (toastRootElement) {
			toastRootElement.remove();
		}

		this._banner = false;
	}

	private _expireSession() {
		Liferay.Util.fetch(
			Liferay.ThemeDisplay.getPathMain() + '/portal/' + 'expire_session'
		).then((response) => {
			if (response.ok) {
				Liferay.fire('sessionExpired');

				if (this.redirectOnExpire) {
					location.href = this.redirectUrl;
				}
			}
			else {
				setTimeout(() => {
					this._expireSession;
				}, 1000);
			}
		});
	}

	private _extendSession() {
		this._setTimestamp();

		Liferay.Util.fetch(
			Liferay.ThemeDisplay.getPathMain() + '/portal/' + 'extend_session'
		).then((response) => {
			if (response.status === 500) {
				this.expire();
			}
		});
	}

	private _formatNumber(value: number) {
		return Math.floor(value).toString().padStart(2, '0');
	}

	private _formatTime(time: string | number) {
		time = Number(time);

		if (Number.isInteger(time) && time > 0) {
			time /= 1000;

			BUFFER_TIME[0] = this._formatNumber(time / 3600);

			time %= 3600;

			BUFFER_TIME[1] = this._formatNumber(time / 60);

			time %= 60;

			BUFFER_TIME[2] = this._formatNumber(time);

			time = BUFFER_TIME.join(':');
		}
		else {
			time = 0;
		}

		return time.toString();
	}

	private _getBanner() {
		let banner = this._banner;

		if (!banner) {
			const openToast = this.openToast;

			const toastDefaultConfig = {
				onClick: ({event}: any) => {
					if (event.target.classList.contains('alert-link')) {
						this.extend();
					}
				},
				renderData: {
					__reactDOMFlushSync: true,
					componentId: TOAST_ID,
				},
				toastProps: {
					autoClose: false,
					id: TOAST_ID,
					role: 'alert',
				},
			};

			openToast({
				message: this._warningText,
				type: 'warning',
				...toastDefaultConfig,
			});

			const toastComponent = Liferay.component(TOAST_ID);

			banner = {
				open: (props: any) => {
					this._destroyBanner();

					openToast({
						...props,
						...toastDefaultConfig,
					});
				},
				...toastComponent,
			};

			this._banner = banner;
		}

		return banner;
	}

	private _getWarningTime() {
		return this.sessionLength - this._warningLength;
	}

	private _setTimestamp() {
		this._timestamp = Date.now().toString();

		this._initTimestamp = this._timestamp;

		if (navigator.cookieEnabled) {
			Liferay.Util.Cookie.set(
				this._cookieKey,
				this._timestamp,
				Liferay.Util.Cookie.TYPES.NECESSARY,
				this._cookieOptions
			);
		}
	}

	private _startTimer() {
		this._intervalId = setInterval(() => {

			// LPS-82336 Maintain session state in multiple tabs

			if (this._initTimestamp !== this._timestamp) {
				this._setTimestamp();

				if (this.sessionState !== 'active') {
					this.setSessionState('active');
				}
			}

			const elapsed =
				Math.floor(
					(Date.now() - parseInt(this._timestamp, 10)) / 1000
				) * 1000;

			const hasExpired = elapsed >= this.sessionLength;
			const hasExpiredTimeoutOffset =
				elapsed >= this.sessionLength - this.sessionTimeoutOffset;
			const hasWarned = elapsed >= this._getWarningTime();

			if (hasExpired && this.sessionState !== 'expired') {
				this.expire();
			}
			else if (this.autoExtend && hasExpiredTimeoutOffset) {
				this.extend();
			}
			else if (
				!this.autoExtend &&
				hasWarned &&
				this.sessionState !== 'warned'
			) {
				this.warn();
			}

			if (!hasWarned) {
				this._uiSetActivated();
			}
			else if (!hasExpired) {
				this._uiSetRemainingTime(
					this.sessionLength - elapsed,
					document.querySelector(`#${TOAST_ID} .countdown-timer`)
				);
			}
		}, 1000);
	}

	private _uiSetActivated() {
		document.title = this._initPageTitle;

		if (this._banner) {
			this._destroyBanner();
		}
	}

	private _uiSetExpired() {
		if (this._banner) {
			this._banner.open({
				message: this._expiredText,
				title: Liferay.Language.get('danger'),
				type: 'danger',
			});

			document.title = this._pageTitle;
		}
	}

	private _uiSetRemainingTime(
		remainingTime: number | string,
		counterTextNode: HTMLElement | null
	) {
		remainingTime = this._formatTime(remainingTime);

		if (!this._alertClosed && counterTextNode) {
			const alert = counterTextNode.closest('div[role="alert"]');

			// Prevent screen reader from rereading alert

			if (alert) {
				alert.removeAttribute('role');
			}

			counterTextNode.innerHTML = remainingTime;
		}

		document.title =
			Liferay.Util.sub(Liferay.Language.get('session-expires-in-x'), [
				remainingTime,
			]) +
			' | ' +
			this._pageTitle;
	}

	private _uiSetWarned() {
		const sessionLength = this.sessionLength;
		const timestamp = parseInt(this._timestamp, 10);
		const warningLength = this._warningLength;

		let elapsed = sessionLength;

		if (timestamp) {
			elapsed = Math.floor((Date.now() - timestamp) / 1000) * 1000;
		}

		let remainingTime = sessionLength - elapsed;

		if (remainingTime > warningLength) {
			remainingTime = warningLength;
		}

		this._getBanner();

		setTimeout(() => {
			this._uiSetRemainingTime(
				remainingTime,
				document.querySelector(`#${TOAST_ID} .countdown-timer`)
			);
		}, 60);
	}
}
