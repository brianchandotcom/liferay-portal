'use strict';

import App from 'senna/src/app/App';
import core from 'metal/src/core';
import dom from 'metal-dom/src/dom';
import LiferaySurface from '../surface/Surface.es';
import Utils from '../util/Utils.es';
import {CancellablePromise} from 'metal-promise/src/promise/Promise';

/**
 * LiferayApp
 *
 * This class inherits from senna/src/app/App and adds Liferay specific
 * behavior to Senna's default App. For instance:
 * 1. Makes cache expiration time configurable from System Settings
 * 2. Allows setting valid status codes. Liferay has its own list of valid
 * status codes listed in ServletResponseConstants.java
 * 3. Shows alert notifications when requests take too long or when they fail.
 * 4. Adds a portletBlacklist option so that we can exclude specific portlets
 * from the SPA lifecycle.
 * @review
 */

class LiferayApp extends App {

	/**
	 * @inheritDoc
	 * @review
	 */

	constructor() {
		super();

		this.portletsBlacklist = {};
		this.validStatusCodes = [];

		this.setShouldUseFacade(true);

		this.timeout = Math.max(Liferay.SPA.requestTimeout, 0) || Utils.getMaxTimeout();
		this.timeoutAlert = null;

		const exceptionsSelector = Liferay.SPA.navigationExceptionSelectors;

		this.setFormSelector('form' + exceptionsSelector);
		this.setLinkSelector('a' + exceptionsSelector);
		this.setLoadingCssClass('lfr-spa-loading');

		this.on('beforeNavigate', this.onBeforeNavigate);
		this.on('endNavigate', this.onEndNavigate);
		this.on('navigationError', this.onNavigationError);
		this.on('startNavigate', this.onStartNavigate);

		Liferay.on('beforeScreenFlip', Utils.resetAllPortlets);
		Liferay.on('io:complete', this.onLiferayIOComplete, this);

		const body = document.body;

		if (!body.id) {
			body.id = 'senna_surface' + core.getUid();
		}

		this.addSurfaces(new LiferaySurface(body.id));

		dom.append(body, '<div class="lfr-spa-loading-bar"></div>');
	}

	/**
	 * Returns the cache expiration time configuration. This configuration
	 * is set uppon App initialization (See init.tmpl) and the value comes
	 * from System Settings.
	 * @return {!Number} The cacheExpirationTime value
	 * @review
	 */

	getCacheExpirationTime() {
		return Liferay.SPA.cacheExpirationTime;
	}

	/**
	 * Returns the valid status codes accepted by Liferay. This values
	 * come from ServletResponseConstants.java.
	 * @return {!Array} The property validStatusCodes.
	 * @review
	 */

	getValidStatusCodes() {
		return this.validStatusCodes;
	}

	/**
	 * Returns wether or not cache is enabled. Cache is considered
	 * enabled whenever {this.getCacheExpirationTime} is greater than zero.
	 * @return {!Boolean} True if cache is enabled.
	 * @review
	 */

	isCacheEnabled() {
		return this.getCacheExpirationTime() > -1;
	}

	/**
	 * Returns wether or not a given portlet element is in the black list
	 * of portlets that should not behave like an SPA.
	 * @param  {!String} element The portlet boundary DOM node.
	 * @return {!Boolean} True if portlet element is blacklisted.
	 * @review
	 */

	isInPortletBlacklist(element) {
		return Object.keys(this.portletsBlacklist).some(
			(portletId) => {
				const boundaryId = Utils.getPortletBoundaryId(portletId);

				const portlets = document.querySelectorAll('[id^="' + boundaryId + '"]');

				return Array.prototype.slice.call(portlets).some(portlet => dom.contains(portlet, element));
			}
		);
	}

	/**
	 * Returns wether or not a given Screen has its cache expired at the
	 * moment this method is called. The expiration timeframe is based on
	 * the value returned by {this.getCacheExpirationTime}.
	 * @param  {!Screen} screen The Senna Screen.
	 * @return {!Boolean} True cache has expired.
	 * @review
	 */

	isScreenCacheExpired(screen) {
		let screenCacheExpired = false;

		if (this.getCacheExpirationTime() !== 0) {
			const lastModifiedInterval = (new Date()).getTime() - screen.getCacheLastModified();

			screenCacheExpired = lastModifiedInterval > this.getCacheExpirationTime();
		}

		return screenCacheExpired;
	}

	/**
	 * Callback for Senna's beforeNavigate event. For Lifray we clear
	 * the all screens cache whenever the flag Liferay.SPA.clearScreensCache
	 * is set or whenever a form submission is about to occur.
	 * We also expose the beforeNavigate event to the Liferay global object
	 * so that anyone can listen to it.
	 * @param  {!Object} data Data about the event.
	 * @param  {!Event} event The event object.
	 * @review
	 */

	onBeforeNavigate(data, event) {
		if (Liferay.SPA.clearScreensCache || data.form) {
			this.clearScreensCache();
		}

		this._clearLayoutData();

		Liferay.fire(
			'beforeNavigate',
			{
				app: this,
				originalEvent: event,
				path: data.path
			}
		);
	}

	/**
	 * Private event handler function called when the "dataLayoutConfigReady"
	 * is fired on the Liferay object. This is when we make sure to
	 * initialize Liferay.Layout
	 * @param  {!Event} event The event object.
	 * @review
	 */

