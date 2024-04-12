/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';

export class HeadlessAdminContentApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-admin-content/v1.0';
	}

	async postStructuredContentDraft({
		categoryIds,
		contentStructureId,
		datePublished,
		siteId,
		tags,
		title,
	}: {
		categoryIds?: number[];
		contentStructureId: number;
		datePublished: string;
		siteId: string;
		tags?: string[];
		title: string;
	}): Promise<StructuredContent> {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/${siteId}/structured-contents/draft`,
			{
				data: {
					contentStructureId,
					datePublished,
					keywords: tags,
					taxonomyCategoryIds: categoryIds,
					title,
				},
				failOnStatusCode: true,
			}
		);
	}
}
