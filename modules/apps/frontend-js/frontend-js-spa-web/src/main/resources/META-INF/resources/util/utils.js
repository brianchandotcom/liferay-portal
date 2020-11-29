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

/**
 * Counter for unique id.
 * @type {Number}
 * @private
 */
let uniqueIdCounter = 1;

/**
 * A collection of static utility functions.
 * @const
 */
class utils {

	/**
	 * Gets the current browser path excluding hashbang.
	 * @return {!string}
	 * @static
	 */
	static getCurrentBrowserPathWithoutHash() {
		return window.location.pathname + window.location.search;
	}

	/**
	 * Checks if url is in the same browser current url excluding the hashbang.
	 * @param  {!string} url
	 * @return {boolean}
	 * @static
	 */
	static isCurrentBrowserPath(url) {
		if (url) {
			const currentBrowserPath = this.getCurrentBrowserPathWithoutHash();

			//const currentBrowserPath = window.location.pathname + window.location.search;

			// the getUrlPath will create a Uri and will normalize the path and
			// remove the trailling '/' for properly comparing paths.

			return (
				getUrlPathWithoutHash(url) === getUrlPath(currentBrowserPath)
			);
		}

		return false;
	}
}

export default utils;

/**
 * Removes all attributes form node.
 * @return {void}
 * @static
 */
export function clearNodeAttributes(node) {
	Array.prototype.slice
		.call(node.attributes)
		.forEach((attribute) => node.removeAttribute(attribute.name));
}

/**
 * Copies attributes form source node to target node.
 * @return {void}
 * @static
 */
export function copyNodeAttributes(source, target) {
	Array.prototype.slice
		.call(source.attributes)
		.forEach((attribute) =>
			target.setAttribute(attribute.name, attribute.value)
		);
}

/**
 * Gets the current browser path including hashbang.
 * @return {!string}
 * @static
 */
export function getCurrentBrowserPath() {
	return `${window.location.pathname}${window.location.search}${window.location.hash}`;
}

/**
 * Gets the current browser path excluding hashbang.
 * @return {!string}
 * @static
 */
export function getCurrentBrowserPathWithoutHash() {
	return `${window.location.pathname}${window.location.search}`;
}

/**
 * Gets the given node offset coordinates.
 * @return {!object}
 * @static
 */
export function getNodeOffset(node) {
	let [offsetLeft, offsetTop] = [0, 0];
	do {
		offsetLeft += node.offsetLeft;
		offsetTop += node.offsetTop;
		node = node.offsetParent;
	} while (node);

	return {
		offsetLeft,
		offsetTop,
	};
}

/**
 * Given a portletId, returns the ID of the portlet's boundary DOM element
 * @param  {!String} portletId The portlet ID
 * @return {!String} The portlet boundary ID
 */
export function getPortletBoundaryId(portletId) {
	return 'p_p_id_' + portletId + '_';
}

/**
 * Given an array of portlet IDs, returns an array of portlet boundary IDs
 * @param  {!Array} The collection of portletIds
 * @return {!Array} The collection of portlet boundary IDs
 */
export function getPortletBoundaryIds(portletIds) {
	return portletIds.map((portletId) => {
		return getPortletBoundaryId(portletId);
	});
}

export function getUid() {
	return uniqueIdCounter++;
}

/**
 * Extracts the path part of an url.
 * @return {!string}
 * @static
 */
export function getUrlPath(url) {
	const uri =
		!url || url.startsWith('/')
			? new URL(url, window.location.origin)
			: new URL(url);

	return uri.pathname + uri.search + uri.hash;
}

/**
 * Extracts the path part of an url without hashbang.
 * @return {!string}
 * @static
 */
export function getUrlPathWithoutHash(url) {
	const uri =
		!url || url.startsWith('/')
			? new URL(url, window.location.origin)
			: new URL(url);

	return uri.pathname + uri.search;
}

/**
 * Extracts the path part of an url without hashbang and query search.
 * @return {!string}
 * @static
 */
export function getUrlPathWithoutHashAndSearch(url) {
	const uri =
		!url || url.startsWith('/')
			? new URL(url, window.location.origin)
			: new URL(url);

	return uri.pathname;
}

/**
 * Checks if url is in the same browser current url excluding the hashbang.
 * @param  {!string} url
 * @return {boolean}
 * @static
 */
export function isCurrentBrowserPath(url) {
	if (url) {
		const currentBrowserPath = getCurrentBrowserPathWithoutHash();

		// the getUrlPath will create a Uri and will normalize the path and
		// remove the trailling '/' for properly comparing paths.

		return getUrlPathWithoutHash(url) === getUrlPath(currentBrowserPath);
	}

	return false;
}

/**
 * Logs the provided message in DEV environments
 * @param {String} message
 */
export function log(message) {
	if (process.env.NODE_ENV === 'development') {
		// eslint-disable-next-line
		console.log(message);
	}
}

/**
 * Removes trailing slash in path.
 * @param {!string}
 * @return {string}
 */
export function removePathTrailingSlash(path) {
	var length = path ? path.length : 0;
	if (length > 1 && path[length - 1] === '/') {
		path = path.substr(0, length - 1);
	}

	return path;
}

/**
 * Destroys all rendered portlets on the page
 */
export function resetAllPortlets() {
	getPortletBoundaryIds(Liferay.Portlet.list).forEach((value) => {
		const portlet = document.querySelector('#' + value);

		if (portlet) {
			Liferay.Portlet.destroy(portlet);

			portlet.portletProcessed = false;
		}
	});

	Liferay.Portlet.readyCounter = 0;

	Liferay.destroyComponents((component, componentConfig) => {
		return componentConfig.destroyOnNavigate;
	});
}

/**
 * Evaluates the code referenced by the given style/link element.
 * @param {!Element} style
 * @param {function()=} defaultFn Optional function to be called
 *   when the script has been run.
 * @param {function()=} appendFn Optional function to append the node
 *   into document.
 *  @return {Element} style
 */
export function runStyle(style, defaultFn, appendFn) {
	const callback = function () {
		if (defaultFn) {
			defaultFn();
		}
	};

	if (
		style.rel &&
		style.rel !== 'stylesheet' &&
		style.rel !== 'canonical' &&
		style.rel !== 'alternate'
	) {
		setTimeout(callback);

		return;
	}

	if (
		style.tagName === 'STYLE' ||
		style.rel === 'canonical' ||
		style.rel === 'alternate'
	) {
		setTimeout(callback);
	}
	else {
		style.addEventListener('load', callback, {once: true});
		style.addEventListener('error', callback, {once: true});
	}

	if (appendFn) {
		appendFn(style);
	}
	else {
		document.head.appendChild(style);
	}

	return style;
}

/**
 * Evaluates any style present in the given element.
 * @param {!Element} element
 * @param {function()=} defaultFn Optional function to be called when the
 *   style has been run.
 * @param {function()=} appendFn Optional function to append the node
 *   into document.
 */
export function runStylesInElement(element, defaultFn, appendFn) {
	const styles = element.querySelectorAll('style,link');
	if (styles.length === 0 && defaultFn) {
		setTimeout(defaultFn);

		return;
	}

	let loadCount = 0;
	const callback = function () {
		if (defaultFn && ++loadCount === styles.length) {
			setTimeout(defaultFn);
		}
	};
	for (let i = 0; i < styles.length; i++) {
		runStyle(styles[i], callback, appendFn);
	}
}

/**
 * Overrides document referrer
 * @param {string} referrer
 * @static
 */
export function setReferrer(referrer) {
	Object.defineProperty(document, 'referrer', {
		configurable: true,
		get() {
			return referrer;
		},
	});
}
