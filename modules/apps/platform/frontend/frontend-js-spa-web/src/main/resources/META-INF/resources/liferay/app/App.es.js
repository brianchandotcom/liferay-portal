'use strict';

import App from 'senna/src/app/App';
import dom from 'metal-dom/src/dom';
import globals from 'senna/src/globals/globals';
import Uri from 'metal-uri/src/Uri';
import Utils from '../util/Utils.es';

class LiferayApp extends App {
	constructor() {
		super();

		this.blacklist = {};

		var exceptionsSelector = ':not([target="_blank"]):not([data-senna-off]):not([data-resource-href])';

		this.setFormSelector('form' + exceptionsSelector);
		this.setLinkSelector('a' + exceptionsSelector);

		this.on('beforeNavigate', this.onBeforeNavigate);
		this.on('endNavigate', this.onEndNavigate);
		this.on('startNavigate', this.onStartNavigate);

		Liferay.on('io:complete', this.onLiferayIOComplete, this);

		this.addSurfaces(document.body.id);

		dom.append(document.body, '<div class="lfr-surface-loading-bar"></div>');
	}

	onBeforeNavigate(event) {
		Liferay.fire(
			'beforeNavigate',
			{
				app: this,
				path: event.path
			}
		);
	}

	onDocClickDelegate_(event) {
		var inBlacklist = false;

		Object.keys(this.blacklist).map(
			(portletId) => {
				var boundaryId = Utils.getPortletBoundaryId(portletId);
				var portlets = document.querySelectorAll('[id^="' + boundaryId +  '"]');

				Array.prototype.slice.call(portlets).forEach(
					(portlet) => {
						if (dom.contains(portlet, event.delegateTarget)) {
							inBlacklist = true;
							return;
						}
					}
				);
			}
		);

		if (inBlacklist) {
			return;
		}

		super.onDocClickDelegate_(event);
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

		if (!event.error && Liferay.Layout && Liferay.Data.layoutConfig) {
			Liferay.Layout.init();
		}

		if (event.form) {
			this.clearScreensCache();
		}

		AUI().Get._insertCache = {};

		dom.removeClasses(document.body, 'lfr-surface-loading');
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

		dom.addClasses(document.body, 'lfr-surface-loading');
	}

	setBlacklist(blacklist) {
		this.blacklist = blacklist;
	}
}

export default LiferayApp;