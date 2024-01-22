/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {BatchPlanner_v1_0_Facet} from './BatchPlanner_v1_0_Facet';
import type {BatchPlanner_v1_0_Plan} from './BatchPlanner_v1_0_Plan';
export type BatchPlanner_v1_0_PagePlan = {
	items?: Array<BatchPlanner_v1_0_Plan>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<BatchPlanner_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
