/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ObjectAdmin_v1_0_ObjectFieldSetting} from './ObjectAdmin_v1_0_ObjectFieldSetting';
export type ObjectAdmin_v1_0_ObjectField = {
	'DBType'?:
		| 'BigDecimal'
		| 'Boolean'
		| 'Clob'
		| 'Date'
		| 'DateTime'
		| 'Double'
		| 'Integer'
		| 'Long'
		| 'String';
	readonly 'actions'?: Record<string, Record<string, string>>;

	/**
	 * @deprecated
	 */
	'defaultValue'?: string;
	'externalReferenceCode'?: string;
	readonly 'id'?: number;
	'indexed'?: boolean;
	'indexedAsKeyword'?: boolean;
	'indexedLanguageId'?: string;
	'label'?: Record<string, string>;
	'listTypeDefinitionExternalReferenceCode'?: string;
	'listTypeDefinitionId'?: number;
	'localized'?: boolean;
	'name'?: string;
	'objectFieldSettings'?: Array<ObjectAdmin_v1_0_ObjectFieldSetting>;
	'readOnlyConditionExpression'?: string;
	'required'?: boolean;
	'state'?: boolean;
	'system'?: boolean;
	readonly 'unique'?: boolean;
	readonly 'x-class-name'?: string;
	'businessType'?:
		| 'Aggregation'
		| 'Attachment'
		| 'AutoIncrement'
		| 'Boolean'
		| 'Date'
		| 'DateTime'
		| 'Decimal'
		| 'Encrypted'
		| 'Formula'
		| 'Integer'
		| 'LongInteger'
		| 'LongText'
		| 'MultiselectPicklist'
		| 'Picklist'
		| 'PrecisionDecimal'
		| 'Relationship'
		| 'RichText'
		| 'Text';
	'readOnly'?: 'conditional' | 'false' | 'true';
	readonly 'relationshipType'?: 'oneToMany' | 'oneToOne';

	/**
	 * @deprecated
	 */
	'type'?:
		| 'BigDecimal'
		| 'Boolean'
		| 'Clob'
		| 'Date'
		| 'DateTime'
		| 'Double'
		| 'Integer'
		| 'Long'
		| 'String';
};
