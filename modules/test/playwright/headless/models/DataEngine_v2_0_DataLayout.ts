/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {DataEngine_v2_0_DataLayoutPage} from './DataEngine_v2_0_DataLayoutPage';
import type {DataEngine_v2_0_DataRule} from './DataEngine_v2_0_DataRule';
export type DataEngine_v2_0_DataLayout = {
	'contentType'?: string;
	'dataDefinitionId'?: number;
	'dataLayoutFields'?: Record<string, Record<string, any>>;
	'dataLayoutKey'?: string;
	'dataLayoutPages'?: Array<DataEngine_v2_0_DataLayoutPage>;
	'dataRules'?: Array<DataEngine_v2_0_DataRule>;
	'dateCreated'?: string;
	'dateModified'?: string;
	'description'?: Record<string, Record<string, any>>;
	'id'?: number;
	'name'?: Record<string, Record<string, any>>;
	'paginationMode'?: string;
	'siteId'?: number;
	'userId'?: number;
	readonly 'x-class-name'?: string;
};
