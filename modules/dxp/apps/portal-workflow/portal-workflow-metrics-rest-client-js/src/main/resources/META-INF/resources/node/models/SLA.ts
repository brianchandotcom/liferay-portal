/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { NodeKey } from './NodeKey';
/**
 * https://www.schema.org/SLA
 */
export type SLA = {
    calendarKey?: string;
    dateModified?: string;
    description?: string;
    duration?: number;
    id?: number;
    name?: string;
    pauseNodeKeys?: {
        nodeKeys?: Array<NodeKey>;
        status?: number;
    };
    processId?: number;
    startNodeKeys?: {
        nodeKeys?: Array<NodeKey>;
        status?: number;
    };
    status?: number;
    stopNodeKeys?: {
        nodeKeys?: Array<NodeKey>;
        status?: number;
    };
};

