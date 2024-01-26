/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DocumentField } from './DocumentField';
import type { SearchRequest } from './SearchRequest';
export type SearchResponse = {
    errors?: Array<Record<string, string>>;
    page?: number;
    pageSize?: number;
    request?: Record<string, any>;
    requestString?: string;
    response?: Record<string, any>;
    responseString?: string;
    searchHits?: {
        hits?: Array<{
            documentFields?: Record<string, DocumentField>;
            explanation?: string;
            id?: string;
            score?: number;
            version?: number;
        }>;
        maxScore?: number;
        totalHits?: number;
    };
    searchRequest?: SearchRequest;
};

