/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../../liferay.config';
import {ApiHelpers} from '../ApiHelpers';

export type SiteNavigationMenu = {
	siteNavigationMenuId: string;
};

export class JSONWebServicesSiteNavigationMenuApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/sitenavigation.sitenavigationmenu';
	}

	async addSiteNavigationMenu(
		groupId: string,
		name: string
	): Promise<SiteNavigationMenu> {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('externalReferenceCode', '');
		urlSearchParams.append('groupId', groupId);
		urlSearchParams.append('name', name);
		urlSearchParams.append('type', '0');
		urlSearchParams.append('auto', 'false');
		urlSearchParams.append('serviceContext', JSON.stringify({}));

		return this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/add-site-navigation-menu`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}
}
