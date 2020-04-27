/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
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
