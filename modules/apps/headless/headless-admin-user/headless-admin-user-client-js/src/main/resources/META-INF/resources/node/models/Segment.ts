/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents a set of users that meet certain criteria. Segments may be used to create customized experiences for users.
 */
export type Segment = {
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
};

