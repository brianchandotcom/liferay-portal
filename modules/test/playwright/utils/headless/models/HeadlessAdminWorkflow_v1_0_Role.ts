/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminWorkflow_v1_0_Creator} from './HeadlessAdminWorkflow_v1_0_Creator';
export type HeadlessAdminWorkflow_v1_0_Role = {

	/**
	 * A list of languages for which the role has a translation.
	 */
	readonly 'availableLanguages'?: Array<string>;
	'creator'?: HeadlessAdminWorkflow_v1_0_Creator;

	/**
	 * The role's creation date.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The last time any of the role's fields were changed.
	 */
	readonly 'dateModified'?: string;

	/**
	 * The role's description.
	 */
	readonly 'description'?: string;
	readonly 'description_i18n'?: Record<string, string>;

	/**
	 * The role's ID.
	 */
	readonly 'id'?: number;

	/**
	 * The role's name.
	 */
	readonly 'name'?: string;
	readonly 'name_i18n'?: Record<string, string>;

	/**
	 * The role's type.
	 */
	readonly 'roleType'?: string;
	readonly 'x-class-name'?: string;
};
