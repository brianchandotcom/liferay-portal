/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Facet } from './Facet';
import type { ObjectFolder } from './ObjectFolder';
export type PageObjectFolder = {
    actions?: Record<string, Record<string, string>>;
    facets?: Array<Facet>;
    items?: Array<ObjectFolder>;
    lastPage?: number;
    pageSize?: number;
    page?: number;
    totalCount?: number;
};

