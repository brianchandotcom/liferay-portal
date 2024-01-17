/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminWorkflow_v1_0_Facet} from './HeadlessAdminWorkflow_v1_0_Facet';
import type {HeadlessAdminWorkflow_v1_0_WorkflowInstance} from './HeadlessAdminWorkflow_v1_0_WorkflowInstance';
export type HeadlessAdminWorkflow_v1_0_PageWorkflowInstance = {
	lastPage?: number;
	totalCount?: number;
	items?: Array<HeadlessAdminWorkflow_v1_0_WorkflowInstance>;
	facets?: Array<HeadlessAdminWorkflow_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
