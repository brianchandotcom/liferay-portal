/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_Status} from './HeadlessCommerceAdminOrder_v1_0_Status';
import type {HeadlessCommerceAdminOrder_v1_0_TermOrderType} from './HeadlessCommerceAdminOrder_v1_0_TermOrderType';
export type HeadlessCommerceAdminOrder_v1_0_Term = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'active'?: boolean;
	'createDate'?: string;
	'description'?: Record<string, string>;
	'displayDate'?: string;
	'expirationDate'?: string;
	'externalReferenceCode'?: string;
	'id'?: number;
	'label'?: Record<string, string>;
	'name': string;
	'neverExpire'?: boolean;
	'priority'?: number;
	'termOrderType'?: Array<HeadlessCommerceAdminOrder_v1_0_TermOrderType>;
	'type': string;
	'typeLocalized'?: string;
	'typeSettings'?: string;
	'workflowStatusInfo'?: HeadlessCommerceAdminOrder_v1_0_Status;
	readonly 'x-class-name'?: string;
};
