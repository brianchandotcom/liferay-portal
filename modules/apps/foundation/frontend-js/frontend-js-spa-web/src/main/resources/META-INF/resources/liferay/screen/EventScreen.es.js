'use strict';

import HtmlScreen from 'senna/src/screen/HtmlScreen';
import globals from 'senna/src/globals/globals';
import {CancellablePromise} from 'metal-promise/src/promise/Promise';

class EventScreen extends HtmlScreen {
	constructor() {
		super();

		this.cacheable = false;
		this.timeout = Liferay.SPA.app.timeout;
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
		const virtualBody = this.virtualDocument.querySelector('body');

		document.body.className = virtualBody.className;
		document.body.onload = virtualBody.onload;
	}

	evaluateStyles(surfaces) {
		const currentLanguageId = document.querySelector('html').lang.replace('-', '_');
		const languageId = this.virtualDocument.lang.replace('-', '_');

		if (currentLanguageId !== languageId) {
			this.stylesPermanentSelector_ = HtmlScreen.selectors.stylesPermanent;
			this.stylesTemporarySelector_ = HtmlScreen.selectors.stylesTemporary;

			this.makePermanentSelectorsTemporary_(currentLanguageId, languageId);
		}

		return super.evaluateStyles(surfaces).then(this.restoreSelectors_.bind(this));
	}

	flip(surfaces) {
		this.copyBodyAttributes();

		return CancellablePromise.resolve(this.beforeScreenFlip())
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

		return (statusCode >= 200 && statusCode <= 500) || (validStatusCodes.indexOf(statusCode) > -1);
	}

	load(path) {
		return super.load(path)
			.then(
				(content) => {
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

	makePermanentSelectorsTemporary_(currentLanguageId, languageId) {
		HtmlScreen.selectors.stylesTemporary = HtmlScreen.selectors.stylesTemporary
			.split(',')
			.concat(
				HtmlScreen.selectors.stylesPermanent
				.split(',')
				.map(
					item => `${item}[href*="${currentLanguageId}"]`
				)
			)
			.join();

		HtmlScreen.selectors.stylesPermanent = HtmlScreen.selectors.stylesPermanent
			.split(',')
			.map(
				item => `${item}[href*="${languageId}"]`
			)
			.join();
	}

	restoreSelectors_() {
		HtmlScreen.selectors.stylesPermanent = this.stylesPermanentSelector_ || HtmlScreen.selectors.stylesPermanent;
		HtmlScreen.selectors.stylesTemporary = this.stylesTemporarySelector_ || HtmlScreen.selectors.stylesTemporary;
	}

	runBodyOnLoad() {
		var onLoad = document.body.onload;

		if (onLoad) {
			onLoad();
		}
	}
}

export default EventScreen;