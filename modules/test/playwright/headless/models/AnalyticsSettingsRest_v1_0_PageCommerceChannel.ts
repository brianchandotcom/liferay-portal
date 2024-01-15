/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {AnalyticsSettingsRest_v1_0_CommerceChannel} from './AnalyticsSettingsRest_v1_0_CommerceChannel';
import type {AnalyticsSettingsRest_v1_0_Facet} from './AnalyticsSettingsRest_v1_0_Facet';
export type AnalyticsSettingsRest_v1_0_PageCommerceChannel = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<AnalyticsSettingsRest_v1_0_CommerceChannel>;
	pageSize?: number;
	facets?: Array<AnalyticsSettingsRest_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
