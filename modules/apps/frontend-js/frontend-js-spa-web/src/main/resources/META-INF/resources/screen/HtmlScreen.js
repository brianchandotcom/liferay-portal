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

import {buildFragment, runScriptsInElement} from 'frontend-js-web';

import globals from '../globals/globals';
import Surface from '../surface/Surface';
import {
	clearNodeAttributes,
	copyNodeAttributes,
	getUid,
	runStylesInElement,
} from '../util/utils';
import RequestScreen from './RequestScreen';

class HtmlScreen extends RequestScreen {

	/**
	 * Screen class that perform a request and extracts surface contents from
	 * the response content.
	 */
	constructor() {
		super();

		/**
		 * Holds the meta selector. Relevant to extract <code>meta</code> tags
		 * elements from request fragments to use as the screen.
		 * @type {!string}
		 * @default meta
		 * @protected
		 */
		this.metaTagsSelector = 'meta';

		/**
		 * Holds the title selector. Relevant to extract the <code><title></code>
		 * element from request fragments to use as the screen title.
		 * @type {!string}
		 * @default title
		 * @protected
		 */
		this.titleSelector = 'title';
	}

	/**
	 * @inheritDoc
	 */
	activate() {
		super.activate();
		this.releaseVirtualDocument();
		this.pendingStyles = null;
	}

	/**
	 * Allocates virtual document for content. After allocated virtual document
	 * can be accessed by <code>this.virtualDocument</code>.
	 * @param {!string} htmlString
	 */
	allocateVirtualDocumentForContent(htmlString) {
		if (!this.virtualDocument) {
			this.virtualDocument = globals.document.createElement('html');
		}

		this.copyNodeAttributesFromContent_(htmlString, this.virtualDocument);

		this.virtualDocument.innerHTML = htmlString;
	}

	/**
	 * Customizes logic to append styles into document. Relevant to when
	 * tracking a style by id make sure to re-positions the new style in the
	 * same dom order.
	 * @param {Element} newStyle
	 */
	appendStyleIntoDocument_(newStyle) {
		var isTemporaryStyle = newStyle.matches(
			HtmlScreen.selectors.stylesTemporary
		);
		if (isTemporaryStyle) {
			this.pendingStyles.push(newStyle);
		}
		if (newStyle.id) {
			var styleInDoc = globals.document.getElementById(newStyle.id);
			if (styleInDoc) {
				styleInDoc.parentNode.insertBefore(
					newStyle,
					styleInDoc.nextSibling
				);

				return;
			}
		}
		globals.document.head.appendChild(newStyle);
	}

	/**
	 * If body is used as surface forces the requested documents to have same id
	 * of the initial page.
	 */
	assertSameBodyIdInVirtualDocument() {
		var bodySurface = this.virtualDocument.querySelector('body');
		if (!globals.document.body.id) {
			globals.document.body.id = 'senna_surface_' + getUid();
		}
		if (bodySurface) {
			bodySurface.id = globals.document.body.id;
		}
	}

	/**
	 * Copies attributes from the <html> tag of content to the given node.
	 */
	copyNodeAttributesFromContent_(content, node) {
		content = content.replace(/[<]\s*html/gi, '<senna');
		content = content.replace(/\/html\s*>/gi, '/senna>');

		node.innerHTML = content;

		const placeholder = node.querySelector('senna');

		if (placeholder) {
			clearNodeAttributes(node);
			copyNodeAttributes(placeholder, node);
		}
	}

	/**
	 * @Override
	 */
	disposeInternal() {
		this.disposePendingStyles();
		super.disposeInternal();
	}

	/**
	 * Disposes pending styles if screen get disposed prior to its loading.
	 */
	disposePendingStyles() {
		if (this.pendingStyles) {
			this.pendingStyles.forEach((element) => element.remove());
		}
	}

	/**
	 * @Override
	 */
	evaluateScripts(surfaces) {
		var evaluateTrackedScripts = this.evaluateTrackedResources_(
			runScriptsInElement,
			HtmlScreen.selectors.scripts,
			HtmlScreen.selectors.scriptsTemporary,
			HtmlScreen.selectors.scriptsPermanent
		);

		return evaluateTrackedScripts.then(() =>
			super.evaluateScripts(surfaces)
		);
	}

