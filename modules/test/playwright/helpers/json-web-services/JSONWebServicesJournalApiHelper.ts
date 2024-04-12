/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../../liferay.config';
import {ApiHelpers} from '../ApiHelpers';

export class JSONWebServicesJournalApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/journal.journalarticle';
	}

	async expireArticle(siteId: string, articleId: string): Promise<void> {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('groupId', siteId);
		urlSearchParams.append('articleId', articleId);
		urlSearchParams.append('articleURL', '');

		return this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/expire-article`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}

	async moveArticleToTrash(siteId: string, articleId: string): Promise<void> {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('groupId', siteId);
		urlSearchParams.append('articleId', articleId);

		return this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/move-article-to-trash`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}
}
