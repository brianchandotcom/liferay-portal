/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminSiteSetting_v1_0_AvailabilityEstimate} from './HeadlessCommerceAdminSiteSetting_v1_0_AvailabilityEstimate';
import type {HeadlessCommerceAdminSiteSetting_v1_0_Facet} from './HeadlessCommerceAdminSiteSetting_v1_0_Facet';
export type HeadlessCommerceAdminSiteSetting_v1_0_PageAvailabilityEstimate = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessCommerceAdminSiteSetting_v1_0_AvailabilityEstimate>;
	pageSize?: number;
	facets?: Array<HeadlessCommerceAdminSiteSetting_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
