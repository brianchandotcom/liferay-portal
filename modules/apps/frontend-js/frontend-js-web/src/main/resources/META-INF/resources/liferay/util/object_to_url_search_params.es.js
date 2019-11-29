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

import {isObject} from 'metal';

/**
 * Returns a FormData containing serialized object.
 * @param {!Object} obj Object to convert to a URLSearchParams
 * @return {URLSearchParams} URLSearchParams object with the set parameters
 * @review
 */

export default function objectToURLSearchParams(obj) {
	if (!isObject(obj)) {
		throw new TypeError('Parameter obj must be an object');
	}

	const urlSearchParams = new URLSearchParams();

	Object.entries(obj).forEach(([key, value]) => {
		if (Array.isArray(value)) {
			for (let i = 0; i < value.length; i++) {
				urlSearchParams.append(key, value[i]);
			}
		}
		else {
			urlSearchParams.append(key, value);
		}
	});

	return urlSearchParams;
}
