/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_TaxonomyCategoryReference} from './HeadlessDelivery_v1_0_TaxonomyCategoryReference';

/**
 * The categories associated with this wiki page.
 */
export type HeadlessDelivery_v1_0_TaxonomyCategoryBrief = {

	/**
	 * Optional field with the embedded taxonomy category, can be embedded with nestedFields
	 */
	readonly 'embeddedTaxonomyCategory'?: Record<string, any>;

	/**
	 * The category's ID. This can be used to retrieve more information in the `TaxonomyCategory` API.
	 */
	readonly 'taxonomyCategoryId'?: number;

	/**
	 * The category's name.
	 */
	readonly 'taxonomyCategoryName'?: string;

	/**
	 * The localized category's names.
	 */
	readonly 'taxonomyCategoryName_i18n'?: Record<string, string>;
	'taxonomyCategoryReference'?: HeadlessDelivery_v1_0_TaxonomyCategoryReference;
	readonly 'x-class-name'?: string;
};
