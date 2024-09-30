/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Facet } from './Facet';
import type { ObjectLayout } from './ObjectLayout';
export type PageObjectLayout = {
    actions?: Record<string, Record<string, string>>;
    facets?: Array<Facet>;
    items?: Array<ObjectLayout>;
    lastPage?: number;
    pageSize?: number;
    page?: number;
    totalCount?: number;
};

