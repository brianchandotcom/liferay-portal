/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';

export class HeadlessSiteApiHelper {
	apiHelpers: ApiHelpers;
	basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-site/v1.0';
	}

	async createSite(name: string): Promise<Site> {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites`,
			{name}
		);
	}

	async createSiteFromSiteTemplate(
		name: string,
		templateKey: number
	): Promise<Site> {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites`,
			{name, templateKey, templateType: 'site-template'}
		);
	}

	async deleteSite(siteId: string) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/${siteId}`
		);
	}
}
