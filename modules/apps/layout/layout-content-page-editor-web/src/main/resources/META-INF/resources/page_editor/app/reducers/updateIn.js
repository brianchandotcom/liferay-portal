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

export default function updateIn(object, keyPath, updater, defaultValue) {
	const [nextKey] = keyPath;
	let target = object;

	if (keyPath.length > 1) {
		target = target instanceof Array ? [...target] : {...target};

		target[nextKey] = updateIn(
			target[nextKey] || {},
			keyPath.slice(1),
			updater,
			defaultValue
		);
	} else {
		const nextValue =
			typeof target[nextKey] === 'undefined'
				? defaultValue
				: target[nextKey];

		const updatedNextValue = updater(nextValue);

		if (updatedNextValue !== target[nextKey]) {
			target = target instanceof Array ? [...target] : {...target};

			target[nextKey] = updatedNextValue;
		}
	}

	return target;
}
