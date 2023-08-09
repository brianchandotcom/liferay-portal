/* eslint-disable @liferay/portal/no-global-fetch */
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../liferay';
import FetcherError from './FetchError';

const liferayHost = window.location.origin;

const headlessAdminUserAPIs = ['account', 'roles', 'user-groups'];

const headlessDeliveryAPIs = [
	'message-board-messages',
	'message-board-threads',
];

function changeResource(resource: RequestInfo) {
	const getIsResourceFromAPI = (apis: string[]) =>
		apis.some((api) => resource.toString().includes(api));

	if (resource.toString().startsWith('http')) {
		return resource;
	}

	if (resource.toString().startsWith('/compare-runs')) {
		return `${liferayHost}/o/osb-testray-rest/v1.0${resource}`;
	}

	if (resource.toString().startsWith('/dispatch-triggers')) {
		return `${liferayHost}/o/dispatch-rest/v1.0${resource}`;
	}

	if (getIsResourceFromAPI(headlessDeliveryAPIs)) {
		return `${liferayHost}/o/headless-delivery/v1.0${resource}`;
	}

	if (getIsResourceFromAPI(headlessAdminUserAPIs)) {
		return `${liferayHost}/o/headless-admin-user/v1.0${resource}`;
	}

	return `${liferayHost}/o/c${resource}`;
}

const fetcher = async <T = any>(
	resource: RequestInfo,
	options?: RequestInit
): Promise<T | undefined> => {
	const response = await fetch(changeResource(resource), {
		...options,
		headers: {
			...options?.headers,
			'Content-Type': 'application/json',
			'x-csrf-token': Liferay.authToken,
		},
	});

	if (!response.ok) {
		const error = new FetcherError(
			'An error occurred while fetching the data.'
		);

		error.info = await response.json();
		error.status = response.status;
		throw error;
	}

	if (options?.method !== 'DELETE' && response.status !== 204) {
		return response.json();
	}
};

fetcher.delete = (resource: RequestInfo) =>
	fetcher(resource, {
		method: 'DELETE',
	});

fetcher.patch = (resource: RequestInfo, data: unknown, options?: RequestInit) =>
	fetcher(resource, {
		...options,
		body: JSON.stringify(data),
		method: 'PATCH',
	});

fetcher.post = <T = any>(
	resource: RequestInfo,
	data?: unknown,
	options?: RequestInit
) =>
	fetcher<T>(resource, {
		...options,
		body: data ? JSON.stringify(data) : null,
		method: 'POST',
	}) as Promise<T>;

fetcher.put = (resource: RequestInfo, data: unknown, options?: RequestInit) =>
	fetcher(resource, {
		...options,
		body: JSON.stringify(data),
		method: 'PUT',
	});

export default fetcher;
