/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_Facet} from './HeadlessCommerceAdminPricing_v2_0_Facet';
import type {HeadlessCommerceAdminPricing_v2_0_PriceListAccountGroup} from './HeadlessCommerceAdminPricing_v2_0_PriceListAccountGroup';
export type HeadlessCommerceAdminPricing_v2_0_PagePriceListAccountGroup = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceAdminPricing_v2_0_PriceListAccountGroup>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceAdminPricing_v2_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
