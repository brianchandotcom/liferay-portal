/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v1_0_DiscountRule} from './HeadlessCommerceAdminPricing_v1_0_DiscountRule';
import type {HeadlessCommerceAdminPricing_v1_0_Facet} from './HeadlessCommerceAdminPricing_v1_0_Facet';
export type HeadlessCommerceAdminPricing_v1_0_PageDiscountRule = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceAdminPricing_v1_0_DiscountRule>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceAdminPricing_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
