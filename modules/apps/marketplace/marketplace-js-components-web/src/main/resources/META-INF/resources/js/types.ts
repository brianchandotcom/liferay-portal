/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export type APIResponse<T = any> = {
	actions: Record<string, unknown>;
	items: T[];
	lastPage: number;
	page: number;
	pageSize: number;
	totalCount: number;
};

export type AppsPermissions = {
	installFreeApps: boolean;
	purchaseAndInstallPaidApps: boolean;
	viewApps: boolean;
};

type Attachment = {
	customFields: CustomField[];
	galleryEnabled: boolean;
	id: number;
	priority: number;
	src: string;
	tags?: string[];
	title: string;
	type: number;
};

type BillingAddress = {
	city?: string;
	country?: string;
	countryISOCode: string;
	description?: string;
	name?: string;
	phoneNumber?: string;
	regionISOCode?: string;
	street1?: string;
	street2?: string;
	zip?: string;
};

export type Cart = {
	accountId: number;
	author?: string;
	billingAddress: BillingAddress;
	cartItems: CartItem[];
	currencyCode: string;
	customFields: any;
	externalReferenceCode: string;
	id: number;
	orderStatusInfo: {[key: string]: string};
	orderTypeExternalReferenceCode: string;
	orderTypeId: number;
	paymentMethod: string;
	paymentStatusInfo: {[key: string]: string};
	paymentStatusLabel: string;
	purchaseOrderNumber?: string;
	shippingAddress: BillingAddress;
	summary: {
		totalFormatted: string;
	};
};

type CartItem = {
	customFields?: {};
	id: number;
	price: {
		currency: string;
		discount: number;
		finalPrice?: number;
		price?: number;
	};
	productId?: number;
	quantity: number;
	settings: {
		maxQuantity: number;
	};
	skuId: number;
};

export type Category = {
	id: number;
	name: string;
	siteId: number;
	title: string;
	vocabulary: string;
};

export interface CloudUserProject {
	environments: {
		isExtensionEnvironment: boolean;
		projectId: string;
	}[];
	rootProjectId: string;
	rootProjectPlanUsage: {
		cpu: PlanUsage;
		instance: PlanUsage;
		memory: PlanUsage;
	};
}

export type CustomField = {
	customValue: {
		data: string | string[];
	};
	dataType: string;
	name: string;
};

export type Image = {
	cdnEnabled: boolean;
	cdnURL: string;
	customFields: CustomField[];
	displayDate: string;
	externalReferenceCode: string;
	fileEntryId: number;
	galleryEnabled: boolean;
	id: number;
	options: Record<string, unknown>;
	priority: number;
	src: string;
	tags: string[];
	title: string;
	type: number;
};

export type MarketplaceAuthorization = {
	accessToken: string;
	accessTokenExpirationTime: string;
};

export type MarketplaceConfiguration = {
	serviceURL: string;
	settings: {
		account: {
			id: number;
			name: string;
		};
		channelId: number;
		cloudProject?: string;
		references: {
			fragmentsFilter: string;
			paymentMethodFilter: string;
		};
		siteId: number;
		userAccount: {
			id: number;
			name: string;
		};
	};
	url: string;
};

export type OrderStatusInfo = {
	code: number;
	label: string;
	label_i18n: string;
};

