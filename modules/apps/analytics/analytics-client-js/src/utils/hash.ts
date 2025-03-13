/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore - Check possibility to install package in ts format

import sha256 from 'hash.js/lib/hash/sha/256';

function sort(object: {[key: string]: any}): any {
	if (typeof object !== 'object' || object === null) {
		return object;
	}
	else if (Array.isArray(object)) {
		return object.map(sort).sort();
	}

	return Object.keys(object)
		.sort()
		.reduce(
			(acc, cur) => {
				acc[cur] = sort(object[cur]);

				return acc;
			},
			{} as {[key: string]: any}
		);
}

function hash(value: {[key: string]: any} | string) {
	if (typeof value === 'object') {
		const toHash = JSON.stringify(sort(value));

		return sha256().update(toHash).digest('hex');
	}

	return sha256().update(value).digest('hex');
}

export {hash};
export default hash;
