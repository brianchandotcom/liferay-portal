/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AccountBrief } from './AccountBrief';
import type { CustomField } from './CustomField';
import type { EmailAddress } from './EmailAddress';
import type { OrganizationBrief } from './OrganizationBrief';
import type { Phone } from './Phone';
import type { PostalAddress } from './PostalAddress';
import type { RoleBrief } from './RoleBrief';
import type { SiteBrief } from './SiteBrief';
import type { UserGroupBrief } from './UserGroupBrief';
import type { WebUrl } from './WebUrl';
/**
 * Represents a user.
 */
export type UserAccount = {
    /**
     * A list of the user's account.
     */
    readonly accountBriefs?: Array<AccountBrief>;
    readonly actions?: Record<string, Record<string, string>>;
    /**
     * The user's additional name (e.g., middle name).
     */
    additionalName?: string;
    /**
     * The user's alias or screen name.
     */
    alternateName?: string;
    /**
     * The user's date of birth, in ISO 8601 format.
     */
    birthDate?: string;
    /**
     * The user's current password. Used to authenticate a user when they attempt to update their own password.
     */
    currentPassword?: string;
    customFields?: Array<CustomField>;
    /**
     * A relative URL to the user's dashboard.
     */
    readonly dashboardURL?: string;
    /**
     * The creation date of the user's account.
     */
    readonly dateCreated?: string;
    /**
     * The last time any field of the user's account was changed.
     */
    readonly dateModified?: string;
    /**
     * The user's main email address.
     */
    emailAddress?: string;
    /**
     * The optional external key of this user account.
     */
    externalReferenceCode?: string;
    /**
     * The user's surname (last name).
     */
    familyName?: string;
    /**
     * The user's first name.
     */
    givenName?: string;
    /**
     * The user's title (e.g., Dr., Mr., Mrs, Ms., etc.).
     */
    honorificPrefix?: string;
    /**
     * The user's suffix (e.g., II, Jr., PhD, etc.).
     */
    honorificSuffix?: string;
    /**
     * The user's ID.
     */
    id?: number;
    /**
     * A relative URL to the user's profile image.
     */
    readonly image?: string;
    /**
     * The user's profile image id.
     */
    imageId?: number;
    /**
     * The user's job title.
     */
    jobTitle?: string;
    /**
     * A list of keywords describing the user.
     */
    readonly keywords?: Array<string>;
    /**
     * The user's preferred language.
     */
    readonly languageDisplayName?: string;
    /**
     * The user's preferred language id.
     */
    languageId?: string;
    /**
     * The last time the user logged in.
     */
    readonly lastLoginDate?: string;
    /**
     * The user's full name.
     */
    readonly name?: string;
    /**
     * A list of the user's organizations.
     */
    readonly organizationBriefs?: Array<OrganizationBrief>;
    /**
     * The user's password.
     */
    password?: string;
    /**
     * A relative URL to the user's profile.
     */
    readonly profileURL?: string;
    /**
     * A list of the user's roles.
     */
    readonly roleBriefs?: Array<RoleBrief>;
    /**
     * A list of the user's sites.
     */
    readonly siteBriefs?: Array<SiteBrief>;
    /**
     * The user's status.
     */
    status?: 'Active' | 'Inactive';
    /**
     * The user's contact information.
     */
    userAccountContactInformation?: {
        /**
         * A list of the user's email addresses, with one optionally marked as primary.
         */
        emailAddresses?: Array<EmailAddress>;
        /**
         * The user's Facebook account.
         */
        facebook?: string;
        /**
         * The ID of the `contactInformation`.
         */
        readonly id?: number;
        /**
         * The user's Jabber handle.
         */
        jabber?: string;
        /**
         * A list of user's postal addresses, with one optionally marked as primary.
         */
        postalAddresses?: Array<PostalAddress>;
        /**
         * The user's Skype handle.
         */
        skype?: string;
        /**
         * The user's SMS number.
         */
        sms?: string;
        /**
         * A list of the user's phone numbers, with one optionally marked as primary.
         */
        telephones?: Array<Phone>;
        /**
         * The user's Twitter handle.
         */
        twitter?: string;
        /**
         * A list of the user's web URLs, with one optionally marked as primary.
         */
        webUrls?: Array<WebUrl>;
    };
    /**
     * A list of the user's userGroups.
     */
    readonly userGroupBriefs?: Array<UserGroupBrief>;
};

