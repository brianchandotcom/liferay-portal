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
 * Recursively inserts a value inside an object creating
 * a copy of the original target. It the object (or any in the path),
 * it's an Array, it will generate new Arrays, preserving the same structure.
 *
 * @param {!Array|!Object} Original object that will be copied
 * @param {!string[]} Array of strings used for reaching the deep property
 * @param {*} value Value to be inserted
 * @return {!Array|!Object} Copy of the original object with the new value
 * @review
 */

function setIn(object, keyPath, value) {
	const nextKey = keyPath[0];
	const target = object instanceof Array ? [...object] : {...object};

	let nextValue = value;

	if (keyPath.length > 1) {
		nextValue = setIn(object[nextKey] || {}, keyPath.slice(1), value);
	}

	target[nextKey] = nextValue;

	return target;
}

export {setIn};
