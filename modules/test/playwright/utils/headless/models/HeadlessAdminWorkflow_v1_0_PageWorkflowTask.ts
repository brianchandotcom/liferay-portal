/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminWorkflow_v1_0_Facet} from './HeadlessAdminWorkflow_v1_0_Facet';
import type {HeadlessAdminWorkflow_v1_0_WorkflowTask} from './HeadlessAdminWorkflow_v1_0_WorkflowTask';
export type HeadlessAdminWorkflow_v1_0_PageWorkflowTask = {
	totalCount?: number;
	lastPage?: number;
	items?: Array<HeadlessAdminWorkflow_v1_0_WorkflowTask>;
	pageSize?: number;
	facets?: Array<HeadlessAdminWorkflow_v1_0_Facet>;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
