/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import React, {
	useCallback,
	useContext,
	useEffect,
	useMemo,
	useState,
} from 'react';

import ApiHelper from '../../../services/ApiHelper';
import {ViewDashboardContext} from '../ViewDashboardContext';
import {buildQueryString} from '../utils/buildQueryString';
import {AllCategoriesDropdown} from './AllCategoriesDropdown';
import {AllStructureTypesDropdown} from './AllStructureTypesDropdown';
import {AllTagsDropdown} from './AllTagsDropdown';
import {AllVocabulariesDropdown} from './AllVocabulariesDropdown';
import {BaseCard} from './BaseCard';
import {Item} from './FilterDropdown';
import {GroupByDropdown} from './GroupByDropdown';
import PaginatedTable from './PaginatedTable';

export interface IAllFiltersDropdown extends React.HTMLAttributes<HTMLElement> {
	item: Item;
	onSelectItem: (item: Item) => void;
}

export type InventoryAnalysisDataType = {
	inventoryAnalysisItems: {count: number; key: string; title: string}[];
	totalCount: number;
};

export const initialStructureType = {
	label: Liferay.Language.get('category'),
	value: 'category',
};

export const initialCategory = {
	label: Liferay.Language.get('all-categories'),
	value: 'all',
};

export const initialStructure = {
	label: Liferay.Language.get('all-structures'),
	value: 'all',
};

export const initialTag = {
	label: Liferay.Language.get('all-tags'),
	value: 'all',
};

export const initialVocabulary = {
	label: Liferay.Language.get('all-vocabularies'),
	value: 'all',
};

export function InventoryAnalysisCard() {
	const {
		filters: {language, space},
	} = useContext(ViewDashboardContext);

	const [category, setCategory] = useState<Item>(initialCategory);
	const [structure, setStructure] = useState<Item>(initialStructure);
	const [structureType, setStructureType] =
		useState<Item>(initialStructureType);
	const [inventoryAnalysisData, setInventoryAnalysisData] =
		useState<InventoryAnalysisDataType>();
	const [tag, setTag] = useState<Item>(initialTag);
	const [vocabulary, setVocabulary] = useState<Item>(initialVocabulary);

	const params = useMemo(
		() => ({
			categoryId: category?.value,
			groupBy: structureType?.value,
			languageId: language?.value,
			spaceId: space?.value,
			structureId: structure?.value,
			vocabularyId: vocabulary?.value,
		}),
		[category, language, space, structure, structureType, vocabulary]
	);

	const fetchStructureData = useCallback(async () => {
		const filteredParams = Object.fromEntries(
			Object.entries(params).filter(
				([, value]) => value !== null && value !== ''
			)
		);
		const queryParams = buildQueryString(filteredParams);
		const endpoint = `/o/analytics-cms-rest/v1.0/inventory-analysis${queryParams}`;

		const {data, error} =
			await ApiHelper.get<InventoryAnalysisDataType>(endpoint);

		if (data) {
			setInventoryAnalysisData({...data});
		}
		if (error) {
			console.error(error);
		}
	}, [params]);

	useEffect(() => {
		setCategory(initialCategory);
		setStructure(initialStructure);
		setTag(initialTag);
		setVocabulary(initialVocabulary);
	}, [space?.value]);

	useEffect(() => {
		fetchStructureData();
	}, [fetchStructureData]);

	return (
		<div className="cms-dashboard__inventory-analysis">
			<BaseCard
				description={Liferay.Language.get(
					'this-report-provides-a-breakdown-of-total-assets-by-categorization,-structure-type,-or-space'
				)}
				title={Liferay.Language.get('inventory-analysis')}
			>
				<div className="align-items-lg-center d-flex flex-column flex-lg-row">
					<div className="align-items-center d-flex mb-2 mb-md-0 mr-md-4">
						<span className="mr-2">
							<Text size={3} weight="semi-bold">
								{Liferay.Language.get('group-by')}
							</Text>
						</span>

						<GroupByDropdown
							item={structureType}
							onSelectItem={setStructureType}
						/>
					</div>

					<div className="d-flex flex-md-row flex-row flex-xs-column">
						<div className="align-items-center d-flex mb-2 mb-lg-0 mr-lg-3">
							<span className="align-self-lg-auto align-self-start mr-2">
								<Text size={3} weight="semi-bold">
									{Liferay.Language.get('filter-by')}
								</Text>
							</span>
						</div>

						<div className="d-flex flex-wrap">
							<div className="mb-2 mb-lg-0 mr-2">
								<AllStructureTypesDropdown
									item={structure}
									onSelectItem={setStructure}
								/>
							</div>

							<div className="mb-2 mb-lg-0 mr-2">
								<AllVocabulariesDropdown
									item={vocabulary}
									onSelectItem={setVocabulary}
								/>
							</div>

							<div className="mb-2 mb-lg-0 mr-2">
								<AllCategoriesDropdown
									item={category}
									onSelectItem={setCategory}
								/>
							</div>

							<div className="mb-2 mb-lg-0">
								<AllTagsDropdown
									item={tag}
									onSelectItem={setTag}
								/>
							</div>
						</div>
					</div>
				</div>

				<PaginatedTable
					currentStructureTypeLabel={structureType.label}
					inventoryAnalysisData={inventoryAnalysisData}
				/>
			</BaseCard>
		</div>
	);
}
