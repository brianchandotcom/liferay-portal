/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_Facet} from './HeadlessAdminUser_v1_0_Facet';
import type {HeadlessAdminUser_v1_0_Phone} from './HeadlessAdminUser_v1_0_Phone';
export type HeadlessAdminUser_v1_0_PagePhone = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessAdminUser_v1_0_Phone>;
	pageSize?: number;
	facets?: Array<HeadlessAdminUser_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
