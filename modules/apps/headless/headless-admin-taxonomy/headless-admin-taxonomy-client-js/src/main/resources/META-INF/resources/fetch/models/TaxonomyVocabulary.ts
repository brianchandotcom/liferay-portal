/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AssetType } from './AssetType';
import type { Creator } from './Creator';
/**
 * Represents a vocabulary, which is a grouping of categories for a specific purpose (e.g., classification, sorting, etc.).
 */
export type TaxonomyVocabulary = {
    readonly actions?: Record<string, Record<string, string>>;
    readonly assetLibraryKey?: string;
    /**
     * A list of asset types that can be associated with this vocabulary.
     */
    assetTypes?: Array<AssetType>;
    /**
     * A list of languages the vocabulary has a translation for.
     */
    readonly availableLanguages?: Array<string>;
    /**
     * The vocabulary's creator.
     */
    readonly creator?: Creator;
    /**
     * The vocabulary's creation date.
     */
    readonly dateCreated?: string;
    /**
     * The vocabulary's most recent modification date.
     */
    readonly dateModified?: string;
    /**
     * The vocabulary's text description.
     */
    description?: string;
    description_i18n?: Record<string, string>;
    /**
     * The vocabulary's external reference code.
     */
    externalReferenceCode?: string;
    /**
     * The vocabulary's ID.
     */
    readonly id?: number;
    /**
     * The vocabulary's name.
     */
    name: string;
    name_i18n?: Record<string, string>;
    /**
     * The number of categories that directly depend on this vocabulary.
     */
    readonly numberOfTaxonomyCategories?: number;
    /**
     * The ID of the site to which this vocabulary is scoped.
     */
    readonly siteId?: number;
    /**
     * A write-only property that specifies the vocabulary's default permissions.
     */
    viewableBy?: 'Anyone' | 'Members' | 'Owner';
};

