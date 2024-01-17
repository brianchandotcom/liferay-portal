/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminWorkflow_v1_0_Assignee} from './HeadlessAdminWorkflow_v1_0_Assignee';
import type {HeadlessAdminWorkflow_v1_0_Facet} from './HeadlessAdminWorkflow_v1_0_Facet';
export type HeadlessAdminWorkflow_v1_0_PageAssignee = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessAdminWorkflow_v1_0_Assignee>;
	facets?: Array<HeadlessAdminWorkflow_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
