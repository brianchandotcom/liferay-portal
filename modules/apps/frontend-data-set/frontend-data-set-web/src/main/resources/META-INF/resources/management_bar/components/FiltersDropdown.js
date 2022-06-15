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

import {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import Icon from '@clayui/icon';
import React, {useContext, useState} from 'react';

import FrontendDataSetContext from '../../FrontendDataSetContext';
import Filter from './filters/Filter';

const FiltersDropdown = () => {
	const {filters: initialFilters} = useContext(FrontendDataSetContext);

	const [active, setActive] = useState(false);
	const [activeFilter, setActiveFilter] = useState(null);
	const [filters, setFilters] = useState(initialFilters);

	const onSearch = (event) => {
		const query = event.target.value;

		setFilters(
			query
				? initialFilters.filter(({label}) =>
						label.toLowerCase().match(query.toLowerCase())
				  ) || []
				: initialFilters
		);
	};

	return (
		<ClayDropDown
			active={active}
			className="filters-dropdown"
			onActiveChange={setActive}
			trigger={
				<button
					className="btn btn-unstyled dropdown-toggle filters-dropdown-button"
					type="button"
				>
					<span className="navbar-text-truncate">
						{Liferay.Language.get('filter')}
					</span>

					<Icon
						className="ml-2"
						symbol={active ? 'caret-top' : 'caret-bottom'}
					/>
				</button>
			}
		>
			{activeFilter ? (
				<>
					<li className="dropdown-subheader">
						<ClayButtonWithIcon
							className="btn-filter-navigation"
							displayType="unstyled"
							onClick={() => {
								setActiveFilter(null);

								setFilters(initialFilters);
							}}
							small
							symbol="angle-left"
						/>

						{activeFilter.label}
					</li>
					<Filter {...activeFilter} />
				</>
			) : (
				<ClayDropDown.Group header={Liferay.Language.get('filters')}>
					<ClayDropDown.Search onChange={onSearch} />

					<ClayDropDown.Divider className="m-0" />

					{filters.length ? (
						<ClayDropDown.ItemList>
							{filters.map((filter) => (
								<ClayDropDown.Item
									active={filter.value !== undefined}
									key={filter.id}
									onClick={() => {
										setActiveFilter(filter);
									}}
								>
									{filter.label}
								</ClayDropDown.Item>
							))}
						</ClayDropDown.ItemList>
					) : (
						<ClayDropDown.Caption>
							{Liferay.Language.get('no-filters-were-found')}
						</ClayDropDown.Caption>
					)}
				</ClayDropDown.Group>
			)}
		</ClayDropDown>
	);
};

export default FiltersDropdown;
