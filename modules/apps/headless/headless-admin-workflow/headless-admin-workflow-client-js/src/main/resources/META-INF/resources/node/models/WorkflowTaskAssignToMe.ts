/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents a write-only schema to assign a workflow task to the currently logged-in user.
 */
export type WorkflowTaskAssignToMe = {
    /**
     * An optional comment to add when accepting the workflow task.
     */
    comment?: string;
    /**
     * The date on which the workflow task should be executed.
     */
    dueDate?: string;
    workflowTaskId?: number;
};

