/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_Facet} from './HeadlessCommerceAdminOrder_v1_0_Facet';
import type {HeadlessCommerceAdminOrder_v1_0_Order} from './HeadlessCommerceAdminOrder_v1_0_Order';
export type HeadlessCommerceAdminOrder_v1_0_PageOrder = {
	items?: Array<HeadlessCommerceAdminOrder_v1_0_Order>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessCommerceAdminOrder_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
