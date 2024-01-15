/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryOrder_v1_0_Facet} from './HeadlessCommerceDeliveryOrder_v1_0_Facet';
import type {HeadlessCommerceDeliveryOrder_v1_0_PlacedOrder} from './HeadlessCommerceDeliveryOrder_v1_0_PlacedOrder';
export type HeadlessCommerceDeliveryOrder_v1_0_PagePlacedOrder = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceDeliveryOrder_v1_0_PlacedOrder>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceDeliveryOrder_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
