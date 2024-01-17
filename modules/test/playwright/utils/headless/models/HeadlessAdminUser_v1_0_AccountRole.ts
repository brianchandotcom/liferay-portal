/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type HeadlessAdminUser_v1_0_AccountRole = {

	/**
	 * The primary key of the account that owns this role.
	 */
	readonly 'accountId'?: number;
	'description'?: string;
	'displayName'?: string;
	readonly 'id'?: number;
	'name'?: string;

	/**
	 * The primary key of the underlying system role.
	 */
	readonly 'roleId'?: number;
	readonly 'x-class-name'?: string;
};
