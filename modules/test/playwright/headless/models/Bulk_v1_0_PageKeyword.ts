/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {Bulk_v1_0_Facet} from './Bulk_v1_0_Facet';
import type {Bulk_v1_0_Keyword} from './Bulk_v1_0_Keyword';
export type Bulk_v1_0_PageKeyword = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<Bulk_v1_0_Keyword>;
	pageSize?: number;
	facets?: Array<Bulk_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
