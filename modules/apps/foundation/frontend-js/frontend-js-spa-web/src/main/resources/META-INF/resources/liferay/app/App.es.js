'use strict';

import App from 'senna/src/app/App';
import core from 'metal/src/core';
import dom from 'metal-dom/src/dom';
import Utils from '../util/Utils.es';
import LiferaySurface from '../surface/Surface.es';

class LiferayApp extends App {
	constructor() {
		super();

		this.portletsBlacklist = {};
		this.validStatusCodes = [];

		var exceptionsSelector = ':not([target="_blank"]):not([data-senna-off]):not([data-resource-href])';

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
		var lastModifiedInterval = (new Date()).getTime() - screen.getCacheLastModified();

		return lastModifiedInterval > this.getCacheExpirationTime();
	}

	onBeforeNavigate(event) {
		if (Liferay.SPA.clearScreensCache || event.form) {
			this.clearScreensCache();
		}

		Liferay.fire(
			'beforeNavigate',
			{
				app: this,
				path: event.path
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

		if (event.error) {
			if (event.error.invalidStatus || event.error.requestError || event.error.timeout) {
				if (event.form) {
					event.form.submit();
				}
				else {
					window.location.href = event.path;
				}
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
	}

	setPortletsBlacklist(portletsBlacklist) {
		this.portletsBlacklist = portletsBlacklist;
	}

	setValidStatusCodes(validStatusCodes) {
		this.validStatusCodes = validStatusCodes;
	}
}

export default LiferayApp;