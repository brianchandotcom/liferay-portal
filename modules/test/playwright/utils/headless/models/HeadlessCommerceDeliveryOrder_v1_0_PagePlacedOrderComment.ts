/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryOrder_v1_0_Facet} from './HeadlessCommerceDeliveryOrder_v1_0_Facet';
import type {HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderComment} from './HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderComment';
export type HeadlessCommerceDeliveryOrder_v1_0_PagePlacedOrderComment = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderComment>;
	facets?: Array<HeadlessCommerceDeliveryOrder_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
