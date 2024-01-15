/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ObjectAdmin_v1_0_Status} from './ObjectAdmin_v1_0_Status';
export type ObjectAdmin_v1_0_ObjectAction = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'active'?: boolean;
	'conditionExpression'?: string;
	readonly 'dateCreated'?: string;
	readonly 'dateModified'?: string;
	'description'?: string;
	'errorMessage'?: Record<string, string>;
	'externalReferenceCode'?: string;
	readonly 'id'?: number;
	'label'?: Record<string, string>;
	'name'?: string;
	'objectActionExecutorKey'?: string;
	'objectActionTriggerKey'?: string;
	'parameters'?: Record<string, Record<string, any>>;
	'status'?: ObjectAdmin_v1_0_Status;
	'system'?: boolean;
	readonly 'x-class-name'?: string;
};
