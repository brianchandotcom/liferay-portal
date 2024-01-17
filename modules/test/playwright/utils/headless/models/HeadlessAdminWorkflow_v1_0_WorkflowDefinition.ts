/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminWorkflow_v1_0_Node} from './HeadlessAdminWorkflow_v1_0_Node';
import type {HeadlessAdminWorkflow_v1_0_Transition} from './HeadlessAdminWorkflow_v1_0_Transition';
export type HeadlessAdminWorkflow_v1_0_WorkflowDefinition = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'active'?: boolean;
	'content'?: string;
	readonly 'dateCreated'?: string;
	readonly 'dateModified'?: string;
	readonly 'description'?: string;
	readonly 'id'?: number;
	'name'?: string;
	readonly 'nodes'?: Array<HeadlessAdminWorkflow_v1_0_Node>;
	'title'?: string;
	'title_i18n'?: Record<string, string>;
	readonly 'transitions'?: Array<HeadlessAdminWorkflow_v1_0_Transition>;
	'version'?: string;
	readonly 'x-class-name'?: string;
};
