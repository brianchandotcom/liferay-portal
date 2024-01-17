/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v1_0_Facet} from './HeadlessCommerceAdminPricing_v1_0_Facet';
import type {HeadlessCommerceAdminPricing_v1_0_PriceListAccountGroup} from './HeadlessCommerceAdminPricing_v1_0_PriceListAccountGroup';
export type HeadlessCommerceAdminPricing_v1_0_PagePriceListAccountGroup = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessCommerceAdminPricing_v1_0_PriceListAccountGroup>;
	facets?: Array<HeadlessCommerceAdminPricing_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
