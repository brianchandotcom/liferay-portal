/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { OrderTypeChannel } from './OrderTypeChannel';
import type { Status } from './Status';
export type OrderType = {
    readonly actions?: Record<string, Record<string, string>>;
    active?: boolean;
    customFields?: Record<string, any>;
    description?: Record<string, string>;
    displayDate?: string;
    displayOrder?: number;
    expirationDate?: string;
    externalReferenceCode?: string;
    id?: number;
    name: Record<string, string>;
    neverExpire?: boolean;
    orderTypeChannels?: Array<OrderTypeChannel>;
    readonly workflowStatusInfo?: Status;
};

