/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_EmailAddress} from './HeadlessAdminUser_v1_0_EmailAddress';
import type {HeadlessAdminUser_v1_0_Phone} from './HeadlessAdminUser_v1_0_Phone';
import type {HeadlessAdminUser_v1_0_PostalAddress} from './HeadlessAdminUser_v1_0_PostalAddress';
import type {HeadlessAdminUser_v1_0_WebUrl} from './HeadlessAdminUser_v1_0_WebUrl';

/**
 * The organization's contact information, which includes email addresses, postal addresses, phone numbers, and web URLs. This is modeled internally as a `Contact`.
 */
export type HeadlessAdminUser_v1_0_OrganizationContactInformation = {

	/**
	 * The organization's email addresses, with one optionally marked as primary.
	 */
	'emailAddresses'?: Array<HeadlessAdminUser_v1_0_EmailAddress>;

	/**
	 * The organization's postal addresses, with one optionally marked as primary.
	 */
	'postalAddresses'?: Array<HeadlessAdminUser_v1_0_PostalAddress>;

	/**
	 * The organization's phones numbers, with one optionally marked as primary.
	 */
	'telephones'?: Array<HeadlessAdminUser_v1_0_Phone>;

	/**
	 * The organization's web URLs, with one optionally marked as primary.
	 */
	'webUrls'?: Array<HeadlessAdminUser_v1_0_WebUrl>;
	readonly 'x-class-name'?: string;
};
