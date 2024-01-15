/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminAddress_v1_0_Facet} from './HeadlessAdminAddress_v1_0_Facet';
import type {HeadlessAdminAddress_v1_0_Region} from './HeadlessAdminAddress_v1_0_Region';
export type HeadlessAdminAddress_v1_0_PageRegion = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessAdminAddress_v1_0_Region>;
	pageSize?: number;
	facets?: Array<HeadlessAdminAddress_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
