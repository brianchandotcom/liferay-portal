/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type HeadlessAdminWorkflow_v1_0_Node = {
	readonly 'label'?: string;
	readonly 'name'?: string;

	/**
	 * The workflow's node types.
	 */
	readonly 'type'?: HeadlessAdminWorkflow_v1_0_Node.type;
	readonly 'x-class-name'?: string;
};
export namespace HeadlessAdminWorkflow_v1_0_Node {

	/**
	 * The workflow's node types.
	 */
	export enum type {
		CONDITION = 'CONDITION',
		FORK = 'FORK',
		INITIAL_STATE = 'INITIAL_STATE',
		JOIN = 'JOIN',
		JOIN_XOR = 'JOIN_XOR',
		STATE = 'STATE',
		TASK = 'TASK',
		TERMINAL_STATE = 'TERMINAL_STATE',
	}
}