	onDataLayoutConfigReady_(event) {
		if (Liferay.Layout) {
			Liferay.Layout.init(Liferay.Data.layoutConfig);
		}
	}

	/**
	 * @inheritDoc
	 * Overrides Senna's default onDocClickDelegate_ handler and
	 * halts SPA behavior if the click target is inside a black-listed
	 * portlet.
	 * @param  {!Event} event The event object.
	 * @review
	 */

	onDocClickDelegate_(event) {
		if (this.isInPortletBlacklist(event.delegateTarget)) {
			return;
		}

		super.onDocClickDelegate_(event);
	}

	/**
	 * @inheritDoc
	 * Overrides Senna's default onDocSubmitDelegate_ handler and
	 * halts SPA behavior if the form is inside a black-listed
	 * portlet.
	 * @param  {!Event} event The event object.
	 * @review
	 */

	onDocSubmitDelegate_(event) {
		if (this.isInPortletBlacklist(event.delegateTarget)) {
			return;
		}

		super.onDocSubmitDelegate_(event);
	}

	/**
	 * Callback for Senna's "endNavigate" event. Exposes the "endNavigate"
	 * event to the Liferay global object.
	 * @param  {!Event} event The event object.
	 * @review
	 */

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

		if (!event.error) {
			this.dataLayoutConfigReadyHandle_ = Liferay.once('dataLayoutConfigReady', this.onDataLayoutConfigReady_);
		}

		AUI().Get._insertCache = {};

		Liferay.DOMTaskRunner.reset();
	}

	/**
	 * Callback for Liferay's "io:complete" event. Clears screens cache when
	 * an async request happens.
	 * @review
	 */

	onLiferayIOComplete() {
		this.clearScreensCache();
	}

	/**
	 * Callback for Senna's "navigationError" event. Shows an appropriate
	 * alert message to the user with information about the error.
	 * @param  {!Event} event The event object.
	 * @review
	 */

	onNavigationError(event) {
		if (event.error.requestPrematureTermination) {
			window.location.href = event.path;
		}
		else if (event.error.invalidStatus || event.error.requestError || event.error.timeout) {
			let message = Liferay.Language.get('there-was-an-unexpected-error.-please-refresh-the-current-page');

			if (Liferay.SPA.debugEnabled) {
				console.error(event.error);

				if (event.error.invalidStatus) {
					message = Liferay.Language.get('the-spa-navigation-request-received-an-invalid-http-status-code');
				}
				if (event.error.requestError) {
					message = Liferay.Language.get('there-was-an-unexpected-error-in-the-spa-request');
				}
				if (event.error.timeout) {
					message = Liferay.Language.get('the-spa-request-timed-out');
				}
			}

			Liferay.Data.layoutConfig = this.dataLayoutConfig_;

			this._createNotification(
				{
					message: message,
					title: Liferay.Language.get('error'),
					type: 'danger'
				}
			);
		}
	}

	/**
	 * Callback for Senna's "startNavigate" event. Exposes the "startNavigate"
	 * event to the Liferay global object.
	 * @param  {!Event} event The event object.
	 * @review
	 */

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

	/**
	 * Setter for the portletsBlacklist property.
	 * @param  {!Object} portletsBlacklist
	 * @review
	 */

	setPortletsBlacklist(portletsBlacklist) {
		this.portletsBlacklist = portletsBlacklist;
	}

	/**
	 * Setter for the validStatusCodes property.
	 * @param  {!Array} validStatusCodes
	 * @review
	 */

	setValidStatusCodes(validStatusCodes) {
		this.validStatusCodes = validStatusCodes;
	}

	/**
	 * Clears and detaches event handlers for Liferay's "dataLayoutConfigReady"
	 * event.
	 * @review
	 */

	_clearLayoutData() {
		this.dataLayoutConfig_ = Liferay.Data.layoutConfig;

		Liferay.Data.layoutConfig = null;

		if (this.dataLayoutConfigReadyHandle_) {
			this.dataLayoutConfigReadyHandle_.detach();
			this.dataLayoutConfigReadyHandle_ = null;
		}
	}

	/**
	 * Clears the timer that will show the user a notification when the SPA
	 * request takes longer than the thresshold time configured in
	 * System Settings (Liferay.SPA.userNotification.timeout).
	 * @review
	 */

	_clearRequestTimer() {
		if (this.requestTimer) {
			clearTimeout(this.requestTimer);
		}
	}

	/**
	 * Creates an user notification
	 * @param  {!Object} configuration object that's passed to Liferay.Notification
	 * @return {!CancellablePromise} A promise that will render a notification
	 * on the screen when resolved.
	 * @review
	 */

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

	/**
	 * Hides the request timeout alert.
	 * @review
	 */

	_hideTimeoutAlert() {
		if (this.timeoutAlert) {
			this.timeoutAlert.hide();
		}
	}

	/**
	 * Starts the timer that will show the user a notification when the SPA
	 * request takes longer than the threshold time configured in
	 * System Settings (Liferay.SPA.userNotification.timeout).
	 * @param  {!String} path The path that may time out.
	 * @review
	 */

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

	/**
	 * @inheritDoc
	 * @review
	 */

	updateHistory_(title, path, state, opt_replaceHistory) {
		if (state && state.redirectPath) {
			state.path = state.redirectPath;
		}

		super.updateHistory_(title, path, state, opt_replaceHistory);
	}
}

export default LiferayApp;