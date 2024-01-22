/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {PortalSearchRest_v1_0_Facet} from './PortalSearchRest_v1_0_Facet';
import type {PortalSearchRest_v1_0_SuggestionsContributorResults} from './PortalSearchRest_v1_0_SuggestionsContributorResults';
export type PortalSearchRest_v1_0_PageSuggestionsContributorResults = {
	items?: Array<PortalSearchRest_v1_0_SuggestionsContributorResults>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<PortalSearchRest_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
