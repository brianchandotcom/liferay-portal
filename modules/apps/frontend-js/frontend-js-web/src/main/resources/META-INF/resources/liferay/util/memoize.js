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

export default function memoize(fn, resolver) {

	/**
	 * Using the Map to enable storing objects as keys
	 */
	const cache = new Map();

	/**
	 * Returns a memoized function.
	 * Takes the passed function and adds cache handling to it,
	 * applying the passed arguments to maintain expected functionality
	 */
	return (...args) => {

		/**
		 * Use the resolver function if it is provided, otherwise use the first argument
		 */
		const key = resolver ? resolver.apply(null, args) : args[0];

		/**
		 * Check if the first argument is cached, if yes, return it, otherwise store it
		 */
		if (cache.has(key)) {
			return cache.get(key);
		}
		else {
			const result = fn.apply(null, args);

			cache.set(key, result);

			return result;
		}
	};
}
