/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_Facet} from './HeadlessCommerceAdminCatalog_v1_0_Facet';
import type {HeadlessCommerceAdminCatalog_v1_0_RelatedProduct} from './HeadlessCommerceAdminCatalog_v1_0_RelatedProduct';
export type HeadlessCommerceAdminCatalog_v1_0_PageRelatedProduct = {
	items?: Array<HeadlessCommerceAdminCatalog_v1_0_RelatedProduct>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessCommerceAdminCatalog_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
