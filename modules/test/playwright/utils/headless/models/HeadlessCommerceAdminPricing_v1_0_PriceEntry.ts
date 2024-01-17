/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v1_0_TierPrice} from './HeadlessCommerceAdminPricing_v1_0_TierPrice';
export type HeadlessCommerceAdminPricing_v1_0_PriceEntry = {
	'customFields'?: Record<string, Record<string, any>>;
	'externalReferenceCode'?: string;
	'hasTierPrice'?: boolean;
	'id'?: number;
	'price': number;
	'priceListExternalReferenceCode'?: string;
	'priceListId'?: number;
	'promoPrice'?: number;
	'sku'?: string;
	'skuExternalReferenceCode'?: string;
	'skuId'?: number;
	'tierPrices'?: Array<HeadlessCommerceAdminPricing_v1_0_TierPrice>;
	readonly 'x-class-name'?: string;
};
