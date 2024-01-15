/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_CustomField} from './HeadlessCommerceAdminCatalog_v1_0_CustomField';
import type {HeadlessCommerceAdminCatalog_v1_0_OptionValue} from './HeadlessCommerceAdminCatalog_v1_0_OptionValue';
export type HeadlessCommerceAdminCatalog_v1_0_Option = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'catalogId'?: number;
	'customFields'?: Array<HeadlessCommerceAdminCatalog_v1_0_CustomField>;
	'description'?: Record<string, string>;
	'externalReferenceCode'?: string;
	'facetable'?: boolean;
	'fieldType': HeadlessCommerceAdminCatalog_v1_0_Option.fieldType;
	readonly 'id'?: number;
	'key': string;
	'name': Record<string, string>;
	'optionValues'?: Array<HeadlessCommerceAdminCatalog_v1_0_OptionValue>;
	'priority'?: number;
	'required'?: boolean;
	'skuContributor'?: boolean;
	readonly 'x-class-name'?: string;
};
export namespace HeadlessCommerceAdminCatalog_v1_0_Option {
	export enum fieldType {
		CHECKBOX = 'checkbox',
		CHECKBOX_MULTIPLE = 'checkbox_multiple',
		DATE = 'date',
		NUMERIC = 'numeric',
		RADIO = 'radio',
		SELECT = 'select',
		TEXT = 'text',
	}
}
