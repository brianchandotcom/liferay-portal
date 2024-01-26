/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Creator } from './Creator';
/**
 * Represents a keyword that describes content. Properties follow the [keywords](https://schema.org/keywords) specification.
 */
export type Keyword = {
    readonly actions?: Record<string, Record<string, string>>;
    readonly assetLibraryKey?: string;
    /**
     * The keyword's creator.
     */
    readonly creator?: Creator;
    /**
     * The keyword's creation date.
     */
    readonly dateCreated?: string;
    /**
     * The keyword's most recent modification date.
     */
    readonly dateModified?: string;
    /**
     * The keyword's ID.
     */
    readonly id?: number;
    /**
     * The number of times this keyword has been used with other assets.
     */
    readonly keywordUsageCount?: number;
    /**
     * The keyword's name.
     */
    name: string;
    /**
     * The ID of the site to which this keyword is scoped.
     */
    readonly siteId?: number;
    /**
     * A flag that indicates whether the user making the requests is subscribed to this keyword.
     */
    readonly subscribed?: boolean;
};

