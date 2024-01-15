/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_RoleBrief} from './HeadlessAdminUser_v1_0_RoleBrief';

/**
 * A list of the user's account.
 */
export type HeadlessAdminUser_v1_0_AccountBrief = {

	/**
	 * The account's external reference code.
	 */
	readonly 'externalReferenceCode'?: string;

	/**
	 * The account's ID.
	 */
	readonly 'id'?: number;

	/**
	 * The account's name.
	 */
	readonly 'name'?: string;

	/**
	 * A list of the user's roles.
	 */
	readonly 'roleBriefs'?: Array<HeadlessAdminUser_v1_0_RoleBrief>;
	readonly 'x-class-name'?: string;
};
