/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminSiteSetting_v1_0_AvailabilityEstimate} from './HeadlessCommerceAdminSiteSetting_v1_0_AvailabilityEstimate';
import type {HeadlessCommerceAdminSiteSetting_v1_0_Facet} from './HeadlessCommerceAdminSiteSetting_v1_0_Facet';
export type HeadlessCommerceAdminSiteSetting_v1_0_PageAvailabilityEstimate = {
	items?: Array<HeadlessCommerceAdminSiteSetting_v1_0_AvailabilityEstimate>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessCommerceAdminSiteSetting_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
