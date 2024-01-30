/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';

export class HeadlessDeliveryApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-delivery/v1.0';
	}

	async createSitePage(
		siteId: string,
		title: string,
		pageDefinition?: PageDefinition
	): Promise<Layout> {
		await this.apiHelpers.featureFlag.updateFeatureFlag('LPS-178052', true);

		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/${siteId}/site-pages`,
			{pageDefinition, title}
		);
	}

	async deleteDocument(documentId: string) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/documents/${documentId}`
		);
	}

	async deleteSiteDocumentsFolderByExternalReferenceCode(
		externalReferenceCode: string
	) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/Guest/documents-folder/by-external-reference-code/${externalReferenceCode}`
		);
	}

	async getSiteDocumentsPage(siteId: string) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/${siteId}/documents`
		);
	}
}
