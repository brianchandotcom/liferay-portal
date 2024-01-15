/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminContent_v1_0_Creator} from './HeadlessAdminContent_v1_0_Creator';
import type {HeadlessAdminContent_v1_0_CustomField} from './HeadlessAdminContent_v1_0_CustomField';
import type {HeadlessAdminContent_v1_0_DisplayPageTemplateSettings} from './HeadlessAdminContent_v1_0_DisplayPageTemplateSettings';
import type {HeadlessAdminContent_v1_0_PageDefinition} from './HeadlessAdminContent_v1_0_PageDefinition';

/**
 * Represents a Display Page Template that has fields and is tied to a content type
 */
export type HeadlessAdminContent_v1_0_DisplayPageTemplate = {
	readonly 'actions'?: Record<string, Record<string, string>>;

	/**
	 * The list of languages the Display Page Template has a translation for.
	 */
	readonly 'availableLanguages'?: Array<string>;
	'creator'?: HeadlessAdminContent_v1_0_Creator;

	/**
	 * The custom fields associated to the page that renders the Display Page Template.
	 */
	'customFields'?: Array<HeadlessAdminContent_v1_0_CustomField>;

	/**
	 * The Display Page Template's creation date.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The last time any field of the Display Page Template was changed.
	 */
	readonly 'dateModified'?: string;

	/**
	 * The Display Page Template's external key.
	 */
	'displayPageTemplateKey'?: string;
	'displayPageTemplateSettings'?: HeadlessAdminContent_v1_0_DisplayPageTemplateSettings;

	/**
	 * Specifies if the Display Page Template is the default one for the content type.
	 */
	'markedAsDefault'?: boolean;
	'pageDefinition'?: HeadlessAdminContent_v1_0_PageDefinition;

	/**
	 * The ID of the site to which this Page Template is scoped.
	 */
	readonly 'siteId'?: number;

	/**
	 * The title of the Display Page Template
	 */
	'title': string;

	/**
	 * A valid external identifier to reference this Display Page Template.
	 */
	readonly 'uuid'?: string;
	readonly 'x-class-name'?: string;
};
