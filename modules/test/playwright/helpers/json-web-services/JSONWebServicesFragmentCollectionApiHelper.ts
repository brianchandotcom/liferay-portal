/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../../liferay.config';
import {ApiHelpers} from '../ApiHelpers';

export class JSONWebServicesFragmentCollectionApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/fragment.fragmentcollection';
	}

	async addFragmentCollection({
		externalReferenceCode = '',
		groupId,
		name,
	}: {
		externalReferenceCode?: string;
		groupId: string;
		name: string;
	}): Promise<FragmentCollection> {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('externalReferenceCode', externalReferenceCode);
		urlSearchParams.append('groupId', groupId);
		urlSearchParams.append('name', name);
		urlSearchParams.append('description', '');
		urlSearchParams.append('serviceContext', JSON.stringify({}));

		return await this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/add-fragment-collection`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}

	async deleteFragmentCollection(
		fragmentCollectionId: string
	): Promise<FragmentCollection> {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('fragmentCollectionId', fragmentCollectionId);

		return await this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/delete-fragment-collection`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}
}
