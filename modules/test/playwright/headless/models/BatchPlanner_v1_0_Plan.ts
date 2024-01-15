/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {BatchPlanner_v1_0_Mapping} from './BatchPlanner_v1_0_Mapping';
import type {BatchPlanner_v1_0_Policy} from './BatchPlanner_v1_0_Policy';
export type BatchPlanner_v1_0_Plan = {
	'active'?: boolean;
	'export'?: boolean;
	'externalType'?: string;
	'externalURL'?: string;
	'id'?: number;
	'internalClassName'?: string;
	readonly 'internalClassNameKey'?: string;
	'mappings'?: Array<BatchPlanner_v1_0_Mapping>;
	'name'?: string;
	'policies'?: Array<BatchPlanner_v1_0_Policy>;
	'size'?: number;
	'status'?: number;
	'taskItemDelegateName'?: string;
	'template'?: boolean;
	'total'?: number;
	readonly 'x-class-name'?: string;
};
