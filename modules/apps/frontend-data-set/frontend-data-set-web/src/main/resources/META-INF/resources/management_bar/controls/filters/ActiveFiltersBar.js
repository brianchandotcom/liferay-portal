/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import {sub} from 'frontend-js-web';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import FrontendDataSetContext from '../../../FrontendDataSetContext';
import ViewsContext from '../../../views/ViewsContext';
import {VIEWS_ACTION_TYPES} from '../../../views/viewsReducer';
import FilterResume from './FilterResume';
import SearchResume from './SearchResume';

function ActiveFiltersBar({dataLoading, disabled, total}) {
	const {searchParam} = useContext(FrontendDataSetContext);
	const [{filters}, viewsDispatch] = useContext(ViewsContext);

	const isSearchActive = () => searchParam?.trim();

	const resetFiltersValue = () => {
		viewsDispatch({
			type: VIEWS_ACTION_TYPES.UPDATE_FILTERS,
			value: filters.map((filter) => ({
				...filter,
				active: false,
				odataFilterString: undefined,
				selectedData: undefined,
			})),
		});
	};

	const activeFilters = filters.filter((filter) => filter.active);

	return activeFilters.length || isSearchActive() ? (
		<div className="management-bar management-bar-light navbar navbar-expand-md">
			<div className="container-fluid">
				<nav className="mb-0 py-3 subnav-tbar subnav-tbar-light subnav-tbar-primary w-100">
					<ul className="tbar-nav">
						<li className="p-0 tbar-item tbar-item-expand">
							<div className="tbar-section">
								{Liferay.FeatureFlags['LPD-52212'] &&
									!dataLoading && (
										<span>
											{sub(
												total === 1
													? Liferay.Language.get(
															'x-result-found-for-colon'
														)
													: Liferay.Language.get(
															'x-results-found-for-colon'
														),
												total
											)}
										</span>
									)}

								{Liferay.FeatureFlags['LPD-52212'] &&
									isSearchActive() && <SearchResume />}

								{activeFilters.map((filter) => {
									return (
										<FilterResume
											disabled={disabled}
											key={filter.id}
											{...filter}
										/>
									);
								})}
							</div>
						</li>

						<li className="tbar-item">
							<div className="tbar-section">
								<ClayButton
									disabled={disabled}
									displayType="unstyled"
									onClick={resetFiltersValue}
								>
									{Liferay.Language.get('reset-filters')}
								</ClayButton>
							</div>
						</li>
					</ul>
				</nav>
			</div>
		</div>
	) : null;
}

ActiveFiltersBar.propTypes = {
	disabled: PropTypes.bool,
};

export default ActiveFiltersBar;
