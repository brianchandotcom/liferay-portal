/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Creator } from './Creator';
import type { RolePermission } from './RolePermission';
/**
 * Represents a relationship between a user and a company or site. This follows the [`Role`](https://www.schema.org/Role) specification.
 */
export type Role = {
    /**
     * Block of actions that the user can perform with the roles.
     */
    readonly actions?: Record<string, Record<string, string>>;
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
    description?: string;
    description_i18n?: Record<string, string>;
    /**
     * The portable ID of this role.
     */
    externalReferenceCode?: string;
    /**
     * The role's ID.
     */
    readonly id?: number;
    /**
     * The role's name.
     */
    name?: string;
    name_i18n?: Record<string, string>;
    rolePermissions?: Array<RolePermission>;
    /**
     * The role's type.
     */
    roleType?: string;
};

