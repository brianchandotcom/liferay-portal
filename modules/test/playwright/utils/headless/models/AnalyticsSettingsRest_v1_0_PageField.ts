/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {AnalyticsSettingsRest_v1_0_Facet} from './AnalyticsSettingsRest_v1_0_Facet';
import type {AnalyticsSettingsRest_v1_0_Field} from './AnalyticsSettingsRest_v1_0_Field';
export type AnalyticsSettingsRest_v1_0_PageField = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<AnalyticsSettingsRest_v1_0_Field>;
	facets?: Array<AnalyticsSettingsRest_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
