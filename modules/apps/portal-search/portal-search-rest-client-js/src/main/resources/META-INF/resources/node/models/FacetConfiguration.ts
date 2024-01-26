/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type FacetConfiguration = {
    /**
     * The name of the aggregation.
     */
    aggregationName?: string;
    /**
     * Additional attributes for the facet.
     */
    attributes?: Record<string, Record<string, any>>;
    /**
     * Frequency threshold for showing the terms.
     */
    frequencyThreshold?: number;
    /**
     * Maximum number of terms to be shown.
     */
    maxTerms?: number;
    /**
     * The name of the facet.
     */
    name?: string;
    /**
     * The values / selections to be filtered by.
     */
    values?: Array<Record<string, any>>;
};

