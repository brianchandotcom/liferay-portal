/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_Category} from './HeadlessCommerceDeliveryCatalog_v1_0_Category';
import type {HeadlessCommerceDeliveryCatalog_v1_0_Facet} from './HeadlessCommerceDeliveryCatalog_v1_0_Facet';
export type HeadlessCommerceDeliveryCatalog_v1_0_PageCategory = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessCommerceDeliveryCatalog_v1_0_Category>;
	facets?: Array<HeadlessCommerceDeliveryCatalog_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
