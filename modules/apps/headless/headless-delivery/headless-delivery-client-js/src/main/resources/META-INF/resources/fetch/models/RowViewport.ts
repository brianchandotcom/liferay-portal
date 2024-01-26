/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents a row viewport.
 */
export type RowViewport = {
    /**
     * The row viewport's ID.
     */
    id: string;
    /**
     * The definition of the row viewport.
     */
    rowViewportDefinition: {
        /**
         * The number of modules per row.
         */
        modulesPerRow?: number;
        /**
         * A flag that indicates whether the row viewport has reverse order.
         */
        reverseOrder?: boolean;
        /**
         * The vertical aligment property of the row viewport.
         */
        verticalAlignment?: string;
    };
};

