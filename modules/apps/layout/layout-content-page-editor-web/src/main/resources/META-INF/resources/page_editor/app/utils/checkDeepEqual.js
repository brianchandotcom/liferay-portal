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
 * Returns true if a and b are deeply equal
 * @param a
 * @param b
 * @returns {boolean}
 */
export function deepEqual(a, b) {
	if (a === b) {
		return true;
	}

	if (Array.isArray(a) && Array.isArray(b)) {
		if (a.length !== b.length) {
			return false;
		}

		return a.every((value, index) => {
			return deepEqual(value, b[index]);
		});
	}

	if (a && b && typeof a === 'object' && typeof b === 'object') {
		const keys = Object.keys(a);

		if (keys.length !== Object.keys(b).length) {
			return false;
		}

		return keys.every((key) => {
			return deepEqual(a[key], b[key]);
		});
	}

	return false;
}
