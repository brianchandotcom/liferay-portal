/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {AnalyticsSettingsRest_v1_0_ContactAccountGroup} from './AnalyticsSettingsRest_v1_0_ContactAccountGroup';
import type {AnalyticsSettingsRest_v1_0_Facet} from './AnalyticsSettingsRest_v1_0_Facet';
export type AnalyticsSettingsRest_v1_0_PageContactAccountGroup = {
	items?: Array<AnalyticsSettingsRest_v1_0_ContactAccountGroup>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<AnalyticsSettingsRest_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
