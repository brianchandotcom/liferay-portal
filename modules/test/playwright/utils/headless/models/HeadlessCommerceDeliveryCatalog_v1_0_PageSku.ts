/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_Facet} from './HeadlessCommerceDeliveryCatalog_v1_0_Facet';
import type {HeadlessCommerceDeliveryCatalog_v1_0_Sku} from './HeadlessCommerceDeliveryCatalog_v1_0_Sku';
export type HeadlessCommerceDeliveryCatalog_v1_0_PageSku = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessCommerceDeliveryCatalog_v1_0_Sku>;
	facets?: Array<HeadlessCommerceDeliveryCatalog_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
