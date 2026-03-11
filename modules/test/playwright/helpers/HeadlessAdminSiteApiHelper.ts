/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';

export class HeadlessAdminSiteApiHelper {
	apiHelpers: ApiHelpers;
	basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-admin-site/v1.0';
	}

	async createPage(
		siteExternalReferenceCode: string,
		page: any
	): Promise<any> {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/${siteExternalReferenceCode}/site-pages`,
			{data: page, failOnStatusCode: true}
		);
	}

	async getPage(
		siteExternalReferenceCode: string,
		pageExternalReferenceCode: string
	): Promise<any> {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/${siteExternalReferenceCode}/site-pages/${pageExternalReferenceCode}`
		);
	}

	async getPages(
		siteExternalReferenceCode: string,
		queryString: string
	): Promise<any> {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/${siteExternalReferenceCode}/site-pages?${queryString}`
		);
	}

	async putPage(
		siteExternalReferenceCode: string,
		pageExternalReferenceCode: string,
		page: any
	): Promise<any> {
		return this.apiHelpers.put(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/${siteExternalReferenceCode}/site-pages/${pageExternalReferenceCode}`,
			{data: page, failOnStatusCode: true}
		);
	}

	async postSiteNavigationMenu(
		siteExternalReferenceCode: string,
		name: string
	): Promise<any> {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/${siteExternalReferenceCode}/navigation-menus`,
			{
				data: {
					name,
				},
				failOnStatusCode: true,
			}
		);
	}

	async deleteSiteNavigationMenu(
		siteExternalReferenceCode: string,
		navigationMenuExternalReferenceCode: string
	): Promise<any> {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/${siteExternalReferenceCode}/navigation-menus/${navigationMenuExternalReferenceCode}`,
			{failOnStatusCode: true}
		);
	}
}
