/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Creator } from './Creator';
/**
 * Represents a relationship between a user and a company or site. This follows the [`Role`](https://www.schema.org/Role) specification.
 */
export type Role = {
    /**
     * A list of languages for which the role has a translation.
     */
    readonly availableLanguages?: Array<string>;
    /**
     * The role's creator.
     */
    readonly creator?: Creator;
    /**
     * The role's creation date.
     */
    readonly dateCreated?: string;
    /**
     * The last time any of the role's fields were changed.
     */
    readonly dateModified?: string;
    /**
     * The role's description.
     */
    readonly description?: string;
    readonly description_i18n?: Record<string, string>;
    /**
     * The role's ID.
     */
    readonly id?: number;
    /**
     * The role's name.
     */
    readonly name?: string;
    readonly name_i18n?: Record<string, string>;
    /**
     * The role's type.
     */
    readonly roleType?: string;
};

