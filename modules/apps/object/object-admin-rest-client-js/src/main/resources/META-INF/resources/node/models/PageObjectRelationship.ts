/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Facet } from './Facet';
import type { ObjectRelationship } from './ObjectRelationship';
export type PageObjectRelationship = {
    actions?: Record<string, Record<string, string>>;
    facets?: Array<Facet>;
    items?: Array<ObjectRelationship>;
    lastPage?: number;
    pageSize?: number;
    page?: number;
    totalCount?: number;
};

