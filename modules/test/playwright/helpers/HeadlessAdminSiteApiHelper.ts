/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers, DataApiHelpers} from './ApiHelpers';

export type TNavigationMenu = {
	name: string;
	navigationMenuItems?: TNavigationMenuItem[];
	navigationType?: 'Primary' | 'Secondary' | 'Social';
};

export type TNavigationMenuItem = {
	name?: string;
	name_i18n?: {[key: string]: string};
	navigationMenuItemSettings?: {
		className?: string;
		externalReferenceCode?: string;
		privatePage?: boolean;
		scopeExternalReferenceCode?: string;
		showAssetVocabularyLevel?: boolean;
		title?: string;
		url?: string;
		useNewTab?: boolean;
	};
	navigationMenuItems?: TNavigationMenuItem[];
	type: 'asset_vocabulary' | 'layout' | 'node' | 'url' | string;
};

export class HeadlessAdminSiteApiHelper {
	apiHelpers: ApiHelpers | DataApiHelpers;
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
		navigationMenu: TNavigationMenu
	): Promise<any> {
		const postNavigationMenu = await this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/${siteExternalReferenceCode}/navigation-menus`,
			{
				data: navigationMenu,
				failOnStatusCode: true,
			}
		);

		if (this.apiHelpers instanceof DataApiHelpers) {
			this.apiHelpers.data.push({
				id: `${siteExternalReferenceCode}|${postNavigationMenu.externalReferenceCode}`,
				type: 'navigationMenu',
			});
		}

		return postNavigationMenu;
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
