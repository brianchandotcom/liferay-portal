/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Facet } from './Facet';
import type { ObjectView } from './ObjectView';
export type PageObjectView = {
    actions?: Record<string, Record<string, string>>;
    facets?: Array<Facet>;
    items?: Array<ObjectView>;
    lastPage?: number;
    pageSize?: number;
    page?: number;
    totalCount?: number;
};

