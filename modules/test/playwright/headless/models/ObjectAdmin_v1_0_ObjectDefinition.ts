/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ObjectAdmin_v1_0_ObjectAction} from './ObjectAdmin_v1_0_ObjectAction';
import type {ObjectAdmin_v1_0_ObjectField} from './ObjectAdmin_v1_0_ObjectField';
import type {ObjectAdmin_v1_0_ObjectLayout} from './ObjectAdmin_v1_0_ObjectLayout';
import type {ObjectAdmin_v1_0_ObjectRelationship} from './ObjectAdmin_v1_0_ObjectRelationship';
import type {ObjectAdmin_v1_0_ObjectValidationRule} from './ObjectAdmin_v1_0_ObjectValidationRule';
import type {ObjectAdmin_v1_0_ObjectView} from './ObjectAdmin_v1_0_ObjectView';
import type {ObjectAdmin_v1_0_Status} from './ObjectAdmin_v1_0_Status';
export type ObjectAdmin_v1_0_ObjectDefinition = {
	'accountEntryRestricted'?: boolean;
	'accountEntryRestrictedObjectFieldName'?: string;
	readonly 'actions'?: Record<string, Record<string, string>>;
	'active'?: boolean;
	readonly 'dateCreated'?: string;
	readonly 'dateModified'?: string;
	'defaultLanguageId'?: string;
	'enableCategorization'?: boolean;
	'enableComments'?: boolean;
	'enableLocalization'?: boolean;
	'enableObjectEntryDraft'?: boolean;
	'enableObjectEntryHistory'?: boolean;
	'externalReferenceCode'?: string;
	readonly 'id'?: number;
	'label'?: Record<string, string>;
	'modifiable'?: boolean;
	'name'?: string;
	'objectActions'?: Array<ObjectAdmin_v1_0_ObjectAction>;
	'objectFields'?: Array<ObjectAdmin_v1_0_ObjectField>;
	'objectFolderExternalReferenceCode'?: string;
	'objectLayouts'?: Array<ObjectAdmin_v1_0_ObjectLayout>;
	'objectRelationships'?: Array<ObjectAdmin_v1_0_ObjectRelationship>;
	'objectValidationRules'?: Array<ObjectAdmin_v1_0_ObjectValidationRule>;
	'objectViews'?: Array<ObjectAdmin_v1_0_ObjectView>;
	'panelAppOrder'?: string;
	'panelCategoryKey'?: string;
	readonly 'parameterRequired'?: boolean;
	'pluralLabel'?: Record<string, string>;
	'portlet'?: boolean;
	readonly 'restContextPath'?: string;
	'rootObjectDefinitionExternalReferenceCode'?: string;
	'scope'?: string;
	'status'?: ObjectAdmin_v1_0_Status;
	'storageType'?: string;
	'system'?: boolean;
	'titleObjectFieldName'?: string;
	readonly 'x-class-name'?: string;
};
