/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {config} from '../config/index';

/**
 * Returns a new FormData built from the given object.
 */
function getFormData(body: Record<string, string>) {
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

export interface Options extends Omit<RequestInit, 'body' | 'method'> {
	body?: Record<string, any>;
	method?: string;
}

/**
 * Performs a POST request to the given url and parses an expected object response.
 * If the response status is over 400, or there is any "error" or "exception"
 * properties on the response object, it rejects the promise with an Error object.
 */
export default function serviceFetch<T>(
	url: string,
	{body = {}, method, ...options}: Options = {body: {}}
): Promise<T> {
	return fetch(url, {
		...options,
		body: getFormData(body),
		method: method || 'POST',
	})
		.then(
			(response) =>
				new Promise<[Response, T]>((resolve, reject) => {
					response
						.clone()
						.json()
						.then((body) => resolve([response, body]))
						.catch(() =>
							reject(
								new Error(
									Liferay.Language.get(
										'an-unexpected-error-occurred'
									)
								)
							)
						);
				})
		)
		.then(([response, body]) => {
			if (response.status >= 400 || typeof body !== 'object' || !body) {
				throw new Error(
					Liferay.Language.get('an-unexpected-error-occurred')
				);
			}

			handleError(body);

			return body;
		});
}

function handleError(body: any) {
	if (body.redirectURL) {
		window.location.href = body.redirectURL as string;
	}
	else if (body.exception) {
		throw new Error(Liferay.Language.get('an-unexpected-error-occurred'));
	}
	else if (body.error) {
		throw new Error(body.error as string);
	}
}
