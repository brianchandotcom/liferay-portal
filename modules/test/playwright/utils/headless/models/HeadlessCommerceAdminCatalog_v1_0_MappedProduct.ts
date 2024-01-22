/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_CustomField} from './HeadlessCommerceAdminCatalog_v1_0_CustomField';
export type HeadlessCommerceAdminCatalog_v1_0_MappedProduct = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'customFields'?: Array<HeadlessCommerceAdminCatalog_v1_0_CustomField>;
	'id'?: number;
	'productExternalReferenceCode'?: string;
	'productId'?: number;
	readonly 'productName'?: Record<string, string>;
	'quantity'?: number;
	'sequence'?: string;
	'sku'?: string;
	'skuExternalReferenceCode'?: string;
	'skuId'?: number;
	readonly 'x-class-name'?: string;
	'type'?: 'diagram' | 'external' | 'sku';
};
