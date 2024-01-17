/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_CustomField} from './HeadlessCommerceAdminCatalog_v1_0_CustomField';
import type {HeadlessCommerceAdminCatalog_v1_0_ProductOptionValue} from './HeadlessCommerceAdminCatalog_v1_0_ProductOptionValue';
export type HeadlessCommerceAdminCatalog_v1_0_ProductOption = {
	'catalogId'?: number;
	'customFields'?: Array<HeadlessCommerceAdminCatalog_v1_0_CustomField>;
	'definedExternally'?: boolean;
	'description'?: Record<string, string>;
	'facetable'?: boolean;
	'fieldType': string;
	readonly 'id'?: number;
	'infoItemServiceKey'?: string;
	'key': string;
	'name': Record<string, string>;
	'optionId': number;
	'priceType'?: string;
	'priority'?: number;
	'productOptionValues'?: Array<HeadlessCommerceAdminCatalog_v1_0_ProductOptionValue>;
	'required'?: boolean;
	'skuContributor'?: boolean;
	'typeSettings'?: string;
	readonly 'x-class-name'?: string;
};
