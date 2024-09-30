/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Facet } from './Facet';
import type { ObjectField } from './ObjectField';
export type PageObjectField = {
    actions?: Record<string, Record<string, string>>;
    facets?: Array<Facet>;
    items?: Array<ObjectField>;
    lastPage?: number;
    pageSize?: number;
    page?: number;
    totalCount?: number;
};

