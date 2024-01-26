/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents a phone number. This follows the [telephone](https://schema.org/telephone) specification.
 */
export type Phone = {
    /**
     * The phone number's extension.
     */
    extension?: string;
    /**
     * The phone number's ID.
     */
    id?: number;
    /**
     * The phone number without its extension.
     */
    phoneNumber?: string;
    /**
     * The phone number's type.
     */
    phoneType?: string;
    /**
     * A flag that identifies whether this is the main phone number of the user/organization.
     */
    primary?: boolean;
};

