/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import {fetch} from 'frontend-js-web';
import {useCallback} from 'react';

import {adminBaseURL, headers, metricsBaseURL} from '../rest/fetch.es';

const usePut = ({body = {}, admin = false, url}) => {
	const fetchURL = admin
		? `${adminBaseURL}${url}`
		: `${metricsBaseURL}${url}`;

	return useCallback(async () => {
		const response = await fetch(fetchURL, {
			body: JSON.stringify(body),
			headers: {...headers, 'Content-Type': 'application/json'},
			method: 'PUT',
		});

		if (response.ok) {
			return true;
		}

		throw await response.json();
	}, [body, fetchURL]);
};

export {usePut};
