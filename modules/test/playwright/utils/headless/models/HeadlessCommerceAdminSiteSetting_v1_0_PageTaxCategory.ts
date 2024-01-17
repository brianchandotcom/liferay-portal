/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminSiteSetting_v1_0_Facet} from './HeadlessCommerceAdminSiteSetting_v1_0_Facet';
import type {HeadlessCommerceAdminSiteSetting_v1_0_TaxCategory} from './HeadlessCommerceAdminSiteSetting_v1_0_TaxCategory';
export type HeadlessCommerceAdminSiteSetting_v1_0_PageTaxCategory = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceAdminSiteSetting_v1_0_TaxCategory>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceAdminSiteSetting_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
