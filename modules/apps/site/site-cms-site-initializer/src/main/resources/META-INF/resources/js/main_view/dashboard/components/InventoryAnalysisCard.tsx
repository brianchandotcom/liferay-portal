/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Text} from '@clayui/core';
import React, {useContext, useEffect, useState} from 'react';

import ApiHelper from '../../../common/services/ApiHelper';
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

export const initialFilters = {
	category: {
		label: Liferay.Language.get('all-categories'),
		value: 'all',
	},
	structure: {
		label: Liferay.Language.get('all-structures'),
		value: 'all',
	},
	structureType: {
		label: Liferay.Language.get('category'),
		value: 'category',
	},
	tag: {
		label: Liferay.Language.get('all-tags'),
		value: 'all',
	},
	vocabulary: {
		label: Liferay.Language.get('all-vocabularies'),
		value: 'all',
	},
};

async function fetchStructureData({
	filters,
	language,
	space,
}: {
	filters: {
		category: Item;
		structure: Item;
		structureType: Item;
		tag: Item;
		vocabulary: Item;
	};
	language: Item;
	space: Item;
}) {
	const queryParams = buildQueryString({
		categoryId: filters.category?.value,
		depotEntryId: space?.value,
		groupBy: filters.structureType?.value,
		languageId: language?.value,
		structureId: filters.structure?.value,
		tagId: filters.tag?.value,
		vocabularyId: filters.vocabulary?.value,
	});

	const endpoint = `/o/analytics-cms-rest/v1.0/inventory-analysis${queryParams}`;

	const {data, error} =
		await ApiHelper.get<InventoryAnalysisDataType>(endpoint);

	if (error) {
		console.error(error);
	}

	if (data) {
		return data;
	}

	return null;
}

export function filterBySpaces(
	assetLibraries: {id: number}[],
	depotEntryId: string
) {
	return assetLibraries.some(({id}) => {

		// Returns true if id belongs to all spaces (-1).

		if (id === -1) {
			return true;
		}

		// Decreasing -1 due a bug where response is increasing +1 in the id.
		// Returns true if match id with id from space.

		return String(id - 1) === depotEntryId;
	});
}

export function InventoryAnalysisCard() {
	const {
		filters: {language, space},
	} = useContext(ViewDashboardContext);

	const [filters, setFilters] = useState<{
		category: Item;
		structure: Item;
		structureType: Item;
		tag: Item;
		vocabulary: Item;
	}>(initialFilters);

	const [inventoryAnalysisData, setInventoryAnalysisData] =
		useState<InventoryAnalysisDataType>();

	useEffect(() => {
		setFilters(initialFilters);
	}, [space?.value]);

	useEffect(() => {
		async function fetchData() {
			const data = await fetchStructureData({filters, language, space});

			if (data) {
				setInventoryAnalysisData(data);
			}
		}

		fetchData();
	}, [filters, language, space]);

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
							item={filters.structureType}
							onSelectItem={(structureType) =>
								setFilters({...filters, structureType})
							}
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
									item={filters.structure}
									onSelectItem={(structure) =>
										setFilters({
											...filters,
											structure,
										})
									}
								/>
							</div>

							<div className="mb-2 mb-lg-0 mr-2">
								<AllVocabulariesDropdown
									item={filters.vocabulary}
									onSelectItem={(vocabulary) => {
										setFilters({
											...filters,
											vocabulary,
										});
									}}
								/>
							</div>

							<div className="mb-2 mb-lg-0 mr-2">
								<AllCategoriesDropdown
									item={filters.category}
									onSelectItem={(category) => {
										setFilters({
											...filters,
											category,
										});
									}}
								/>
							</div>

							<div className="mb-2 mb-lg-0">
								<AllTagsDropdown
									item={filters.tag}
									onSelectItem={(tag) =>
										setFilters({
											...filters,
											tag,
										})
									}
								/>
							</div>
						</div>
					</div>
				</div>

				<PaginatedTable
					currentStructureTypeLabel={filters.structureType.label}
					inventoryAnalysisData={inventoryAnalysisData}
				/>
			</BaseCard>
		</div>
	);
}
