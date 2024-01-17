/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_RoleBrief} from './HeadlessAdminUser_v1_0_RoleBrief';

/**
 * A list of the user's sites.
 */
export type HeadlessAdminUser_v1_0_SiteBrief = {

	/**
	 * The site's descriptive name.
	 */
	readonly 'descriptiveName'?: string;
	readonly 'descriptiveName_i18n'?: Record<string, string>;

	/**
	 * The site's ID.
	 */
	readonly 'id'?: number;

	/**
	 * The site's name.
	 */
	readonly 'name'?: string;
	readonly 'name_i18n'?: Record<string, string>;

	/**
	 * A list of the user's roles.
	 */
	readonly 'roleBriefs'?: Array<HeadlessAdminUser_v1_0_RoleBrief>;
	readonly 'x-class-name'?: string;
};
