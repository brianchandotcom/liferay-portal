/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_Channel} from './HeadlessCommerceDeliveryCatalog_v1_0_Channel';
import type {HeadlessCommerceDeliveryCatalog_v1_0_Facet} from './HeadlessCommerceDeliveryCatalog_v1_0_Facet';
export type HeadlessCommerceDeliveryCatalog_v1_0_PageChannel = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceDeliveryCatalog_v1_0_Channel>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceDeliveryCatalog_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
