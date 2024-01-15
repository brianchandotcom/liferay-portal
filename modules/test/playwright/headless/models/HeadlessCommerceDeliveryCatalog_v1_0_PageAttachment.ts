/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_Attachment} from './HeadlessCommerceDeliveryCatalog_v1_0_Attachment';
import type {HeadlessCommerceDeliveryCatalog_v1_0_Facet} from './HeadlessCommerceDeliveryCatalog_v1_0_Facet';
export type HeadlessCommerceDeliveryCatalog_v1_0_PageAttachment = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceDeliveryCatalog_v1_0_Attachment>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceDeliveryCatalog_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
