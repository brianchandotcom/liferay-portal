/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryOrder_v1_0_Facet} from './HeadlessCommerceDeliveryOrder_v1_0_Facet';
import type {HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderItem} from './HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderItem';
export type HeadlessCommerceDeliveryOrder_v1_0_PagePlacedOrderItem = {
	items?: Array<HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderItem>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessCommerceDeliveryOrder_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
