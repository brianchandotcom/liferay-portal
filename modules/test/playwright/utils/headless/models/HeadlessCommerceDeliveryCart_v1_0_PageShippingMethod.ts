/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCart_v1_0_Facet} from './HeadlessCommerceDeliveryCart_v1_0_Facet';
import type {HeadlessCommerceDeliveryCart_v1_0_ShippingMethod} from './HeadlessCommerceDeliveryCart_v1_0_ShippingMethod';
export type HeadlessCommerceDeliveryCart_v1_0_PageShippingMethod = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceDeliveryCart_v1_0_ShippingMethod>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceDeliveryCart_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
