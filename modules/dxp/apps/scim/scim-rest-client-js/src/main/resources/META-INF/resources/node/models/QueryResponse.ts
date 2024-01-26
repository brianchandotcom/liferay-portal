/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Group } from './Group';
import type { User } from './User';
export type QueryResponse = {
    /**
     * A multi-valued list of complex objects containing the requested resources.
     */
    Resources?: (Group | User);
    /**
     * The number of resources returned in a list response page.
     */
    itemsPerPage?: number;
    /**
     * The 1-based index of the first result in the current set of list results.
     */
    startIndex?: number;
    /**
     * The total number of results returned by the list or query operation.
     */
    totalResults?: number;
};

