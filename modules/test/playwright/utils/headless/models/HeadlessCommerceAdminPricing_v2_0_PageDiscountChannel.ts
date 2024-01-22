/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_DiscountChannel} from './HeadlessCommerceAdminPricing_v2_0_DiscountChannel';
import type {HeadlessCommerceAdminPricing_v2_0_Facet} from './HeadlessCommerceAdminPricing_v2_0_Facet';
export type HeadlessCommerceAdminPricing_v2_0_PageDiscountChannel = {
	items?: Array<HeadlessCommerceAdminPricing_v2_0_DiscountChannel>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessCommerceAdminPricing_v2_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
