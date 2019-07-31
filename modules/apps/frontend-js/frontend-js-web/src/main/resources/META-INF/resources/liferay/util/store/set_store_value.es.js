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

const TOKEN_SERIALIZE = 'serialize://';

/**
 * Sets the Store utility fetch value
 * @param {String} key of the formData
 * @param {Object|String} value of the key for the formData
 * @review
 */

export default function setStoreValue(key, value) {
	if (typeof key !== 'string') {
		throw new TypeError('Parameter key must be a string');
	}

	if (
		typeof value !== 'boolean' &&
		typeof value !== 'object' &&
		typeof value !== 'string'
	) {
		throw new TypeError(
			'Parameter value must be boolean or an object or string'
		);
	}

	if (typeof value === 'object') {
		value = TOKEN_SERIALIZE + JSON.stringify(value);
	}

	const doAsUserIdEncoded = Liferay.ThemeDisplay.getDoAsUserIdEncoded();

	const formData = new FormData();

	formData.append(key, value);

	const parameters = {
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
		body: formData,
		method: 'POST'
	});
}
