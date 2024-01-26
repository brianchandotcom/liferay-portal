/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents a write-only schema to assign a workflow task to a specific role.
 */
export type WorkflowTaskAssignToRole = {
    /**
     * An optional comment to add when assigning the workflow task.
     */
    comment?: string;
    /**
     * The date on which the workflow task should be executed.
     */
    dueDate?: string;
    /**
     * The ID of the role to assign the workflow task.
     */
    roleId?: number;
};

