/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_EmailAddress} from './HeadlessAdminUser_v1_0_EmailAddress';
import type {HeadlessAdminUser_v1_0_Phone} from './HeadlessAdminUser_v1_0_Phone';
import type {HeadlessAdminUser_v1_0_PostalAddress} from './HeadlessAdminUser_v1_0_PostalAddress';
import type {HeadlessAdminUser_v1_0_WebUrl} from './HeadlessAdminUser_v1_0_WebUrl';

/**
 * The user's contact information.
 */
export type HeadlessAdminUser_v1_0_UserAccountContactInformation = {

	/**
	 * A list of the user's email addresses, with one optionally marked as primary.
	 */
	'emailAddresses'?: Array<HeadlessAdminUser_v1_0_EmailAddress>;

	/**
	 * The user's Facebook account.
	 */
	'facebook'?: string;

	/**
	 * The ID of the `contactInformation`.
	 */
	readonly 'id'?: number;

	/**
	 * The user's Jabber handle.
	 */
	'jabber'?: string;

	/**
	 * A list of user's postal addresses, with one optionally marked as primary.
	 */
	'postalAddresses'?: Array<HeadlessAdminUser_v1_0_PostalAddress>;

	/**
	 * The user's Skype handle.
	 */
	'skype'?: string;

	/**
	 * The user's SMS number.
	 */
	'sms'?: string;

	/**
	 * A list of the user's phone numbers, with one optionally marked as primary.
	 */
	'telephones'?: Array<HeadlessAdminUser_v1_0_Phone>;

	/**
	 * The user's Twitter handle.
	 */
	'twitter'?: string;

	/**
	 * A list of the user's web URLs, with one optionally marked as primary.
	 */
	'webUrls'?: Array<HeadlessAdminUser_v1_0_WebUrl>;
	readonly 'x-class-name'?: string;
};
