/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {DEFAULT_FETCH_HEADERS} from '@liferay/frontend-data-set-web';
import {fetch} from 'frontend-js-web';

const PAGE_SIZE = 100;

export interface IPage<T> {
	items?: T[];
	lastPage?: number;
	page?: number;
	totalCount?: number;
}

export default async function fetchAllPages<T>(
	baseURL: string,
	signal?: AbortSignal
): Promise<T[]> {
	const items: T[] = [];

	let page = 1;
	let lastPage = 1;

	do {
		const url = new URL(baseURL, window.location.origin);

		url.searchParams.set('page', String(page));
		url.searchParams.set('pageSize', String(PAGE_SIZE));

		const response = await fetch(url.toString(), {
			headers: DEFAULT_FETCH_HEADERS,
			signal,
		});

		if (!response.ok) {
			throw new Error(
				`Request to ${url.pathname} failed: ${response.status}`
			);
		}

		const json = (await response.json()) as IPage<T>;

		if (Array.isArray(json.items)) {
			items.push(...json.items);
		}

		lastPage = json.lastPage ?? 1;
		page += 1;
	} while (page <= lastPage);

	return items;
}
