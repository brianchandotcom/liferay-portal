/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ManagementToolbar} from 'frontend-js-components-web';
import React from 'react';

import filterConstants from '../../shared/components/filter/util/filterConstants.es';
import ResultsBar from '../../shared/components/results-bar/ResultsBar.es';
import SearchField from '../../shared/components/search-field/SearchField.es';
import ProcessVersionFilter from '../filter/ProcessVersionFilter.es';
import TimeRangeFilter from '../filter/TimeRangeFilter.es';

const hasFilterToShow = (selectedFilters = [], hideFilters = []) =>
	!!selectedFilters.filter(
		(selectedItem) =>
			!hideFilters.find((hideItem) => selectedItem.key === hideItem)
	).length;

export default function Header({
	filterKeys,
	hideFilters = [],
	routeParams,
	selectedFilters,
	totalCount,
}) {
	const showFiltersResult =
		routeParams.search || hasFilterToShow(selectedFilters, hideFilters);

	return (
		<>
			<ManagementToolbar.Container className="mb-0">
				<ManagementToolbar.ItemList>
					<ManagementToolbar.Item>
						<strong className="ml-0 mr-0 navbar-text">
							{Liferay.Language.get('filter-by')}
						</strong>
					</ManagementToolbar.Item>

					<ProcessVersionFilter
						filterKey={filterConstants.processVersion.key}
						options={{
							hideControl: true,
							multiple: false,
							withAllVersions: true,
						}}
						processId={routeParams.processId}
					/>
				</ManagementToolbar.ItemList>

				<SearchField
					disabled={false}
					placeholder={Liferay.Language.get('search-for-step-name')}
				/>

				<ManagementToolbar.ItemList>
					<TimeRangeFilter />
				</ManagementToolbar.ItemList>
			</ManagementToolbar.Container>

			{showFiltersResult && (
				<ResultsBar>
					<ResultsBar.TotalCount
						search={routeParams.search}
						totalCount={totalCount}
					/>

					<ResultsBar.FilterItems
						filters={selectedFilters}
						hideFilters={hideFilters}
						{...routeParams}
					/>

					<ResultsBar.Clear
						filterKeys={filterKeys}
						filters={selectedFilters}
						{...routeParams}
					/>
				</ResultsBar>
			)}
		</>
	);
}
