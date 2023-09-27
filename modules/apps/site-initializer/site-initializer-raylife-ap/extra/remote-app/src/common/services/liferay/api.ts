/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import _axios from 'axios';

import {Liferay} from './liferay';

const {REACT_APP_LIFERAY_HOST = window.location.origin} = process.env;

const baseFetch = async (url: string, options = {}) => {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	return fetch(REACT_APP_LIFERAY_HOST + '/' + url, {
		headers: {
			'Content-Type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
		...options,
	});
};

const axios = _axios.create({
	baseURL: REACT_APP_LIFERAY_HOST,
	headers: {
		'x-csrf-token': Liferay.authToken,
	},
});

export {axios};

export default baseFetch;
