/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Node } from './Node';
import type { Transition } from './Transition';
export type WorkflowDefinition = {
    readonly actions?: Record<string, Record<string, string>>;
    active?: boolean;
    content?: string;
    readonly dateCreated?: string;
    readonly dateModified?: string;
    readonly description?: string;
    readonly id?: number;
    name?: string;
    readonly nodes?: Array<Node>;
    title?: string;
    title_i18n?: Record<string, string>;
    readonly transitions?: Array<Transition>;
    version?: string;
};

