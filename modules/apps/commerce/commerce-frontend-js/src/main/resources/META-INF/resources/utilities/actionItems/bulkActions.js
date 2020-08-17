/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {fetch} from 'frontend-js-web';

import {fetchParams} from '../index';

export function listenToBulkActionStatus(
	id,
	batchTasksStatusApiUrl,
	timeout = 10000
) {
	let interval;

	return Promise.race([
		new Promise((resolve, reject) => {
			interval = setInterval(function getBulkActionStatus() {
				return fetch(`${batchTasksStatusApiUrl}/${id}`, fetchParams)
					.then((response) => response.json())
					.then((jsonResponse) => {
						if (jsonResponse.executeStatus === 'COMPLETED') {
							clearInterval(interval);
							resolve('success');
						}
						if (jsonResponse.executeStatus === 'FAILED') {
							clearInterval(interval);
							reject(jsonResponse.errorMessage);
						}
					});
			}, 1000);
		}),
		new Promise((_, reject) =>
			setTimeout(() => {
				clearInterval(interval);
				reject(Liferay.Language.get('request-timeout'));
			}, timeout)
		),
	]);
}
