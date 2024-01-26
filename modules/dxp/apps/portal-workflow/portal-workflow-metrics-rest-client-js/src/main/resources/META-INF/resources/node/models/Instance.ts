/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Assignee } from './Assignee';
import type { Creator } from './Creator';
import type { SLAResult } from './SLAResult';
import type { Transition } from './Transition';
/**
 * https://www.schema.org/Instance
 */
export type Instance = {
    active?: boolean;
    readonly assetTitle?: string;
    assetTitle_i18n?: Record<string, string>;
    readonly assetType?: string;
    assetType_i18n?: Record<string, string>;
    readonly assignees?: Array<Assignee>;
    className?: string;
    classPK?: number;
    completed?: boolean;
    creator?: Creator;
    dateCompletion?: string;
    dateCreated?: string;
    dateModified?: string;
    duration?: number;
    id?: number;
    processId?: number;
    processVersion?: string;
    readonly slaResults?: Array<SLAResult>;
    readonly slaStatus?: 'OnTime' | 'Overdue' | 'Untracked';
    readonly taskNames?: Array<string>;
    transitions?: Array<Transition>;
};

