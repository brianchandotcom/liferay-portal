/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Analytics} from './types';
import {HEADER_PROJECT_ID, REQUEST_TIMEOUT} from './utils/constants';

/**
 * Client used to abstract communication with the Analytics Endpoint. It exposes the send
 * as only valid entry point for sending and modifying requests.
 * It process queues to send messages to the server. It will periodically run a task to
 * process messages in its queue.
 */
class ClientAdapter {
	projectId: string;
	timeout: number;
	url: string;

	constructor({
		endpointUrl,
		projectId,
		timeout,
	}: {
		endpointUrl: string;
		projectId: string;
		timeout?: number;
	}) {
		this.projectId = projectId;
		this.timeout = timeout || REQUEST_TIMEOUT;
		this.url = endpointUrl;
	}

	/**
	 * Returns a Request object with all data from the analytics instance
	 * including the batched event objects
	 */
	_getRequestParameters(): RequestInit {
		const headers = {'Content-Type': 'application/json'};

		if (this.projectId) {
			Object.assign(headers, {[HEADER_PROJECT_ID]: this.projectId});
		}

		return {
			cache: 'default',
			credentials: 'same-origin',
			headers,
			method: 'POST',
			mode: 'cors',
		};
	}

	/**
	 * Returns the Response object or a rejected Promise based on the
	 * HTTP Response Code of the Response object
	 */
	_validateResponse(response: Response) {
		let newResponse: Response | Promise<Response> = response;

		if (!response.ok) {
			newResponse = new Promise((_, reject) => reject(response));
		}

		return newResponse;
	}

	/**
	 * Returns a resolved or rejected promise as per the response status or if the request times out.
	 */
	sendWithTimeout(payload: Analytics.Event | Analytics.Identity) {
		return Promise.race([this.send(payload), this._timeout()]);
	}

	/**
	 * Send a request with given payload and url.
	 */
	send(payload: Analytics.Event | Analytics.Identity) {
		const parameters = this._getRequestParameters();

		Object.assign(parameters, {
			body: JSON.stringify(payload),
		});

		return fetch(this.url, parameters).then(this._validateResponse);
	}

	/**
	 * Returns a promise that times out after the given time limit is exceeded
	 */
	_timeout() {
		return new Promise((_, reject) => setTimeout(reject, this.timeout));
	}
}

export {ClientAdapter};
export default ClientAdapter;
