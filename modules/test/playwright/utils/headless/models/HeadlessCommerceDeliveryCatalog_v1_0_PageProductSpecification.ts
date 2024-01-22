/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_Facet} from './HeadlessCommerceDeliveryCatalog_v1_0_Facet';
import type {HeadlessCommerceDeliveryCatalog_v1_0_ProductSpecification} from './HeadlessCommerceDeliveryCatalog_v1_0_ProductSpecification';
export type HeadlessCommerceDeliveryCatalog_v1_0_PageProductSpecification = {
	items?: Array<HeadlessCommerceDeliveryCatalog_v1_0_ProductSpecification>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessCommerceDeliveryCatalog_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
