/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type DataEngine_v2_0_DataDefinitionField = {
	'customProperties'?: Record<string, Record<string, any>>;
	'defaultValue'?: Record<string, Record<string, any>>;
	'fieldType'?: string;
	'id'?: number;
	'indexType'?: 'all' | 'keyword' | 'none' | 'text';
	'indexable'?: boolean;
	'label'?: Record<string, Record<string, any>>;
	'localizable'?: boolean;
	'name'?: string;

	/**
	 * A list of child data definition fields that depend on this resource.
	 */
	'nestedDataDefinitionFields'?: Array<DataEngine_v2_0_DataDefinitionField>;
	'readOnly'?: boolean;
	'repeatable'?: boolean;
	'required'?: boolean;
	'showLabel'?: boolean;
	'tip'?: Record<string, Record<string, any>>;
	'visible'?: boolean;
	readonly 'x-class-name'?: string;
};
