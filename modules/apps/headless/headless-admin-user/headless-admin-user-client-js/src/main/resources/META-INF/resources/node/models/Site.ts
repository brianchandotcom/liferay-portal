/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Creator } from './Creator';
/**
 * Represents the site where the content is created. Properties follow the [WebSite](https://schema.org/WebSite) specification.
 */
export type Site = {
    availableLanguages?: Array<string>;
    creator?: Creator;
    description?: string;
    description_i18n?: Record<string, string>;
    descriptiveName?: string;
    descriptiveName_i18n?: Record<string, string>;
    friendlyUrlPath?: string;
    id?: number;
    key?: string;
    membershipType?: string;
    name?: string;
    name_i18n?: Record<string, string>;
    parentSiteId?: number;
    sites?: Array<Site>;
};

