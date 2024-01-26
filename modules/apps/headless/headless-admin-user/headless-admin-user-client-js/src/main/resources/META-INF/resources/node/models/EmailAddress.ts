/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents an email address. Properties follow the [email](https://schema.org/email) specification.
 */
export type EmailAddress = {
    /**
     * The email address.
     */
    emailAddress?: string;
    /**
     * The email address's ID.
     */
    readonly id?: number;
    /**
     * A flag that indicates whether this is the main email address of the user/organization.
     */
    primary?: boolean;
    /**
     * The email address's type.
     */
    type?: string;
};

