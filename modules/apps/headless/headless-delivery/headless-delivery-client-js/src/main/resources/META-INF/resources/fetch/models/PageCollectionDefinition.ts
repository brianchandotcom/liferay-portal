/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ClassNameReference } from './ClassNameReference';
import type { ClassPKReference } from './ClassPKReference';
import type { CollectionViewport } from './CollectionViewport';
import type { FragmentStyle } from './FragmentStyle';
import type { FragmentViewport } from './FragmentViewport';
/**
 * Represents a definition of a Page Collection.
 */
export type PageCollectionDefinition = {
    collectionConfig?: {
        /**
         * The page collection's reference.
         */
        collectionReference: (ClassNameReference | ClassPKReference);
        /**
         * The page collection's type (Collection, CollectionProvider).
         */
        collectionType: 'Collection' | 'CollectionProvider';
    };
    /**
     * A list of viewports of the page collection.
     */
    collectionViewports?: Array<CollectionViewport>;
    /**
     * Whether to show all items when pagination is disabled.
     */
    displayAllItems?: boolean;
    /**
     * Whether to show all pages when pagination is enabled.
     */
    displayAllPages?: boolean;
    emptyCollectionConfig?: {
        /**
         * Whether to display a message when the collection is empty or no results match the applied filters (true by default).
         */
        displayMessage?: boolean;
        /**
         * The localized message to display when the collection is empty or no results match the applied filters ('No Results Found' by default).
         */
        message_i18n?: Record<string, string>;
    };
    /**
     * The fragment style of the page collection.
     */
    fragmentStyle?: FragmentStyle;
    /**
     * The fragment viewports of the page collection.
     */
    fragmentViewports?: Array<FragmentViewport>;
    /**
     * the page section's layout.
     */
    layout?: {
        align?: 'Baseline' | 'Center' | 'End' | 'None' | 'Start' | 'Stretch';
        flexWrap?: 'NoWrap' | 'Wrap' | 'WrapReverse';
        justify?: 'Center' | 'End' | 'None' | 'SpaceAround' | 'SpaceBetween' | 'Start';
    };
    /**
     * The style of a list of items in the page collection.
     */
    listItemStyle?: string;
    /**
     * The style of a list in the page collection.
     */
    listStyle?: string;
    /**
     * The custom name of a Page Collection.
     */
    name?: string;
    /**
     * The number of columns in the page collection.
     */
    numberOfColumns?: number;
    /**
     * The maximum number of items to display in the page collection when pagination is disabled.
     */
    numberOfItems?: number;
    /**
     * The number of items per page in the page collection.
     */
    numberOfItemsPerPage?: number;
    /**
     * The maximum number of pages to show when pagination is enabled.
     */
    numberOfPages?: number;
    /**
     * The type of pagination.
     */
    paginationType?: 'None' | 'Numeric' | 'Regular' | 'Simple';
    /**
     * Whether to show all items when pagination is enabled. Deprecated as of Cavanaugh (7.4.x), replaced by displayAllPages
     * @deprecated
     */
    showAllItems?: boolean;
    /**
     * The page collection's template key.
     */
    templateKey?: string;
};

