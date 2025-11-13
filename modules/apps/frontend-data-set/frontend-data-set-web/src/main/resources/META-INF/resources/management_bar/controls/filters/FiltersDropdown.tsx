/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import Button, {ClayButtonWithIcon} from '@clayui/button';
import ClayDropDown from '@clayui/drop-down';
import ClayIcon from '@clayui/icon';
import React, {useContext, useEffect, useState} from 'react';

import ViewsContext, {
	IViewsContext,
	TViewsContextDispatch,
} from '../../../views/ViewsContext';
import Filter, {FilterComponentArgs, IFilter} from './Filter';

const FiltersDropdown = () => {
	const [{filters, filtersGroups}]: [IViewsContext, TViewsContextDispatch] =
		useContext(ViewsContext);

	const [active, setActive] = useState(false);
	const [activeFilter, setActiveFilter] = useState<IFilter | null>(null);
	const [filters, setFilters] = useState<IFilter[]>(initialFilters);
	const [query, setQuery] = useState('');

	const onSearch = (query: string) => {
		setQuery(query);

		setFilters(
			query
				? initialFilters.filter(({label}) =>
						label.toLowerCase().match(query.toLowerCase())
					) || []
				: initialFilters
		);
	};

	useEffect(() => {
		setFilters(initialFilters);

		setActiveFilter((currentActiveFilter: FilterComponentArgs | null) => {
			if (!currentActiveFilter) {
				return null;
			}

			return (
				initialFilters.find(
					(filter) => filter.id === currentActiveFilter.id
				) || null
			);
		});
	}, [initialFilters, setActiveFilter, setFilters]);

	const validFilters = filters.filter(
		(filter) => !filter.clientExtensionResolutionError
	);

	const groupedFilters = filtersGroups?.map((group) => ({
		children: group.filters.map((filterId: string) =>
				validFilters.find((f) => f.id === filterId)
			)
			.filter(Boolean),
		label: group.label,
	}));

	const filtersList = filtersGroups ? groupedFilters : validFilters;

	return (
		<ClayDropDown
			active={active}
			className="filters-dropdown"
			onActiveChange={setActive}
			trigger={
				<Button
					aria-expanded={active}
					borderless
					className="filters-dropdown-button nav-link"
					displayType="secondary"
					size="sm"
				>
					<span className="inline-item inline-item-before">
						<ClayIcon symbol="filter" />
					</span>

					<span className="navbar-text-truncate">
						{Liferay.Language.get('filter')}
					</span>

					<ClayIcon
						className="inline-item inline-item-after"
						symbol="caret-bottom"
					/>
				</Button>
			}
		>
			{activeFilter ? (
				<>
					<li className="dropdown-subheader">
						<ClayButtonWithIcon
							aria-label={Liferay.Language.get('back')}
							className="btn-filter-navigation"
							displayType="unstyled"
							onClick={() => {
								setActiveFilter(null);

								setFilters(initialFilters);
							}}
							size="sm"
							symbol="angle-left"
						/>

						{activeFilter.label}
					</li>

					<Filter
						{...activeFilter}
						onClose={() => setActive(false)}
					/>
				</>
			) : (
				<ClayDropDown.Group header={Liferay.Language.get('filters')}>
					<ClayDropDown.Search
						aria-label={Liferay.Language.get('search')}
					/>

					<ClayDropDown.Divider />

					{filtersList.length ? (
						<ClayDropDown.ItemList items={filtersList}>
							{filtersGroups
								? (group: any) => (
										<ClayDropDown.Group
											header={group.label}
											items={group.children}
											key={group.label}
										>
											{(filter: any) => (
												<ClayDropDown.Item
													key={filter.id}
													onClick={() =>
														setActiveFilter(filter)
													}
												>
													{filter.label}
												</ClayDropDown.Item>
											)}
										</ClayDropDown.Group>
									)
								: (filter: any) => (
										<ClayDropDown.Item
											key={filter.id}
											onClick={() =>
												setActiveFilter(filter)
											}
										>
											{filter.label}
										</ClayDropDown.Item>
									)}
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
