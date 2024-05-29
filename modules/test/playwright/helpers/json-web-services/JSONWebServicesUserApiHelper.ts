/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../../liferay.config';
import {ApiHelpers} from '../ApiHelpers';

export class JSONWebServicesUserApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/user';
	}

	async addGroupUsers(groupId: string, userIds: string[]) {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('groupId', groupId);
		urlSearchParams.append('userIds', JSON.stringify(userIds));

		this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/add-group-users`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}

	async assignUsersToSite(groupId: string, userId: string): Promise<void> {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('groupId', groupId);
		urlSearchParams.append('userId', userId);

		return this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/add-group-users/group-id/${groupId}/user-ids/${userId}`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}

	async assignUsersToRole(roleId: string, userIds: string): Promise<void> {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('roleId', roleId);
		urlSearchParams.append('userIds', userIds);

		return this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/add-role-users/role-id/${roleId}/user-ids/${userIds}`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}
}
