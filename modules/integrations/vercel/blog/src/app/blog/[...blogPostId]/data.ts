/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {WithLiferay} from '../../liferay/index';

export interface CMSBlogPosting {
	content: string;
	contentRawText: string;
	coverImage: {id: number; link: {href: string; label: string}};
	creator: {
		additionalName: string;
		contentType: string;
		externalReferenceCode: string;
		familyName: string;
		givenName: string;
		id: number;
		name: string;
	};
	dateCreated: string;
	friendlyUrlPath: string;
	id: number;
	keywords: string[];
	subtitle: string;
	title: string;
}

export async function getCMSBlogPosting({
	blogId,
	liferay,
}: WithLiferay<{blogId: number}>) {
	try {
		const response = await liferay.fetch(
			liferay.cmsEndpoints.blogPost({blogId})
		);

		return {data: (await response.json()) as CMSBlogPosting};
	}
	catch (error) {
		return {
			data: null,
			error,
		};
	}
}
