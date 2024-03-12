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

	/**
	 * This method requires the feature flag LPS-178052 to be enabled,
	 * please enable it in your test if using it.
	 *
	 * It allows creating a page inside a site.
	 *
	 * @param siteId the id of the site in which the page will be created
	 * @param title the title of the page
	 * @param pageDefinition the definition of the page in case that we want
	 * to specify some content for it, for example some fragments+
	 */
	async createSitePage(
		siteId: string,
		title: string,
		pageDefinition?: PageDefinition
	): Promise<Layout> {
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

	async getSiteDocumentsPage(siteId: string, sort: string = 'id') {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/${siteId}/documents?sort=${sort}`
		);
	}

	async postStructuredContent(
		siteId: string,
		contentStructureId: number,
		datePublished: string,
		title: string
	): Promise<StructuredContent> {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/${siteId}/structured-contents`,
			{contentStructureId, datePublished, title},
			true
		);
	}

	async getStructuredContentByKey(
		siteId: string,
		key: string
	): Promise<StructuredContent> {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/${siteId}/structured-contents/by-key/${key}`,
			true
		);
	}
}
