/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ObjectAdmin_v1_0_ObjectField} from './ObjectAdmin_v1_0_ObjectField';
export type ObjectAdmin_v1_0_ObjectRelationship = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'edge'?: boolean;
	'externalReferenceCode'?: string;
	readonly 'id'?: number;
	'label'?: Record<string, string>;
	'name'?: string;
	'objectDefinitionExternalReferenceCode1'?: string;
	'objectDefinitionExternalReferenceCode2'?: string;
	'objectDefinitionId1'?: number;
	'objectDefinitionId2'?: number;
	'objectDefinitionModifiable2'?: boolean;
	'objectDefinitionName2'?: string;
	'objectDefinitionSystem2'?: boolean;
	'objectField'?: ObjectAdmin_v1_0_ObjectField;
	'parameterObjectFieldId'?: number;
	'parameterObjectFieldName'?: string;
	readonly 'reverse'?: boolean;
	'system'?: boolean;
	readonly 'x-class-name'?: string;
	'deletionType'?: 'cascade' | 'disassociate' | 'prevent';
	'type'?: 'oneToMany' | 'oneToOne' | 'manyToMany';
};
