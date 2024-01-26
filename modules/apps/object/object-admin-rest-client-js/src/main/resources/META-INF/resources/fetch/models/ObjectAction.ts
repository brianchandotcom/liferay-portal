/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Status } from './Status';
export type ObjectAction = {
    readonly actions?: Record<string, Record<string, string>>;
    active?: boolean;
    conditionExpression?: string;
    readonly dateCreated?: string;
    readonly dateModified?: string;
    description?: string;
    errorMessage?: Record<string, string>;
    externalReferenceCode?: string;
    readonly id?: number;
    label?: Record<string, string>;
    name?: string;
    objectActionExecutorKey?: string;
    objectActionTriggerKey?: string;
    parameters?: Record<string, any>;
    readonly status?: Status;
    system?: boolean;
};

