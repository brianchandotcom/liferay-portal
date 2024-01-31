/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import updateNetwork from '../actions/updateNetwork';
import {Options} from './serviceFetch';
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
): Promise<T>;
