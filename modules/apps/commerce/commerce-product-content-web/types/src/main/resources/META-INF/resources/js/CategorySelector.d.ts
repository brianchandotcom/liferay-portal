/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
