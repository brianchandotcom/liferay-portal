/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';
import {useCallback, useState} from 'react';

import {adminBaseURL, headers, metricsBaseURL} from '../rest/fetch.es';

const useFetch = ({
	admin = false,
	callback = (data) => data,
	params = {},
	plainText = false,
	url,
}) => {
	const [data, setData] = useState();

	let fetchURL = admin ? `${adminBaseURL}${url}` : `${metricsBaseURL}${url}`;

	fetchURL = new URL(fetchURL, Liferay.ThemeDisplay.getPortalURL());

	Object.entries(params).map(([key, value]) => {
		if (value !== null && value !== undefined) {
			fetchURL.searchParams.append(key, value);
		}
	});

	const fetchData = useCallback(async () => {
		const response = await fetch(fetchURL, {
			headers,
			method: 'GET',
		});

		const data = plainText ? await response.text() : await response.json();

		if (response.ok) {
			setData(data);

			return callback(data);
		}

		throw data;
	}, [callback, fetchURL, plainText]);

	return {
		data,
		fetchData,
	};
};

export {useFetch};
