/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminSiteSetting_v1_0_Facet} from './HeadlessCommerceAdminSiteSetting_v1_0_Facet';
import type {HeadlessCommerceAdminSiteSetting_v1_0_MeasurementUnit} from './HeadlessCommerceAdminSiteSetting_v1_0_MeasurementUnit';
export type HeadlessCommerceAdminSiteSetting_v1_0_PageMeasurementUnit = {
	items?: Array<HeadlessCommerceAdminSiteSetting_v1_0_MeasurementUnit>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessCommerceAdminSiteSetting_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
