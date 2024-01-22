/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminWorkflow_v1_0_Creator} from './HeadlessAdminWorkflow_v1_0_Creator';
import type {HeadlessAdminWorkflow_v1_0_Role} from './HeadlessAdminWorkflow_v1_0_Role';
export type HeadlessAdminWorkflow_v1_0_WorkflowLog = {
	'auditPerson'?: HeadlessAdminWorkflow_v1_0_Creator;

	/**
	 * The log's comments.
	 */
	readonly 'commentLog'?: string;

	/**
	 * The log's creation date.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The log's description.
	 */
	readonly 'description'?: string;

	/**
	 * The log's ID.
	 */
	readonly 'id'?: number;
	'person'?: HeadlessAdminWorkflow_v1_0_Creator;
	'previousPerson'?: HeadlessAdminWorkflow_v1_0_Creator;
	'previousRole'?: HeadlessAdminWorkflow_v1_0_Role;

	/**
	 * The workflow's previous state.
	 */
	readonly 'previousState'?: string;

	/**
	 * The workflow's previous state Label.
	 */
	readonly 'previousStateLabel'?: string;
	'role'?: HeadlessAdminWorkflow_v1_0_Role;

	/**
	 * The workflow's current state.
	 */
	readonly 'state'?: string;

	/**
	 * The workflow's current state Label.
	 */
	readonly 'stateLabel'?: string;

	/**
	 * The task associated with this workflow log.
	 */
	readonly 'workflowTaskId'?: number;
	readonly 'x-class-name'?: string;

	/**
	 * The workflow log's type.
	 */
	readonly 'type'?:
		| 'NodeEntry'
		| 'TaskAssign'
		| 'TaskCompletion'
		| 'TaskUpdate'
		| 'Transition';
};
