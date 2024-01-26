/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Creator } from './Creator';
import type { TaxonomyCategoryProperty } from './TaxonomyCategoryProperty';
/**
 * Represents a category, which is a hierarchical classification that can be associated with particular asset types. Properties follow the [category](https://schema.org/category) specification.
 */
export type TaxonomyCategory = {
    readonly actions?: Record<string, Record<string, string>>;
    /**
     * A list of languages the category has a translation for.
     */
    readonly availableLanguages?: Array<string>;
    /**
     * The category's creator.
     */
    readonly creator?: Creator;
    /**
     * The category's creation date.
     */
    readonly dateCreated?: string;
    /**
     * The category's most recent modification date.
     */
    readonly dateModified?: string;
    /**
     * The category's text description.
     */
    description?: string;
    description_i18n?: Record<string, string>;
    /**
     * The category's external reference code
     */
    externalReferenceCode?: string;
    /**
     * The category's ID.
     */
    readonly id?: string;
    /**
     * The category's name.
     */
    name: string;
    name_i18n?: Record<string, string>;
    /**
     * The number of times this category has been used in other assets.
     */
    readonly numberOfTaxonomyCategories?: number;
    /**
     * The category's parent category, if it exists.
     */
    parentTaxonomyCategory?: {
        id?: number;
        name?: string;
        name_i18n?: Record<string, string>;
    };
    /**
     * The parent category's `TaxonomyVocabulary`, if such a parent category exists.
     */
    readonly parentTaxonomyVocabulary?: {
        id?: number;
        name?: string;
        name_i18n?: Record<string, string>;
    };
    /**
     * The ID of the site to which this category is scoped.
     */
    readonly siteId?: number;
    /**
     * The category's properties.
     */
    taxonomyCategoryProperties?: Array<TaxonomyCategoryProperty>;
    readonly taxonomyCategoryUsageCount?: number;
    /**
     * The `TaxonomyVocabulary` id, only if the category does not have a parent category.
     */
    taxonomyVocabularyId?: number;
    /**
     * A write-only property that specifies the category's default permissions.
     */
    viewableBy?: 'Anyone' | 'Members' | 'Owner';
};

