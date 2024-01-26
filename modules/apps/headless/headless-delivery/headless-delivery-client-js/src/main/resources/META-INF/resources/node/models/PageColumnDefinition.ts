/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ColumnViewport } from './ColumnViewport';
/**
 * Represents a definition of a Page Column.
 */
export type PageColumnDefinition = {
    /**
     * Deprecated as of Athanasius (7.3.x), replaced by columnViewports
     * @deprecated
     */
    columnViewportConfig?: {
        landscapeMobile?: {
            size?: number;
        };
        portraitMobile?: {
            size?: number;
        };
        tablet?: {
            size?: number;
        };
    };
    /**
     * A list of column viewports of the page column definition.
     */
    columnViewports?: Array<ColumnViewport>;
    /**
     * The page column's size.
     */
    size?: number;
};

