/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * https://schema.org/TaskBulkSelection
 */
export type TaskBulkSelection = {
    assigneeIds?: Array<number>;
    instanceIds?: Array<number>;
    processId?: number;
    slaStatuses?: Array<'OnTime' | 'Overdue' | 'Untracked'>;
    taskNames?: Array<string>;
};

