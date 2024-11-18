/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {liferayConfig} from '../../liferay.config';
import {LAYOUT_PAGE_TEMPLATE_ENTRY_TYPES} from '../../utils/layoutPageTemplateEntryTypes';
import {ApiHelpers} from '../ApiHelpers';

export class JSONWebServicesLayoutPageTemplateCollectionApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = '/api/jsonws/layout.layoutpagetemplatecollection';
	}

	async addLayoutPageTemplateCollection({
		externalReferenceCode = '',
		groupId,
		name,
		type = 'basic',
	}: {
		externalReferenceCode?: string;
		groupId: string;
		name: string;
		type?: LayoutPageTemplateEntryType;
	}): Promise<LayoutPageTemplateCollection> {
		const urlSearchParams = new URLSearchParams();

		urlSearchParams.append('externalReferenceCode', externalReferenceCode);
		urlSearchParams.append('groupId', groupId);
		urlSearchParams.append('parentLayoutPageTemplateCollectionId', '0');
		urlSearchParams.append('name', name);
		urlSearchParams.append('description', '');
		urlSearchParams.append('type', LAYOUT_PAGE_TEMPLATE_ENTRY_TYPES[type]);
		urlSearchParams.append('serviceContext', JSON.stringify({}));

		return await this.apiHelpers.post(
			`${liferayConfig.environment.baseUrl}${this.basePath}/add-layout-page-template-collection`,
			{
				data: urlSearchParams.toString(),
				failOnStatusCode: true,
				headers: await this.apiHelpers.getJSONWebServicesHeaders(),
			}
		);
	}
}
