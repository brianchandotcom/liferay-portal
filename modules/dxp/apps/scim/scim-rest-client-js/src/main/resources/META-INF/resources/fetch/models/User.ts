/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BaseScim } from './BaseScim';
import type { MultiValuedAttribute } from './MultiValuedAttribute';
export type User = (BaseScim & {
    /**
     * A Boolean value indicating the user's administrative status.
     */
    active?: boolean;
    /**
     * A physical mailing address for this user.
     */
    addresses?: Array<(MultiValuedAttribute & {
        /**
         * The country name component.
         */
        country?: string;
        /**
         * The full mailing address, formatted for display or use with a mailing label.
         */
        formatted?: string;
        /**
         * The city or locality component.
         */
        locality?: string;
        /**
         * The zip code or postal code component.
         */
        postalCode?: string;
        /**
         * The state or region component.
         */
        region?: string;
        /**
         * The full street address component, which may include house number, street name, P.O. box, and multi-line extended street address information.
         */
        streetAddress?: string;
    })>;
    /**
     * The name of the user, suitable for display to end-users.
     */
    displayName?: string;
    /**
     * Email addresses for the User.
     */
    emails?: Array<MultiValuedAttribute>;
    /**
     * A list of entitlements for the user that represent a thing the user has.
     */
    entitlements?: Array<MultiValuedAttribute>;
    /**
     * A list of groups to which the user belongs, either through direct membership, through nested groups, or dynamically calculated.
     */
    groups?: Array<MultiValuedAttribute>;
    /**
     * Instant messaging address for the user.
     */
    ims?: Array<MultiValuedAttribute>;
    /**
     * Used to indicate the User's default location for purposes of localizing such items as currency, date time format, or numerical representations.
     */
    locale?: string;
    /**
     * The components of the user's name.
     */
    name?: {
        familyName?: string;
        formatted?: string;
        givenName?: string;
        honorificPrefix?: string;
        honorificSuffix?: string;
        middleName?: string;
    };
    /**
     * The casual way to address the user in real life, e.g., "Bob" or "Bobby" instead of "Robert".
     */
    nickName?: string;
    /**
     * This attribute is intended to be used as a means to set, replace, or compare (i.e., filter for equality) a password.
     */
    password?: string;
    /**
     * Phone numbers for the user.
     */
    phoneNumbers?: Array<MultiValuedAttribute>;
    /**
     * A URI that is a uniform resource locator that points to a resource location representing the user's image.
     */
    photos?: Array<MultiValuedAttribute>;
    /**
     * Indicates the user's preferred written or spoken languages and is generally used for selecting a localized user interface.
     */
    preferredLanguage?: string;
    /**
     * A URI that is a uniform resource locator and that points to a location representing the user's online profile (e.g., a web page).
     */
    profileUrl?: string;
    /**
     * A list of roles for the user that collectively represent who the user is, e.g., "Student", "Faculty".
     */
    roles?: Array<MultiValuedAttribute>;
    /**
     * The User's time zone, in IANA Time Zone database format, also known as the "Olson" time zone database format (e.g., "America/Los_Angeles").
     */
    timezone?: string;
    /**
     * The user's title, such as "Vice President".
     */
    title?: string;
    /**
     * A service provider's unique identifier for the user, typically used by the user to directly authenticate to the service provider.
     */
    userName?: string;
    /**
     * Used to identify the relationship between the organization and the user.
     */
    userType?: string;
    /**
     * A list of certificates associated with the resource (e.g., a User).
     */
    x509Certificates?: Array<MultiValuedAttribute>;
});

