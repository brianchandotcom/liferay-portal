/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_Facet} from './HeadlessCommerceAdminCatalog_v1_0_Facet';
import type {HeadlessCommerceAdminCatalog_v1_0_GroupedProduct} from './HeadlessCommerceAdminCatalog_v1_0_GroupedProduct';
export type HeadlessCommerceAdminCatalog_v1_0_PageGroupedProduct = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessCommerceAdminCatalog_v1_0_GroupedProduct>;
	facets?: Array<HeadlessCommerceAdminCatalog_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
