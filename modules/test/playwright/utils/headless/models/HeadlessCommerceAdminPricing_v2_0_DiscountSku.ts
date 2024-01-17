/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_Sku} from './HeadlessCommerceAdminPricing_v2_0_Sku';
export type HeadlessCommerceAdminPricing_v2_0_DiscountSku = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'discountExternalReferenceCode'?: string;
	readonly 'discountId'?: number;
	readonly 'discountSkuId'?: number;
	'productId'?: number;
	'productName'?: Record<string, string>;
	'sku'?: HeadlessCommerceAdminPricing_v2_0_Sku;
	'skuExternalReferenceCode'?: string;
	'skuId': number;
	'unitOfMeasureKey'?: string;
	readonly 'x-class-name'?: string;
};
