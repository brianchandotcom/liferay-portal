'use strict';

import App from 'senna/src/app/App';
import core from 'metal/src/core';
import dom from 'metal-dom/src/dom';
import LiferaySurface from '../surface/Surface.es';
import Utils from '../util/Utils.es';
import {CancellablePromise} from 'metal-promise/src/promise/Promise';

class LiferayApp extends App {
	constructor() {
		super();

		this.portletsBlacklist = {};
		this.validStatusCodes = [];

		this.setShouldUseFacade(true);

		this.timeout = Math.max(Liferay.SPA.requestTimeout, 0) || Utils.getMaxTimeout();
		this.timeoutAlert = null;

		var exceptionsSelector = Liferay.SPA.navigationExceptionSelectors;

		this.setFormSelector('form' + exceptionsSelector);
		this.setLinkSelector('a' + exceptionsSelector);
		this.setLoadingCssClass('lfr-spa-loading');

		this.on('beforeNavigate', this.onBeforeNavigate);
		this.on('endNavigate', this.onEndNavigate);
		this.on('startNavigate', this.onStartNavigate);

		Liferay.on('io:complete', this.onLiferayIOComplete, this);

		var body = document.body;

		if (!body.id) {
			body.id = 'senna_surface' + core.getUid();
		}

		this.addSurfaces(new LiferaySurface(body.id));

		dom.append(body, '<div class="lfr-spa-loading-bar"></div>');
	}

	getCacheExpirationTime() {
		return Liferay.SPA.cacheExpirationTime;
	}

	getValidStatusCodes() {
		return this.validStatusCodes;
	}

	isCacheEnabled() {
		return this.getCacheExpirationTime() > -1;
	}

	isInPortletBlacklist(element) {
		return Object.keys(this.portletsBlacklist).some(
			(portletId) => {
				var boundaryId = Utils.getPortletBoundaryId(portletId);

				var portlets = document.querySelectorAll('[id^="' + boundaryId + '"]');

				return Array.prototype.slice.call(portlets).some(portlet => dom.contains(portlet, element));
			}
		);
	}

	isScreenCacheExpired(screen) {
		if (this.getCacheExpirationTime() === 0) {
			return false;
		}

		var lastModifiedInterval = (new Date()).getTime() - screen.getCacheLastModified();

		return lastModifiedInterval > this.getCacheExpirationTime();
	}

	onBeforeNavigate(data, event) {
		if (Liferay.SPA.clearScreensCache || data.form) {
			this.clearScreensCache();
		}

		Liferay.fire(
			'beforeNavigate',
			{
				app: this,
				originalEvent: event,
				path: data.path
			}
		);
	}

	onDocClickDelegate_(event) {
		if (this.isInPortletBlacklist(event.delegateTarget)) {
			return;
		}

		super.onDocClickDelegate_(event);
	}

	onDocSubmitDelegate_(event) {
		if (this.isInPortletBlacklist(event.delegateTarget)) {
			return;
		}

		super.onDocSubmitDelegate_(event);
	}

	onEndNavigate(event) {
		Liferay.fire(
			'endNavigate',
			{
				app: this,
				error: event.error,
				path: event.path
			}
		);

		if (!this.pendingNavigate) {
			this._clearRequestTimer();
			this._hideTimeoutAlert();
		}

		if (event.error) {
			if (event.error.invalidStatus || event.error.requestError || event.error.timeout) {
				this._createNotification(
					{
						message: Liferay.Language.get('there-was-an-unexpected-error.-please-refresh-the-current-page'),
						title: Liferay.Language.get('error'),
						type: 'danger'
					}
				);
			}
		}
		else if (Liferay.Layout && Liferay.Data.layoutConfig) {
			Liferay.Layout.init();
		}

		AUI().Get._insertCache = {};

		Liferay.DOMTaskRunner.reset();
	}

	onLiferayIOComplete() {
		this.clearScreensCache();
	}

	onStartNavigate(event) {
		Liferay.fire(
			'startNavigate',
			{
				app: this,
				path: event.path
			}
		);

		this._startRequestTimer(event.path);
	}

	setPortletsBlacklist(portletsBlacklist) {
		this.portletsBlacklist = portletsBlacklist;
	}

	setValidStatusCodes(validStatusCodes) {
		this.validStatusCodes = validStatusCodes;
	}

	_clearRequestTimer() {
		if (this.requestTimer) {
			clearTimeout(this.requestTimer);
		}
	}

	_createNotification(config) {
		return new CancellablePromise(
			(resolve) => {
				AUI().use(
					'liferay-notification',
					() => {
						resolve(
							new Liferay.Notification(
								Object.assign(
									{
										closeable: true,
										delay: {
											hide: 0,
											show: 0
										},
										duration: 500,
										type: 'warning'
									},
									config
								)
							).render('body')
						);
					}
				);
			}
		);
	}

	_hideTimeoutAlert() {
		if (this.timeoutAlert) {
			this.timeoutAlert.hide();
		}
	}

	_startRequestTimer(path) {
		this._clearRequestTimer();

		if (Liferay.SPA.userNotification.timeout > 0) {
			this.requestTimer = setTimeout(
				() => {
					Liferay.fire(
						'spaRequestTimeout',
						{
							path: path
						}
					);

					if (!this.timeoutAlert) {
						this._createNotification(
							{
								message: Liferay.SPA.userNotification.message,
								title: Liferay.SPA.userNotification.title,
								type: 'warning'
							}
						).then(
							(alert) => {
								this.timeoutAlert = alert;
							}
						);
					}
					else {
						this.timeoutAlert.show();
					}
				},
				Liferay.SPA.userNotification.timeout
			);
		}
	}
}

export default LiferayApp;