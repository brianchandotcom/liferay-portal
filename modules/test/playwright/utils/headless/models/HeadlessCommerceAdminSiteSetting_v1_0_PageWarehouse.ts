/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminSiteSetting_v1_0_Facet} from './HeadlessCommerceAdminSiteSetting_v1_0_Facet';
import type {HeadlessCommerceAdminSiteSetting_v1_0_Warehouse} from './HeadlessCommerceAdminSiteSetting_v1_0_Warehouse';
export type HeadlessCommerceAdminSiteSetting_v1_0_PageWarehouse = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessCommerceAdminSiteSetting_v1_0_Warehouse>;
	facets?: Array<HeadlessCommerceAdminSiteSetting_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
