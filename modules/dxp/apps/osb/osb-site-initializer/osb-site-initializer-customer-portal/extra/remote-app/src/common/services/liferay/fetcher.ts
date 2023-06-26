/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

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
		const cause = await response.text();

		console.error(cause, JSON.stringify({options, url}, null, 2));

		throw new Error(cause);
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
