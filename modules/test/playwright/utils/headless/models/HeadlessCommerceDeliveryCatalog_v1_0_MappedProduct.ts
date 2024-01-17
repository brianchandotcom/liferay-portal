/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_Availability} from './HeadlessCommerceDeliveryCatalog_v1_0_Availability';
import type {HeadlessCommerceDeliveryCatalog_v1_0_Price} from './HeadlessCommerceDeliveryCatalog_v1_0_Price';
import type {HeadlessCommerceDeliveryCatalog_v1_0_ProductConfiguration} from './HeadlessCommerceDeliveryCatalog_v1_0_ProductConfiguration';
import type {HeadlessCommerceDeliveryCatalog_v1_0_ProductOption} from './HeadlessCommerceDeliveryCatalog_v1_0_ProductOption';
import type {HeadlessCommerceDeliveryCatalog_v1_0_SkuOption} from './HeadlessCommerceDeliveryCatalog_v1_0_SkuOption';
export type HeadlessCommerceDeliveryCatalog_v1_0_MappedProduct = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'availability'?: HeadlessCommerceDeliveryCatalog_v1_0_Availability;
	'firstAvailableReplacementMappedProduct'?: HeadlessCommerceDeliveryCatalog_v1_0_MappedProduct;
	'id'?: number;
	'price'?: HeadlessCommerceDeliveryCatalog_v1_0_Price;
	'productConfiguration'?: HeadlessCommerceDeliveryCatalog_v1_0_ProductConfiguration;
	'productExternalReferenceCode'?: string;
	'productId'?: number;
	readonly 'productName'?: Record<string, string>;
	'productOptions'?: Array<HeadlessCommerceDeliveryCatalog_v1_0_ProductOption>;
	readonly 'purchasable'?: boolean;
	'quantity'?: number;
	'replacementMappedProduct'?: HeadlessCommerceDeliveryCatalog_v1_0_MappedProduct;
	readonly 'replacementMessage'?: string;
	'sequence'?: string;
	'sku'?: string;
	'skuExternalReferenceCode'?: string;
	'skuId'?: number;
	'skuOptions'?: Array<HeadlessCommerceDeliveryCatalog_v1_0_SkuOption>;
	readonly 'thumbnail'?: string;
	'type'?: HeadlessCommerceDeliveryCatalog_v1_0_MappedProduct.type;
	'urls'?: Record<string, string>;
	readonly 'x-class-name'?: string;
};
export namespace HeadlessCommerceDeliveryCatalog_v1_0_MappedProduct {
	export enum type {
		DIAGRAM = 'diagram',
		EXTERNAL = 'external',
		SKU = 'sku',
	}
}
