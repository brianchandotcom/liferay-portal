/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminWorkflow_v1_0_Creator} from './HeadlessAdminWorkflow_v1_0_Creator';
import type {HeadlessAdminWorkflow_v1_0_ObjectReviewed} from './HeadlessAdminWorkflow_v1_0_ObjectReviewed';
import type {HeadlessAdminWorkflow_v1_0_Role} from './HeadlessAdminWorkflow_v1_0_Role';
export type HeadlessAdminWorkflow_v1_0_WorkflowTask = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'assigneePerson'?: HeadlessAdminWorkflow_v1_0_Creator;
	'assigneeRoles'?: Array<HeadlessAdminWorkflow_v1_0_Role>;

	/**
	 * A flag that indicates whether the task is complete.
	 */
	readonly 'completed'?: boolean;

	/**
	 * The task's completion date.
	 */
	readonly 'dateCompletion'?: string;

	/**
	 * The task's creation date.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The date the task should be completed by.
	 */
	readonly 'dateDue'?: string;

	/**
	 * The task's description.
	 */
	readonly 'description'?: string;

	/**
	 * The task's ID.
	 */
	readonly 'id'?: number;
	readonly 'label'?: string;

	/**
	 * The task's name.
	 */
	readonly 'name'?: string;
	'objectReviewed'?: HeadlessAdminWorkflow_v1_0_ObjectReviewed;
	readonly 'workflowDefinitionId'?: number;

	/**
	 * The name of the task's workflow definition.
	 */
	readonly 'workflowDefinitionName'?: string;
	readonly 'workflowDefinitionVersion'?: string;
	readonly 'workflowInstanceId'?: number;
	readonly 'x-class-name'?: string;
};
