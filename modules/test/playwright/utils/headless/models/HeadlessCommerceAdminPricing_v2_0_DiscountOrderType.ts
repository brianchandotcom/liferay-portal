/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_OrderType} from './HeadlessCommerceAdminPricing_v2_0_OrderType';
export type HeadlessCommerceAdminPricing_v2_0_DiscountOrderType = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'discountExternalReferenceCode'?: string;
	readonly 'discountId'?: number;
	readonly 'discountOrderTypeId'?: number;
	'orderType'?: HeadlessCommerceAdminPricing_v2_0_OrderType;
	'orderTypeExternalReferenceCode'?: string;
	'orderTypeId': number;
	'priority'?: number;
	readonly 'x-class-name'?: string;
};
