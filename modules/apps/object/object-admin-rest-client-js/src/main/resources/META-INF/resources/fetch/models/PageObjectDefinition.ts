/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Facet } from './Facet';
import type { ObjectDefinition } from './ObjectDefinition';
export type PageObjectDefinition = {
    actions?: Record<string, Record<string, string>>;
    facets?: Array<Facet>;
    items?: Array<ObjectDefinition>;
    lastPage?: number;
    pageSize?: number;
    page?: number;
    totalCount?: number;
};

