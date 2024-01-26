/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents a mailing address. This follows the [`PostalAddress`](https://www.schema.org/PostalAddress) specification.
 */
export type PostalAddress = {
    /**
     * The address's country (e.g., USA).
     */
    addressCountry?: string;
    addressCountry_i18n?: Record<string, string>;
    /**
     * The address's locality (e.g., city).
     */
    addressLocality?: string;
    /**
     * The address's region (e.g., state).
     */
    addressRegion?: string;
    /**
     * The address's type.
     */
    addressType?: string;
    /**
     * The address's ID.
     */
    readonly id?: number;
    /**
     * The address's name.
     */
    name?: string;
    /**
     * The phone number.
     */
    phoneNumber?: string;
    /**
     * The address's postal code (e.g., zip code).
     */
    postalCode?: string;
    /**
     * A flag that identifies whether this is the main address of the user/organization.
     */
    primary?: boolean;
    /**
     * The street address's first line (e.g., 1600 Amphitheatre Pkwy.).
     */
    streetAddressLine1?: string;
    /**
     * The street address's second line.
     */
    streetAddressLine2?: string;
    /**
     * The street address's third line.
     */
    streetAddressLine3?: string;
};

