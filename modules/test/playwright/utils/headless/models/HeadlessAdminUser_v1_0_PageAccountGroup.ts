/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_AccountGroup} from './HeadlessAdminUser_v1_0_AccountGroup';
import type {HeadlessAdminUser_v1_0_Facet} from './HeadlessAdminUser_v1_0_Facet';
export type HeadlessAdminUser_v1_0_PageAccountGroup = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessAdminUser_v1_0_AccountGroup>;
	pageSize?: number;
	facets?: Array<HeadlessAdminUser_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
