/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {ObjectAdmin_v1_0_ObjectFieldSetting} from './ObjectAdmin_v1_0_ObjectFieldSetting';
export type ObjectAdmin_v1_0_ObjectField = {
	'DBType'?: ObjectAdmin_v1_0_ObjectField.DBType;
	readonly 'actions'?: Record<string, Record<string, string>>;
	'businessType'?: ObjectAdmin_v1_0_ObjectField.businessType;

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
	'readOnly'?: ObjectAdmin_v1_0_ObjectField.readOnly;
	'readOnlyConditionExpression'?: string;
	readonly 'relationshipType'?: ObjectAdmin_v1_0_ObjectField.relationshipType;
	'required'?: boolean;
	'state'?: boolean;
	'system'?: boolean;

	/**
	 * @deprecated
	 */
	'type'?: ObjectAdmin_v1_0_ObjectField.type;
	readonly 'unique'?: boolean;
	readonly 'x-class-name'?: string;
};
export namespace ObjectAdmin_v1_0_ObjectField {
	export enum DBType {
		BIG_DECIMAL = 'BigDecimal',
		BOOLEAN = 'Boolean',
		CLOB = 'Clob',
		DATE = 'Date',
		DATE_TIME = 'DateTime',
		DOUBLE = 'Double',
		INTEGER = 'Integer',
		LONG = 'Long',
		STRING = 'String',
	}
	export enum businessType {
		AGGREGATION = 'Aggregation',
		ATTACHMENT = 'Attachment',
		AUTO_INCREMENT = 'AutoIncrement',
		BOOLEAN = 'Boolean',
		DATE = 'Date',
		DATE_TIME = 'DateTime',
		DECIMAL = 'Decimal',
		ENCRYPTED = 'Encrypted',
		FORMULA = 'Formula',
		INTEGER = 'Integer',
		LONG_INTEGER = 'LongInteger',
		LONG_TEXT = 'LongText',
		MULTISELECT_PICKLIST = 'MultiselectPicklist',
		PICKLIST = 'Picklist',
		PRECISION_DECIMAL = 'PrecisionDecimal',
		RELATIONSHIP = 'Relationship',
		RICH_TEXT = 'RichText',
		TEXT = 'Text',
	}
	export enum readOnly {
		CONDITIONAL = 'conditional',
		FALSE = 'false',
		TRUE = 'true',
	}
	export enum relationshipType {
		ONE_TO_MANY = 'oneToMany',
		ONE_TO_ONE = 'oneToOne',
	}
	export enum type {
		BIG_DECIMAL = 'BigDecimal',
		BOOLEAN = 'Boolean',
		CLOB = 'Clob',
		DATE = 'Date',
		DATE_TIME = 'DateTime',
		DOUBLE = 'Double',
		INTEGER = 'Integer',
		LONG = 'Long',
		STRING = 'String',
	}
}
