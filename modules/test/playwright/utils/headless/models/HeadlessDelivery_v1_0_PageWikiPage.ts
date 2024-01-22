/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_Facet} from './HeadlessDelivery_v1_0_Facet';
import type {HeadlessDelivery_v1_0_WikiPage} from './HeadlessDelivery_v1_0_WikiPage';
export type HeadlessDelivery_v1_0_PageWikiPage = {
	items?: Array<HeadlessDelivery_v1_0_WikiPage>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessDelivery_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
