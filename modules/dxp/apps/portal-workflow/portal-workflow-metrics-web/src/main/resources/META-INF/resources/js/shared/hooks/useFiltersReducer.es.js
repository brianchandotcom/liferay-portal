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

import {useReducer, useMemo} from 'react';

import {useRouterParams} from './useRouterParams.es';

const buildFilterItem = data => {
	if (typeof data === 'string') {
		return {
			active: true,
			key: data
		};
	}

	return {
		...data,
		active: true
	};
};

const buildInitialState = (filterKeys, filters) => {
	const initialState = {};

	Object.entries(filterKeys).forEach(([key, value]) => {
		if (filters[value]) {
			initialState[key] = filters[value].map(buildFilterItem);
		}
	});

	return initialState;
};

const reducer = filterKeys => (state, {filterKey, selectedItems}) => {
	const filterProp = Object.entries(filterKeys).find(
		([_, value]) => value === filterKey
	)[0];

	return {
		...state,
		[filterProp]: selectedItems
	};
};

const useFiltersReducer = filterKeys => {
	const {filters} = useRouterParams();

	const initialState = useMemo(
		() => buildInitialState(filterKeys, filters),
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[]
	);

	return useReducer(reducer(filterKeys), initialState);
};

export {useFiltersReducer};
