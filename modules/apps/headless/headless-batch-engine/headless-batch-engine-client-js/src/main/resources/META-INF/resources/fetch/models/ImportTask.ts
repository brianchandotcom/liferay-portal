/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { FailedItem } from './FailedItem';
export type ImportTask = {
    /**
     * The item class name for which data will be processed in batch.
     */
    className?: string;
    /**
     * The file content type.
     */
    contentType?: string;
    /**
     * The end time of import task operation.
     */
    endTime?: string;
    /**
     * The error message in case of import task's failed execution.
     */
    errorMessage?: string;
    /**
     * The status of import task's execution.
     */
    executeStatus?: 'COMPLETED' | 'FAILED' | 'INITIAL' | 'STARTED';
    /**
     * The optional external key of this account.
     */
    externalReferenceCode?: string;
    failedItems?: Array<FailedItem>;
    /**
     * The task's ID.
     */
    id?: number;
    /**
     * Defines if import task will fail when error occurs or continue importing rest of the items.
     */
    importStrategy?: 'ON_ERROR_CONTINUE' | 'ON_ERROR_FAIL';
    /**
     * The operation of import task.
     */
    operation?: 'CREATE' | 'DELETE' | 'UPDATE';
    /**
     * Number of items processed by import task opeartion.
     */
    processedItemsCount?: number;
    /**
     * The start time of import task operation.
     */
    startTime?: string;
    /**
     * Total number of items that will be processed by import task operation.
     */
    totalItemsCount?: number;
};

