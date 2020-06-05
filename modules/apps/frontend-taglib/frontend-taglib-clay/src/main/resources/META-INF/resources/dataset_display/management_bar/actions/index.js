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
	UPDATE_FILTER_VALUE: 'updateFilterValue',
};

const updateFilterValue = (dispatch) => (id, value = null) =>
	dispatch({
		payload: {
			id,
			value,
		},
		type: actionsDefinition.UPDATE_FILTER_VALUE,
	});

const resetFiltersValue = (dispatch) => () =>
	dispatch({
		type: actionsDefinition.RESET_FILTERS_VALUE,
	});

export const actions = {
	resetFiltersValue,
	updateFilterValue,
};

export default actions;
