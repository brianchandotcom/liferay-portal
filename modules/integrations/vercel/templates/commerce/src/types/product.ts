/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type Attachment = {
	attachment: string;
	cdnEnabled: boolean;
	cdnURL: string;
	displayDate: string;
	expirationDate: string;
	externalReferenceCode: string;
	fileEntryId: number;
	galleryEnabled: boolean;
	id: number;
	neverExpire: boolean;
	options: {
		[key: string]: string;
	};
	priority: number;
	src: string;
	tags: Array<string>;
	title: string;
	type: number;
};

export type PageProduct = {
	actions: {
		[key: string]: {
			[key: string]: string;
		};
	};
	items: Array<Product>;
	lastPage: number;
	page: number;
	pageSize: number;
	totalCount: number;
};

type Price = {
	currency: string;
	discount: string;
	discountPercentage: string;
	discountPercentages: Array<string>;
	finalPrice: string;
	price: number;
	priceFormatted: string;
	priceOnApplication: boolean;
	pricingQuantityPrice: number;
	pricingQuantityPriceFormatted: string;
	promoPrice: number;
	promoPriceFormatted: string;
	tierPrice: number;
	tierPriceFormatted: string;
};

export type Product = {
	catalogName: string;
	createDate: string;
	description: string;
	expando: {
		[key: string]: {
			[key: string]: unknown;
		};
	};
	externalReferenceCode: string;
	id: number;
	images: Array<Attachment>;
	metaDescription: string;
	metaKeyword: string;
	metaTitle: string;
	modifiedDate: string;
	multipleOrderQuantity: number;
	name: string;
	productConfiguration: ProductConfiguration;
	productId: number;
	productSpecifications: Array<ProductSpecification>;
	productType: string;
	shortDescription: string;
	skus: Array<Sku>;
	slug: string;
	tags: Array<string>;
	urlImage: string;
	urls: {
		[key: string]: string;
	};
};

type ProductConfiguration = {
	allowBackOrder: boolean;
	allowedOrderQuantities: Array<number>;
	availabilityEstimateId: number;
	availabilityEstimateName: string;
	displayAvailability: boolean;
	displayStockQuantity: boolean;
	inventoryEngine: string;
	lowStockAction: string;
	maxOrderQuantity: number;
	minOrderQuantity: number;
	minStockQuantity: number;
	multipleOrderQuantity: number;
};

export type ProductSpecification = {
	id: number;
	optionCategoryId: number;
	priority: number;
	productId: number;
	specificationGroupKey: string;
	specificationGroupTitle: string;
	specificationId: number;
	specificationKey: string;
	specificationPriority: number;
	specificationTitle: string;
	value: string;
};

export type Sku = {
	allowedOrderQuantities: Array<string>;
	availability: {
		label: string;
		label_i18n: string;
		stockQuantity: number;
	};
	discontinued: boolean;
	discontinuedDate: string;
	displayDate: string;
	displayDiscountLevels: boolean;
	expirationDate: string;
	externalReferenceCode: string;
	gtin: string;
	height: number;
	id: number;
	incomingQuantityLabel: string;
	manufacturerPartNumber: string;
	maxOrderQuantity: number;
	minOrderQuantity: number;
	neverExpire: boolean;
	price: Price;
	productConfiguration: ProductConfiguration;
	productId: number;
	published: boolean;
	purchasable: boolean;
	replacementSkuExternalReferenceCode: string;
	replacementSkuId: number;
	sku: string;
	skuUnitOfMeasures: Array<unknown>;
	weight: number;
	width: number;
};
