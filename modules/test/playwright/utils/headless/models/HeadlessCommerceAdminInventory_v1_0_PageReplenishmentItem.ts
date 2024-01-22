/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminInventory_v1_0_Facet} from './HeadlessCommerceAdminInventory_v1_0_Facet';
import type {HeadlessCommerceAdminInventory_v1_0_ReplenishmentItem} from './HeadlessCommerceAdminInventory_v1_0_ReplenishmentItem';
export type HeadlessCommerceAdminInventory_v1_0_PageReplenishmentItem = {
	items?: Array<HeadlessCommerceAdminInventory_v1_0_ReplenishmentItem>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessCommerceAdminInventory_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
