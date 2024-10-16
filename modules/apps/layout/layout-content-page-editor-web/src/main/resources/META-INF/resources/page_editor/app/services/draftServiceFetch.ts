/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateNetwork from '../actions/updateNetwork';
import {SERVICE_NETWORK_STATUS_TYPES} from '../config/constants/serviceNetworkStatusTypes';
import serviceFetch, {Options} from './serviceFetch';

export interface OnNetworkStatus {
	(action: ReturnType<typeof updateNetwork>): void;
}

/**
 * Performs a POST request to the given url and parses an expected object response.
 * If the response status is over 400, or there is any "error" or "exception"
 * properties on the response object, it rejects the promise with an Error object.
 */
export default function draftServiceFetch<T>(
	url: string,
	options: Options,
	onNetworkStatus: OnNetworkStatus
): Promise<T> {
	onNetworkStatus(
		updateNetwork({
			status: SERVICE_NETWORK_STATUS_TYPES.savingDraft,
		})
	);

	return serviceFetch<T>(url, options)
		.then((response) => {
			onNetworkStatus(
				updateNetwork({
					status: SERVICE_NETWORK_STATUS_TYPES.draftSaved,
				})
			);

			return response;
		})
		.catch((error) => {
			return handleErroredResponse(error.message, onNetworkStatus);
		});
}

/**
 * @param {string} error
 * @param {function} onNetworkStatus
 */
function handleErroredResponse(
	error: string,
	onNetworkStatus: OnNetworkStatus
) {
	onNetworkStatus(
		updateNetwork({
			error,
			status: SERVICE_NETWORK_STATUS_TYPES.error,
		})
	);

	return Promise.reject(error);
}
