/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../../liferay.config';
import {ApiHelpers} from '../ApiHelpers';

export class JSONWebServicesClientExtensionApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/remoteapp.clientextensionentry';
	}

	async addClientExtension({
		externalReferenceCode = '',
		name,
		type,
		url,
	}: {
		externalReferenceCode?: string;
		name: string;
		type: string;
		url: string;
	}): Promise<ClientExtension> {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('externalReferenceCode', externalReferenceCode);
		urlSearchParams.append('description', '');
		urlSearchParams.append('nameMap', JSON.stringify({en_US: name}));
		urlSearchParams.append('properties', '');
		urlSearchParams.append('sourceCodeURL', '');
		urlSearchParams.append('type', type);
		urlSearchParams.append('typeSettings', `url=${url}`);

		return await this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/add-client-extension-entry`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}

	async deleteClientExtension(
		clientExtensionEntryId: string
	): Promise<ClientExtension> {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append(
			'clientExtensionEntryId',
			clientExtensionEntryId
		);

		return this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/delete-client-extension-entry`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}
}
