/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type ExportTask = {
    /**
     * The item class name for which data will be exported in batch.
     */
    className?: string;
    /**
     * The file content type.
     */
    contentType?: string;
    /**
     * The end time of export task operation.
     */
    endTime?: string;
    /**
     * The error message in case of export task's failed execution.
     */
    errorMessage?: string;
    /**
     * The status of export task's execution.
     */
    executeStatus?: 'COMPLETED' | 'FAILED' | 'INITIAL' | 'STARTED';
    /**
     * The optional external key of this account.
     */
    externalReferenceCode?: string;
    /**
     * The task's ID.
     */
    id?: number;
    /**
     * Number of items processed by export task opeartion.
     */
    processedItemsCount?: number;
    /**
     * The start time of export task operation.
     */
    startTime?: string;
    /**
     * Total number of items that will be processed by export task operation.
     */
    totalItemsCount?: number;
};

