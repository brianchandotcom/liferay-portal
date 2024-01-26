/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { RoleBrief } from './RoleBrief';
/**
 * Represents an account's basic information, to be embedded in other resources. This resource's ID can be used to query the account's complete information.
 */
export type AccountBrief = {
    /**
     * The account's external reference code.
     */
    readonly externalReferenceCode?: string;
    /**
     * The account's ID.
     */
    readonly id?: number;
    /**
     * The account's name.
     */
    readonly name?: string;
    /**
     * A list of the user's roles.
     */
    readonly roleBriefs?: Array<RoleBrief>;
};

