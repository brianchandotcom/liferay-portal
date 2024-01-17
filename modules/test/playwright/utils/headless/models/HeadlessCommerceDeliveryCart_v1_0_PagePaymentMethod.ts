/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCart_v1_0_Facet} from './HeadlessCommerceDeliveryCart_v1_0_Facet';
import type {HeadlessCommerceDeliveryCart_v1_0_PaymentMethod} from './HeadlessCommerceDeliveryCart_v1_0_PaymentMethod';
export type HeadlessCommerceDeliveryCart_v1_0_PagePaymentMethod = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceDeliveryCart_v1_0_PaymentMethod>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceDeliveryCart_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
