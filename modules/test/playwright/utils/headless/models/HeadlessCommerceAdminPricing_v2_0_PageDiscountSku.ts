/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_DiscountSku} from './HeadlessCommerceAdminPricing_v2_0_DiscountSku';
import type {HeadlessCommerceAdminPricing_v2_0_Facet} from './HeadlessCommerceAdminPricing_v2_0_Facet';
export type HeadlessCommerceAdminPricing_v2_0_PageDiscountSku = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceAdminPricing_v2_0_DiscountSku>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceAdminPricing_v2_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
