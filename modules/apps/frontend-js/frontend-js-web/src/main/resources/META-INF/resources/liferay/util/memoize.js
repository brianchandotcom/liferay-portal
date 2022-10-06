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

export default function memoize(fn) {
	if (typeof fn !== 'function') {
		throw new TypeError(`Parameter fn must be a function`);
	}

	const cache = new Map();

	const memoized = (...args) => {
		let key;

		if (args.find((arg) => typeof arg === 'object')) {
			const objectArguments = args.filter(
				(arg) => typeof arg === 'object'
			);

			key = objectArguments.map((objArg) => JSON.stringify(objArg));

			if (args.length > 1 && objectArguments.length < args.length) {
				args.forEach((arg) => typeof arg !== 'object' && key.push(arg));
			}

			key = key.join(',');
		}
		else {
			key = args.length > 1 ? args.join(',') : args[0];
		}

		if (cache.has(key)) {
			return cache.get(key);
		}
		else {
			const result = fn.apply(null, args);

			cache.set(key, result);

			return result;
		}
	};

	memoized.getCache = () => cache;

	return memoized;
}
