/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayAlert from '@clayui/alert';
import ClayEmptyState from '@clayui/empty-state';
import {SearchResultsMessage} from '@liferay/layout-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useMemo, useState} from 'react';

import {Fragment, FragmentFilter} from '../../constants/Fragment';
import Filter from './Filter';
import FragmentList from './FragmentList';
import ResultsBar from './ResultsBar';
import getFragmentsByFilterValue from './getFragmentsByFilterValue';

export default function RenderTimes({url}: {url: string}) {
	const [ascending, setAscending] = useState(false);
	const [filters, setFilters] = useState<FragmentFilter>({
		origin: null,
		status: null,
		type: null,
	});
	const [fragments, setFragments] = useState<Fragment[]>([]);
	const [searchValue, setSearchValue] = useState('');
	const [visibleInfo, setVisibleInfo] = useState<boolean>(true);

	const filteredFragments = useMemo(() => {
		const fragmentsByFilterValue = getFragmentsByFilterValue(
			filters,
			fragments
		);

		const fragmentsBySearchValue = searchValue
			? fragmentsByFilterValue.filter(
					(fragment) =>
						fragment.name.toLowerCase().indexOf(searchValue) !== -1
			  )
			: fragmentsByFilterValue;

		return fragmentsBySearchValue;
	}, [fragments, searchValue, filters]);

	useEffect(() => {
		fetch(url, {method: 'GET'})
			.then((response) => response.json())
			.then((fragments) => setFragments(fragments))
			.catch((error) => console.error(error));
	}, [url]);

	return (
		<>
			<Filter
				filters={filters}
				isAscendingSort={ascending}
				onFilterValue={setFilters}
				onSearchValue={setSearchValue}
				onSort={setAscending}
			/>

			<SearchResultsMessage numberOfResults={filteredFragments.length} />

			<ResultsBar
				className="c-mb-3"
				filters={filters}
				fragments={filteredFragments}
				onSetFilters={setFilters}
			/>

			{visibleInfo ? (
				<ClayAlert
					className="c-mb-4"
					displayType="info"
					onClose={() => setVisibleInfo(false)}
					role="none"
				>
					{Liferay.Language.get(
						'render-times-are-approximate-and-subject-to-slight-variations-due-to-server-status-and-load'
					)}
				</ClayAlert>
			) : null}

			{filteredFragments.length ? (
				<FragmentList
					ascendingSort={ascending}
					fragments={filteredFragments}
				/>
			) : (
				<ClayEmptyState
					description={Liferay.Language.get(
						'try-again-with-a-different-search'
					)}
					imgSrc={`${Liferay.ThemeDisplay.getPathThemeImages()}/states/search_state.gif`}
					small
					title={Liferay.Language.get('no-results-found')}
				/>
			)}
		</>
	);
}
