/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_OrderType} from './HeadlessCommerceAdminOrder_v1_0_OrderType';
export type HeadlessCommerceAdminOrder_v1_0_OrderRuleOrderType = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'orderRuleExternalReferenceCode'?: string;
	'orderRuleId': number;
	readonly 'orderRuleOrderTypeId'?: number;
	'orderType'?: HeadlessCommerceAdminOrder_v1_0_OrderType;
	'orderTypeExternalReferenceCode'?: string;
	'orderTypeId': number;
	readonly 'x-class-name'?: string;
};
