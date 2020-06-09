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
import React, {useEffect} from 'react';

import ActiveFiltersBar from './components/ActiveFiltersBar';
import BulkActions from './components/BulkActions';
import {StoreProvider, useAppState} from './components/Context';
import NavBar from './components/NavBar';

function ManagementBar({
	activeView,
	bulkActions,
	creationMenuItems,
	fluid,
	onFiltersChange,
	selectAllItems,
	selectedItemsKey,
	selectedItemsValue,
	selectionType,
	setActiveView,
	showSearch,
	totalItemsCount,
	views,
}) {
	const {state} = useAppState();

	useEffect(() => {
		onFiltersChange(state.filters);

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [state.filters]);

	return (
		<>
			{selectionType === 'multiple' && (
				<BulkActions
					bulkActions={bulkActions}
					fluid={fluid}
					selectAllItems={selectAllItems}
					selectedItemsKey={selectedItemsKey}
					selectedItemsValue={selectedItemsValue}
					totalItemsCount={totalItemsCount}
				/>
			)}
			{(!selectedItemsValue.length || selectionType === 'single') && (
				<NavBar
					activeView={activeView}
					creationMenuItems={creationMenuItems}
					setActiveView={setActiveView}
					showSearch={showSearch}
					views={views}
				/>
			)}
			<ActiveFiltersBar disabled={!!selectedItemsValue.length} />
		</>
	);
}

function Wrapper({filters, ...otherProps}) {
	return (
		<StoreProvider filters={filters}>
			<ManagementBar {...otherProps} />
		</StoreProvider>
	);
}

Wrapper.propTypes = {
	activeView: PropTypes.number.isRequired,
	bulkActions: PropTypes.arrayOf(
		PropTypes.shape({
			href: PropTypes.string.isRequired,
			icon: PropTypes.string.isRequired,
			label: PropTypes.string.isRequired,
			method: PropTypes.string,
			target: PropTypes.oneOf(['sidePanel', 'modal']),
		})
	),
	creationMenuItems: PropTypes.array,
	filters: PropTypes.array,
	fluid: PropTypes.bool,
	onFiltersChange: PropTypes.func.isRequired,
	selectedItemsKey: PropTypes.string,
	selectedItemsValue: PropTypes.array,
	selectionType: PropTypes.oneOf(['single', 'multiple']).isRequired,
	setActiveView: PropTypes.func.isRequired,
	showSearch: PropTypes.bool,
	totalItemsCount: PropTypes.number,
	views: PropTypes.array.isRequired,
};

Wrapper.defaultProps = {
	filters: [],
	fluid: false,
	showSearch: true,
};

export default Wrapper;
