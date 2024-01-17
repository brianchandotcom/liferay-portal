/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_AccountBrief} from './HeadlessAdminUser_v1_0_AccountBrief';
import type {HeadlessAdminUser_v1_0_CustomField} from './HeadlessAdminUser_v1_0_CustomField';
import type {HeadlessAdminUser_v1_0_OrganizationBrief} from './HeadlessAdminUser_v1_0_OrganizationBrief';
import type {HeadlessAdminUser_v1_0_RoleBrief} from './HeadlessAdminUser_v1_0_RoleBrief';
import type {HeadlessAdminUser_v1_0_SiteBrief} from './HeadlessAdminUser_v1_0_SiteBrief';
import type {HeadlessAdminUser_v1_0_UserAccountContactInformation} from './HeadlessAdminUser_v1_0_UserAccountContactInformation';
import type {HeadlessAdminUser_v1_0_UserGroupBrief} from './HeadlessAdminUser_v1_0_UserGroupBrief';
export type HeadlessAdminUser_v1_0_UserAccount = {

	/**
	 * A list of the user's account.
	 */
	readonly 'accountBriefs'?: Array<HeadlessAdminUser_v1_0_AccountBrief>;
	readonly 'actions'?: Record<string, Record<string, string>>;

	/**
	 * The user's additional name (e.g., middle name).
	 */
	'additionalName'?: string;

	/**
	 * The user's alias or screen name.
	 */
	'alternateName'?: string;

	/**
	 * The user's date of birth, in ISO 8601 format.
	 */
	'birthDate'?: string;
	'currentPassword'?: string;
	'customFields'?: Array<HeadlessAdminUser_v1_0_CustomField>;

	/**
	 * A relative URL to the user's dashboard.
	 */
	readonly 'dashboardURL'?: string;

	/**
	 * The creation date of the user's account.
	 */
	readonly 'dateCreated'?: string;

	/**
	 * The last time any field of the user's account was changed.
	 */
	readonly 'dateModified'?: string;

	/**
	 * The user's main email address.
	 */
	'emailAddress'?: string;

	/**
	 * The optional external key of this user account.
	 */
	readonly 'externalReferenceCode'?: string;

	/**
	 * The user's surname (last name).
	 */
	'familyName'?: string;

	/**
	 * The user's first name.
	 */
	'givenName'?: string;

	/**
	 * The user's title (e.g., Dr., Mr., Mrs, Ms., etc.).
	 */
	'honorificPrefix'?: string;

	/**
	 * The user's suffix (e.g., II, Jr., PhD, etc.).
	 */
	'honorificSuffix'?: string;

	/**
	 * The user's ID.
	 */
	'id'?: number;

	/**
	 * A relative URL to the user's profile image.
	 */
	readonly 'image'?: string;

	/**
	 * The user's profile image id.
	 */
	'imageId'?: number;

	/**
	 * The user's job title.
	 */
	'jobTitle'?: string;

	/**
	 * A list of keywords describing the user.
	 */
	readonly 'keywords'?: Array<string>;

	/**
	 * The user's preferred language.
	 */
	readonly 'languageDisplayName'?: string;

	/**
	 * The user's preferred language id.
	 */
	'languageId'?: string;

	/**
	 * The last time the user logged in.
	 */
	readonly 'lastLoginDate'?: string;

	/**
	 * The user's full name.
	 */
	readonly 'name'?: string;

	/**
	 * A list of the user's organizations.
	 */
	readonly 'organizationBriefs'?: Array<HeadlessAdminUser_v1_0_OrganizationBrief>;
	'password'?: string;

	/**
	 * A relative URL to the user's profile.
	 */
	readonly 'profileURL'?: string;

	/**
	 * A list of the user's roles.
	 */
	readonly 'roleBriefs'?: Array<HeadlessAdminUser_v1_0_RoleBrief>;

	/**
	 * A list of the user's sites.
	 */
	readonly 'siteBriefs'?: Array<HeadlessAdminUser_v1_0_SiteBrief>;

	/**
	 * The user's status.
	 */
	'status'?: HeadlessAdminUser_v1_0_UserAccount.status;
	'userAccountContactInformation'?: HeadlessAdminUser_v1_0_UserAccountContactInformation;

	/**
	 * A list of the user's userGroups.
	 */
	readonly 'userGroupBriefs'?: Array<HeadlessAdminUser_v1_0_UserGroupBrief>;
	readonly 'x-class-name'?: string;
};
export namespace HeadlessAdminUser_v1_0_UserAccount {

	/**
	 * The user's status.
	 */
	export enum status {
		ACTIVE = 'Active',
		INACTIVE = 'Inactive',
	}
}
