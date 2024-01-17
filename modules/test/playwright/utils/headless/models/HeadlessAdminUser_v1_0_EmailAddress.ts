/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * A list of the user's email addresses, with one optionally marked as primary.
 */
export type HeadlessAdminUser_v1_0_EmailAddress = {

	/**
	 * The email address.
	 */
	'emailAddress'?: string;

	/**
	 * The email address's ID.
	 */
	readonly 'id'?: number;

	/**
	 * A flag that indicates whether this is the main email address of the user/organization.
	 */
	'primary'?: boolean;

	/**
	 * The email address's type.
	 */
	'type'?: string;
	readonly 'x-class-name'?: string;
};
