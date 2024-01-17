/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminSiteSetting_v1_0_Facet} from './HeadlessCommerceAdminSiteSetting_v1_0_Facet';
import type {HeadlessCommerceAdminSiteSetting_v1_0_TaxCategory} from './HeadlessCommerceAdminSiteSetting_v1_0_TaxCategory';
export type HeadlessCommerceAdminSiteSetting_v1_0_PageTaxCategory = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessCommerceAdminSiteSetting_v1_0_TaxCategory>;
	facets?: Array<HeadlessCommerceAdminSiteSetting_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
