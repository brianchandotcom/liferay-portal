/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_Facet} from './HeadlessCommerceAdminPricing_v2_0_Facet';
import type {HeadlessCommerceAdminPricing_v2_0_PriceListDiscount} from './HeadlessCommerceAdminPricing_v2_0_PriceListDiscount';
export type HeadlessCommerceAdminPricing_v2_0_PagePriceListDiscount = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceAdminPricing_v2_0_PriceListDiscount>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceAdminPricing_v2_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
