/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../../liferay.config';
import {ApiHelpers} from '../ApiHelpers';

type Group = {
	groupId: string;
};

export class JSONWebServicesGroupApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/group';
	}

	async getCompanyGroup(companyId: string): Promise<Group> {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('companyId', companyId);

		return this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/get-company-group`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}

	async getGroupByKey(companyId: string, groupKey: string): Promise<Group> {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('companyId', companyId);
		urlSearchParams.append('groupKey', groupKey);

		return this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/get-group`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}
}
