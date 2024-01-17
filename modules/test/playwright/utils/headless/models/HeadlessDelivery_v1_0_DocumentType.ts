/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_ContentField} from './HeadlessDelivery_v1_0_ContentField';
export type HeadlessDelivery_v1_0_DocumentType = {

	/**
	 * The list of languages the document type has a translation for.
	 */
	readonly 'availableLanguages'?: Array<string>;

	/**
	 * The list of content fields the document type has.
	 */
	'contentFields'?: Array<HeadlessDelivery_v1_0_ContentField>;

	/**
	 * The document type's description.
	 */
	'description'?: string;

	/**
	 * The localized document type's description.
	 */
	readonly 'description_i18n'?: Record<string, string>;

	/**
	 * The document type's name.
	 */
	'name'?: string;

	/**
	 * The localized document type's name.
	 */
	readonly 'name_i18n'?: Record<string, string>;
	readonly 'x-class-name'?: string;
};
