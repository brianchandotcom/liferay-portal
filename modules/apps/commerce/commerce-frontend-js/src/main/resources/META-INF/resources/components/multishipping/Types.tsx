/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export interface IAPIResponseError {
	detail?: string;
	errorDescription?: string;
}

export interface ICountry {
	a2: string;
	a3: string;
	active: boolean;
	id: number;
	name: string;
	regions: Array<IRegion>;
	title_i18n: {
		[key: string]: string;
	};
}

export interface ICountryAPIResponse {
	items: Array<ICountry>;
}

export interface IDeliveryGroup {
	addressId: number;
	deliveryDate: string;
	id: number;
	name: string;
}

export interface IFieldError {
	[key: string]: string;
}

export interface IOrderItemDeliveryGroup {
	options: string;
	orderItemId: number;
	originalQuantity: number;
	quantity: number;
	replacedSkuId: number;
	skuId: number;
	skuUnitOfMeasure: any;
}

export interface IOrderItem {
	cartItems?: Array<any>;
	deliveryGroupName?: string;
	deliveryGroups?: {[key: string]: IOrderItemDeliveryGroup};
	externalReferenceCode?: string;
	id: number;
	name?: string;
	options: string;
	price?: {
		currency: string;
		discount: number;
		discountFormatted: string;
		finalPrice: number;
		finalPriceFormatted: string;
		price: number;
		priceFormatted: string;
		priceOnApplication: boolean;
	};
	productId?: number;
	quantity: number;
	replacedSkuId: number;
	requestedDeliveryDate: string;
	settings?: {
		allowedQuantities?: Array<number>;
		maxQuantity: number;
		minQuantity: number;
		multipleQuantity: number;
	};
	shippingAddressId: number;
	sku?: string;
	skuId: number;
	skuUnitOfMeasure: {
		incrementalOrderQuantity: number;
		key: string;
		precision: number;
	};
	thumbnail?: string;
}

export interface IOrderItemAPIResponse {
	cartItems: Array<IOrderItem>;
	items: Array<IOrderItem>;
}

export interface IPostalAddress {
	addressCountry?: string;
	addressLocality?: string;
	addressRegion?: string;
	addressType?: string;
	externalReferenceCode?: string;
	id: number;
	name?: string;
	phoneNumber?: string;
	postalCode?: string;
	primary?: boolean;
	streetAddressLine1?: string;
	streetAddressLine2?: string;
	streetAddressLine3?: string;
	[key: string]: boolean | number | string | undefined;
}

export interface IPostalAddressAPIResponse {
	items: Array<IPostalAddress>;
}

export interface IRegion {
	active: boolean;
	countryId: number;
	id: number;
	label?: string;
	name: string;
	regionCode: string;
	title_i18n: {
		[key: string]: string;
	};
}
