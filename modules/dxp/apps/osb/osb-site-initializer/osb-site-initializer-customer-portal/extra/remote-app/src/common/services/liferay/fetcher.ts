/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import i18n from '~/common/I18n';
import FetcherError from './FetchError';

export async function fetcher<T = any>(
	url: string | URL,
	options?: RequestInit
): Promise<T | undefined> {
	// eslint-disable-next-line @liferay/portal/no-global-fetch
	const response = await fetch(url, {
		...options,
		headers: {
			...options?.headers,
			...(options?.method === 'POST' && {
				'Content-Type': 'application/json',
			}),
		},
	});

	if (!response.ok) {
		const error = new FetcherError(
			i18n.translate('an-unexpected-error-occurred')
		);

		error.info = await response.json();
		error.status = response.status;
		console.error(error.info, JSON.stringify({options, url}, null, 2));

		throw error;
	}

	if (response.status !== 204) {
		return response.json();
	}
}

const baseFetcher = <T = any>(
	baseURL: string | URL,
	baseOptions?: RequestInit
) => (url: string | URL, options?: RequestInit) =>
	fetcher<T>(`${baseURL}${url}`, {
		...baseOptions,
		...options,
	});

export {baseFetcher};
