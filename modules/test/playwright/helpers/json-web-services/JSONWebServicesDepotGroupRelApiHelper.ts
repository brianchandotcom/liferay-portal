/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../../liferay.config';
import {ApiHelpers} from '../ApiHelpers';

type DepotEntryGroupRel = {
	depotEntryGroupRelId: string;
	depotEntryId: string;
	groupId: string;
};

export class JSONWebServicesDepotGroupRelApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/depot.depotentrygrouprel';
	}

	async addDepotEntryGroupRel(
		depotEntryId: string,
		toGroupId: string
	): Promise<DepotEntryGroupRel> {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('depotEntryId', depotEntryId);
		urlSearchParams.append('toGroupId', toGroupId);

		return this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/add-depot-entry-group-rel`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}
}
