/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_Facet} from './HeadlessCommerceAdminOrder_v1_0_Facet';
import type {HeadlessCommerceAdminOrder_v1_0_OrderRuleChannel} from './HeadlessCommerceAdminOrder_v1_0_OrderRuleChannel';
export type HeadlessCommerceAdminOrder_v1_0_PageOrderRuleChannel = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceAdminOrder_v1_0_OrderRuleChannel>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceAdminOrder_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
