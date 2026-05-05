/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	AssetTagsSelector,
	AssetVocabularyCategoriesSelector,
} from 'asset-taglib';
import React from 'react';

import {DefaultValueInput} from './condition_builder/DefaultValueInput';

import type {ValueInputRenderer} from './condition_builder/types';

interface SelectedItem {
	label: string;
	value: string;
}

/**
 * Builds a `renderValueInput` for the Collection condition builder.
 *
 * Handles the two virtual asset properties (`assetTags`, `assetCategories`)
 * by rendering the asset-taglib selectors. All other property types fall
 * through to the generic DefaultValueInput.
 */
export default function createValueInput({
	categorySelectorURL,
	groupIds,
	namespace,
	tagSelectorURL,
	vocabularyIds,
}: {
	categorySelectorURL?: string;
	groupIds?: string[];
	namespace: string;
	tagSelectorURL?: string;
	vocabularyIds?: string[];
}): ValueInputRenderer {
	return (index, property, operator, value, onChange) => {
		if (property.type === 'asset-tags') {
			return (
				<AssetTagsSelector
					eventName={`${namespace}selectTag`}
					formGroupClassName="condition-builder__asset-selector mb-0"
					groupIds={groupIds}
					inputName={`${namespace}queryTagNames`}
					onSelectedItemsChange={(selectedItems: SelectedItem[]) =>
						onChange(
							selectedItems.map(({label, value}) => ({
								label,
								value,
							}))
						)
					}
					portletURL={tagSelectorURL}
					selectedItems={value}
					showLabel={false}
					showSelectButton
					showSubtitle={false}
					tagNames={value || ''}
				/>
			);
		}

		if (property.type === 'asset-categories') {
			return (
				<AssetVocabularyCategoriesSelector
					categoryIds={value || ''}
					eventName={`${namespace}selectCategory`}
					formGroupClassName="condition-builder__asset-selector mb-0"
					groupIds={groupIds}
					inputName={`${namespace}queryCategoryIds`}
					onSelectedItemsChange={(selectedItems: SelectedItem[]) =>
						onChange(
							selectedItems.map(({label, value}) => ({
								label,
								value,
							}))
						)
					}
					portletURL={categorySelectorURL}
					selectedItems={value}
					sourceItemsVocabularyIds={vocabularyIds}
				/>
			);
		}

		return DefaultValueInput(index, property, operator, value, onChange);
	};
}
