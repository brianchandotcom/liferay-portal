/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ObjectReviewed } from './ObjectReviewed';
/**
 * Represents an instance to be executed in a workflow.
 */
export type WorkflowInstance = {
    readonly actions?: Record<string, Record<string, string>>;
    /**
     * A flag that indicates whether the instance is complete.
     */
    readonly completed?: boolean;
    /**
     * The instance's current node names.
     */
    readonly currentNodeNames?: Array<string>;
    /**
     * The instance's completion date.
     */
    readonly dateCompletion?: string;
    /**
     * The instance's creation date.
     */
    readonly dateCreated?: string;
    /**
     * The instance's ID.
     */
    readonly id?: number;
    /**
     * The object/asset that the instance's workflow is managing.
     */
    readonly objectReviewed?: ObjectReviewed;
    /**
     * The name of the instance's workflow definition.
     */
    readonly workflowDefinitionName?: string;
    readonly workflowDefinitionVersion?: string;
};

