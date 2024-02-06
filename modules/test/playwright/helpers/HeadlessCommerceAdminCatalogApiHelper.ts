/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getRandomInt} from '../utils/util';
import {ApiHelpers} from './ApiHelpers';

export class HeadlessCommerceAdminCatalogApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-commerce-admin-catalog/v1.0/';
	}

	async deleteAttachment(attachmentId: string) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/attachment/${attachmentId}`
		);
	}

	async deleteCatalog(catalogId: string) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/catalog/${catalogId}`
		);
	}

	async deleteOptionCategory(optionCategoryId: string) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/optionCategories/${optionCategoryId}`
		);
	}

	async deleteProduct(productId: string) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/products/${productId}`
		);
	}

	async deleteSpecification(specificationId: string) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/specifications/${specificationId}`
		);
	}

	async getCatalog(catalogId: string) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/catalogs/${catalogId}`
		);
	}

	async getOptionCategory(optionCategoryId: string) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/optionCategories/${optionCategoryId}`
		);
	}

	async getProduct(productId: string) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/products/${productId}`
		);
	}

	async getSpecification(specificationId: string) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/specifications/${specificationId}`
		);
	}

	async patchProduct(productId: string, product?: DataObject) {
		const response = await this.apiHelpers.patch(
			`${this.apiHelpers.baseUrl}${this.basePath}/products/${productId}`,
			{
				name: {
					en_US: 'Product' + getRandomInt(),
				},
				...(product || {}),
			}
		);

		return response;
	}

	async postAttachment(
		productId: string,
		fileEntryId: number,
		title: string = 'Attachment' + getRandomInt()
	) {
		const postAttachment = await this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/products/${productId}/attachments`,
			{
				fileEntryId,
				title: {en_US: title},
			}
		);

		return postAttachment;
	}

	async postCatalog(catalogName: string = 'Catalog' + getRandomInt()) {
		const postCatalog = await this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/catalogs`,
			{
				accountId: 0,
				currencyCode: 'USD',
				defaultLanguageId: 'en_US',
				name: catalogName,
			}
		);

		return postCatalog;
	}

	async postOptionCategory(
		optionCategoryName: string = 'OptionCategory' + getRandomInt(),
		priority: number = getRandomInt()
	) {
		const postOptionCategory = await this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/optionCategories`,
			{
				key: optionCategoryName,
				priority,
				title: {
					en_US: optionCategoryName,
				},
			}
		);

		return postOptionCategory;
	}

	async postProduct(
		catalogId: number,
		productName: string = 'Product' + getRandomInt(),
		productSpecifications?: DataObject[]
	) {
		if (typeof productSpecifications !== 'undefined') {
			return this.apiHelpers.post(
				`${this.apiHelpers.baseUrl}${this.basePath}/products`,
				{
					active: true,
					catalogId,
					name: {
						en_US: productName,
					},
					productSpecifications,
					productStatus: 0,
					productType: 'simple',
				}
			);
		}

		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/products`,
			{
				active: true,
				catalogId,
				name: {
					en_US: productName,
				},
				productStatus: 0,
				productType: 'simple',
			}
		);
	}

	async postSpecification(
		facetable: boolean = true,
		specificationTitle: string = 'Specification' + getRandomInt(),
		optionCategory?: DataObject
	) {
		if (typeof optionCategory !== 'undefined') {
			return this.apiHelpers.post(
				`${this.apiHelpers.baseUrl}${this.basePath}/specifications`,
				{
					facetable,
					key: specificationTitle,
					optionCategory,
					title: {
						en_US: specificationTitle,
					},
				}
			);
		}

		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/specifications`,
			{
				facetable,
				key: specificationTitle,
				title: {
					en_US: specificationTitle,
				},
			}
		);
	}
}
