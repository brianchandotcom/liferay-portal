/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { RoleBrief } from './RoleBrief';
/**
 * Represents a site's basic information.
 */
export type SiteBrief = {
    /**
     * The site's descriptive name.
     */
    readonly descriptiveName?: string;
    readonly descriptiveName_i18n?: Record<string, string>;
    /**
     * The site's ID.
     */
    readonly id?: number;
    /**
     * The site's name.
     */
    readonly name?: string;
    readonly name_i18n?: Record<string, string>;
    /**
     * A list of the user's roles.
     */
    readonly roleBriefs?: Array<RoleBrief>;
};

