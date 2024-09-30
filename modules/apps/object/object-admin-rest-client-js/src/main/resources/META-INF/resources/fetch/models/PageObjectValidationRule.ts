/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Facet } from './Facet';
import type { ObjectValidationRule } from './ObjectValidationRule';
export type PageObjectValidationRule = {
    actions?: Record<string, Record<string, string>>;
    facets?: Array<Facet>;
    items?: Array<ObjectValidationRule>;
    lastPage?: number;
    pageSize?: number;
    page?: number;
    totalCount?: number;
};

