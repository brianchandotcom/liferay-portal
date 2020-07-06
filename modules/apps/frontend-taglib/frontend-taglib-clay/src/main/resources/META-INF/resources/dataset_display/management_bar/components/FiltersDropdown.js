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

import ClayDropDown from '@clayui/drop-down';
import Icon from '@clayui/icon';
import ClayPanel from '@clayui/panel';
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

import getAppContext from './Context';
import {Filter} from './filters/index';

function DropdownFilterItem(props) {
	const {actions} = getAppContext();

	return (
		<ClayPanel
			className={classNames(
				`mb-0 filter-panel-head`,
				props.value && 'active'
			)}
			collapsable
			displayTitle={props.label}
			key={props.id}
			showCollapseIcon={true}
		>
			<ClayPanel.Body className="filter-body">
				<Filter {...props} actions={actions} />
			</ClayPanel.Body>
		</ClayPanel>
	);
}

function FiltersDropdown() {
	const [active, setActive] = useState(false);
	const [query, setQuery] = useState('');
	const {state} = getAppContext();
	const [visibleFilters, setVisibleFilter] = useState(
		state.filters.filter((filter) => !filter.invisible)
	);

	useEffect(() => {
		const results = state.filters.filter(
			(filter) =>
				!filter.invisible &&
				(!query ||
					filter.id.toLowerCase().includes(query) ||
					filter.label.toLowerCase().includes(query))
		);

		setVisibleFilter(results);
	}, [state.filters, query]);

	return state.filters.length ? (
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
						{Liferay.Language.get('set-filters')}
					</span>
					{active ? (
						<Icon className="ml-2" symbol="caret-top" />
					) : (
						<Icon className="ml-2" symbol="caret-bottom" />
					)}
				</button>
			}
		>
			<ClayDropDown.Search
				onChange={(event) => setQuery(event.target.value)}
				value={query}
			/>
			{visibleFilters.length ? (
				<ClayDropDown.ItemList>
					{visibleFilters.map((item) => (
						<DropdownFilterItem key={item.id} {...item} />
					))}
				</ClayDropDown.ItemList>
			) : (
				<div className="dropdown-section text-muted">
					{Liferay.Language.get('no-filters-were-found')}
				</div>
			)}
		</ClayDropDown>
	) : null;
}

export default FiltersDropdown;
