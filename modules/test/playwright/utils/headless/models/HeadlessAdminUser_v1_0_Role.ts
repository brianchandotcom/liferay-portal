/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_Creator} from './HeadlessAdminUser_v1_0_Creator';
import type {HeadlessAdminUser_v1_0_RolePermission} from './HeadlessAdminUser_v1_0_RolePermission';
export type HeadlessAdminUser_v1_0_Role = {

	/**
	 * Block of actions that the user can perform with the roles.
	 */
	readonly 'actions'?: Record<string, Record<string, string>>;

	/**
	 * A list of languages for which the role has a translation.
	 */
	readonly 'availableLanguages'?: Array<string>;
	'creator'?: HeadlessAdminUser_v1_0_Creator;

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
	'description'?: string;
	'description_i18n'?: Record<string, string>;

	/**
	 * The portable ID of this role.
	 */
	'externalReferenceCode'?: string;

	/**
	 * The role's ID.
	 */
	readonly 'id'?: number;

	/**
	 * The role's name.
	 */
	'name'?: string;
	'name_i18n'?: Record<string, string>;
	'rolePermissions'?: Array<HeadlessAdminUser_v1_0_RolePermission>;

	/**
	 * The role's type.
	 */
	'roleType'?: string;
	readonly 'x-class-name'?: string;
};
