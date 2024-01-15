/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ObjectAdmin_v1_0_ObjectLayoutTab} from './ObjectAdmin_v1_0_ObjectLayoutTab';
export type ObjectAdmin_v1_0_ObjectLayout = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	readonly 'dateCreated'?: string;
	readonly 'dateModified'?: string;
	'defaultObjectLayout'?: boolean;
	readonly 'id'?: number;
	'name'?: Record<string, string>;
	'objectDefinitionExternalReferenceCode'?: string;
	'objectDefinitionId'?: number;
	'objectLayoutTabs'?: Array<ObjectAdmin_v1_0_ObjectLayoutTab>;
	readonly 'x-class-name'?: string;
};
