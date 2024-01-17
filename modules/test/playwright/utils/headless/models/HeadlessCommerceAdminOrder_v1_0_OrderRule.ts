/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_OrderRuleAccount} from './HeadlessCommerceAdminOrder_v1_0_OrderRuleAccount';
import type {HeadlessCommerceAdminOrder_v1_0_OrderRuleAccountGroup} from './HeadlessCommerceAdminOrder_v1_0_OrderRuleAccountGroup';
import type {HeadlessCommerceAdminOrder_v1_0_OrderRuleChannel} from './HeadlessCommerceAdminOrder_v1_0_OrderRuleChannel';
import type {HeadlessCommerceAdminOrder_v1_0_OrderRuleOrderType} from './HeadlessCommerceAdminOrder_v1_0_OrderRuleOrderType';
import type {HeadlessCommerceAdminOrder_v1_0_Status} from './HeadlessCommerceAdminOrder_v1_0_Status';
export type HeadlessCommerceAdminOrder_v1_0_OrderRule = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'active'?: boolean;
	'author'?: string;
	'createDate'?: string;
	'description'?: string;
	'displayDate'?: string;
	'expirationDate'?: string;
	'externalReferenceCode'?: string;
	'id'?: number;
	'name': string;
	'neverExpire'?: boolean;
	'orderRuleAccount'?: Array<HeadlessCommerceAdminOrder_v1_0_OrderRuleAccount>;
	'orderRuleAccountGroup'?: Array<HeadlessCommerceAdminOrder_v1_0_OrderRuleAccountGroup>;
	'orderRuleChannel'?: Array<HeadlessCommerceAdminOrder_v1_0_OrderRuleChannel>;
	'orderRuleOrderType'?: Array<HeadlessCommerceAdminOrder_v1_0_OrderRuleOrderType>;
	'priority'?: number;
	'type': string;
	'typeSettings'?: string;
	'workflowStatusInfo'?: HeadlessCommerceAdminOrder_v1_0_Status;
	readonly 'x-class-name'?: string;
};
