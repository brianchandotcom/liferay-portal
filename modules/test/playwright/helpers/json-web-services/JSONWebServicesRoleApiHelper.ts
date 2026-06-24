/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../../liferay.config';
import {ApiHelpers} from '../ApiHelpers';

type Role = {
	name: string;
	roleId: string;
};

export class JSONWebServicesRoleApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/role';
	}

	async getRole(companyId: string, name: string): Promise<Role> {
		return this.apiHelpers.get(
			`${liferayConfig.environment.baseUrl}${this.basePath}/get-role/company-id/${companyId}/name/${name}`,
			true,
			await this.apiHelpers.getJSONWebServicesHeaders()
		);
	}
}
