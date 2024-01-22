/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_Document} from './HeadlessDelivery_v1_0_Document';
import type {HeadlessDelivery_v1_0_Facet} from './HeadlessDelivery_v1_0_Facet';
export type HeadlessDelivery_v1_0_PageDocument = {
	items?: Array<HeadlessDelivery_v1_0_Document>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessDelivery_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
