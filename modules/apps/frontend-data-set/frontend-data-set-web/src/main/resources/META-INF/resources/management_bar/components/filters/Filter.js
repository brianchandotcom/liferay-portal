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

import ClayLoadingIndicator from '@clayui/loading-indicator';
import React, {useContext, useEffect, useState} from 'react';

import FrontendDataSetContext from '../../../FrontendDataSetContext';
import {getComponentByModuleURL} from '../../../utils/modules';
import AutocompleteFilter, {
	getOdataString as getAutocompleteFilterOdataString,
	getSelectedItemsLabel as getAutocompleteFilterSelectedItemsLabel,
} from './AutocompleteFilter';
import CheckboxesFilter, {
	getOdataString as getCheckboxesFilterOdataString,
	getSelectedItemsLabel as getCheckboxesFilterSelectedItemsLabel,
} from './CheckboxesFilter';
import DateRangeFilter, {
	getOdataString as getDateRangeFilterOdataString,
	getSelectedItemsLabel as getDateRangeFilterSelectedItemsLabel,
} from './DateRangeFilter';
import RadioFilter, {
	getOdataString as getRadioFilterOdataString,
	getSelectedItemsLabel as getRadioFilterSelectedItemsLabel,
} from './RadioFilter';

const FILTER_TYPE_COMPONENT = {
	autocomplete: AutocompleteFilter,
	checkbox: CheckboxesFilter,
	dateRange: DateRangeFilter,
	radio: RadioFilter,
};

const getFilterSelectedItemsLabel = (filter) => {
	switch (filter.type) {
		case 'autocomplete':
			return getAutocompleteFilterSelectedItemsLabel(filter);
		case 'checkbox':
			return getCheckboxesFilterSelectedItemsLabel(filter);
		case 'dateRange':
			return getDateRangeFilterSelectedItemsLabel(filter);
		case 'radio':
			return getRadioFilterSelectedItemsLabel(filter);
		default:
			return '';
	}
};

const getOdataFilterString = (filter) => {
	switch (filter.type) {
		case 'autocomplete':
			return getAutocompleteFilterOdataString(filter);
		case 'checkbox':
			return getCheckboxesFilterOdataString(filter);
		case 'dateRange':
			return getDateRangeFilterOdataString(filter);
		case 'radio':
			return getRadioFilterOdataString(filter);
		default:
			return '';
	}
};

const Filter = ({moduleURL, type, ...otherProps}) => {
	const {setFilters} = useContext(FrontendDataSetContext);

	const [Component, setComponent] = useState(() => {
		if (!moduleURL) {
			const Matched = FILTER_TYPE_COMPONENT[type];

			if (!Matched) {
				throw new Error(`Filter type '${type}' not found.`);
			}

			return Matched;
		}
		else {
			return null;
		}
	});

	useEffect(() => {
		if (moduleURL) {
			getComponentByModuleURL(moduleURL).then((FetchedComponent) =>
				setComponent(() => FetchedComponent)
			);
		}
	}, [moduleURL]);

	const setFilter = ({id, ...otherProps}) => {
		setFilters((filters) => {
			return filters.map((filter) => ({
				...filter,
				...(filter.id === id ? {...otherProps} : {}),
			}));
		});
	};

	return Component ? (
		<div className="data-set-filter">
			<Component setFilter={setFilter} {...otherProps} />
		</div>
	) : (
		<ClayLoadingIndicator small />
	);
};

export {getFilterSelectedItemsLabel, getOdataFilterString};
export default Filter;
