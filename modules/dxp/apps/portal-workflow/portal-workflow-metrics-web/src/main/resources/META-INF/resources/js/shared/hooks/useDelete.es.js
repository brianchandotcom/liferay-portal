/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';
import {useCallback} from 'react';

import {adminBaseURL, headers, metricsBaseURL} from '../rest/fetch.es';

const useDelete = ({admin = false, callback = () => {}, url}) => {
	const fetchURL = admin
		? `${adminBaseURL}${url}`
		: `${metricsBaseURL}${url}`;

	return useCallback(async () => {
		const response = await fetch(fetchURL, {headers, method: 'DELETE'});

		if (response.ok) {
			return callback();
		}

		const requestFailedMessage = Liferay.Language.get(
			'your-request-has-failed'
		);

		throw new Error(requestFailedMessage);
	}, [callback, fetchURL]);
};

export {useDelete};
