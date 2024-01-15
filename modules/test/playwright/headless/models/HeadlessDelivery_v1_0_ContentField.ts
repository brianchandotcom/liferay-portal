/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_ContentFieldValue} from './HeadlessDelivery_v1_0_ContentFieldValue';

/**
 * The list of fields that store the structured content's information.
 */
export type HeadlessDelivery_v1_0_ContentField = {
	'contentFieldValue'?: HeadlessDelivery_v1_0_ContentFieldValue;

	/**
	 * The localized field's values.
	 */
	'contentFieldValue_i18n'?: Record<
		string,
		HeadlessDelivery_v1_0_ContentFieldValue
	>;

	/**
	 * The field type (e.g., image, text, etc.).
	 */
	readonly 'dataType'?: string;

	/**
	 * The field's control type (e.g., text, text area, etc.).
	 */
	readonly 'inputControl'?: string;

	/**
	 * The field's label.
	 */
	readonly 'label'?: string;

	/**
	 * The localized field's labels.
	 */
	readonly 'label_i18n'?: Record<string, string>;

	/**
	 * The field's internal name. This is valid for comparisons and unique in the structured content.
	 */
	'name'?: string;

	/**
	 * A list of child content fields that depend on this resource.
	 */
	'nestedContentFields'?: Array<HeadlessDelivery_v1_0_ContentField>;

	/**
	 * A flag that indicates whether this field can be rendered multiple times.
	 */
	readonly 'repeatable'?: boolean;
	readonly 'x-class-name'?: string;
};
