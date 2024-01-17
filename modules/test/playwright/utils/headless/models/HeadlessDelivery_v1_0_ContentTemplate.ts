/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_Creator} from './HeadlessDelivery_v1_0_Creator';

/**
 * Represents a content template.
 */
export type HeadlessDelivery_v1_0_ContentTemplate = {

	/**
	 * Block of actions allowed by the user making the request.
	 */
	readonly 'actions'?: Record<string, Record<string, string>>;

	/**
	 * The key of the asset library to which the content template is scoped.
	 */
	readonly 'assetLibraryKey'?: string;

	/**
	 * The list of languages the content template has a translation for.
	 */
	readonly 'availableLanguages'?: Array<string>;

	/**
	 * The ID of the `ContentStructure`.
	 */
	'contentStructureId': number;
	'creator'?: HeadlessDelivery_v1_0_Creator;

	/**
	 * The content template's creation date.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The last time the content template changed.
	 */
	readonly 'dateModified'?: string;

	/**
	 * the content template's description.
	 */
	'description'?: string;

	/**
	 * the localized content template's descriptions.
	 */
	'description_i18n'?: Record<string, string>;

	/**
	 * The content template's ID.
	 */
	readonly 'id'?: string;

	/**
	 * the content template's name.
	 */
	'name': string;

	/**
	 * the localized content template's name.
	 */
	'name_i18n'?: Record<string, string>;

	/**
	 * the content template's programming language.
	 */
	'programmingLanguage'?: string;

	/**
	 * The ID of the site to which the content template is scoped.
	 */
	readonly 'siteId'?: number;

	/**
	 * The content template's script.
	 */
	'templateScript'?: string;
	readonly 'x-class-name'?: string;
};
