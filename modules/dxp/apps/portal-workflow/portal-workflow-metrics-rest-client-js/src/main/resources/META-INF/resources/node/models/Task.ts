/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Assignee } from './Assignee';
/**
 * https://www.schema.org/Task
 */
export type Task = {
    readonly assetTitle?: string;
    assetTitle_i18n?: Record<string, string>;
    readonly assetType?: string;
    assetType_i18n?: Record<string, string>;
    assignee?: Assignee;
    className?: string;
    classPK?: number;
    completed?: boolean;
    completionUserId?: number;
    dateCompletion?: string;
    dateCreated?: string;
    dateModified?: string;
    duration?: number;
    id?: number;
    instanceId?: number;
    readonly label?: string;
    name?: string;
    nodeId?: number;
    processId?: number;
    processVersion?: string;
};

