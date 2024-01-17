/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {PortalSearchRest_v1_0_Facet} from './PortalSearchRest_v1_0_Facet';
import type {PortalSearchRest_v1_0_SearchResult} from './PortalSearchRest_v1_0_SearchResult';
export type PortalSearchRest_v1_0_PageSearchResult = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<PortalSearchRest_v1_0_SearchResult>;
	pageSize?: number;
	facets?: Array<PortalSearchRest_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
