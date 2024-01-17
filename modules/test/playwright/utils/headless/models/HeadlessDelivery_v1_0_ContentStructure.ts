/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessDelivery_v1_0_ContentStructureField} from './HeadlessDelivery_v1_0_ContentStructureField';
import type {HeadlessDelivery_v1_0_Creator} from './HeadlessDelivery_v1_0_Creator';
export type HeadlessDelivery_v1_0_ContentStructure = {

	/**
	 * The key of the asset library to which the content structure is scoped.
	 */
	readonly 'assetLibraryKey'?: string;

	/**
	 * The list of languages the content structure has a translation for.
	 */
	readonly 'availableLanguages'?: Array<string>;

	/**
	 * The list of the content structure's fields.
	 */
	readonly 'contentStructureFields'?: Array<HeadlessDelivery_v1_0_ContentStructureField>;
	'creator'?: HeadlessDelivery_v1_0_Creator;

	/**
	 * The content structure's creation date.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The last time a field of the content structure changed.
	 */
	readonly 'dateModified'?: string;

	/**
	 * The content structure's description.
	 */
	readonly 'description'?: string;

	/**
	 * The localized content structure's description.
	 */
	readonly 'description_i18n'?: Record<string, string>;

	/**
	 * The content structure's ID.
	 */
	readonly 'id'?: number;

	/**
	 * The content structure's name.
	 */
	readonly 'name'?: string;

	/**
	 * The localized content structure's names.
	 */
	readonly 'name_i18n'?: Record<string, string>;

	/**
	 * The ID of the site to which the content structure is scoped.
	 */
	readonly 'siteId'?: number;
	readonly 'x-class-name'?: string;
};
