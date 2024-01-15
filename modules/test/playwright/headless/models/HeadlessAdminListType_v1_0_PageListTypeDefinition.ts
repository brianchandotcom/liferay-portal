/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminListType_v1_0_Facet} from './HeadlessAdminListType_v1_0_Facet';
import type {HeadlessAdminListType_v1_0_ListTypeDefinition} from './HeadlessAdminListType_v1_0_ListTypeDefinition';
export type HeadlessAdminListType_v1_0_PageListTypeDefinition = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessAdminListType_v1_0_ListTypeDefinition>;
	pageSize?: number;
	facets?: Array<HeadlessAdminListType_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
