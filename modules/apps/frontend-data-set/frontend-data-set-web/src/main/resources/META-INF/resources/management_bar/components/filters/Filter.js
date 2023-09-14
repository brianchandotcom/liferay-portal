/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {useContext, useEffect, useState} from 'react';

import {getComponentByModuleURL} from '../../../utils/modules';
import ViewsContext from '../../../views/ViewsContext';
import {VIEWS_ACTION_TYPES} from '../../../views/viewsReducer';
import clientExtensionFilterImplementation from './implementation/clientExtensionFilter';
import dateRangeFilterImplementation from './implementation/dateRangeFilter';
import selectionFilterImplementation from './implementation/selectionFilter';

const FILTER_IMPLEMENTATIONS = {
	clientExtension: clientExtensionFilterImplementation,
	dateRange: dateRangeFilterImplementation,
	selection: selectionFilterImplementation,
};

const Filter = ({id, moduleURL, type, ...otherProps}) => {
	const [{filters}, viewsDispatch] = useContext(ViewsContext);

	const filterImplementation = FILTER_IMPLEMENTATIONS[type];

	if (!filterImplementation) {
		throw new Error(`Filter type '${type}' not found.`);
	}

	const [Component, setComponent] = useState(() =>
		moduleURL ? null : filterImplementation.Component
	);

	useEffect(() => {
		if (moduleURL) {
			getComponentByModuleURL(moduleURL).then((FetchedComponent) =>
				setComponent(() => FetchedComponent)
			);
		}
	}, [moduleURL]);

	const filterId = id;

	const setFilter = ({id, selectedData, ...otherProps}) => {
		if (id !== undefined && id !== filterId) {
			throw new Error(
				`Trying to modify filter ${id} from filter ${filterId}`
			);
		}

		const newFilter = {
			...filters.find((filter) => filter.id === filterId),
			selectedData,
			...otherProps,
		};

		newFilter.odataFilterString = filterImplementation.getOdataString(
			newFilter
		);
		newFilter.selectedItemsLabel = filterImplementation.getSelectedItemsLabel(
			newFilter
		);

		viewsDispatch({
			type: VIEWS_ACTION_TYPES.UPDATE_FILTERS,
			value: filters.map((filter) =>
				filter.id === filterId ? newFilter : filter
			),
		});
	};

	return Component ? (
		<div className="data-set-filter">
			<Component
				id={id}
				setFilter={setFilter}
				type={type}
				{...otherProps}
			/>
		</div>
	) : (
		<ClayLoadingIndicator size="sm" />
	);
};

export {FILTER_IMPLEMENTATIONS};
export default Filter;
