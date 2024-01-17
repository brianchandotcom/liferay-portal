/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_AccountRole} from './HeadlessAdminUser_v1_0_AccountRole';
import type {HeadlessAdminUser_v1_0_Facet} from './HeadlessAdminUser_v1_0_Facet';
export type HeadlessAdminUser_v1_0_PageAccountRole = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessAdminUser_v1_0_AccountRole>;
	facets?: Array<HeadlessAdminUser_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
