/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_Facet} from './HeadlessAdminUser_v1_0_Facet';
import type {HeadlessAdminUser_v1_0_Segment} from './HeadlessAdminUser_v1_0_Segment';
export type HeadlessAdminUser_v1_0_PageSegment = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessAdminUser_v1_0_Segment>;
	facets?: Array<HeadlessAdminUser_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
