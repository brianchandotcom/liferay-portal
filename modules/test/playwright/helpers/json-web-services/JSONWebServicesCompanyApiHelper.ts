/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../../liferay.config';
import {ApiHelpers} from '../ApiHelpers';

type Company = {
	companyId: string;
};

export class JSONWebServicesCompanyApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/company';
	}

	async getCompanyByWebId(webId: string): Promise<Company> {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('webId', webId);

		return this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/get-company-by-web-id`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}
}
