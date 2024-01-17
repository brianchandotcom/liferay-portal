/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_Facet} from './HeadlessAdminUser_v1_0_Facet';
import type {HeadlessAdminUser_v1_0_Segment} from './HeadlessAdminUser_v1_0_Segment';
export type HeadlessAdminUser_v1_0_PageSegment = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessAdminUser_v1_0_Segment>;
	pageSize?: number;
	facets?: Array<HeadlessAdminUser_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
