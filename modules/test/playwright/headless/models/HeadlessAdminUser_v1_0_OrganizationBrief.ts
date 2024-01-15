/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_RoleBrief} from './HeadlessAdminUser_v1_0_RoleBrief';

/**
 * A list of the user's organizations.
 */
export type HeadlessAdminUser_v1_0_OrganizationBrief = {

	/**
	 * The organization's ID.
	 */
	readonly 'id'?: number;

	/**
	 * The organization's name.
	 */
	readonly 'name'?: string;

	/**
	 * A list of the user's roles.
	 */
	readonly 'roleBriefs'?: Array<HeadlessAdminUser_v1_0_RoleBrief>;
	readonly 'x-class-name'?: string;
};
