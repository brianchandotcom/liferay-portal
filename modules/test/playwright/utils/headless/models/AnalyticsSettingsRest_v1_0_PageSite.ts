/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {AnalyticsSettingsRest_v1_0_Facet} from './AnalyticsSettingsRest_v1_0_Facet';
import type {AnalyticsSettingsRest_v1_0_Site} from './AnalyticsSettingsRest_v1_0_Site';
export type AnalyticsSettingsRest_v1_0_PageSite = {
	items?: Array<AnalyticsSettingsRest_v1_0_Site>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<AnalyticsSettingsRest_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
