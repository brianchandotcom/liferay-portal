/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type QueryAttributes = {
    /**
     * A multi-valued list of strings indicating the names of resource attributes to return in the response, overriding the set of attributes that would be returned by default.
     */
    attributes?: Array<string>;
    /**
     * An integer indicating the desired maximum number of query results per page.
     */
    count?: number;
    /**
     * A multi-valued list of strings indicating the names of resource attributes to be removed from the default set of attributes to return.
     */
    excludedAttributes?: Array<string>;
    /**
     * The filter string used to request a subset of resources.
     */
    filter?: string;
    /**
     * A string indicating the attribute whose value SHALL be used to order the returned responses.
     */
    sortBy?: string;
    /**
     * A string indicating the order in which the "sortBy" parameter is applied.
     */
    sortOrder?: string;
    /**
     * An integer indicating the 1-based index of the first query result.
     */
    startIndex?: number;
};

