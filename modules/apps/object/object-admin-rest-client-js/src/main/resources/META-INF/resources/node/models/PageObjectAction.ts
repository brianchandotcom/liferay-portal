/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Facet } from './Facet';
import type { ObjectAction } from './ObjectAction';
export type PageObjectAction = {
    actions?: Record<string, Record<string, string>>;
    facets?: Array<Facet>;
    items?: Array<ObjectAction>;
    lastPage?: number;
    pageSize?: number;
    page?: number;
    totalCount?: number;
};

