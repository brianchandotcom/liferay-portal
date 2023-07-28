/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {AssetVocabularyCategoriesSelector} from 'asset-taglib';
import React from 'react';

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

export function CategorySelector({
	categorySelectorURL,
	eventName,
	groupIds,
	index = 0,
	namespace,
	onChange,
	rule,
	vocabularyIds,
}: Props) {
	const inputId = `${namespace}queryCategoryIds${index}`;
	const selectorId = `${namespace}assetCategoriesSelector${index}`;

	const groupIdsList =
		groupIds && !!groupIds.length ? groupIds.split(',') : [];

	const selectedItems =
		rule.queryValues && !!rule.queryValues.length
			? rule.queryValues.split(',').map((categoryId, index) => ({
					label: rule.categoryIdsTitles?.[index],
					value: categoryId,
			  }))
			: [];

	const sourceItemsVocabularyIds = vocabularyIds.length
		? vocabularyIds.split(',')
		: [];

	return (
		<div className="category-selector d-inline-block">
			<input name={inputId} type="hidden" value={rule.queryValues} />

			<AssetVocabularyCategoriesSelector
				eventName={eventName}
				groupIds={groupIdsList}
				id={selectorId}
				inputName={selectorId}
				label={Liferay.Language.get('categories')}
				onSelectedItemsChange={onChange}
				portletURL={categorySelectorURL}
				required={false}
				selectedItems={selectedItems}
				showVocabularyLabel={false}
				singleSelect={false}
				sourceItemsVocabularyIds={sourceItemsVocabularyIds}
				useFallbackInput={false}
			/>
		</div>
	);
}
