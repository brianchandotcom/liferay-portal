/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminInventory_v1_0_Facet} from './HeadlessCommerceAdminInventory_v1_0_Facet';
import type {HeadlessCommerceAdminInventory_v1_0_Warehouse} from './HeadlessCommerceAdminInventory_v1_0_Warehouse';
export type HeadlessCommerceAdminInventory_v1_0_PageWarehouse = {
	items?: Array<HeadlessCommerceAdminInventory_v1_0_Warehouse>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessCommerceAdminInventory_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
