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

import addParams from './../../util/add_params.es';
import fetch from './../../util/fetch.es';
import objectToFormData from '../form/object_to_form_data.es';

/**
 * Gets the Store utility fetch values for given keys
 * @param {Array} keys object for fetch request
 * @param callback
 * @review
 */

export default function getStoreValues(keys, callback) {
	if (!Array.isArray(keys)) {
		throw new TypeError('Parameter keys must be an array');
	}

	if (callback && typeof callback !== 'function') {
		throw new TypeError('Parameter callback must be a function');
	}

	const doAsUserIdEncoded = Liferay.ThemeDisplay.getDoAsUserIdEncoded();

	const formData = objectToFormData({
		key: JSON.stringify(keys)
	});

	const parameters = {
		cmd: 'getAll',
		p_auth: Liferay.authToken
	};

	if (doAsUserIdEncoded) {
		parameters.doAsUserId = doAsUserIdEncoded;
	}

	const url = addParams(
		parameters,
		`${Liferay.ThemeDisplay.getPathMain()}/portal/session_click`
	);

	fetch(url, {
		method: 'POST',
		body: formData
	})
		.then(response => {
			return response.json();
		})
		.then(response => {
			if (callback) {
				callback(response);
			}
		});
}
