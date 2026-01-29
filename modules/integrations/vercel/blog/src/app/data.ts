/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {Page, WithLiferay} from '../app/liferay/index';

export interface CMSBlogPosting {
	content: string;
	contentRawText: string;
	coverImage: {id: number; link: {href: string; label: string}};
	creator: {name: string};
	dateCreated: string;
	friendlyUrlPath: string;
	id: number;
	keywords: string[];
	title: string;
}

export async function getCMSBlogPostings({
	liferay,
	page,
	search,
}: WithLiferay<{page: number; search?: string}>) {
	try {
		const liferaySpace = liferay.getSpace();
		const response = await liferay.fetch(
			liferay.cmsEndpoints.blogPosts({
				page,
				pageSize: 20,
				search,
				sort: 'dateCreated:desc',
				spaceId: liferaySpace.id,
			})
		);

		if (!response.ok) {
			return {
				data: null,
				error: await response.text(),
			};
		}

		const data = (await response.json()) as Page<CMSBlogPosting>;

		return {data};
	}
	catch (error) {
		return {
			data: null,
			error,
		};
	}
}
