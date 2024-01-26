/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ObjectLayoutRow } from './ObjectLayoutRow';
export type ObjectLayoutBox = {
    collapsable?: boolean;
    readonly id?: number;
    name?: Record<string, string>;
    objectLayoutRows?: Array<ObjectLayoutRow>;
    priority?: number;
    type?: 'categorization' | 'regular';
};

