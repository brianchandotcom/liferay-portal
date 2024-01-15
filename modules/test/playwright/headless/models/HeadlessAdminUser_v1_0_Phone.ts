/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * A list of the user's phone numbers, with one optionally marked as primary.
 */
export type HeadlessAdminUser_v1_0_Phone = {

	/**
	 * The phone number's extension.
	 */
	'extension'?: string;

	/**
	 * The phone number's ID.
	 */
	'id'?: number;

	/**
	 * The phone number without its extension.
	 */
	'phoneNumber'?: string;

	/**
	 * The phone number's type.
	 */
	'phoneType'?: string;

	/**
	 * A flag that identifies whether this is the main phone number of the user/organization.
	 */
	'primary'?: boolean;
	readonly 'x-class-name'?: string;
};
