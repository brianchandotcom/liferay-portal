/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_Facet} from './HeadlessCommerceAdminCatalog_v1_0_Facet';
import type {HeadlessCommerceAdminCatalog_v1_0_SkuUnitOfMeasure} from './HeadlessCommerceAdminCatalog_v1_0_SkuUnitOfMeasure';
export type HeadlessCommerceAdminCatalog_v1_0_PageSkuUnitOfMeasure = {
	items?: Array<HeadlessCommerceAdminCatalog_v1_0_SkuUnitOfMeasure>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessCommerceAdminCatalog_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
