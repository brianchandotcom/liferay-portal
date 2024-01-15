/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_Account} from './HeadlessAdminUser_v1_0_Account';
import type {HeadlessAdminUser_v1_0_CustomField} from './HeadlessAdminUser_v1_0_CustomField';
import type {HeadlessAdminUser_v1_0_Location} from './HeadlessAdminUser_v1_0_Location';
import type {HeadlessAdminUser_v1_0_OrganizationContactInformation} from './HeadlessAdminUser_v1_0_OrganizationContactInformation';
import type {HeadlessAdminUser_v1_0_Service} from './HeadlessAdminUser_v1_0_Service';
import type {HeadlessAdminUser_v1_0_UserAccount} from './HeadlessAdminUser_v1_0_UserAccount';
export type HeadlessAdminUser_v1_0_Organization = {
	readonly 'actions'?: Record<string, Record<string, string>>;
	'childOrganizations'?: Array<HeadlessAdminUser_v1_0_Organization>;

	/**
	 * The text of a comment associated with the organization.
	 */
	'comment'?: string;
	'customFields'?: Array<HeadlessAdminUser_v1_0_CustomField>;

	/**
	 * The organization's creation date.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The most recent time any of the organization's fields changed.
	 */
	readonly 'dateModified'?: string;

	/**
	 * The optional external key of this organization.
	 */
	'externalReferenceCode'?: string;

	/**
	 * The organization's ID.
	 */
	'id'?: string;

	/**
	 * A relative URL to the organization's image.
	 */
	readonly 'image'?: string;

	/**
	 * The organization's image id.
	 */
	'imageId'?: number;

	/**
	 * A list of keywords describing the organization.
	 */
	readonly 'keywords'?: Array<string>;
	'location'?: HeadlessAdminUser_v1_0_Location;

	/**
	 * The organization's name.
	 */
	'name'?: string;

	/**
	 * The number of this organization's associated accounts.
	 */
	readonly 'numberOfAccounts'?: number;

	/**
	 * The number of this organization's child organizations.
	 */
	readonly 'numberOfOrganizations'?: number;

	/**
	 * The number of this organization's associated users.
	 */
	readonly 'numberOfUsers'?: number;
	'organizationAccounts'?: Array<HeadlessAdminUser_v1_0_Account>;
	'organizationContactInformation'?: HeadlessAdminUser_v1_0_OrganizationContactInformation;
	'parentOrganization'?: HeadlessAdminUser_v1_0_Organization;

	/**
	 * A list of services the organization provides. This follows the [`Service`](https://www.schema.org/Service) specification.
	 */
	'services'?: Array<HeadlessAdminUser_v1_0_Service>;

	/**
	 * The tree path of the organization.
	 */
	'treePath'?: string;
	'userAccounts'?: Array<HeadlessAdminUser_v1_0_UserAccount>;
	readonly 'x-class-name'?: string;
};
