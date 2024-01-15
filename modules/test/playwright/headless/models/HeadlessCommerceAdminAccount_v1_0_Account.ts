/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminAccount_v1_0_AccountAddress} from './HeadlessCommerceAdminAccount_v1_0_AccountAddress';
import type {HeadlessCommerceAdminAccount_v1_0_AccountMember} from './HeadlessCommerceAdminAccount_v1_0_AccountMember';
import type {HeadlessCommerceAdminAccount_v1_0_AccountOrganization} from './HeadlessCommerceAdminAccount_v1_0_AccountOrganization';
export type HeadlessCommerceAdminAccount_v1_0_Account = {
	'accountAddresses'?: Array<HeadlessCommerceAdminAccount_v1_0_AccountAddress>;
	'accountMembers'?: Array<HeadlessCommerceAdminAccount_v1_0_AccountMember>;
	'accountOrganizations'?: Array<HeadlessCommerceAdminAccount_v1_0_AccountOrganization>;
	'active'?: boolean;
	'customFields'?: Record<string, Record<string, any>>;

	/**
	 * The account's creation date.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The account's most recent modification date.
	 */
	readonly 'dateModified'?: string;
	'defaultBillingAccountAddressId'?: number;
	'defaultShippingAccountAddressId'?: number;
	'emailAddresses'?: Array<string>;
	'externalReferenceCode': string;
	'id'?: number;
	'logoId'?: number;
	'logoURL'?: string;
	'name': string;
	'root'?: boolean;
	'taxId'?: string;
	'type'?: number;
	readonly 'x-class-name'?: string;
};
