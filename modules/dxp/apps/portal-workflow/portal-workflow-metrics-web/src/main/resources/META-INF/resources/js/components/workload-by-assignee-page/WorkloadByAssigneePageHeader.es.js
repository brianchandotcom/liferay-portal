/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ManagementToolbar} from 'frontend-js-components-web';
import React from 'react';

import filterConstants from '../../shared/components/filter/util/filterConstants.es';
import ResultsBar from '../../shared/components/results-bar/ResultsBar.es';
import SearchField from '../../shared/components/search-field/SearchField.es';
import ProcessStepFilter from '../filter/ProcessStepFilter.es';
import RoleFilter from '../filter/RoleFilter.es';

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
