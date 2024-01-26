/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CustomCSSViewport } from './CustomCSSViewport';
import type { FragmentStyle } from './FragmentStyle';
import type { FragmentViewport } from './FragmentViewport';
import type { RowViewport } from './RowViewport';
/**
 * Represents a definition of a Page row.
 */
export type PageRowDefinition = {
    /**
     * A list of CSS Classes that are applied to the element.
     */
    cssClasses?: Array<string>;
    /**
     * Custom CSS that is applied on the fragment.
     */
    customCSS?: string;
    /**
     * The custom CSS viewports of the page collection.
     */
    customCSSViewports?: Array<CustomCSSViewport>;
    /**
     * The fragment style of a Page row.
     */
    fragmentStyle?: FragmentStyle;
    /**
     * A list of fragment viewports of a Page row.
     */
    fragmentViewports?: Array<FragmentViewport>;
    /**
     * A flag that indicates whether the page row has gutters.
     */
    gutters?: boolean;
    /**
     * A flag that indicates whether the page row is indexed or not.
     */
    indexed?: boolean;
    /**
     * The page row's modules per row.
     */
    modulesPerRow?: number;
    /**
     * The custom name of a Page row.
     */
    name?: string;
    /**
     * The page row's number of columns.
     */
    numberOfColumns?: number;
    /**
     * A flag that indicates whether the page row has reverse order.
     */
    reverseOrder?: boolean;
    /**
     * Deprecated as of Athanasius (7.3.x), replaced by rowViewports
     * @deprecated
     */
    rowViewportConfig?: {
        landscapeMobile?: {
            modulesPerRow?: number;
            reverseOrder?: boolean;
            verticalAlignment?: string;
        };
        portraitMobile?: {
            modulesPerRow?: number;
            reverseOrder?: boolean;
            verticalAlignment?: string;
        };
        tablet?: {
            modulesPerRow?: number;
            reverseOrder?: boolean;
            verticalAlignment?: string;
        };
    };
    /**
     * A list of viewports of the page row.
     */
    rowViewports?: Array<RowViewport>;
    /**
     * The vertical aligment property of the page row.
     */
    verticalAlignment?: string;
};

