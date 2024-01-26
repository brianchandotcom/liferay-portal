/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Creator } from './Creator';
import type { PageDefinition } from './PageDefinition';
import type { PageTemplateCollection } from './PageTemplateCollection';
import type { TaxonomyCategoryBrief } from './TaxonomyCategoryBrief';
/**
 * Represents a Page template.
 */
export type PageTemplate = {
    /**
     * The page template's creator.
     */
    readonly creator?: Creator;
    /**
     * The page template's creation date.
     */
    readonly dateCreated?: string;
    /**
     * The last time the page template changed.
     */
    readonly dateModified?: string;
    /**
     * The page template's ID.
     */
    readonly id?: number;
    /**
     * A list of keywords describing the page template.
     */
    keywords?: Array<string>;
    /**
     * The page template's name.
     */
    name?: string;
    /**
     * The page template's definition.
     */
    pageDefinition?: PageDefinition;
    /**
     * The page template's collection.
     */
    readonly pageTemplateCollection?: PageTemplateCollection;
    /**
     * The categories associated with this page template.
     */
    readonly taxonomyCategoryBriefs?: Array<TaxonomyCategoryBrief>;
    /**
     * A write-only field that adds `TaxonomyCategory` instances to the page template.
     */
    taxonomyCategoryIds?: Array<number>;
    /**
     * A valid external identifier to reference this page template.
     */
    readonly uuid?: string;
};

