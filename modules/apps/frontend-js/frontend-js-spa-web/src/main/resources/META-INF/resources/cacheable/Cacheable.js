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

import Disposable from '../util/Disposable';

class Cacheable extends Disposable {

	/**
	 * Abstract class for defining cacheable behavior.
	 */
	constructor() {
		super();

		/**
		 * Holds the cached data.
		 * @type {!Object}
		 * @default null
		 * @protected
		 */
		this.cache = null;

		/**
		 * Holds whether class is cacheable.
		 * @type {boolean}
		 * @default false
		 * @protected
		 */
		this.cacheable = false;
	}

	/**
	 * Adds content to the cache.
	 * @param {string} content Content to be cached.
	 * @chainable
	 */
	addCache(content) {
		if (this.cacheable) {
			this.cache = content;
		}

		return this;
	}

	/**
	 * Clears the cache.
	 * @chainable
	 */
	clearCache() {
		this.cache = null;

		return this;
	}

	/**
	 * Disposes of this instance's object references.
	 * @override
	 */
	disposeInternal() {
		this.clearCache();
	}

	/**
	 * Gets the cached content.
	 * @return {Object} Cached content.
	 * @protected
	 */
	getCache() {
		return this.cache;
	}

	/**
	 * Whether the class is cacheable.
	 * @return {boolean} Returns true when class is cacheable, false otherwise.
	 */
	isCacheable() {
		return this.cacheable;
	}

	/**
	 * Sets whether the class is cacheable.
	 * @param {boolean} cacheable
	 */
	setCacheable(cacheable) {
		if (!cacheable) {
			this.clearCache();
		}
		this.cacheable = cacheable;
	}
}

export default Cacheable;
