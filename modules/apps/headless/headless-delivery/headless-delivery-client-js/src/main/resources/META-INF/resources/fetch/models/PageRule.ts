/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PageRuleAction } from './PageRuleAction';
import type { PageRuleCondition } from './PageRuleCondition';
/**
 * Represents a definition of a Page Rule.
 */
export type PageRule = {
    /**
     * The custom name of a Page rule.
     */
    conditionType?: 'All' | 'Any';
    /**
     * The page rule ID.
     */
    id?: string;
    /**
     * The custom name of a Page rule.
     */
    name?: string;
    /**
     * A list of actions of a Page rule.
     */
    pageRuleActions?: Array<PageRuleAction>;
    /**
     * A list of conditions of a Page rule.
     */
    pageRuleConditions?: Array<PageRuleCondition>;
};

