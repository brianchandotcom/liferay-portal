/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { RoleBrief } from './RoleBrief';
/**
 * Represents an organization's basic information, to be embedded in other resources. This resource's ID can be used to query the organization's complete information.
 */
export type OrganizationBrief = {
    /**
     * The organization's ID.
     */
    readonly id?: number;
    /**
     * The organization's name.
     */
    readonly name?: string;
    /**
     * A list of the user's roles.
     */
    readonly roleBriefs?: Array<RoleBrief>;
};

