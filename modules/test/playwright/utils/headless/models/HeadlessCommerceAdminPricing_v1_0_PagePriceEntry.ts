/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v1_0_Facet} from './HeadlessCommerceAdminPricing_v1_0_Facet';
import type {HeadlessCommerceAdminPricing_v1_0_PriceEntry} from './HeadlessCommerceAdminPricing_v1_0_PriceEntry';
export type HeadlessCommerceAdminPricing_v1_0_PagePriceEntry = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceAdminPricing_v1_0_PriceEntry>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceAdminPricing_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
