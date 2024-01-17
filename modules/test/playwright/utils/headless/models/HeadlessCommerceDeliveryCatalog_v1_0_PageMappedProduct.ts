/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_Facet} from './HeadlessCommerceDeliveryCatalog_v1_0_Facet';
import type {HeadlessCommerceDeliveryCatalog_v1_0_MappedProduct} from './HeadlessCommerceDeliveryCatalog_v1_0_MappedProduct';
export type HeadlessCommerceDeliveryCatalog_v1_0_PageMappedProduct = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceDeliveryCatalog_v1_0_MappedProduct>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceDeliveryCatalog_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
