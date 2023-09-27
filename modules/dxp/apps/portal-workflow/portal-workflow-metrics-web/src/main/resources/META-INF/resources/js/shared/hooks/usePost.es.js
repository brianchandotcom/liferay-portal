/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';
import {useCallback, useState} from 'react';

import {adminBaseURL, headers, metricsBaseURL} from '../rest/fetch.es';

const usePost = ({
	admin = false,
	body = {},
	callback = (data) => data,
	params = {},
	url,
}) => {
	const [data, setData] = useState({});

	let fetchURL = admin ? `${adminBaseURL}${url}` : `${metricsBaseURL}${url}`;

	fetchURL = new URL(fetchURL, Liferay.ThemeDisplay.getPortalURL());

	Object.entries(params).map(([key, value]) => {
		if (value) {
			fetchURL.searchParams.append(key, value);
		}
	});

	const postData = useCallback(async () => {
		const response = await fetch(fetchURL, {
			body: JSON.stringify(body),
			headers: {...headers, 'Content-Type': 'application/json'},
			method: 'POST',
			params,
		});

		const data = await response.json();

		if (response.ok) {
			setData(data);

			return callback(data);
		}

		throw data;
	}, [body, callback, fetchURL, params]);

	return {
		data,
		postData,
	};
};

export {usePost};
