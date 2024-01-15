/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_OrderType} from './HeadlessCommerceAdminOrder_v1_0_OrderType';
export type HeadlessCommerceAdminOrder_v1_0_TermOrderType = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'orderType'?: HeadlessCommerceAdminOrder_v1_0_OrderType;
	'orderTypeExternalReferenceCode'?: string;
	'orderTypeId': number;
	'termExternalReferenceCode'?: string;
	'termId': number;
	readonly 'termOrderTypeId'?: number;
	readonly 'x-class-name'?: string;
};
