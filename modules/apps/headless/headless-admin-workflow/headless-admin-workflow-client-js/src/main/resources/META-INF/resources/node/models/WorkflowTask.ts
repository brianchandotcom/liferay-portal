/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Creator } from './Creator';
import type { ObjectReviewed } from './ObjectReviewed';
import type { Role } from './Role';
/**
 * Represents a task to be executed in a workflow.
 */
export type WorkflowTask = {
    readonly actions?: Record<string, Record<string, string>>;
    assigneePerson?: Creator;
    assigneeRoles?: Array<Role>;
    /**
     * A flag that indicates whether the task is complete.
     */
    readonly completed?: boolean;
    /**
     * The task's completion date.
     */
    readonly dateCompletion?: string;
    /**
     * The task's creation date.
     */
    readonly dateCreated?: string;
    /**
     * The date the task should be completed by.
     */
    readonly dateDue?: string;
    /**
     * The task's description.
     */
    readonly description?: string;
    /**
     * The task's ID.
     */
    readonly id?: number;
    readonly label?: string;
    /**
     * The task's name.
     */
    readonly name?: string;
    /**
     * The object/asset that the task's workflow is managing.
     */
    readonly objectReviewed?: ObjectReviewed;
    readonly workflowDefinitionId?: number;
    /**
     * The name of the task's workflow definition.
     */
    readonly workflowDefinitionName?: string;
    readonly workflowDefinitionVersion?: string;
    readonly workflowInstanceId?: number;
};

