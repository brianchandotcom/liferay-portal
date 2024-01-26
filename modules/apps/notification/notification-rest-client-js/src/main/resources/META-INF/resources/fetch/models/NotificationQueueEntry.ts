/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type NotificationQueueEntry = {
    readonly actions?: Record<string, Record<string, string>>;
    body?: string;
    readonly fromName?: string;
    readonly id?: number;
    recipients?: Array<Record<string, any>>;
    readonly recipientsSummary?: string;
    readonly sentDate?: string;
    readonly status?: number;
    subject?: string;
    readonly triggerBy?: string;
    type?: string;
    readonly typeLabel?: string;
};

