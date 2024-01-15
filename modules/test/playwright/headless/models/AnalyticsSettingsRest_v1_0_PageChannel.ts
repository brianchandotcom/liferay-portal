/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {AnalyticsSettingsRest_v1_0_Channel} from './AnalyticsSettingsRest_v1_0_Channel';
import type {AnalyticsSettingsRest_v1_0_Facet} from './AnalyticsSettingsRest_v1_0_Facet';
export type AnalyticsSettingsRest_v1_0_PageChannel = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<AnalyticsSettingsRest_v1_0_Channel>;
	pageSize?: number;
	facets?: Array<AnalyticsSettingsRest_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
