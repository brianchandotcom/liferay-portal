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
	readonly 'id'?: number;
	'key': string;
	'name': Record<string, string>;
	'optionValues'?: Array<HeadlessCommerceAdminCatalog_v1_0_OptionValue>;
	'priority'?: number;
	'required'?: boolean;
	'skuContributor'?: boolean;
	readonly 'x-class-name'?: string;
	'fieldType':
		| 'checkbox'
		| 'checkbox_multiple'
		| 'date'
		| 'numeric'
		| 'radio'
		| 'select'
		| 'text';
};
