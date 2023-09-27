/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {createContext, useMemo, useReducer} from 'react';

import {getFilterValues} from './util/filterUtil.es';

const FilterContext = createContext();

const filterReducer = (state, {error, filterKey, removeError, ...newState}) => {
	if (error) {
		newState.errors = removeError
			? state.errors.filter((key) => key !== filterKey)
			: [...state.errors, filterKey];
	}

	return {...state, ...newState};
};

const FilterContextProvider = ({children}) => {
	const [filterState, dispatch] = useReducer(filterReducer, {errors: []});

	const dispatchFilter = (filterKey, selectedItems) => {
		return dispatch({[filterKey]: selectedItems});
	};

	const dispatchFilterError = (filterKey, removeError) => {
		return dispatch({error: true, filterKey, removeError});
	};

	const filterValues = useMemo(() => getFilterValues(filterState), [
		filterState,
	]);

	return (
		<FilterContext.Provider
			value={{
				dispatch,
				dispatchFilter,
				dispatchFilterError,
				filterState,
				filterValues,
			}}
		>
			{children}
		</FilterContext.Provider>
	);
};

export {FilterContext, filterReducer, FilterContextProvider};
