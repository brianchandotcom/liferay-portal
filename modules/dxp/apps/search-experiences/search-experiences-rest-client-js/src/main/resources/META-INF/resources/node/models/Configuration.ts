/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Clause } from './Clause';
import type { Condition } from './Condition';
import type { HighlightField } from './HighlightField';
import type { InnerHit } from './InnerHit';
import type { Parameter } from './Parameter';
export type Configuration = {
    advancedConfiguration?: {
        collapse?: {
            field?: string;
            innerHits?: Array<InnerHit>;
            maxConcurrentGroupRequests?: number;
        };
        source?: {
            excludes?: Array<string>;
            fetchSource?: boolean;
            includes?: Array<string>;
        };
        stored_fields?: Array<string>;
    };
    aggregationConfiguration?: {
        aggs?: Record<string, any>;
    };
    generalConfiguration?: {
        clauseContributorsExcludes?: Array<string>;
        clauseContributorsIncludes?: Array<string>;
        emptySearchEnabled?: boolean;
        explain?: boolean;
        includeResponseString?: boolean;
        languageId?: string;
        queryString?: string;
        searchableAssetTypes?: Array<string>;
        timeZoneId?: string;
    };
    highlightConfiguration?: {
        fields?: Record<string, HighlightField>;
        fragment_size?: number;
        number_of_fragments?: number;
        post_tags?: Array<string>;
        pre_tags?: Array<string>;
        require_field_match?: boolean;
        type?: string;
    };
    indexConfiguration?: {
        external?: boolean;
        indexName?: string;
    };
    parameterConfiguration?: {
        parameters?: Record<string, Parameter>;
    };
    queryConfiguration?: {
        applyIndexerClauses?: boolean;
        queryEntries?: Array<{
            clauses?: Array<Clause>;
            condition?: Condition;
            enabled?: boolean;
            postFilterClauses?: Array<Clause>;
            rescores?: Array<{
                query?: Record<string, any>;
                queryWeight?: Record<string, any>;
                rescoreQueryWeight?: Record<string, any>;
                scoreMode?: string;
                windowSize?: Record<string, any>;
            }>;
        }>;
    };
    searchContextAttributes?: Record<string, Record<string, any>>;
    sortConfiguration?: {
        sorts?: Record<string, any>;
    };
};

