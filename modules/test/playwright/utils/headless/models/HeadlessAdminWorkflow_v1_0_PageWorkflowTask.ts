/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminWorkflow_v1_0_Facet} from './HeadlessAdminWorkflow_v1_0_Facet';
import type {HeadlessAdminWorkflow_v1_0_WorkflowTask} from './HeadlessAdminWorkflow_v1_0_WorkflowTask';
export type HeadlessAdminWorkflow_v1_0_PageWorkflowTask = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessAdminWorkflow_v1_0_WorkflowTask>;
	facets?: Array<HeadlessAdminWorkflow_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
