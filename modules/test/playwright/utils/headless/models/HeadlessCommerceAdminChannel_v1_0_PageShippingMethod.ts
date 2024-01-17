/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminChannel_v1_0_Facet} from './HeadlessCommerceAdminChannel_v1_0_Facet';
import type {HeadlessCommerceAdminChannel_v1_0_ShippingMethod} from './HeadlessCommerceAdminChannel_v1_0_ShippingMethod';
export type HeadlessCommerceAdminChannel_v1_0_PageShippingMethod = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessCommerceAdminChannel_v1_0_ShippingMethod>;
	facets?: Array<HeadlessCommerceAdminChannel_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
