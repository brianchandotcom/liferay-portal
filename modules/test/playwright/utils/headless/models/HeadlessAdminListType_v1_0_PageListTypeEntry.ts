/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminListType_v1_0_Facet} from './HeadlessAdminListType_v1_0_Facet';
import type {HeadlessAdminListType_v1_0_ListTypeEntry} from './HeadlessAdminListType_v1_0_ListTypeEntry';
export type HeadlessAdminListType_v1_0_PageListTypeEntry = {
	items?: Array<HeadlessAdminListType_v1_0_ListTypeEntry>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessAdminListType_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
