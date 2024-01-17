/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ObjectAdmin_v1_0_ObjectValidationRuleSetting} from './ObjectAdmin_v1_0_ObjectValidationRuleSetting';
export type ObjectAdmin_v1_0_ObjectValidationRule = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'active'?: boolean;
	readonly 'dateCreated'?: string;
	readonly 'dateModified'?: string;
	'engine'?: string;
	readonly 'engineLabel'?: string;
	'errorLabel'?: Record<string, string>;
	'externalReferenceCode'?: string;
	readonly 'id'?: number;
	'name'?: Record<string, string>;
	'objectDefinitionExternalReferenceCode'?: string;
	'objectDefinitionId'?: number;
	'objectValidationRuleSettings'?: Array<ObjectAdmin_v1_0_ObjectValidationRuleSetting>;
	'outputType'?: ObjectAdmin_v1_0_ObjectValidationRule.outputType;
	'script'?: string;
	'system'?: boolean;
	readonly 'x-class-name'?: string;
};
export namespace ObjectAdmin_v1_0_ObjectValidationRule {
	export enum outputType {
		FULL_VALIDATION = 'fullValidation',
		PARTIAL_VALIDATION = 'partialValidation',
	}
}
