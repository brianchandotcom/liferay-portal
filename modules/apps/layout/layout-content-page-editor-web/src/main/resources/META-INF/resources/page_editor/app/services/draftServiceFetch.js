/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {updateNetwork} from '../actions/index';
import {SERVICE_NETWORK_STATUS_TYPES} from '../config/constants/serviceNetworkStatusTypes';
import serviceFetch from './serviceFetch';

/**
 * Performs a POST request to the given url and parses an expected object response.
 * If the response status is over 400, or there is any "error" or "exception"
 * properties on the response object, it rejects the promise with an Error object.
 * @param {string} url
 * @param {object} [body={}]
 * @param {function} onNetworkStatus
 * @private
 * @return {Promise<object>}
 * @review
 */
export default function draftServiceFetch(url, options, onNetworkStatus) {
	onNetworkStatus(
		updateNetwork({
			status: SERVICE_NETWORK_STATUS_TYPES.savingDraft,
		})
	);

	return serviceFetch(url, options)
		.then((response) => {
			onNetworkStatus(
				updateNetwork({
					status: SERVICE_NETWORK_STATUS_TYPES.draftSaved,
				})
			);

			return response;
		})
		.catch((error) => {
			handleErroredResponse(error, onNetworkStatus);
		});
}

/**
 * @param {string} error
 * @param {function} onNetworkStatus
 */
function handleErroredResponse(error, onNetworkStatus) {
	onNetworkStatus(
		updateNetwork({
			error,
			status: SERVICE_NETWORK_STATUS_TYPES.error,
		})
	);

	return Promise.reject(error);
}
