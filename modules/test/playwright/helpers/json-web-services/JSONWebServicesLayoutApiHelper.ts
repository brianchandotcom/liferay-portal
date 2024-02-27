/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../../liferay.config';
import {ApiHelpers} from '../ApiHelpers';

type Layout = {
	friendlyURL: string;
};

export class JSONWebServicesLayoutApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/layout';
	}

	async addLayout(siteId: string, title: string): Promise<Layout> {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('groupId', siteId);
		urlSearchParams.append('privateLayout', 'false');
		urlSearchParams.append('parentLayoutId', '0');
		urlSearchParams.append('name', title);
		urlSearchParams.append('title', title);
		urlSearchParams.append('description', '');
		urlSearchParams.append('type', 'portlet');
		urlSearchParams.append('hidden', 'false');
		urlSearchParams.append('friendlyURL', `/${title}`);

		return this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/add-layout`,
			urlSearchParams.toString(),
			true,
			await this.apiHelpers.getJSONWebServicesHeaders()
		);
	}
}
