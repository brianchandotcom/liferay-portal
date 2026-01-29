/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const LIFERAY_HOST = process.env.LIFERAY_HOST || '';
const LIFERAY_HEADLESS_BASE_URL = `${LIFERAY_HOST}/o`;
const LIFERAY_SPACE_ID = process.env.LIFERAY_SPACE_ID || 0;

export const liferay = {
	cmsEndpoints: {
		blogPost: ({blogId}: {blogId: number}) => {
			return `/cms/blogs/${blogId}`;
		},

		blogPosts: ({
			page = 1,
			pageSize,
			search = '',
			sort,
			spaceId,
		}: {
			page: number;
			pageSize: number;
			search?: string;
			sort: string;
			spaceId: number;
		}) => {
			const searchParams = new URLSearchParams({
				page: `${page}`,
				pageSize: `${pageSize}`,
				search: `${search}`,
				sort: `${sort}`,
			});

			return `/cms/blogs/scopes/${spaceId}?${searchParams.toString()}`;
		},
	},

	fetch: async (
		resource: string | URL | globalThis.Request,
		init?: RequestInit
	) => {
		const endpoint =
			typeof resource === 'string' && resource.startsWith('/')
				? `${LIFERAY_HEADLESS_BASE_URL}${resource}`
				: resource;

		const response = await fetch(endpoint, {
			headers: {
				'accept': '*/*',
				'accept-language': 'en-US,en;q=0.5',
				'content-type': 'application/json',
				...init?.headers,
			},
			method: init?.method || 'GET',
			next: {
				revalidate: 3600,
			},
		});

		return response;
	},

	getDocument: (documentPath: string) => {
		if (documentPath.startsWith('/')) {
			return `${LIFERAY_HOST}${documentPath}`;
		}

		return documentPath;
	},

	getSpace: () => {
		return {
			id: Number(LIFERAY_SPACE_ID),
		};
	},
};

export type Liferay = typeof liferay;

export type WithLiferay<TParams = unknown> = TParams & {liferay: Liferay};
