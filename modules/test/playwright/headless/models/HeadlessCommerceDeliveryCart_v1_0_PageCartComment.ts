/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCart_v1_0_CartComment} from './HeadlessCommerceDeliveryCart_v1_0_CartComment';
import type {HeadlessCommerceDeliveryCart_v1_0_Facet} from './HeadlessCommerceDeliveryCart_v1_0_Facet';
export type HeadlessCommerceDeliveryCart_v1_0_PageCartComment = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceDeliveryCart_v1_0_CartComment>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceDeliveryCart_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
