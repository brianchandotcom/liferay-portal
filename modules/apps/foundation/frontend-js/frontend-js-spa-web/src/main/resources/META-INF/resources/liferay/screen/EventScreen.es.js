'use strict';

import HtmlScreen from 'senna/src/screen/HtmlScreen';
import globals from 'senna/src/globals/globals';
import {CancellablePromise} from 'metal-promise/src/promise/Promise';

/**
 * EventScreen
 *
 * Inherits from Senna's `HtmlScreen`. It performs logic that is
 * common to both {@link ActionURLScreen|ActionURLScreen} and 
 * {@link RenderURLScreen|RenderURLScreen}.
 */

class EventScreen extends HtmlScreen {

	/**
	 * @inheritDoc
	 */

	constructor() {
		super();

		this.cacheable = false;
		this.timeout = Liferay.SPA.app.timeout;
	}

	/**
	 * @inheritDoc
	 * Exposes the `screenDispose` event to the Liferay global object
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
	 * Exposes the `screenActivate` event to the Liferay global object
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
	 */

	addCache(content) {
		super.addCache(content);

		this.cacheLastModified = (new Date()).getTime();
	}

	/**
	 * Attempts a regular navigation to the given path, if a form is not being 
	 * submitted and the redirect Path can't be matched to a known route
	 * @param  {!String} redirectPath The path to check
	 */

	checkRedirectPath(redirectPath) {
		const app = Liferay.SPA.app;

		if (!globals.capturedFormElement && !app.findRoute(redirectPath)) {
			window.location.href = redirectPath;
		}
	}

	/**
	 * @inheritDoc
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
	 * Copies the classes and onload event from the virtual document to the actual
	 * document on the page
	 */

	copyBodyAttributes() {
		const virtualBody = this.virtualDocument.querySelector('body');

		document.body.className = virtualBody.className;
		document.body.onload = virtualBody.onload;
	}

	/**
	 * @inheritDoc
	 * Temporarily makes all permanent styles temporary when a language change is 
	 * detected, so that they are disposed, re-downloaded, and re-parsed before
	 * the screen flips. This is important because the content of the portal and
	 * theme styles are dynamic and may depend on the displayed language. 
	 * Right-to-left (RTL) languages, for instance, have diffrent styles.
	 * @param  {!Array} surfaces The surfaces to evaluate styles from
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
	 * Adds the `beforeScreenFlip` event to the lifecycle and exposes the
	 * `screenFlip` event to the Liferay global object
	 * @param  {!Array} surfaces The surfaces to flip
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
	 * Returns the cache if it's not expired or if the cache 
	 * feature is not disabled
	 * @return {!String} The cache contents
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
	 * Returns the timestamp the cache was last modified
	 * @return {!Number} `cacheLastModified` time
	 */

	getCacheLastModified() {
		return this.cacheLastModified;
	}

	/**
	 * Returns whether a given status code is considered valid
	 * @param  {!Number} The status code to check
	 * @return {!Boolean} True if the given status code is valid
	 */

	isValidResponseStatusCode(statusCode) {
		const validStatusCodes = Liferay.SPA.app.getValidStatusCodes();

		return (statusCode >= 200 && statusCode <= 500) || (validStatusCodes.indexOf(statusCode) > -1);
	}

	/**
	 * @inheritDoc
	 * @return {!String} The cache contents
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
	 * The method used by {@link EventScreen#evaluateStyles|evaluateStyles}. This 
	 * changes the static properties `HtmlScreen.selectors.stylesTemporary` and 
	 * `HtmlScreen.selectors.stylesPermanent` temporarily. The action can be 
	 * undone by {@link EventScreen#restoreSelectors_|restoreSelectors_}
	 * @param  {!String} currentLanguageId
	 * @param  {!String} languageId
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
	 * The method used by {@link EventScreen#evaluateStyles|evaluateStyles}. This
	 * restores the permanent and temporary selectors changed by 
	 * {@link EventScreen#makePermanentSelectorsTemporary_|makePermanentSelectorsTemporary_}.
	 */

	restoreSelectors_() {
		HtmlScreen.selectors.stylesPermanent = this.stylesPermanentSelector_ || HtmlScreen.selectors.stylesPermanent;
		HtmlScreen.selectors.stylesTemporary = this.stylesTemporarySelector_ || HtmlScreen.selectors.stylesTemporary;
	}

	/**
	 * Executes the `document.body.onload` event every time a navigation occurs
	 */

	runBodyOnLoad() {
		const onLoad = document.body.onload;

		if (onLoad) {
			onLoad();
		}
	}
}

export default EventScreen;