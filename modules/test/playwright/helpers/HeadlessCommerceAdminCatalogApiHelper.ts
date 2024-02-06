/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getRandomInt} from '../utils/util';
import {ApiHelpers} from './ApiHelpers';

type TCatalog = {
	accountId?: number;
	currencyCode?: string;
	defaultLanguageId?: string;
	name?: string;
};

type TProduct = {
	active?: boolean;
	catalogId: number;
	name?: {
		[key: string]: string;
	};
	productId?: number;
	productOptions?: any[];
	productSpecifications?: any[];
	productStatus?: number;
	productType?: string;
	skus?: TSku[];
};

type TSku = {
	cost: number;
	id?: number;
	price: number;
	published: boolean;
	purchasable: boolean;
	sku: string;
};

type TSkuUnitOfMeasure = {
	active?: boolean;
	basePrice?: number;
	incrementalOrderQuantity?: number;
	key?: string;
	name?: {
		[key: string]: string;
	};
	primary?: boolean;
	priority?: number;
	rate?: number;
};

export class HeadlessCommerceAdminCatalogApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-commerce-admin-catalog/v1.0';
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

	async deleteOption(optionId: string) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/options/${optionId}`
		);
	}

	async deleteOptionCategory(optionCategoryId: string) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/optionCategories/${optionCategoryId}`
		);
	}

	async deleteProduct(productId: number) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/products/${productId}`
		);
	}

	async deleteSkuUnitOfMeasure(skuUnitOfMeasureId: string) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/sku-unit-of-measures/${skuUnitOfMeasureId}`
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

	async getProduct(productId: number) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/products/${productId}?nestedFields=skus`
		);
	}

	async getSpecification(specificationId: string) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/specifications/${specificationId}`
		);
	}

	async patchProduct(productId: string, product?: DataObject) {
		return this.apiHelpers.patch(
			`${this.apiHelpers.baseUrl}${this.basePath}/products/${productId}`,
			{
				name: {
					en_US: 'Product' + getRandomInt(),
				},
				...(product || {}),
			}
		);
	}

	async postAttachment(
		productId: number,
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

	async postCatalog(catalog?: TCatalog) {
		return await this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/catalogs`,
			{
				accountId: 0,
				currencyCode: 'USD',
				defaultLanguageId: 'en_US',
				name: 'Catalog' + getRandomInt(),
				...(catalog || {}),
			}
		);
	}

	async postOption(
		fieldType: string = 'select',
		key: string = 'key-' + getRandomInt(),
		name: string = 'Option' + getRandomInt(),
		priority: number = getRandomInt()
	) {
		const postOption = await this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/options`,
			{
				fieldType,
				key,
				name: {
					en_US: name,
				},
				priority,
			}
		);

		return postOption;
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

	async postProduct(product: TProduct): Promise<TProduct> {
		return await this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/products?nestedFields=skus`,
			{
				active: true,
				catalogId: 0,
				name: {
					en_US: 'Product' + getRandomInt(),
				},
				productStatus: 0,
				productType: 'simple',
				skus: [
					{
						cost: 0,
						price: 0,
						published: true,
						purchasable: true,
						sku: 'Sku' + getRandomInt(),
					},
				],
				...product,
			}
		);
	}

	async postSkuUnitOfMeasure(
		skuId: number,
		skuUnitOfMeasure: TSkuUnitOfMeasure
	) {
		const postSkuUnitOfMeasure = await this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/skus/${skuId}/sku-unit-of-measures`,
			{
				active: true,
				basePrice: 0,
				incrementalOrderQuantity: 1,
				key: 'key-' + getRandomInt(),
				name: {
					en_US: 'UOM' + getRandomInt(),
				},
				primary: false,
				priority: getRandomInt(),
				rate: getRandomInt(),
				...skuUnitOfMeasure,
			}
		);

		return postSkuUnitOfMeasure;
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
