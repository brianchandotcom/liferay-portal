/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_Account} from './HeadlessAdminUser_v1_0_Account';
import type {HeadlessAdminUser_v1_0_Facet} from './HeadlessAdminUser_v1_0_Facet';
export type HeadlessAdminUser_v1_0_PageAccount = {
	items?: Array<HeadlessAdminUser_v1_0_Account>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessAdminUser_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