export type PlacedOrder = {
	account: string;
	accountId: number;
	author: string;
	couponCode: string;
	createDate: string;
	customFields: {[key: string]: string};
	externalReferenceCode: string;
	id: number;
	lastPriceUpdateDate: string;
	modifiedDate: string;
	orderStatusInfo: OrderStatusInfo;
	orderTypeExternalReferenceCode: string;
	orderTypeId: number;
	orderUUID: string;
	paymentMethod: string;
	paymentMethodLabel?: string;
	paymentStatus: number;
	paymentStatusInfo: OrderStatusInfo;
	paymentStatusLabel: string;
	placedOrderBillingAddressId: number;
	placedOrderItems: {
		adaptiveMediaImageHTMLTag: string;
		customFields: {[key: string]: string};
		externalReferenceCode: string;
		id: number;
		name: string;
		options: string;
		parentOrderItemId: number;
		price: Price;
		productId: number;
		productURLs: {
			en_US: string;
		};
		quantity: number;
		replacedSku: string;
		sku: string;
		skuId: number;
		subscription: boolean;
		thumbnail: string;
		unitOfMeasureKey: string;
		virtualItemURLs?: string[];
		virtualItems?: {
			url: string;
			usages: number;
			version: string;
		}[];
	}[];
	placedOrderShippingAddressId: number;
	printedNote: string;
	purchaseOrderNumber: string;
	shippingOption: string;
	status: string;
	summary: {
		currency: string;
		itemsQuantity: number;
		shippingDiscountPercentages: string[];
		shippingDiscountValue: number;
		shippingDiscountValueFormatted: string;
		shippingValue: number;
		shippingValueFormatted: string;
		shippingValueWithTaxAmount: number;
		shippingValueWithTaxAmountFormatted: string;
		subtotal: number;
		subtotalDiscountPercentages: string[];
		subtotalDiscountValue: number;
		subtotalDiscountValueFormatted: string;
		subtotalFormatted: string;
		taxValue: number;
		taxValueFormatted: string;
		total: number;
		totalDiscountPercentages: string[];
		totalDiscountValue: number;
		totalDiscountValueFormatted: string;
		totalFormatted: string;
	};
	workflowStatusInfo: OrderStatusInfo;
};

interface PlanUsage {
	free: number;
	limit: number;
	used: number;
}

export type Price = {
	currency: string;
	discount: number;
	discountFormatted: string;
	discountPercentage: string;
	discountPercentageLevel1: number;
	discountPercentageLevel2: number;
	discountPercentageLevel3: number;
	discountPercentageLevel4: number;
	finalPrice: number;
	finalPriceFormatted: string;
	price: number;
	priceFormatted: string;
};

export type Product = {
	attachments: Attachment[];
	catalogId: number;
	catalogName: string;
	categories: Category[];
	createDate: string;
	customFields: CustomField[];
	description: string;
	expando: Record<string, string>;
	externalReferenceCode: string;
	id: number;
	images: Image[];
	metaDescription: string;
	metaKeyword: string;
	metaTitle: string;
	modifiedDate: string;
	name: string;
	productConfiguration: ProductConfiguration;
	productId: number;
	productSpecifications: ProductSpecification[];
	productType: string;
	shortDescription: string;
	skus: SKU[];
	slug: string;
	tags: string[];
	urlImage: string;
	urls: Record<string, string>;
};

export type ProductConfiguration = {
	allowBackOrder: boolean;
	allowedOrderQuantities: number[];
	availabilityEstimateId: number;
	inventoryEngine: string;
	maxOrderQuantity: number;
	minOrderQuantity: number;
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

export type SKU = {
	availability: Record<string, unknown>;
	backOrderAllowed: boolean;
	customFields: CustomField[];
	depth: number;
	discontinued: boolean;
	displayDate: string;
	displayDiscountLevels: boolean;
	externalReferenceCode: string;
	gtin: string;
	height: number;
	id: number;
	incomingQuantityLabel: string;
	manufacturerPartNumber: string;
	price: Price;
	productConfiguration: ProductConfiguration;
	productId: number;
	published: boolean;
	purchasable: boolean;
	sku: string;
	skuOptions: SKUOption[];
	skuUnitOfMeasures: unknown[];
	tierPrices: TierPrice[];
	weight: number;
	width: number;
};

export type SKUOption = {
	key: number;
	price: string;
	priceType: string;
	quantity: string;
	skuOptionId: number;
	skuOptionKey: string;
	skuOptionName: string;
	skuOptionValueId: number;
	skuOptionValueKey: string;
	skuOptionValueNames: string[];
	value: number;
};

export type TierPrice = {
	currency: string;
	price: number;
	priceFormatted: string;
	quantity: number;
};
