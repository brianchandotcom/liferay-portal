/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_Facet} from './HeadlessCommerceAdminCatalog_v1_0_Facet';
import type {HeadlessCommerceAdminCatalog_v1_0_ProductSpecification} from './HeadlessCommerceAdminCatalog_v1_0_ProductSpecification';
export type HeadlessCommerceAdminCatalog_v1_0_PageProductSpecification = {
	items?: Array<HeadlessCommerceAdminCatalog_v1_0_ProductSpecification>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessCommerceAdminCatalog_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
