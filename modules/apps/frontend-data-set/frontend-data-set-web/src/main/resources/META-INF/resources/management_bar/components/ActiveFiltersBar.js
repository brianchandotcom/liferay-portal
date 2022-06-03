/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import ClayButton from '@clayui/button';
import PropTypes from 'prop-types';
import React, {useContext} from 'react';

import FrontendDataSetContext from '../../FrontendDataSetContext';
import FilterResume from './FilterResume';

function ActiveFiltersBar({disabled}) {
	const {filters, setFilters} = useContext(FrontendDataSetContext);

	const resetFiltersValue = () => {
		setFilters((filters) => {
			return filters.map((filter) => ({
				...filter,
				active: false,
				odataFilterString: undefined,
				selectedData: undefined,
			}));
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
