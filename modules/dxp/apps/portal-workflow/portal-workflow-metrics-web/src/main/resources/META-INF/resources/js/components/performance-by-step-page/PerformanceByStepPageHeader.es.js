/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayManagementToolbar from '@clayui/management-toolbar';
import React from 'react';

import filterConstants from '../../shared/components/filter/util/filterConstants.es';
import ResultsBar from '../../shared/components/results-bar/ResultsBar.es';
import SearchField from '../../shared/components/search-field/SearchField.es';
import ProcessVersionFilter from '../filter/ProcessVersionFilter.es';
import TimeRangeFilter from '../filter/TimeRangeFilter.es';

const hasFilterToShow = (selectedFilters = [], hideFilters = []) =>
	selectedFilters.filter(
		(selectedItem) =>
			!hideFilters.find((hideItem) => selectedItem.key === hideItem)
	).length > 0;

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
			<ClayManagementToolbar className="mb-0">
				<ClayManagementToolbar.ItemList>
					<ClayManagementToolbar.Item>
						<strong className="ml-0 mr-0 navbar-text">
							{Liferay.Language.get('filter-by')}
						</strong>
					</ClayManagementToolbar.Item>

					<ProcessVersionFilter
						filterKey={filterConstants.processVersion.key}
						options={{
							hideControl: true,
							multiple: false,
							withAllVersions: true,
						}}
						processId={routeParams.processId}
					/>
				</ClayManagementToolbar.ItemList>

				<SearchField
					disabled={false}
					placeholder={Liferay.Language.get('search-for-step-name')}
				/>

				<ClayManagementToolbar.ItemList>
					<TimeRangeFilter />
				</ClayManagementToolbar.ItemList>
			</ClayManagementToolbar>

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