	/**
	 * @Override
	 */
	evaluateStyles(surfaces) {
		this.pendingStyles = [];
		var evaluateTrackedStyles = this.evaluateTrackedResources_(
			runStylesInElement,
			HtmlScreen.selectors.styles,
			HtmlScreen.selectors.stylesTemporary,
			HtmlScreen.selectors.stylesPermanent,
			this.appendStyleIntoDocument_.bind(this)
		);

		return evaluateTrackedStyles.then(() => super.evaluateStyles(surfaces));
	}

	/**
	 * Allows a screen to evaluate the favicon style before the screen becomes visible.
	 * @return {Promise}
	 */
	evaluateFavicon_() {
		const resourcesInVirtual = this.virtualQuerySelectorAll_(
			HtmlScreen.selectors.favicon
		);
		const resourcesInDocument = this.querySelectorAll_(
			HtmlScreen.selectors.favicon
		);

		return new Promise((resolve) => {
			resourcesInDocument.forEach((element) => element.remove());
			this.runFaviconInElement_(resourcesInVirtual).then(() => resolve());
		});
	}

	/**
	 * Evaluates tracked resources inside incoming fragment and remove existing
	 * temporary resources.
	 * @param {?function()} appendFn Function to append the node into document.
	 * @param {!string} selector Selector used to find resources to track.
	 * @param {!string} selectorTemporary Selector used to find temporary
	 *     resources to track.
	 * @param {!string} selectorPermanent Selector used to find permanent
	 *     resources to track.
	 * @param {!function} opt_appendResourceFn Optional function used to
	 *     evaluate fragment containing resources.
	 * @return {Promise} Deferred that waits resources evaluation to
	 *     complete.
	 * @private
	 */
	evaluateTrackedResources_(
		evaluatorFn,
		selector,
		selectorTemporary,
		selectorPermanent,
		opt_appendResourceFn
	) {
		var tracked = this.virtualQuerySelectorAll_(selector);
		var temporariesInDoc = this.querySelectorAll_(selectorTemporary);
		var permanentsInDoc = this.querySelectorAll_(selectorPermanent);

		// Adds permanent resources in document to cache.

		permanentsInDoc.forEach((resource) => {
			var resourceKey = this.getResourceKey_(resource);
			if (resourceKey) {
				HtmlScreen.permanentResourcesInDoc[resourceKey] = true;
			}
		});

		var frag = buildFragment();
		tracked.forEach((resource) => {
			var resourceKey = this.getResourceKey_(resource);

			// Do not load permanent resources if already in document.

			if (!HtmlScreen.permanentResourcesInDoc[resourceKey]) {
				frag.appendChild(resource);
			}

			// If resource has key and is permanent add to cache.

			if (resourceKey && resource.matches(selectorPermanent)) {
				HtmlScreen.permanentResourcesInDoc[resourceKey] = true;
			}
		});

		return new Promise((resolve) => {
			evaluatorFn(
				frag,
				() => {
					temporariesInDoc.forEach((element) => element.remove());
					resolve();
				},
				opt_appendResourceFn
			);
		});
	}

	/**
	 * @Override
	 */
	flip(surfaces) {
		return super.flip(surfaces).then(() => {
			clearNodeAttributes(globals.document.documentElement);
			copyNodeAttributes(
				this.virtualDocument,
				globals.document.documentElement
			);
			this.evaluateFavicon_();
			this.updateMetaTags_();
		});
	}

	updateMetaTags_() {
		const currentMetaNodes = this.querySelectorAll_('meta');
		const metasFromVirtualDocument = this.metas;
		if (currentMetaNodes) {
			currentMetaNodes.forEach((element) => element.remove());
			if (metasFromVirtualDocument) {
				metasFromVirtualDocument.forEach((meta) =>
					globals.document.head.appendChild(meta)
				);
			}
		}
	}

