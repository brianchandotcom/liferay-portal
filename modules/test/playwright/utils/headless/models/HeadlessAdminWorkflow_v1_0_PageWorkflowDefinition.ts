/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminWorkflow_v1_0_Facet} from './HeadlessAdminWorkflow_v1_0_Facet';
import type {HeadlessAdminWorkflow_v1_0_WorkflowDefinition} from './HeadlessAdminWorkflow_v1_0_WorkflowDefinition';
export type HeadlessAdminWorkflow_v1_0_PageWorkflowDefinition = {
	items?: Array<HeadlessAdminWorkflow_v1_0_WorkflowDefinition>;
	lastPage?: number;
	totalCount?: number;
	facets?: Array<HeadlessAdminWorkflow_v1_0_Facet>;
	pageSize?: number;
	page?: number;
	actions?: Record<string, Record<string, string>>;
};
