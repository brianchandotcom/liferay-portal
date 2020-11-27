/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {runScriptsInElement} from 'frontend-js-web';

import Cacheable from '../cacheable/Cacheable';
import {getUid, log} from '../util/utils';

class Screen extends Cacheable {

	/**
	 * Screen class is a special type of route handler that provides helper
	 * utilities that adds lifecycle and methods to provide content to each
	 * registered surface.
	 */
	constructor() {
		super();

		/**
		 * Holds the screen id.
		 * @type {string}
		 * @protected
		 */
		this.id = this.makeId_(getUid());

		/**
		 * Holds the screen meta tags. Relevant when the meta tags
		 * should be updated when screen is rendered.
		 */
		this.metas = null;

		/**
		 * Holds the screen title. Relevant when the page title should be
		 * upadated when screen is rendered.
		 * @type {?string=}
		 * @default null
		 * @protected
		 */
		this.title = null;
	}

	/**
	 * Fires when the screen is active. Allows a screen to perform any setup
	 * that requires its DOM to be visible. Lifecycle.
	 */
	activate() {
		log('Screen [' + this + '] activate');
	}

	/**
	 * Gives the Screen a chance to cancel the navigation and stop itself from
	 * activating. Can be used, for example, to prevent navigation if a user
	 * is not authenticated. Lifecycle.
	 * @return {boolean=|?Promise=} If returns or resolves to true,
	 *     the current screen is locked and the next nagivation interrupted.
	 */
	beforeActivate() {
		log('Screen [' + this + '] beforeActivate');
	}

	/**
	 * Gives the Screen a chance to cancel the navigation and stop itself from
	 * being deactivated. Can be used, for example, if the screen has unsaved
	 * state. Lifecycle. Clean-up should not be preformed here, since the
	 * navigation may still be cancelled. Do clean-up in deactivate.
	 * @return {boolean=|?Promise=} If returns or resolves to true,
	 *     the current screen is locked and the next nagivation interrupted.
	 */
	beforeDeactivate() {
		log('Screen [' + this + '] beforeDeactivate');
	}

	/**
	 * Gives the Screen a chance format the path before history update.
	 * @path {!string} path Navigation path.
	 * @return {!string} Navigation path to use on history.
	 */
	beforeUpdateHistoryPath(path) {
		return path;
	}

	/**
	 * Gives the Screen a chance format the state before history update.
	 * @path {!object} state History state.
	 * @return {!object} History state to use on history.
	 */
	beforeUpdateHistoryState(state) {
		return state;
	}

	/**
	 * Allows a screen to do any cleanup necessary after it has been
	 * deactivated, for example cancelling outstanding requests or stopping
	 * timers. Lifecycle.
	 */
	deactivate() {
		log('Screen [' + this + '] deactivate');
	}

	/**
	 * Dispose a screen, either after it is deactivated (in the case of a
	 * non-cacheable view) or when the App is itself disposed for whatever
	 * reason. Lifecycle.
	 */
	disposeInternal() {
		super.disposeInternal();
		log('Screen [' + this + '] dispose');
	}

	/**
	 * Allows a screen to evaluate scripts before the element is made visible.
	 * Lifecycle.
	 * @param {!object} surfaces Map of surfaces to flip keyed by surface id.
	 * @return {?Promise=} This can return a promise, which will
	 *     pause the navigation until it is resolved.
	 */
	evaluateScripts(surfaces) {
		Object.keys(surfaces).forEach((sId) => {
			if (surfaces[sId].activeChild) {
				runScriptsInElement(surfaces[sId].activeChild);
			}
		});

		return Promise.resolve();
	}

	/**
	 * Allows a screen to evaluate styles before the element is made visible.
	 * Lifecycle.
	 * @param {!object} surfaces Map of surfaces to flip keyed by surface id.
	 * @return {?Promise=} This can return a promise, which will
	 *     pause the navigation until it is resolved.
	 */
	evaluateStyles() {
		return Promise.resolve();
	}

	/**
	 * Allows a screen to perform any setup immediately before the element is
	 * made visible. Lifecycle.
	 * @param {!object} surfaces Map of surfaces to flip keyed by surface id.
	 * @return {?Promise=} This can return a promise, which will pause the
	 *     navigation until it is resolved.
	 */
	flip(surfaces) {
		log('Screen [' + this + '] flip');

		var transitions = [];

		Object.keys(surfaces).forEach((sId) => {
			var surface = surfaces[sId];
			var deferred = surface.show(this.id);
			transitions.push(deferred);
		});

		return Promise.all(transitions);
	}

	/**
	 * Gets the screen id.
	 * @return {string}
	 */
	getId() {
		return this.id;
	}

	/**
	 * Gets the screen meta tags.
	 * @return {NodeList|Node}
	 */
	getMetas() {
		return this.metas;
	}

	/**
	 * Returns the content for the given surface, or null if the surface isn't
	 * used by this screen. This will be called when a screen is initially
	 * constructed or, if a screen is non-cacheable, when navigated.
	 * @param {!string} surfaceId The id of the surface DOM element.
	 * @param {!Object} params Params extracted from the current path.
	 * @return {?string|Element=} This can return a string or node representing
	 *     the content of the surface. If returns falsy values surface default
	 *     content is restored.
	 */
	getSurfaceContent() {
		log('Screen [' + this + '] getSurfaceContent');
	}

	/**
	 * Gets the screen title.
	 * @return {?string=}
	 */
	getTitle() {
		return this.title;
	}

	/**
	 * Returns all contents for the surfaces. This will pass the loaded content
	 * to <code>Screen.load</code> with all information you
	 * need to fulfill the surfaces. Lifecycle.
	 * @param {!string=} path The requested path.
	 * @return {!Promise} This can return a string representing the
	 *     contents of the surfaces or a promise, which will pause the navigation
	 *     until it is resolved. This is useful for loading async content.
	 */
	load() {
		log('Screen [' + this + '] load');

		return Promise.resolve();
	}

	/**
	 * Makes the id for the screen.
	 * @param {!string} id The screen id the content belongs too.
	 * @return {string}
	 * @private
	 */
	makeId_(id) {
		return 'screen_' + id;
	}

	/**
	 * Sets the screen id.
	 * @param {!string} id
	 */
	setId(id) {
		this.id = id;
	}

	/**
	 * Sets the screen meta tags.
	 * @param {NodeList|Node} metas
	 */
	setMetas(metas) {
		this.metas = metas;
	}

	/**
	 * Sets the screen title.
	 * @param {?string=} title
	 */
	setTitle(title) {
		this.title = title;
	}

	/**
	 * @return {string}
	 */
	toString() {
		return this.id;
	}
}

/**
 * @param {*} object
 * @return {boolean} Whether a given instance implements
 * <code>Screen</code>.
 */
Screen.isImplementedBy = function (object) {
	return object instanceof Screen;
};

export default Screen;
