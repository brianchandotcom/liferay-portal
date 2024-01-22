/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {AnalyticsSettingsRest_v1_0_ContactUserGroup} from './AnalyticsSettingsRest_v1_0_ContactUserGroup';
import type {AnalyticsSettingsRest_v1_0_Facet} from './AnalyticsSettingsRest_v1_0_Facet';
export type AnalyticsSettingsRest_v1_0_PageContactUserGroup = {
	items?: Array<AnalyticsSettingsRest_v1_0_ContactUserGroup>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<AnalyticsSettingsRest_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
