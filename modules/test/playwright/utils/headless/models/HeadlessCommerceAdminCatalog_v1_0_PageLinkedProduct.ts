/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_Facet} from './HeadlessCommerceAdminCatalog_v1_0_Facet';
import type {HeadlessCommerceAdminCatalog_v1_0_LinkedProduct} from './HeadlessCommerceAdminCatalog_v1_0_LinkedProduct';
export type HeadlessCommerceAdminCatalog_v1_0_PageLinkedProduct = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceAdminCatalog_v1_0_LinkedProduct>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceAdminCatalog_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
