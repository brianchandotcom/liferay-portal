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

import Uri from 'metal-uri';

import globals from '../globals/globals';

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
		return (
			globals.window.location.pathname + globals.window.location.search
		);
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

			//const currentBrowserPath = globals.window.location.pathname + globals.window.location.search;

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
 * Helper for converting a HTML string into a document fragment.
 * @param {string} htmlString The HTML string to convert.
 * @return {!Element} The resulting document fragment.
 */
export function buildFragment(htmlString) {
	const tempDiv = document.createElement('div');
	tempDiv.innerHTML = `<br>${htmlString}`;
	tempDiv.removeChild(tempDiv.firstChild);

	const fragment = document.createDocumentFragment();
	while (tempDiv.firstChild) {
		fragment.appendChild(tempDiv.firstChild);
	}

	return fragment;
}

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
	return `${globals.window.location.pathname}${globals.window.location.search}${globals.window.location.hash}`;
}

/**
 * Gets the current browser path excluding hashbang.
 * @return {!string}
 * @static
 */
export function getCurrentBrowserPathWithoutHash() {
	return `${globals.window.location.pathname}${globals.window.location.search}`;
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

export function getUid() {
	return uniqueIdCounter++;
}

/**
 * Extracts the path part of an url.
 * @return {!string}
 * @static
 */
export function getUrlPath(url) {
	var uri = new Uri(url);

	return uri.getPathname() + uri.getSearch() + uri.getHash();
}

/**
 * Extracts the path part of an url without hashbang.
 * @return {!string}
 * @static
 */
export function getUrlPathWithoutHash(url) {
	var uri = new Uri(url);

	return uri.getPathname() + uri.getSearch();
}

/**
 * Extracts the path part of an url without hashbang and query search.
 * @return {!string}
 * @static
 */
export function getUrlPathWithoutHashAndSearch(url) {
	var uri = new Uri(url);

	return uri.getPathname();
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
 * Overrides document referrer
 * @param {string} referrer
 * @static
 */
export function setReferrer(referrer) {
	Object.defineProperty(globals.document, 'referrer', {
		configurable: true,
		get() {
			return referrer;
		},
	});
}
