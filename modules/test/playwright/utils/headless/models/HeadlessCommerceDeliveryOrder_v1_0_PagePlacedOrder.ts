/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryOrder_v1_0_Facet} from './HeadlessCommerceDeliveryOrder_v1_0_Facet';
import type {HeadlessCommerceDeliveryOrder_v1_0_PlacedOrder} from './HeadlessCommerceDeliveryOrder_v1_0_PlacedOrder';
export type HeadlessCommerceDeliveryOrder_v1_0_PagePlacedOrder = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessCommerceDeliveryOrder_v1_0_PlacedOrder>;
	facets?: Array<HeadlessCommerceDeliveryOrder_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
