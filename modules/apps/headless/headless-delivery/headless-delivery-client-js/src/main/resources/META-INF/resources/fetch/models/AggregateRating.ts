/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents the average rating. See [AggregateRating](https://www.schema.org/AggregateRating) for more information.
 */
export type AggregateRating = {
    /**
     * The highest possible rating (by default normalized to 1.0).
     */
    readonly bestRating?: number;
    /**
     * The average rating.
     */
    readonly ratingAverage?: number;
    /**
     * The number of ratings.
     */
    readonly ratingCount?: number;
    /**
     * The rating value.
     */
    readonly ratingValue?: number;
    /**
     * The lowest possible rating (by default normalized to 0.0).
     */
    readonly worstRating?: number;
};

