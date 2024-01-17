/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminChannel_v1_0_Facet} from './HeadlessCommerceAdminChannel_v1_0_Facet';
import type {HeadlessCommerceAdminChannel_v1_0_TaxCategory} from './HeadlessCommerceAdminChannel_v1_0_TaxCategory';
export type HeadlessCommerceAdminChannel_v1_0_PageTaxCategory = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessCommerceAdminChannel_v1_0_TaxCategory>;
	facets?: Array<HeadlessCommerceAdminChannel_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
