/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_Facet} from './HeadlessCommerceDeliveryCatalog_v1_0_Facet';
import type {HeadlessCommerceDeliveryCatalog_v1_0_LinkedProduct} from './HeadlessCommerceDeliveryCatalog_v1_0_LinkedProduct';
export type HeadlessCommerceDeliveryCatalog_v1_0_PageLinkedProduct = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessCommerceDeliveryCatalog_v1_0_LinkedProduct>;
	facets?: Array<HeadlessCommerceDeliveryCatalog_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
