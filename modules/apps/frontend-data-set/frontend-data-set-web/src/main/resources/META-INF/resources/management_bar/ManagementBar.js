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

import PropTypes from 'prop-types';
import React, {useEffect, useState} from 'react';

import ActiveFiltersBar from './components/ActiveFiltersBar';
import BulkActions from './components/BulkActions';
import NavBar from './components/NavBar';
import FiltersContext from './components/filters/FiltersContext';

function ManagementBar({
	bulkActions,
	creationMenu,
	filters: propFilters,
	fluid,
	onFiltersChange,
	selectAllItems,
	selectedItemsKey,
	selectedItemsValue,
	selectionType,
	showSearch,
	total,
}) {
	const [filters, setFilters] = useState(propFilters);

	useEffect(() => {
		onFiltersChange(filters);
	}, [filters, onFiltersChange]);

	const state = {
		filters,
		resetFiltersValue: () => {
			setFilters((filters) => {
				return filters.map((element) => ({
					...element,
					additionalData: undefined,
					odataFilterString: undefined,
					resumeCustomLabel: undefined,
					value: undefined,
				}));
			});
		},
		updateFilterState: (id, value, formattedValue, odataFilterString) => {
			setFilters((filters) => {
				return filters.map((filter) => ({
					...filter,
					...(filter.id === id
						? {
								formattedValue,
								odataFilterString,
								value,
						  }
						: {}),
				}));
			});
		},
	};

	return (
		<FiltersContext.Provider value={state}>
			{selectionType === 'multiple' && (
				<BulkActions
					bulkActions={bulkActions}
					fluid={fluid}
					selectAllItems={selectAllItems}
					selectedItemsKey={selectedItemsKey}
					selectedItemsValue={selectedItemsValue}
					total={total}
				/>
			)}

			{(!selectedItemsValue.length || selectionType === 'single') && (
				<NavBar creationMenu={creationMenu} showSearch={showSearch} />
			)}

			<ActiveFiltersBar disabled={!!selectedItemsValue.length} />
		</FiltersContext.Provider>
	);
}

ManagementBar.propTypes = {
	bulkActions: PropTypes.arrayOf(
		PropTypes.shape({
			href: PropTypes.string.isRequired,
			icon: PropTypes.string.isRequired,
			label: PropTypes.string.isRequired,
			method: PropTypes.string,
			target: PropTypes.oneOf(['sidePanel', 'modal']),
		})
	),
	creationMenu: PropTypes.shape({
		primaryItems: PropTypes.array,
		secondaryItems: PropTypes.array,
	}),
	filters: PropTypes.array,
	fluid: PropTypes.bool,
	onFiltersChange: PropTypes.func.isRequired,
	selectedItemsKey: PropTypes.string,
	selectedItemsValue: PropTypes.array,
	selectionType: PropTypes.oneOf(['single', 'multiple']),
	showSearch: PropTypes.bool,
	total: PropTypes.number,
};

ManagementBar.defaultProps = {
	filters: [],
	fluid: false,
	showSearch: true,
};

export default ManagementBar;
