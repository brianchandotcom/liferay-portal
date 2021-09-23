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

import sha256 from 'hash.js/lib/hash/sha/256';

function sort(object) {
	if (typeof object !== 'object' || !object) {
		return object;
	}
	else if (Array.isArray(object)) {
		return object.sort().map(sort);
	}

	return Object.keys(object)
		.sort()
		.reduce((acc, cur) => {
			acc[cur] = sort(object[cur]);

			return acc;
		}, {});
}

function hash(value) {
	let toHash = value;

	if (typeof value === 'object') {
		toHash = JSON.stringify(sort(value));
	}

	return sha256().update(toHash).digest('hex');
}

export {hash};
export default hash;
