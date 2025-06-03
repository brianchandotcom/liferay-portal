/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {useContext, useState} from 'react';

import ApiHelper from '../../../services/ApiHelper';
import {ViewDashboardContext} from '../ViewDashboardContext';
import {buildQueryString} from '../utils/buildQueryString';
import {FilterDropdown, Item} from './FilterDropdown';
import {IAllFiltersDropdown, initialVocabulary} from './InventoryAnalysisCard';

const AllVocabulariesDropdown: React.FC<IAllFiltersDropdown> = ({
	className,
	item,
	onSelectItem,
}) => {
	const {
		filters: {space},
	} = useContext(ViewDashboardContext);

	const [vocabularies, setVocabularies] = useState<Item[]>([
		initialVocabulary,
	]);

	const [dropdownActive, setDropdownActive] = useState(false);
	const [loading, setLoading] = useState(false);

	const fetchVocabularies = async (search: string = '') => {
		const queryParams = buildQueryString({
			search,
		});

		const endpoint = `/o/headless-admin-taxonomy/v1.0/taxonomy-vocabularies${queryParams}`;

		const {data, error} = await ApiHelper.get<{
			items: {assetLibraries: {id: number}[]; id: string; name: string}[];
		}>(endpoint);

		if (data) {
			return data.items
				.filter(({assetLibraries}) => {
					if (space.value === 'all') {
						return true;
					}

					// Decreasing 1 on id due a bug on Objects

					return assetLibraries.some(
						({id}) => String(id - 1) === space.value
					);
				})
				.map(({id, name}) => ({
					label: name,
					value: String(id),
				}));
		}

		if (error) {
			console.error(error);
		}

		return [];
	};

	return (
		<FilterDropdown
			active={dropdownActive}
			className={className}
			filterByValue="vocabularies"
			icon="vocabulary"
			items={vocabularies}
			loading={loading}
			onActiveChange={() => setDropdownActive(!dropdownActive)}
			onSearch={async (value) => {
				setLoading(true);

				const vocabularies = await fetchVocabularies(value);

				setVocabularies(
					value ? vocabularies : [initialVocabulary, ...vocabularies]
				);

				setLoading(false);
			}}
			onSelectItem={(item) => {
				onSelectItem(item);

				setDropdownActive(false);
			}}
			onTrigger={async () => {
				setLoading(true);

				const vocabularies = await fetchVocabularies();

				setVocabularies([initialVocabulary, ...vocabularies]);

				setLoading(false);
			}}
			selectedItem={item}
			title={Liferay.Language.get('filter-by-vocabulary')}
		/>
	);
};

export {AllVocabulariesDropdown};
