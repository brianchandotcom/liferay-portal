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

import ResultsBar from '../../shared/components/results-bar/ResultsBar.es';
import SearchField from '../../shared/components/search-field/SearchField.es';
import TimeRangeFilter from '../filter/TimeRangeFilter.es';

const Header = ({filterKeys, routeParams, totalCount}) => {
	return (
		<>
			<ClayManagementToolbar className="mb-0">
				<ClayManagementToolbar.Item>
					<strong className="ml-0 mr-0 navbar-text">
						{Liferay.Language.get('filter-by')}
					</strong>
				</ClayManagementToolbar.Item>

				<div className="navbar-form-autofit">
					<SearchField
						disabled={false}
						placeholder={Liferay.Language.get(
							'search-for-step-name'
						)}
					/>
				</div>

				<TimeRangeFilter
					buttonClassName="btn-flat btn-sm"
					options={{position: 'right'}}
				/>
			</ClayManagementToolbar>

			{routeParams.search && (
				<ResultsBar>
					<>
						<ResultsBar.TotalCount
							search={routeParams.search}
							totalCount={totalCount}
						/>

						<ResultsBar.Clear
							filterKeys={filterKeys}
							{...routeParams}
						/>
					</>
				</ResultsBar>
			)}
		</>
	);
};

export {Header};
