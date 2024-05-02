/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';

interface createSitePageProps {
	pageDefinition?: PageDefinition;
	pagePermissions?: PagePermission[];
	siteId: string;
	title: string;
}

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
	async createSitePage({
		pageDefinition,
		pagePermissions,
		siteId,
		title,
	}: createSitePageProps): Promise<Layout> {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/${siteId}/site-pages`,
			{data: {pageDefinition, pagePermissions, title}}
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

	async postSiteKnowledgeBaseArticle({
		articleBody,
		siteId,
		title,
	}: {
		articleBody: string;
		siteId: string;
		title: string;
	}): Promise<KnowledgeBaseArticle> {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/${siteId}/knowledge-base-articles`,
			{
				data: {
					articleBody,
					title,
				},
				failOnStatusCode: true,
			}
		);
	}

	async postStructuredContent({
		categoryIds,
		contentStructureId,
		datePublished,
		siteId,
		tags,
		title,
	}: {
		categoryIds?: number[];
		contentStructureId: number;
		datePublished: string;
		siteId: string;
		tags?: string[];
		title: string;
	}): Promise<StructuredContent> {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/${siteId}/structured-contents`,
			{
				data: {
					contentStructureId,
					datePublished,
					keywords: tags,
					taxonomyCategoryIds: categoryIds,
					title,
				},
				failOnStatusCode: true,
			}
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
