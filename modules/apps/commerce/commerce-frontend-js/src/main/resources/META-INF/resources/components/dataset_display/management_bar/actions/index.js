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

export const actionsDefinition = {
	RESET_FILTERS_VALUE: 'resetFiltersValue',
	UPDATE_FILTER_STATE: 'updateFilterState',
};

const updateFilterState = (dispatch) => (
	id,
	value = null,
	formattedValue = null,
	odataFilterString = null
) =>
	dispatch({
		payload: {
			formattedValue,
			id,
			odataFilterString,
			value,
		},
		type: actionsDefinition.UPDATE_FILTER_STATE,
	});

const resetFiltersValue = (dispatch) => () =>
	dispatch({
		type: actionsDefinition.RESET_FILTERS_VALUE,
	});

export const actions = {
	resetFiltersValue,
	updateFilterState,
};

export default actions;
