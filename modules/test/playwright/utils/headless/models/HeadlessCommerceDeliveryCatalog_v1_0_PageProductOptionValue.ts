/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_Facet} from './HeadlessCommerceDeliveryCatalog_v1_0_Facet';
import type {HeadlessCommerceDeliveryCatalog_v1_0_ProductOptionValue} from './HeadlessCommerceDeliveryCatalog_v1_0_ProductOptionValue';
export type HeadlessCommerceDeliveryCatalog_v1_0_PageProductOptionValue = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessCommerceDeliveryCatalog_v1_0_ProductOptionValue>;
	facets?: Array<HeadlessCommerceDeliveryCatalog_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
