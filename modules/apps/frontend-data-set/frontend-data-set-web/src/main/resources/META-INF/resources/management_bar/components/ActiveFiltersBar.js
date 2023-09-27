/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import ViewsContext from '../../views/ViewsContext';
import {VIEWS_ACTION_TYPES} from '../../views/viewsReducer';
import FilterResume from './FilterResume';

function ActiveFiltersBar({disabled}) {
	const [{filters}, viewsDispatch] = useContext(ViewsContext);

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

	return activeFilters.length ? (
		<div className="management-bar management-bar-light navbar navbar-expand-md">
			<div className="container-fluid container-fluid-max-xl">
				<nav className="mb-0 py-3 subnav-tbar subnav-tbar-light subnav-tbar-primary w-100">
					<ul className="tbar-nav">
						<li className="p-0 tbar-item tbar-item-expand">
							<div className="tbar-section">
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
