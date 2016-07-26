'use strict';

import HtmlScreen from 'senna/src/screen/HtmlScreen';
import globals from 'senna/src/globals/globals';
import {CancellablePromise} from 'metal-promise/src/promise/Promise';
import Utils from '../util/Utils.es';

class EventScreen extends HtmlScreen {
	constructor() {
		super();

		this.cacheable = false;
		this.timeout = Math.max(Liferay.SPA.requestTimeout, 0) || Utils.getMaxTimeout();
	}

	dispose() {
		super.dispose();

		Liferay.fire(
			'screenDispose',
			{
				app: Liferay.SPA.app,
				screen: this
			}
		);
	}

	activate() {
		super.activate();

		Liferay.fire(
			'screenActivate',
			{
				app: Liferay.SPA.app,
				screen: this
			}
		);
	}

	_clearRequestTimer() {
		if (this.requestTimer) {
			clearTimeout(this.requestTimer);
		}

		if (this.timeoutAlert) {
			this.timeoutAlert.hide();
		}
	}

	_createTimeoutNotification() {
		var instance = this;

		AUI().use(
			'liferay-notification',
			() => {
				instance.timeoutAlert = new Liferay.Notification(
					{
						closeable: true,
						delay: {
							hide: 0,
							show: 0
						},
						duration: 500,
						message: Liferay.SPA.userNotification.message,
						title: Liferay.SPA.userNotification.title,
						type: 'warning'
					}
				).render('body');
			}
		);
	}

	_startRequestTimer(path) {
		if (Liferay.SPA.userNotification.timeout > 0) {
			this._clearRequestTimer();

			this.requestTimer = setTimeout(
				() => {
					Liferay.fire(
						'spaRequestTimeout',
						{
							path: path
						}
					);

					if (!this.timeoutAlert) {
						this._createTimeoutNotification();
					}
					else {
						this.timeoutAlert.show();
					}
				},
				Liferay.SPA.userNotification.timeout
			);
		}
	}

	addCache(content) {
		super.addCache(content);

		this.cacheLastModified = (new Date()).getTime();
	}

	checkRedirectPath(redirectPath) {
		var app = Liferay.SPA.app;

		if (!globals.capturedFormElement && !app.findRoute(redirectPath)) {
			window.location.href = redirectPath;
		}
	}

	deactivate() {
		super.deactivate();

		Liferay.fire(
			'screenDeactivate',
			{
				app: Liferay.SPA.app,
				screen: this
			}
		);
	}

	beforeScreenFlip() {
		Liferay.fire(
			'beforeScreenFlip',
			{
				app: Liferay.SPA.app,
				screen: this
			}
		);
	}

	copyBodyAttributes() {
		var virtualBody = this.virtualDocument.querySelector('body');

		document.body.className = virtualBody.className;
		document.body.onload = virtualBody.onload;
	}

	flip(surfaces) {
		this.copyBodyAttributes();

		return CancellablePromise.resolve(Utils.resetAllPortlets())
			.then(CancellablePromise.resolve(this.beforeScreenFlip()))
			.then(super.flip(surfaces))
			.then(
				() => {
					this.runBodyOnLoad();

					Liferay.fire(
						'screenFlip',
						{
							app: Liferay.SPA.app,
							screen: this
						}
					);
				}
			);
	}

	getCache() {
		var app = Liferay.SPA.app;

		if (app.isCacheEnabled() && !app.isScreenCacheExpired(this)) {
			return super.getCache();
		}

		return null;
	}

	getCacheLastModified() {
		return this.cacheLastModified;
	}

	isValidResponseStatusCode(statusCode) {
		var validStatusCodes = Liferay.SPA.app.getValidStatusCodes();

		return super.isValidResponseStatusCode(statusCode) || (validStatusCodes.indexOf(statusCode) > -1);
	}

	load(path) {
		this._startRequestTimer(path);

		return super.load(path)
			.then(
				(content) => {
					this._clearRequestTimer();

					var redirectPath = this.beforeUpdateHistoryPath(path);

					this.checkRedirectPath(redirectPath);

					Liferay.fire(
						'screenLoad',
						{
							app: Liferay.SPA.app,
							content: content,
							screen: this
						}
					);

					return content;
				}
			);
	}

	runBodyOnLoad() {
		var onLoad = document.body.onload;

		if (onLoad) {
			onLoad();
		}
	}
}

export default EventScreen;