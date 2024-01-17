/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminContent_v1_0_PageRuleAction} from './HeadlessAdminContent_v1_0_PageRuleAction';
import type {HeadlessAdminContent_v1_0_PageRuleCondition} from './HeadlessAdminContent_v1_0_PageRuleCondition';

/**
 * A list of the page rules this page has.
 */
export type HeadlessAdminContent_v1_0_PageRule = {

	/**
	 * The custom name of a Page rule.
	 */
	'conditionType'?: 'All' | 'Any';

	/**
	 * The page rule ID.
	 */
	'id'?: string;

	/**
	 * The custom name of a Page rule.
	 */
	'name'?: string;

	/**
	 * A list of actions of a Page rule.
	 */
	'pageRuleActions'?: Array<HeadlessAdminContent_v1_0_PageRuleAction>;

	/**
	 * A list of conditions of a Page rule.
	 */
	'pageRuleConditions'?: Array<HeadlessAdminContent_v1_0_PageRuleCondition>;
	readonly 'x-class-name'?: string;
};
