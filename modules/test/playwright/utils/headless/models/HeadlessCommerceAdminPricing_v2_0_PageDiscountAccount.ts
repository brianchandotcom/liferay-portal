/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_DiscountAccount} from './HeadlessCommerceAdminPricing_v2_0_DiscountAccount';
import type {HeadlessCommerceAdminPricing_v2_0_Facet} from './HeadlessCommerceAdminPricing_v2_0_Facet';
export type HeadlessCommerceAdminPricing_v2_0_PageDiscountAccount = {
	items?: Array<HeadlessCommerceAdminPricing_v2_0_DiscountAccount>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessCommerceAdminPricing_v2_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
