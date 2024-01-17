/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {PortalSearchRest_v1_0_Facet} from './PortalSearchRest_v1_0_Facet';
import type {PortalSearchRest_v1_0_SearchResult} from './PortalSearchRest_v1_0_SearchResult';
export type PortalSearchRest_v1_0_PageSearchResult = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<PortalSearchRest_v1_0_SearchResult>;
	facets?: Array<PortalSearchRest_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
