/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_Price} from './HeadlessCommerceDeliveryCatalog_v1_0_Price';
import type {HeadlessCommerceDeliveryCatalog_v1_0_TierPrice} from './HeadlessCommerceDeliveryCatalog_v1_0_TierPrice';
export type HeadlessCommerceDeliveryCatalog_v1_0_SkuUnitOfMeasure = {
	'incrementalOrderQuantity'?: number;
	'key'?: string;
	'name'?: string;
	'precision'?: number;
	'price'?: HeadlessCommerceDeliveryCatalog_v1_0_Price;
	'primary'?: boolean;
	'priority'?: number;
	'rate'?: number;
	'tierPrices'?: Array<HeadlessCommerceDeliveryCatalog_v1_0_TierPrice>;
	readonly 'x-class-name'?: string;
};
