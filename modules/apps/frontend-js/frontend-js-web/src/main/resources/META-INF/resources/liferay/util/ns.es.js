/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import memoize from './memoize';

const nsCached = memoize((namespace, str) => {
	if (typeof str !== 'undefined' && !(str.lastIndexOf(namespace, 0) === 0)) {
		str = `${namespace}${str}`;
	}

	return str;
});

export default function ns(namespace, object) {
	let value;

	if (typeof object !== 'object') {
		value = nsCached(namespace, object);
	}
	else {
		value = {};

		const keys = Object.keys(object);

		keys.forEach((item) => {
			const originalItem = item;

			item = nsCached(namespace, item);

			value[item] = object[originalItem];
		});
	}

	return value;
}
