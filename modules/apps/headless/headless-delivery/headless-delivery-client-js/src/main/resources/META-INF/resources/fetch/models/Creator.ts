/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { UserGroupBrief } from './UserGroupBrief';
/**
 * Represents the user account of the content's creator/author. Properties follow the [creator](https://schema.org/creator) specification.
 */
export type Creator = {
    /**
     * The author's additional name (e.g., middle name).
     */
    readonly additionalName?: string;
    /**
     * The type of the content.
     */
    readonly contentType?: string;
    /**
     * The author's surname.
     */
    readonly familyName?: string;
    /**
     * The author's first name.
     */
    readonly givenName?: string;
    /**
     * The author's ID.
     */
    readonly id?: number;
    /**
     * A relative URL to the author's profile image.
     */
    readonly image?: string;
    /**
     * The author's full name.
     */
    readonly name?: string;
    /**
     * A relative URL to the author's user profile. Optional field, can be embedded with nestedFields.
     */
    readonly profileURL?: string;
    /**
     * A list of userGroups information.
     */
    userGroupBriefs?: Array<UserGroupBrief>;
};

