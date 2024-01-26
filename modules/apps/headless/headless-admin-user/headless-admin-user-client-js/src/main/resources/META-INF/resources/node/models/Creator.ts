/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents the user who created/authored the content. Properties follow the [creator](https://schema.org/creator) specification.
 */
export type Creator = {
    /**
     * The user's additional name, which can be used as a middle name.
     */
    readonly additionalName?: string;
    /**
     * The type of the content.
     */
    readonly contentType?: string;
    /**
     * The user's surname (last name).
     */
    readonly familyName?: string;
    /**
     * The user's first name.
     */
    readonly givenName?: string;
    /**
     * The user's ID.
     */
    readonly id?: number;
    /**
     * A relative URL to the user's profile image.
     */
    readonly image?: string;
    /**
     * The user's full name.
     */
    readonly name?: string;
    /**
     * A relative URL to the user's profile.
     */
    readonly profileURL?: string;
};

