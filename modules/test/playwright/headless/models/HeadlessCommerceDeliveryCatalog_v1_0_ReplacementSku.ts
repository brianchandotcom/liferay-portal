/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_Price} from './HeadlessCommerceDeliveryCatalog_v1_0_Price';
import type {HeadlessCommerceDeliveryCatalog_v1_0_ProductConfiguration} from './HeadlessCommerceDeliveryCatalog_v1_0_ProductConfiguration';
import type {HeadlessCommerceDeliveryCatalog_v1_0_SkuOption} from './HeadlessCommerceDeliveryCatalog_v1_0_SkuOption';
import type {HeadlessCommerceDeliveryCatalog_v1_0_SkuUnitOfMeasure} from './HeadlessCommerceDeliveryCatalog_v1_0_SkuUnitOfMeasure';
export type HeadlessCommerceDeliveryCatalog_v1_0_ReplacementSku = {
	'price'?: HeadlessCommerceDeliveryCatalog_v1_0_Price;
	'productConfiguration'?: HeadlessCommerceDeliveryCatalog_v1_0_ProductConfiguration;
	readonly 'sku'?: string;
	readonly 'skuId'?: number;
	readonly 'skuOptions'?: Array<HeadlessCommerceDeliveryCatalog_v1_0_SkuOption>;
	'skuUnitOfMeasures'?: Array<HeadlessCommerceDeliveryCatalog_v1_0_SkuUnitOfMeasure>;
	readonly 'urls'?: Record<string, string>;
	readonly 'x-class-name'?: string;
};
