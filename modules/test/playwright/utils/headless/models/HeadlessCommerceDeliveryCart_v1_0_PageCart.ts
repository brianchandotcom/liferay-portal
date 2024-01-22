/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCart_v1_0_Cart} from './HeadlessCommerceDeliveryCart_v1_0_Cart';
import type {HeadlessCommerceDeliveryCart_v1_0_Facet} from './HeadlessCommerceDeliveryCart_v1_0_Facet';
export type HeadlessCommerceDeliveryCart_v1_0_PageCart = {
	items?: Array<HeadlessCommerceDeliveryCart_v1_0_Cart>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessCommerceDeliveryCart_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
