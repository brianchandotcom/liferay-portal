/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_Product} from './HeadlessCommerceAdminPricing_v2_0_Product';
import type {HeadlessCommerceAdminPricing_v2_0_Sku} from './HeadlessCommerceAdminPricing_v2_0_Sku';
import type {HeadlessCommerceAdminPricing_v2_0_TierPrice} from './HeadlessCommerceAdminPricing_v2_0_TierPrice';
export type HeadlessCommerceAdminPricing_v2_0_PriceEntry = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'active'?: boolean;
	'bulkPricing'?: boolean;
	'customFields'?: Record<string, Record<string, any>>;
	'discountDiscovery'?: boolean;
	'discountLevel1'?: number;
	'discountLevel2'?: number;
	'discountLevel3'?: number;
	'discountLevel4'?: number;
	'discountLevelsFormatted'?: string;
	'displayDate'?: string;
	'expirationDate'?: string;
	'externalReferenceCode'?: string;
	'hasTierPrice'?: boolean;
	'neverExpire'?: boolean;
	'price': number;
	'priceEntryId'?: number;
	'priceFormatted'?: string;
	'priceListExternalReferenceCode'?: string;
	'priceListId': number;
	'priceOnApplication'?: boolean;
	'product'?: HeadlessCommerceAdminPricing_v2_0_Product;
	'quantity'?: number;
	'sku'?: HeadlessCommerceAdminPricing_v2_0_Sku;
	'skuExternalReferenceCode'?: string;
	'skuId': number;
	'tierPrices'?: Array<HeadlessCommerceAdminPricing_v2_0_TierPrice>;
	'unitOfMeasureKey'?: string;
	readonly 'x-class-name'?: string;
};
