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

import {BaseRule} from './Rule';
export interface Category {
	label: string;
	value: string;
}
export interface CategoryRule extends BaseRule {
	categoryIdsTitles?: string[];
	queryValues?: string;
	type: 'assetCategories';
}
interface Props {
	categorySelectorURL: string;
	eventName: string;
	groupIds: string;
	index: number;
	namespace: string;
	onChange: (categories: Category[]) => void;
	rule: CategoryRule;
	vocabularyIds: string;
}
export declare function CategorySelector({
	categorySelectorURL,
	eventName,
	groupIds,
	index,
	namespace,
	onChange,
	rule,
	vocabularyIds,
}: Props): JSX.Element;
export {};
