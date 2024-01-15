/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_CustomField} from './HeadlessAdminUser_v1_0_CustomField';
import type {HeadlessAdminUser_v1_0_PostalAddress} from './HeadlessAdminUser_v1_0_PostalAddress';
import type {HeadlessAdminUser_v1_0_UserAccount} from './HeadlessAdminUser_v1_0_UserAccount';

/**
 * An account represents an external account, for example a customer business.
 */
export type HeadlessAdminUser_v1_0_Account = {

	/**
	 * The users linked to the account
	 */
	'accountUserAccounts'?: Array<HeadlessAdminUser_v1_0_UserAccount>;

	/**
	 * Block of actions allowed by the user making the request.
	 */
	readonly 'actions'?: Record<string, Record<string, string>>;
	'customFields'?: Array<HeadlessAdminUser_v1_0_CustomField>;

	/**
	 * The account's creation date.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The account's most recent modification date.
	 */
	readonly 'dateModified'?: string;
	'defaultBillingAddressId'?: number;
	'defaultShippingAddressId'?: number;
	'description'?: string;

	/**
	 * The account's email domains. Users assigned to this account generally will have email addresses under one of these domains.
	 */
	'domains'?: Array<string>;

	/**
	 * The optional external key of this account.
	 */
	'externalReferenceCode'?: string;
	readonly 'id'?: number;
	'logoId'?: number;
	'logoURL'?: string;
	'name': string;

	/**
	 * The number of this account's associated users.
	 */
	readonly 'numberOfUsers'?: number;
	'organizationIds'?: Array<number>;
	'parentAccountId'?: number;

	/**
	 * The addresses linked to the account
	 */
	'postalAddresses'?: Array<HeadlessAdminUser_v1_0_PostalAddress>;
	'status'?: number;
	'taxId'?: string;
	'type'?: HeadlessAdminUser_v1_0_Account.type;
	readonly 'x-class-name'?: string;
};
export namespace HeadlessAdminUser_v1_0_Account {
	export enum type {
		BUSINESS = 'business',
		GUEST = 'guest',
		PERSON = 'person',
		SUPPLIER = 'supplier',
	}
}
