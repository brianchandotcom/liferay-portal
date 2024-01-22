/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_Facet} from './HeadlessCommerceAdminPricing_v2_0_Facet';
import type {HeadlessCommerceAdminPricing_v2_0_PriceListAccount} from './HeadlessCommerceAdminPricing_v2_0_PriceListAccount';
export type HeadlessCommerceAdminPricing_v2_0_PagePriceListAccount = {
	items?: Array<HeadlessCommerceAdminPricing_v2_0_PriceListAccount>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessCommerceAdminPricing_v2_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
