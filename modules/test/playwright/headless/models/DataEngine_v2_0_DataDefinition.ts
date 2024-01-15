/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {DataEngine_v2_0_DataDefinitionField} from './DataEngine_v2_0_DataDefinitionField';
import type {DataEngine_v2_0_DataLayout} from './DataEngine_v2_0_DataLayout';
import type {DataEngine_v2_0_DataRule} from './DataEngine_v2_0_DataRule';
export type DataEngine_v2_0_DataDefinition = {
	'availableLanguageIds'?: Array<string>;
	'contentType'?: string;
	'dataDefinitionFields'?: Array<DataEngine_v2_0_DataDefinitionField>;
	'dataDefinitionKey'?: string;
	'dataRules'?: Array<DataEngine_v2_0_DataRule>;
	'dateCreated'?: string;
	'dateModified'?: string;
	'defaultDataLayout'?: DataEngine_v2_0_DataLayout;
	'defaultLanguageId'?: string;
	'description'?: Record<string, Record<string, any>>;
	'id'?: number;
	'name'?: Record<string, Record<string, any>>;
	'siteId'?: number;
	'storageType'?: string;
	'userId'?: number;
	readonly 'x-class-name'?: string;
};
