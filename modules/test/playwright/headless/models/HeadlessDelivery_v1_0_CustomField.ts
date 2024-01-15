/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_CustomValue} from './HeadlessDelivery_v1_0_CustomValue';

/**
 * A list of the custom fields associated with the wiki page.
 */
export type HeadlessDelivery_v1_0_CustomField = {
	'customValue'?: HeadlessDelivery_v1_0_CustomValue;

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
