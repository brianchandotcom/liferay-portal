/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
