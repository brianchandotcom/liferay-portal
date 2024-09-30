/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Facet } from './Facet';
import type { TestEntity } from './TestEntity';
export type PageTestEntity = {
    actions?: Record<string, Record<string, string>>;
    facets?: Array<Facet>;
    items?: Array<TestEntity>;
    lastPage?: number;
    pageSize?: number;
    page?: number;
    totalCount?: number;
};

