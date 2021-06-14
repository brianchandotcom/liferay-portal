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
import React, {useContext, useMemo, useState} from 'react';

import FiltersContext from './filters/FiltersContext';
import {Filter} from './filters/index';

function FiltersDropdown() {
	const filtersState = useContext(FiltersContext);
	const [active, setActive] = useState(false);
	const [query, setQuery] = useState('');

	const visibleFilters = useMemo(
		() => filtersState.filters.filter((filter) => !filter.invisible),
		[filtersState]
	);

	const [activeFilterId, setActiveFilterId] = useState(null);

	const activeFilter = useMemo(() => {
		return visibleFilters.length && activeFilterId
			? activeFilterId &&
					visibleFilters.find(
						(filter) => filter.id === activeFilterId
					)
			: null;
	}, [visibleFilters, activeFilterId]);

	return filtersState.filters.length ? (
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
			{activeFilterId ? (
				<>
					<li className="dropdown-subheader">
						<ClayButtonWithIcon
							className="btn-filter-navigation"
							displayType="unstyled"
							onClick={() => setActiveFilterId(null)}
							small
							symbol="angle-left"
						/>
						{activeFilter.label}
					</li>
					<Filter
						{...activeFilter}
						updateFilterState={filtersState.updateFilterState}
					/>
				</>
			) : (
				<ClayDropDown.Group header={Liferay.Language.get('filters')}>
					<ClayDropDown.Search
						onChange={(event) => setQuery(event.target.value)}
						value={query}
					/>
					<ClayDropDown.Divider className="m-0" />
					{visibleFilters.length ? (
						<ClayDropDown.ItemList>
							{visibleFilters.map((item) => (
								<ClayDropDown.Item
									active={item.value !== undefined}
									key={item.id}
									onClick={() => {
										setActiveFilterId(item.id);
									}}
								>
									{item.label}
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
	) : null;
}

export default FiltersDropdown;
