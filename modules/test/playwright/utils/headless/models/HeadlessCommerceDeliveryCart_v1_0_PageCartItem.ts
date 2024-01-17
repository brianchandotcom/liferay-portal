/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCart_v1_0_CartItem} from './HeadlessCommerceDeliveryCart_v1_0_CartItem';
import type {HeadlessCommerceDeliveryCart_v1_0_Facet} from './HeadlessCommerceDeliveryCart_v1_0_Facet';
export type HeadlessCommerceDeliveryCart_v1_0_PageCartItem = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceDeliveryCart_v1_0_CartItem>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceDeliveryCart_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
