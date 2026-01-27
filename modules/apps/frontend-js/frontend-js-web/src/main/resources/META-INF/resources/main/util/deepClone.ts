/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Deep clones an object, including its function members (by reference).
 * Handles Primitives, Dates, and RegExps.
 * @param object The object to clone.
 * @returns A deep clone of the object.
 */
export default function deepClone(object: any) {
	if (Array.isArray(object)) {
		return object.map((item): any => deepClone(item));
	}

	if (object instanceof Date) {
		return new Date(object.getTime());
	}

	if (object instanceof RegExp) {
		return new RegExp(object.source, object.flags);
	}

	if (object && typeof object === 'object') {
		const newObject: Record<string, any> = {};

		Object.keys(object).forEach((key) => {
			const value = deepClone(object[key]);

			newObject[key] = value;
		});

		return newObject;
	}

	return object;
}
