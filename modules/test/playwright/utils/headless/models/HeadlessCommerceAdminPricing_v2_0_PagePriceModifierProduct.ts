/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_Facet} from './HeadlessCommerceAdminPricing_v2_0_Facet';
import type {HeadlessCommerceAdminPricing_v2_0_PriceModifierProduct} from './HeadlessCommerceAdminPricing_v2_0_PriceModifierProduct';
export type HeadlessCommerceAdminPricing_v2_0_PagePriceModifierProduct = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceAdminPricing_v2_0_PriceModifierProduct>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceAdminPricing_v2_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
