/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
/**
 * https://www.schema.org/SLA
 */
export type SLAResult = {
    dateModified?: string;
    dateOverdue?: string;
    id?: number;
    name?: string;
    onTime?: boolean;
    remainingTime?: number;
    status?: 'NEW' | 'PAUSED' | 'RUNNING' | 'STOPPED';
};

