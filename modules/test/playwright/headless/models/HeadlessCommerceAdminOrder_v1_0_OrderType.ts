/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_OrderTypeChannel} from './HeadlessCommerceAdminOrder_v1_0_OrderTypeChannel';
import type {HeadlessCommerceAdminOrder_v1_0_Status} from './HeadlessCommerceAdminOrder_v1_0_Status';
export type HeadlessCommerceAdminOrder_v1_0_OrderType = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'active'?: boolean;
	'customFields'?: Record<string, Record<string, any>>;
	'description'?: Record<string, string>;
	'displayDate'?: string;
	'displayOrder'?: number;
	'expirationDate'?: string;
	'externalReferenceCode'?: string;
	'id'?: number;
	'name': Record<string, string>;
	'neverExpire'?: boolean;
	'orderTypeChannels'?: Array<HeadlessCommerceAdminOrder_v1_0_OrderTypeChannel>;
	'workflowStatusInfo'?: HeadlessCommerceAdminOrder_v1_0_Status;
	readonly 'x-class-name'?: string;
};
