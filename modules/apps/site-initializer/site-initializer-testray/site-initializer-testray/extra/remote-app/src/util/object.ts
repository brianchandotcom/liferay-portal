/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const isObject = (object: Object) => {
	return object !== null && typeof object === 'object';
};

const isDeepEqual = (object1: Object, object2: Object) => {
	const keysObject1 = Object.keys(object1);
	const keysObject2 = Object.keys(object2);

	if (keysObject1.length !== keysObject2.length) {
		return false;
	}

	for (const key of keysObject1) {
		const value1 = (object1 as Record<string, any>)[key];
		const value2 = (object2 as Record<string, any>)[key];

		const isObjects = isObject(value1) && isObject(value2);

		if (
			(isObjects && !isDeepEqual(value1, value2)) ||
			(!isObjects && value1 !== value2)
		) {
			return false;
		}
	}

	return true;
};

export default isDeepEqual;
