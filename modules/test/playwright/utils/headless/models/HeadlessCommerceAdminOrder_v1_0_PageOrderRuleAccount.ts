/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_Facet} from './HeadlessCommerceAdminOrder_v1_0_Facet';
import type {HeadlessCommerceAdminOrder_v1_0_OrderRuleAccount} from './HeadlessCommerceAdminOrder_v1_0_OrderRuleAccount';
export type HeadlessCommerceAdminOrder_v1_0_PageOrderRuleAccount = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceAdminOrder_v1_0_OrderRuleAccount>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceAdminOrder_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
