/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Creator } from './Creator';
import type { Role } from './Role';
/**
 * Represents the log containing the workflow's activity history (e.g., transitions, assignees, etc.).
 */
export type WorkflowLog = {
    /**
     * The user account of the person auditing the workflow.
     */
    readonly auditPerson?: Creator;
    /**
     * The log's comments.
     */
    readonly commentLog?: string;
    /**
     * The log's creation date.
     */
    readonly dateCreated?: string;
    /**
     * The log's description.
     */
    readonly description?: string;
    /**
     * The log's ID.
     */
    readonly id?: number;
    /**
     * The person assigned to the workflow.
     */
    readonly person?: Creator;
    /**
     * The previous person assigned to the workflow.
     */
    readonly previousPerson?: Creator;
    readonly previousRole?: Role;
    /**
     * The workflow's previous state.
     */
    readonly previousState?: string;
    /**
     * The workflow's previous state Label.
     */
    readonly previousStateLabel?: string;
    readonly role?: Role;
    /**
     * The workflow's current state.
     */
    readonly state?: string;
    /**
     * The workflow's current state Label.
     */
    readonly stateLabel?: string;
    /**
     * The workflow log's type.
     */
    readonly type?: 'NodeEntry' | 'TaskAssign' | 'TaskCompletion' | 'TaskUpdate' | 'Transition';
    /**
     * The task associated with this workflow log.
     */
    readonly workflowTaskId?: number;
};

