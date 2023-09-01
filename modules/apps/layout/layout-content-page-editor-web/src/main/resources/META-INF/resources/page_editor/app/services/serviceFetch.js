/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {updateNetwork} from '../actions/index';
import {SERVICE_NETWORK_STATUS_TYPES} from '../config/constants/serviceNetworkStatusTypes';
import {config} from '../config/index';

/**
 * Returns a new FormData built from the given object.
 * @param {object} body
 * @return {FormData}
 * @review
 */
function getFormData(body) {
	const formData = new FormData();

	Object.entries(body).forEach(([key, value]) => {
		if (!value && process.env.NODE_ENV === 'development') {
			console.warn(
				`${key} does not have any value, sending it this way could cause some wrong behavior`
			);
		}

		formData.append(`${config.portletNamespace}${key}`, value);
	});

	return formData;
}

/**
 * Performs a POST request to the given url and parses an expected object response.
 * If the response status is over 400, or there is any "error" or "exception"
 * properties on the response object, it rejects the promise with an Error object.
 * @param {string} url
 * @param {object} [body={}]
 * @param {function} onNetworkStatus
 * @param {{ requestGenerateDraft: boolean }} [options={requestGenerateDraft: false}]
 * @private
 * @return {Promise<object>}
 * @review
 */
export default function serviceFetch(
	url,
	{body = {}, method, ...options} = {body: {}},
	onNetworkStatus,
	{requestGenerateDraft = false} = {requestGenerateDraft: false}
) {
	if (requestGenerateDraft) {
		onNetworkStatus(
			updateNetwork({
				status: SERVICE_NETWORK_STATUS_TYPES.savingDraft,
			})
		);
	}

	return fetch(url, {
		...options,
		body: getFormData(body),
		method: method || 'POST',
	})
		.then(
			(response) =>
				new Promise((resolve, reject) => {
					response
						.clone()
						.json()
						.then((body) => resolve([response, body]))
						.catch(() => response.clone().text())
						.then((body) => resolve([response, body]))
						.catch(reject);
				})
		)
		.then(([response, body]) => {
			if (typeof body === 'object') {
				if ('redirectURL' in body) {
					window.location.href = body.redirectURL;
				}
				else if ('exception' in body) {
					return handleErroredResponse(
						Liferay.Language.get('an-unexpected-error-occurred'),
						onNetworkStatus
					);
				}
				else if ('error' in body) {
					return handleErroredResponse(
						Liferay.Language.get(body.error),
						onNetworkStatus
					);
				}
			}
			else {
				return handleErroredResponse(
					Liferay.Language.get('an-unexpected-error-occurred'),
					onNetworkStatus
				);
			}

			if (response.status >= 400) {
				return handleErroredResponse(
					Liferay.Language.get('an-unexpected-error-occurred'),
					onNetworkStatus
				);
			}

			if (requestGenerateDraft) {
				onNetworkStatus(
					updateNetwork({
						status: SERVICE_NETWORK_STATUS_TYPES.draftSaved,
					})
				);
			}

			return body;
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
