/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v1_0_DiscountProduct} from './HeadlessCommerceAdminPricing_v1_0_DiscountProduct';
import type {HeadlessCommerceAdminPricing_v1_0_Facet} from './HeadlessCommerceAdminPricing_v1_0_Facet';
export type HeadlessCommerceAdminPricing_v1_0_PageDiscountProduct = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceAdminPricing_v1_0_DiscountProduct>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceAdminPricing_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
