/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_Facet} from './HeadlessCommerceAdminOrder_v1_0_Facet';
import type {HeadlessCommerceAdminOrder_v1_0_OrderTypeChannel} from './HeadlessCommerceAdminOrder_v1_0_OrderTypeChannel';
export type HeadlessCommerceAdminOrder_v1_0_PageOrderTypeChannel = {
	items?: Array<HeadlessCommerceAdminOrder_v1_0_OrderTypeChannel>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessCommerceAdminOrder_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
