/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_Facet} from './HeadlessCommerceAdminOrder_v1_0_Facet';
import type {HeadlessCommerceAdminOrder_v1_0_OrderNote} from './HeadlessCommerceAdminOrder_v1_0_OrderNote';
export type HeadlessCommerceAdminOrder_v1_0_PageOrderNote = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessCommerceAdminOrder_v1_0_OrderNote>;
	facets?: Array<HeadlessCommerceAdminOrder_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
