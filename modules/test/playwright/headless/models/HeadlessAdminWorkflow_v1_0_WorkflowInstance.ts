/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminWorkflow_v1_0_ObjectReviewed} from './HeadlessAdminWorkflow_v1_0_ObjectReviewed';
export type HeadlessAdminWorkflow_v1_0_WorkflowInstance = {
	readonly 'actions'?: Record<string, Record<string, string>>;

	/**
	 * A flag that indicates whether the instance is complete.
	 */
	readonly 'completed'?: boolean;

	/**
	 * The instance's current node names.
	 */
	readonly 'currentNodeNames'?: Array<string>;

	/**
	 * The instance's completion date.
	 */
	readonly 'dateCompletion'?: string;

	/**
	 * The instance's creation date.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The instance's ID.
	 */
	readonly 'id'?: number;
	'objectReviewed'?: HeadlessAdminWorkflow_v1_0_ObjectReviewed;

	/**
	 * The name of the instance's workflow definition.
	 */
	readonly 'workflowDefinitionName'?: string;
	readonly 'workflowDefinitionVersion'?: string;
	readonly 'x-class-name'?: string;
};
