/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents a role's basic information, to be embedded with other resources. The ID of this resource can be used to query the role's complete information.
 */
export type RoleBrief = {
    /**
     * The role's ID.
     */
    readonly id?: number;
    /**
     * The role's name.
     */
    readonly name?: string;
    readonly name_i18n?: Record<string, string>;
};

