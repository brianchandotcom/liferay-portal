/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * Represents a node in a workflow.
 */
export type Node = {
    readonly label?: string;
    readonly name?: string;
    /**
     * The workflow's node types.
     */
    readonly type?: 'CONDITION' | 'FORK' | 'INITIAL_STATE' | 'JOIN' | 'JOIN_XOR' | 'STATE' | 'TASK' | 'TERMINAL_STATE';
};

