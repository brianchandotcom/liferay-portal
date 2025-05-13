/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

async function fetchMetrics(endpoint: string) {
	const HEADERS = new Headers({
		'Accept': 'application/json',
		'x-csrf-token': Liferay.authToken,
	});

	const response = await fetch(endpoint, {
		headers: HEADERS,
		method: 'GET',
	});

	if (response.ok) {
		return await response.json();
	}
	else {
		const {status} = response;
		throw new Error(
			`GET request failed to fetch data with provided parameters. Code: ${status.toString()}`
		);
	}
}

export {fetchMetrics};
