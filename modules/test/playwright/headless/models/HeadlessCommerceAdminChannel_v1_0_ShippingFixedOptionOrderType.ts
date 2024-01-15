/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminChannel_v1_0_OrderType} from './HeadlessCommerceAdminChannel_v1_0_OrderType';
export type HeadlessCommerceAdminChannel_v1_0_ShippingFixedOptionOrderType = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'orderType'?: HeadlessCommerceAdminChannel_v1_0_OrderType;
	'orderTypeExternalReferenceCode'?: string;
	'orderTypeId': number;
	'priority'?: number;
	'shippingFixedOptionId': number;
	readonly 'shippingFixedOptionOrderTypeId'?: number;
	readonly 'x-class-name'?: string;
};
