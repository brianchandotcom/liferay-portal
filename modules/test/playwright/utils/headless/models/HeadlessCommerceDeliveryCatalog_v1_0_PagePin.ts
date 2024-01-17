/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_Facet} from './HeadlessCommerceDeliveryCatalog_v1_0_Facet';
import type {HeadlessCommerceDeliveryCatalog_v1_0_Pin} from './HeadlessCommerceDeliveryCatalog_v1_0_Pin';
export type HeadlessCommerceDeliveryCatalog_v1_0_PagePin = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceDeliveryCatalog_v1_0_Pin>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceDeliveryCatalog_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
