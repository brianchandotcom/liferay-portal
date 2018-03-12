'use strict';

import HtmlScreen from 'senna/src/screen/HtmlScreen';
import globals from 'senna/src/globals/globals';
import {CancellablePromise} from 'metal-promise/src/promise/Promise';

/**
 * EventScreen
 *
 * This class inherits from Senna's HtmlScreen. It performs logic that are
 * common to both ActionURLScreen and RenderURLScreen.
 * @review
 */

class EventScreen extends HtmlScreen {

	/**
	 * @inheritDoc
	 * @review
	 */

	constructor() {
		super();

		this.cacheable = false;
		this.timeout = Liferay.SPA.app.timeout;
	}

	/**
	 * @inheritDoc
	 * Exposes the 'screenDispose' event to the Liferay global object.
	 * @review
	 */

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

	/**
	 * @inheritDoc
	 * Exposes the 'screenActivate' event to the Liferay global object.
	 * @review
	 */

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

	/**
	 * @inheritDoc
	 * @review
	 */

	addCache(content) {
		super.addCache(content);

		this.cacheLastModified = (new Date()).getTime();
	}

	/**
	 * If we are not submitting a form and we cannot match the redirect path
	 * to a known route, we try to do a regular navigation to the given path.
	 * @param  {!String} redirectPath The path to check.
	 * @review
	 */

	checkRedirectPath(redirectPath) {
		const app = Liferay.SPA.app;

		if (!globals.capturedFormElement && !app.findRoute(redirectPath)) {
			window.location.href = redirectPath;
		}
	}

	/**
	 * @inheritDoc
	 * @review
	 */

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

	/**
	 * @inheritDoc
	 * @review
	 */

	beforeScreenFlip() {
		Liferay.fire(
			'beforeScreenFlip',
			{
				app: Liferay.SPA.app,
				screen: this
			}
		);
	}

	/**
	 * Copies classes and onload event from virtual document to actual
	 * document on the page.
	 * @review
	 */

	copyBodyAttributes() {
		const virtualBody = this.virtualDocument.querySelector('body');

		document.body.className = virtualBody.className;
		document.body.onload = virtualBody.onload;
	}

	/**
	 * @inheritDoc
	 * If a language change is detected, we temporarely make all permanent styles
	 * temporary, so that they are disposed and re-downloaded an re-parsed before
	 * the screen flips. This is important because the content of our portal and
	 * theme styles are dynamic and may depend on the displayed language. RTL
	 * languages, for instance, have diffrent styles.
	 * @param  {!Array} surfaces The surfaces to evaluate styles from.
	 * @review
	 */

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

	/**
	 * @inheritDoc
	 * Adds the beforeScreenFlip event to the lifecycle, and exposes the
	 * 'screenFlip' event to the Liferay global object.
	 * @param  {!Array} surfaces The surfaces to flip.
	 * @review
	 */

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

	/**
	 * @inheritDoc
	 * Returns cache if it's not expired or if the cache feature is not diabled.
	 * @return {!String} The cache contents.
	 * @review
	 */

	getCache() {
		let cache = null;

		const app = Liferay.SPA.app;

		if (app.isCacheEnabled() && !app.isScreenCacheExpired(this)) {
			cache = super.getCache();
		}

		return cache;
	}

	/**
	 * Returns the timestamp the cache was last modified.
	 * @return {!Number} cacheLastModified time.
	 * @review
	 */

	getCacheLastModified() {
		return this.cacheLastModified;
	}

	/**
	 * Wether or not a given status code is considered valid.
	 * @param  {!Number} The status code to check.
	 * @return {!Boolean} True if the given status code is valid.
	 * @review
	 */

	isValidResponseStatusCode(statusCode) {
		const validStatusCodes = Liferay.SPA.app.getValidStatusCodes();

		return (statusCode >= 200 && statusCode <= 500) || (validStatusCodes.indexOf(statusCode) > -1);
	}

	/**
	 * @inheritDoc
	 * @return {!String} The cache contents.
	 * @review
	 */

	load(path) {
		return super.load(path)
			.then(
				(content) => {
					const redirectPath = this.beforeUpdateHistoryPath(path);

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

	/**
	 * Method used by {this.evaluateStyles}. Detailed description about
	 * why this exists is given there. It changes the static properties
	 * HtmlScreen.selectors.stylesTemporary and HtmlScreen.selectors.stylesPermanent
	 * temporarely. The action can be undone by {this.restoreSelectors_}
	 * @param  {!String} currentLanguageId.
	 * @param  {!String} languageId.
	 * @review
	 */

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

	/**
	 * Method used by {this.evaluateStyles}. Detailed description about
	 * why this exists is given there. It restores the permanent and temporary
	 * selectors changed by (this.makePermanentSelectorsTemporary_)
	 * @review
	 */

	restoreSelectors_() {
		HtmlScreen.selectors.stylesPermanent = this.stylesPermanentSelector_ || HtmlScreen.selectors.stylesPermanent;
		HtmlScreen.selectors.stylesTemporary = this.stylesTemporarySelector_ || HtmlScreen.selectors.stylesTemporary;
	}

	/**
	 * Executes the document.body.onload every time a navigation happens.
	 * @review
	 */

	runBodyOnLoad() {
		const onLoad = document.body.onload;

		if (onLoad) {
			onLoad();
		}
	}
}

export default EventScreen;