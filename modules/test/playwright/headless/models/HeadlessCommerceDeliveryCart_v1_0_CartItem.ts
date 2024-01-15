/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCart_v1_0_Price} from './HeadlessCommerceDeliveryCart_v1_0_Price';
import type {HeadlessCommerceDeliveryCart_v1_0_Settings} from './HeadlessCommerceDeliveryCart_v1_0_Settings';
import type {HeadlessCommerceDeliveryCart_v1_0_SkuUnitOfMeasure} from './HeadlessCommerceDeliveryCart_v1_0_SkuUnitOfMeasure';
export type HeadlessCommerceDeliveryCart_v1_0_CartItem = {
	readonly 'adaptiveMediaImageHTMLTag'?: string;
	'customFields'?: Record<string, Record<string, any>>;
	'errorMessages'?: Array<string>;
	readonly 'id'?: number;
	readonly 'name'?: string;
	'options'?: string;
	readonly 'parentCartItemId'?: number;
	'price'?: HeadlessCommerceDeliveryCart_v1_0_Price;
	'productId'?: number;
	readonly 'productURLs'?: Record<string, string>;
	'quantity'?: number;
	readonly 'replacedSku'?: string;
	'replacedSkuId'?: number;
	'settings'?: HeadlessCommerceDeliveryCart_v1_0_Settings;
	readonly 'sku'?: string;
	'skuId': number;
	'skuUnitOfMeasure'?: HeadlessCommerceDeliveryCart_v1_0_SkuUnitOfMeasure;
	readonly 'subscription'?: boolean;
	readonly 'thumbnail'?: string;
	readonly 'valid'?: boolean;
	readonly 'x-class-name'?: string;
};
