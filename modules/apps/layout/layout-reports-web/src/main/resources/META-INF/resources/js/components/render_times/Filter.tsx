/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayButtonWithIcon} from '@clayui/button';
import {ClayDropDownWithItems} from '@clayui/drop-down';
import {SearchForm} from '@liferay/layout-js-components-web';
import {sub} from 'frontend-js-web';
import React, {Dispatch, SetStateAction} from 'react';

import {
	Entries,
	FILTER_NAMES,
	FILTER_TYPE_NAMES,
	FRAGMENT_FILTERS,
	FragmentFilter,
} from '../../constants/Fragment';

interface Props {
	filters: FragmentFilter;
	isAscendingSort: boolean;
	onFilterValue: Dispatch<SetStateAction<FragmentFilter>>;
	onSearchValue: Dispatch<SetStateAction<string>>;
	onSort: Dispatch<SetStateAction<boolean>>;
}

export default function Filter({
	filters,
	isAscendingSort,
	onFilterValue,
	onSearchValue,
	onSort,
}: Props) {
	const sortLabel = sub(
		Liferay.Language.get('x-sort-fragments-by-render-time'),
		isAscendingSort
			? Liferay.Language.get('descending')
			: Liferay.Language.get('ascending')
	);

	const items = (Object.entries(FRAGMENT_FILTERS) as Entries<
		typeof FRAGMENT_FILTERS
	>).map(([filterType, filterValues]) => ({
		items: filterValues.map((filterValue) => ({
			active: filters[filterType] === filterValue,
			label: FILTER_NAMES[filterValue],
			onClick: () => {
				onFilterValue((previousFilter) => ({
					...previousFilter,
					[filterType]: filterValue,
				}));
			},
			value: filterValue,
		})),
		label: FILTER_TYPE_NAMES[filterType],
		type: 'group' as const,
	}));

	return (
		<div className="d-flex pt-1">
			<SearchForm
				className="flex-grow-1"
				label={Liferay.Language.get('search-fragments')}
				onChange={onSearchValue}
			/>

			<ClayDropDownWithItems
				items={items}
				trigger={
					<ClayButtonWithIcon
						aria-label={Liferay.Language.get('filter')}
						borderless
						className="ml-2 mt-0"
						displayType="secondary"
						size="sm"
						symbol="filter"
						title={Liferay.Language.get('filter')}
					/>
				}
			/>

			<ClayButtonWithIcon
				aria-label={sortLabel}
				borderless
				className="ml-2 mt-0"
				displayType="secondary"
				onClick={() => onSort(!isAscendingSort)}
				size="sm"
				symbol={isAscendingSort ? 'order-list-up' : 'order-list-down'}
				title={sortLabel}
			/>
		</div>
	);
}
