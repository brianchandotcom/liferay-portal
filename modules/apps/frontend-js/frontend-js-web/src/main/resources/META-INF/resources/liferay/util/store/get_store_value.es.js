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

import fetch from './../../util/fetch.es';
import addParams from './../../util/add_params.es';

/**
 * Gets the Store utility fetch value for given key
 * @param {String} key string for fetch request
 * @param callback
 * @review
 */

export default function getStoreValue(key, callback) {
	if (typeof key !== 'string') {
		throw new TypeError('Parameter key must be a string');
	}

	if (callback && typeof callback !== 'function') {
		throw new TypeError('Parameter callback must be a function');
	}

	const doAsUserIdEncoded = Liferay.ThemeDisplay.getDoAsUserIdEncoded();

	const parameters = {
		cmd: 'get',
		key: key,
		p_auth: Liferay.authToken
	};

	if (doAsUserIdEncoded) {
		parameters.doAsUserId = doAsUserIdEncoded;
	}

	const url = addParams(
		parameters,
		`${Liferay.ThemeDisplay.getPathMain()}/portal/session_click`
	);

	fetch(url)
		.then(response => {
			return response.text();
		})
		.then(response => {
			if (callback) {
				callback(response);
			}
		});
}
