/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Creator } from './Creator';
/**
 * Represents a rating/score received by any kind of asset. Properties follow the [Rating](https://schema.org/Rating) specification.
 */
export type Rating = {
    /**
     * Block of actions allowed by the user making the request.
     */
    readonly actions?: Record<string, Record<string, string>>;
    /**
     * The best possible rating an asset can receive (normalized to 1.0 by default).
     */
    readonly bestRating?: number;
    /**
     * The rating's creator.
     */
    readonly creator?: Creator;
    /**
     * The rating's creation date.
     */
    readonly dateCreated?: string;
    /**
     * The last time a field of the rating changed.
     */
    readonly dateModified?: string;
    /**
     * The rating's ID.
     */
    readonly id?: number;
    /**
     * The rating's value.
     */
    ratingValue?: number;
    /**
     * The worst possible rating an asset can receive (normalized to 0.0 by default).
     */
    readonly worstRating?: number;
};

