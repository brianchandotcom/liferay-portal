/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Account } from './Account';
import type { CustomField } from './CustomField';
import type { EmailAddress } from './EmailAddress';
import type { Phone } from './Phone';
import type { PostalAddress } from './PostalAddress';
import type { UserAccount } from './UserAccount';
import type { WebUrl } from './WebUrl';
/**
 * Represents an organization. Organizations can contain other organizations (suborganizations). Properties follow the [Organization](https://schema.org/Organization) specification.
 */
export type Organization = {
    readonly actions?: Record<string, Record<string, string>>;
    childOrganizations?: Array<Organization>;
    /**
     * The text of a comment associated with the organization.
     */
    comment?: string;
    customFields?: Array<CustomField>;
    /**
     * The organization's creation date.
     */
    readonly dateCreated?: string;
    /**
     * The most recent time any of the organization's fields changed.
     */
    readonly dateModified?: string;
    /**
     * The optional external key of this organization.
     */
    externalReferenceCode?: string;
    /**
     * The organization's ID.
     */
    id?: string;
    /**
     * A relative URL to the organization's image.
     */
    readonly image?: string;
    /**
     * The organization's image id.
     */
    imageId?: number;
    /**
     * A list of keywords describing the organization.
     */
    readonly keywords?: Array<string>;
    /**
     * The organization's postal information (country and region).
     */
    location?: {
        /**
         * The organization's country. This follows the [`addressCountry`](https://schema.org/addressCountry) specification.
         */
        addressCountry?: string;
        /**
         * The organization's country isocode.
         */
        readonly addressCountryCode?: string;
        addressCountry_i18n?: Record<string, string>;
        /**
         * The organization's region. This follows the [`addressRegion`](https://schema.org/addressRegion) specification.
         */
        addressRegion?: string;
        /**
         * The organization's region code.
         */
        readonly addressRegionCode?: string;
        /**
         * The location's ID.
         */
        readonly id?: number;
    };
    /**
     * The organization's name.
     */
    name?: string;
    /**
     * The number of this organization's associated accounts.
     */
    readonly numberOfAccounts?: number;
    /**
     * The number of this organization's child organizations.
     */
    readonly numberOfOrganizations?: number;
    /**
     * The number of this organization's associated users.
     */
    readonly numberOfUsers?: number;
    organizationAccounts?: Array<Account>;
    /**
     * The organization's contact information, which includes email addresses, postal addresses, phone numbers, and web URLs. This is modeled internally as a `Contact`.
     */
    organizationContactInformation?: {
        /**
         * The organization's email addresses, with one optionally marked as primary.
         */
        emailAddresses?: Array<EmailAddress>;
        /**
         * The organization's postal addresses, with one optionally marked as primary.
         */
        postalAddresses?: Array<PostalAddress>;
        /**
         * The organization's phones numbers, with one optionally marked as primary.
         */
        telephones?: Array<Phone>;
        /**
         * The organization's web URLs, with one optionally marked as primary.
         */
        webUrls?: Array<WebUrl>;
    };
    /**
     * The organization's parent organization.
     */
    parentOrganization?: Organization;
    /**
     * A list of services the organization provides. This follows the [`Service`](https://www.schema.org/Service) specification.
     */
    services?: Array<{
        /**
         * A list of hours when the organization is open. This follows the [`OpeningHoursSpecification`](https://www.schema.org/OpeningHoursSpecification) specification.
         */
        hoursAvailable?: Array<{
            /**
             * The organization's closing time (in `HH:MM` format).
             */
            closes?: string;
            /**
             * The day of the week.
             */
            dayOfWeek?: string;
            /**
             * The organization's opening time (in `HH:MM` format).
             */
            opens?: string;
        }>;
        /**
         * The type of service the organization provides.
         */
        serviceType?: string;
    }>;
    /**
     * The tree path of the organization.
     */
    treePath?: string;
    userAccounts?: Array<UserAccount>;
};

