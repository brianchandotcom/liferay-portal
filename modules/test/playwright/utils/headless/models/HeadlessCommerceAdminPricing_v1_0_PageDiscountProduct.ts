/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v1_0_DiscountProduct} from './HeadlessCommerceAdminPricing_v1_0_DiscountProduct';
import type {HeadlessCommerceAdminPricing_v1_0_Facet} from './HeadlessCommerceAdminPricing_v1_0_Facet';
export type HeadlessCommerceAdminPricing_v1_0_PageDiscountProduct = {
	items?: Array<HeadlessCommerceAdminPricing_v1_0_DiscountProduct>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessCommerceAdminPricing_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
