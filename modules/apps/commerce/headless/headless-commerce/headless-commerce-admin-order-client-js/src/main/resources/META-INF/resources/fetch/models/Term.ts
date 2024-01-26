/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Status } from './Status';
import type { TermOrderType } from './TermOrderType';
export type Term = {
    readonly actions?: Record<string, Record<string, string>>;
    active?: boolean;
    createDate?: string;
    description?: Record<string, string>;
    displayDate?: string;
    expirationDate?: string;
    externalReferenceCode?: string;
    id?: number;
    label?: Record<string, string>;
    name: string;
    neverExpire?: boolean;
    priority?: number;
    termOrderType?: Array<TermOrderType>;
    type: string;
    typeLocalized?: string;
    typeSettings?: string;
    readonly workflowStatusInfo?: Status;
};

