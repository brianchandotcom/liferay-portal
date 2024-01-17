/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_Catalog} from './HeadlessCommerceAdminCatalog_v1_0_Catalog';
import type {HeadlessCommerceAdminCatalog_v1_0_Facet} from './HeadlessCommerceAdminCatalog_v1_0_Facet';
export type HeadlessCommerceAdminCatalog_v1_0_PageCatalog = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessCommerceAdminCatalog_v1_0_Catalog>;
	facets?: Array<HeadlessCommerceAdminCatalog_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
