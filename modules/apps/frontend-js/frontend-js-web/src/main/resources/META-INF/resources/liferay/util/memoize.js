/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
