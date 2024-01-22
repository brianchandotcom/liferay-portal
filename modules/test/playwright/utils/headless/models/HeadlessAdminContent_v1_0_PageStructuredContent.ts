/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminContent_v1_0_Facet} from './HeadlessAdminContent_v1_0_Facet';
import type {HeadlessAdminContent_v1_0_StructuredContent} from './HeadlessAdminContent_v1_0_StructuredContent';
export type HeadlessAdminContent_v1_0_PageStructuredContent = {
	items?: Array<HeadlessAdminContent_v1_0_StructuredContent>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessAdminContent_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
