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

import {ManagementToolbar} from 'frontend-js-components-web';
import React from 'react';

import filterConstants from '../../shared/components/filter/util/filterConstants.es';
import ResultsBar from '../../shared/components/results-bar/ResultsBar.es';
import SearchField from '../../shared/components/search-field/SearchField.es';
import ProcessStepFilter from '../filter/ProcessStepFilter.es';
import RoleFilter from '../filter/RoleFilter.es';
import TimeRangeFilter from '../filter/TimeRangeFilter.es';

export default function Header({
	filterKeys,
	routeParams,
	selectedFilters,
	totalCount,
}) {
	const showFiltersResult = routeParams.search || !!selectedFilters.length;

	return (
		<>
			<ManagementToolbar.Container className="mb-0">
				<ManagementToolbar.ItemList>
					<ManagementToolbar.Item>
						<strong className="ml-0 mr-0 navbar-text">
							{Liferay.Language.get('filter-by')}
						</strong>
					</ManagementToolbar.Item>

					<RoleFilter
						completed={true}
						filterKey={filterConstants.roles.key}
						processId={routeParams.processId}
					/>

					<ProcessStepFilter
						filterKey={filterConstants.processStep.key}
						processId={routeParams.processId}
					/>
				</ManagementToolbar.ItemList>

				<SearchField
					disabled={false}
					placeholder={Liferay.Language.get(
						'search-for-assignee-name'
					)}
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
