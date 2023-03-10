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
