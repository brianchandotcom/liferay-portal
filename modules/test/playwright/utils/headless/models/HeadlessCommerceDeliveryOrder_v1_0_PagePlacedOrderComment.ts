/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryOrder_v1_0_Facet} from './HeadlessCommerceDeliveryOrder_v1_0_Facet';
import type {HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderComment} from './HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderComment';
export type HeadlessCommerceDeliveryOrder_v1_0_PagePlacedOrderComment = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderComment>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceDeliveryOrder_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
