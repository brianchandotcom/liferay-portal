/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Mapping } from './Mapping';
import type { Policy } from './Policy';
export type Plan = {
    active?: boolean;
    export?: boolean;
    externalType?: string;
    externalURL?: string;
    id?: number;
    internalClassName?: string;
    readonly internalClassNameKey?: string;
    mappings?: Array<Mapping>;
    name?: string;
    policies?: Array<Policy>;
    size?: number;
    status?: number;
    taskItemDelegateName?: string;
    template?: boolean;
    total?: number;
};

