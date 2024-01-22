/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_Facet} from './HeadlessCommerceDeliveryCatalog_v1_0_Facet';
import type {HeadlessCommerceDeliveryCatalog_v1_0_Product} from './HeadlessCommerceDeliveryCatalog_v1_0_Product';
export type HeadlessCommerceDeliveryCatalog_v1_0_PageProduct = {
	items?: Array<HeadlessCommerceDeliveryCatalog_v1_0_Product>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessCommerceDeliveryCatalog_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
