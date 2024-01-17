/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v1_0_Facet} from './HeadlessCommerceAdminPricing_v1_0_Facet';
import type {HeadlessCommerceAdminPricing_v1_0_PriceList} from './HeadlessCommerceAdminPricing_v1_0_PriceList';
export type HeadlessCommerceAdminPricing_v1_0_PagePriceList = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessCommerceAdminPricing_v1_0_PriceList>;
	facets?: Array<HeadlessCommerceAdminPricing_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
