/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_CustomValue} from './HeadlessCommerceAdminCatalog_v1_0_CustomValue';
export type HeadlessCommerceAdminCatalog_v1_0_CustomField = {
	'customValue'?: HeadlessCommerceAdminCatalog_v1_0_CustomValue;

	/**
	 * The field type (e.g., image, text, etc.).
	 */
	readonly 'dataType'?: string;

	/**
	 * The field's internal name. This is valid for comparisons and unique in the structured content.
	 */
	'name'?: string;
	readonly 'x-class-name'?: string;
};
