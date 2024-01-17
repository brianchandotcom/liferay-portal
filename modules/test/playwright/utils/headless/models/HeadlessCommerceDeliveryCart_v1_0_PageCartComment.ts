/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCart_v1_0_CartComment} from './HeadlessCommerceDeliveryCart_v1_0_CartComment';
import type {HeadlessCommerceDeliveryCart_v1_0_Facet} from './HeadlessCommerceDeliveryCart_v1_0_Facet';
export type HeadlessCommerceDeliveryCart_v1_0_PageCartComment = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessCommerceDeliveryCart_v1_0_CartComment>;
	facets?: Array<HeadlessCommerceDeliveryCart_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
