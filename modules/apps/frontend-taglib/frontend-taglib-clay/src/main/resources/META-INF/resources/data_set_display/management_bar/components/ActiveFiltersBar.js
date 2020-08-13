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
import React from 'react';

import {useAppState} from './Context';
import FilterResume from './FilterResume';

function ActiveFiltersBar({disabled}) {
	const {actions, state} = useAppState();

	const filtersActive = state.filters.reduce(
		(acc, filter) =>
			filter.value && !filter.invisible ? acc.concat(filter.id) : acc,
		[]
	);

	return filtersActive.length ? (
		<div className="management-bar management-bar-light navbar navbar-expand-md">
			<div className="container-fluid container-fluid-max-xl">
				<nav className="mb-0 py-3 subnav-tbar subnav-tbar-light subnav-tbar-primary w-100">
					<ul className="tbar-nav">
						<li className="p-0 tbar-item tbar-item-expand">
							<div className="tbar-section">
								{filtersActive.map((id) => {
									const filter = state.filters.reduce(
										(found, filter) =>
											found ||
											(filter.id === id ? filter : null),
										null
									);

									if (!filter) {
										throw new Error(
											`Filter "${id}" not found.`
										);
									}

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
									onClick={actions.resetFiltersValue}
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

export default ActiveFiltersBar;
