/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

/// <reference types="react" />

import {CategoryRule} from './CategorySelector';
import {TagRule} from './TagSelector';
export declare type RuleType = 'assetCategories' | 'assetTags';
export interface BaseRule {
	queryAndOperator: boolean;
	queryContains: boolean;
	type: RuleType;
}
export declare type Rule<T extends RuleType = RuleType> = T extends 'assetTags'
	? TagRule
	: T extends 'assetCategories'
	? CategoryRule
	: never;
export declare const QUERY_AND_OPERATOR_OPTIONS: {
	label: string;
	value: string;
}[];
export declare const QUERY_CONTAINS_OPTIONS: {
	label: string;
	value: string;
}[];
export declare const QUERY_NAME_OPTIONS: Array<{
	label: string;
	value: RuleType;
}>;
export interface RuleProps<T extends RuleType> {
	categorySelectorURL: string;
	groupIds: string;
	index: number;
	namespace: string;
	onChange: (index: number, changes: Partial<Rule>) => void;
	onDelete: (index: number) => void;
	rule: Rule<T>;
	tagSelectorURL: string;
	vocabularyIds: string;
}
export declare function Rule<T extends RuleType>({
	categorySelectorURL,
	groupIds,
	index,
	namespace,
	onChange,
	onDelete,
	rule,
	tagSelectorURL,
	vocabularyIds,
}: RuleProps<T>): JSX.Element;
