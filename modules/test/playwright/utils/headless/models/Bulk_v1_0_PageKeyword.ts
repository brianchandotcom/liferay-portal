/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {Bulk_v1_0_Facet} from './Bulk_v1_0_Facet';
import type {Bulk_v1_0_Keyword} from './Bulk_v1_0_Keyword';
export type Bulk_v1_0_PageKeyword = {
	items?: Array<Bulk_v1_0_Keyword>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<Bulk_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
