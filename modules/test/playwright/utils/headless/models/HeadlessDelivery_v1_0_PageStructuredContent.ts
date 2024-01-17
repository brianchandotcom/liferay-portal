/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_Facet} from './HeadlessDelivery_v1_0_Facet';
import type {HeadlessDelivery_v1_0_StructuredContent} from './HeadlessDelivery_v1_0_StructuredContent';
export type HeadlessDelivery_v1_0_PageStructuredContent = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessDelivery_v1_0_StructuredContent>;
	pageSize?: number;
	facets?: Array<HeadlessDelivery_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
