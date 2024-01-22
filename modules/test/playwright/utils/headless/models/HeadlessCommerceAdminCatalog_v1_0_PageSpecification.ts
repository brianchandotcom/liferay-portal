/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_Facet} from './HeadlessCommerceAdminCatalog_v1_0_Facet';
import type {HeadlessCommerceAdminCatalog_v1_0_Specification} from './HeadlessCommerceAdminCatalog_v1_0_Specification';
export type HeadlessCommerceAdminCatalog_v1_0_PageSpecification = {
	items?: Array<HeadlessCommerceAdminCatalog_v1_0_Specification>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessCommerceAdminCatalog_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
