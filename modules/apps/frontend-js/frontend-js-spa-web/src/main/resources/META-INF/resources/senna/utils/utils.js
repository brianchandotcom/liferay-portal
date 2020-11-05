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

import {exitDocument} from 'metal-dom';
import Uri from 'metal-uri';

import globals from '../globals/globals';

/**
 * Counter for unique id.
 * @type {Number}
 * @private
 */
let uniqueIdCounter_ = 1;

/**
 * A collection of static utility functions.
 * @const
 */
class utils {

	/**
	 * Copies attributes form source node to target node.
	 * @return {void}
	 * @static
	 */
	static copyNodeAttributes(source, target) {
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
	static getCurrentBrowserPath() {
		return (
			this.getCurrentBrowserPathWithoutHash() +
			globals.window.location.hash
		);
	}

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
	 * Gets the given node offset coordinates.
	 * @return {!object}
	 * @static
	 */
	static getNodeOffset(node) {
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

	static getUid() {
		return uniqueIdCounter_++;
	}

	/**
	 * Extracts the path part of an url.
	 * @return {!string}
	 * @static
	 */
	static getUrlPath(url) {
		var uri = new Uri(url);

		return uri.getPathname() + uri.getSearch() + uri.getHash();
	}

	/**
	 * Extracts the path part of an url without hashbang.
	 * @return {!string}
	 * @static
	 */
	static getUrlPathWithoutHash(url) {
		var uri = new Uri(url);

		return uri.getPathname() + uri.getSearch();
	}

	/**
	 * Extracts the path part of an url without hashbang and query search.
	 * @return {!string}
	 * @static
	 */
	static getUrlPathWithoutHashAndSearch(url) {
		var uri = new Uri(url);

		return uri.getPathname();
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

			// the getUrlPath will create a Uri and will normalize the path and
			// remove the trailling '/' for properly comparing paths.

			return (
				utils.getUrlPathWithoutHash(url) ===
				this.getUrlPath(currentBrowserPath)
			);
		}

		return false;
	}

	/**
	 * Returns true if HTML5 History api is supported.
	 * @return {boolean}
	 * @static
	 */
	static isHtml5HistorySupported() {
		return !!(globals.window.history && globals.window.history.pushState);
	}

	/**
	 * Checks if a given url is a valid http(s) uri and returns the formed Uri
	 * or false if the parsing failed
	 * @return {Uri|boolean}
	 * @static
	 */
	static isWebUri(url) {
		try {
			return new Uri(url);
		}
		catch (err) {
			console.error(`${err.message} ${url}`);

			return false;
		}
	}

	/**
	 * Logs the provided message in DEV environments
	 * @param {String} message
	 */
	static log(message) {
		if (process.env.NODE_ENV === 'development') {
			// eslint-disable-next-line
			console.log(message);
		}
	}

	/**
	 * Removes all attributes form node.
	 * @return {void}
	 * @static
	 */
	static clearNodeAttributes(node) {
		Array.prototype.slice
			.call(node.attributes)
			.forEach((attribute) => node.removeAttribute(attribute.name));
	}

	/**
	 * Remove elements from the document.
	 * @param {!Array<Element>} elements
	 */
	static removeElementsFromDocument(elements) {
		elements.forEach((element) => exitDocument(element));
	}

	/**
	 * Removes trailing slash in path.
	 * @param {!string}
	 * @return {string}
	 */
	static removePathTrailingSlash(path) {
		var length = path ? path.length : 0;
		if (length > 1 && path[length - 1] === '/') {
			path = path.substr(0, length - 1);
		}

		return path;
	}

	/**
	 * Adds a random suffix to the href attribute of the element.
	 * @param {!element} element
	 * @return {element}
	 */
	static setElementWithRandomHref(element) {
		element.href = element.href + '?q=' + Math.random();

		return element;
	}

	/**
	 * Overrides document referrer
	 * @param {string} referrer
	 * @static
	 */
	static setReferrer(referrer) {
		Object.defineProperty(globals.document, 'referrer', {
			configurable: true,
			get() {
				return referrer;
			},
		});
	}

	/**
	 * Slices the given array, just like Array.prototype.slice, but this
	 * is faster and working on all array-like objects (like arguments).
	 * @param {!Object} arr Array-like object to slice.
	 * @param {number} start The index that should start the slice.
	 * @param {number=} end The index where the slice should end, not
	 *   included in the final array. If not given, all elements after the
	 *   start index will be included.
	 * @return {!Array}
	 */
	static slice(arr, start, end = arr.length) {
		const sliced = [];
		for (let i = start; i < end; i++) {
			sliced.push(arr[i]);
		}

		return sliced;
	}
}

export default utils;