	/**
	 * Extracts a key to identify the resource based on its attributes.
	 * @param {Element} resource
	 * @return {string} Extracted key based on resource attributes in order of
	 *     preference: id, href, src.
	 */
	getResourceKey_(resource) {
		return resource.id || resource.href || resource.src || '';
	}

	/**
	 * @inheritDoc
	 */
	getSurfaceContent(surfaceId) {
		var surface = this.virtualDocument.querySelector('#' + surfaceId);
		if (surface) {
			var defaultChild = surface.querySelector(
				'#' + surfaceId + '-' + Surface.DEFAULT
			);
			if (defaultChild) {
				return defaultChild.innerHTML;
			}

			return surface.innerHTML; // If default content not found, use surface content
		}
	}

	/**
	 * Gets the title selector.
	 * @return {!string}
	 */
	getTitleSelector() {
		return this.titleSelector;
	}

	/**
	 * @inheritDoc
	 */
	load(path) {
		return super.load(path).then((content) => {
			this.allocateVirtualDocumentForContent(content);
			this.resolveTitleFromVirtualDocument();
			this.resolveMetaTagsFromVirtualDocument();
			this.assertSameBodyIdInVirtualDocument();

			return content;
		});
	}

	/**
	 * Adds the favicon elements to the document.
	 * @param {!Array<Element>} elements
	 * @private
	 * @return {Promise}
	 */
	runFaviconInElement_(elements) {
		return new Promise((resolve) => {
			elements.forEach((element) => {
				element.href = element.href + '?q=' + Math.random();

				document.head.appendChild(element);
			});
			resolve();
		});
	}

	/**
	 * Queries elements from virtual document and returns an array of elements.
	 * @param {!string} selector
	 * @return {array.<Element>}
	 */
	virtualQuerySelectorAll_(selector) {
		return Array.prototype.slice.call(
			this.virtualDocument.querySelectorAll(selector)
		);
	}

	/**
	 * Queries elements from document and returns an array of elements.
	 * @param {!string} selector
	 * @return {array.<Element>}
	 */
	querySelectorAll_(selector) {
		return Array.prototype.slice.call(
			globals.document.querySelectorAll(selector)
		);
	}

	/**
	 * Releases virtual document allocated for content.
	 */
	releaseVirtualDocument() {
		this.virtualDocument = null;
	}

	/**
	 * Resolves title from allocated virtual document.
	 */
	resolveTitleFromVirtualDocument() {
		const title = this.virtualDocument.querySelector(this.titleSelector);
		if (title) {
			this.setTitle(title.textContent.trim());
		}
	}

	resolveMetaTagsFromVirtualDocument() {
		const metas = this.virtualQuerySelectorAll_(this.metaTagsSelector);
		if (metas) {
			this.setMetas(metas);
		}
	}

	/**
	 * Sets the title selector.
	 * @param {!string} titleSelector
	 */
	setTitleSelector(titleSelector) {
		this.titleSelector = titleSelector;
	}
}

/**
 * Helper selector for ignore favicon when exist data-senna-track.
 */
const ignoreFavicon =
	':not([rel="Shortcut Icon"]):not([rel="shortcut icon"]):not([rel="icon"]):not([href$="favicon.icon"])';

/**
 * Helper selectors for tracking resources.
 * @type {object}
 * @protected
 * @static
 */
HtmlScreen.selectors = {
	favicon:
		'link[rel="Shortcut Icon"],link[rel="shortcut icon"],link[rel="icon"],link[href$="favicon.icon"]',
	scripts: 'script[data-senna-track]',
	scriptsPermanent: 'script[data-senna-track="permanent"]',
	scriptsTemporary: 'script[data-senna-track="temporary"]',
	styles: `style[data-senna-track],link[data-senna-track]${ignoreFavicon}`,
	stylesPermanent: `style[data-senna-track="permanent"],link[data-senna-track="permanent"]${ignoreFavicon}`,
	stylesTemporary: `style[data-senna-track="temporary"],link[data-senna-track="temporary"]${ignoreFavicon}`,
};

/**
 * Caches permanent resource keys.
 * @type {object}
 * @protected
 * @static
 */
HtmlScreen.permanentResourcesInDoc = {};

export default HtmlScreen;
