/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents a write-only schema to assign a workflow task to a specific user.
 */
export type WorkflowTaskAssignToUser = {
    /**
     * The ID of the user to assign the workflow task.
     */
    assigneeId?: number;
    /**
     * An optional comment to add when assigning the workflow task.
     */
    comment?: string;
    /**
     * The date on which the workflow task should be executed.
     */
    dueDate?: string;
    workflowTaskId?: number;
};

