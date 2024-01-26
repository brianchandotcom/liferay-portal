/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Creator } from './Creator';
/**
 * Represents a Page template collection.
 */
export type PageTemplateCollection = {
    /**
     * The page template collection's creator.
     */
    readonly creator?: Creator;
    /**
     * The page template collection's creation date.
     */
    readonly dateCreated?: string;
    /**
     * The last time the page template collection changed.
     */
    readonly dateModified?: string;
    /**
     * The page template collection's description.
     */
    description?: string;
    /**
     * The page template collection's ID.
     */
    readonly id?: number;
    /**
     * The page template collection's name.
     */
    name?: string;
    /**
     * A valid external identifier to reference this page template collection.
     */
    readonly uuid?: string;
};

