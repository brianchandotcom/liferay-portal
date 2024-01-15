/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {BatchPlanner_v1_0_Facet} from './BatchPlanner_v1_0_Facet';
import type {BatchPlanner_v1_0_Field} from './BatchPlanner_v1_0_Field';
export type BatchPlanner_v1_0_PageField = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<BatchPlanner_v1_0_Field>;
	pageSize?: number;
	facets?: Array<BatchPlanner_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
