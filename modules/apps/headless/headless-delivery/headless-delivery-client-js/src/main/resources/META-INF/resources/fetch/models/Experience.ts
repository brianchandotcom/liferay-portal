/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents a customized experience for a given page.
 */
export type Experience = {
    /**
     * the experience's key.
     */
    key?: string;
    /**
     * the experience's name.
     */
    name?: string;
    /**
     * the localized experience's names.
     */
    name_i18n?: Record<string, string>;
    /**
     * A list of segments the experience is used for.
     */
    segments?: Array<{
        /**
         * A flag that indicates whether the segment is active.
         */
        readonly active?: boolean;
        /**
         * The segment's criteria.
         */
        readonly criteria: string;
        /**
         * The segment's criteria in JSON.
         */
        readonly criteriaValue?: Record<string, Record<string, any>>;
        /**
         * The segment's creation date.
         */
        readonly dateCreated?: string;
        /**
         * The segment's most recent modification date.
         */
        readonly dateModified?: string;
        /**
         * The segment's ID.
         */
        readonly id?: number;
        /**
         * The segment's name.
         */
        readonly name: string;
        /**
         * The ID of the segment's site.
         */
        readonly siteId?: number;
        /**
         * The segment's source.
         */
        readonly source?: string;
    }>;
};

