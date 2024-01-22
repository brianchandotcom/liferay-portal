/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type HeadlessAdminWorkflow_v1_0_Node = {
	readonly 'label'?: string;
	readonly 'name'?: string;
	readonly 'x-class-name'?: string;

	/**
	 * The workflow's node types.
	 */
	readonly 'type'?:
		| 'CONDITION'
		| 'FORK'
		| 'INITIAL_STATE'
		| 'JOIN'
		| 'JOIN_XOR'
		| 'STATE'
		| 'TASK'
		| 'TERMINAL_STATE';
};
