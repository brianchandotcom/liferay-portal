/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../../liferay.config';
import {ApiHelpers} from '../ApiHelpers';

export class JSONWebServicesResourcePermissionApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/resourcepermission';
	}

	async addResourcePermission(
		actionId: string,
		companyId: string,
		groupId: string,
		name: string,
		primKey: string,
		roleId: string,
		scope: string
	) {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('groupId', groupId);
		urlSearchParams.append('companyId', companyId);
		urlSearchParams.append('name', name);
		urlSearchParams.append('scope', scope);
		urlSearchParams.append('primKey', primKey);
		urlSearchParams.append('roleId', roleId);
		urlSearchParams.append('actionId', actionId);

		this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/add-resource-permission`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}

	async removeResourcePermission(
		actionId: string,
		companyId: string,
		groupId: string,
		name: string,
		primKey: string,
		roleId: string,
		scope: string
	) {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('groupId', groupId);
		urlSearchParams.append('companyId', companyId);
		urlSearchParams.append('name', name);
		urlSearchParams.append('scope', scope);
		urlSearchParams.append('primKey', primKey);
		urlSearchParams.append('roleId', roleId);
		urlSearchParams.append('actionId', actionId);

		this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/remove-resource-permission`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}
}
