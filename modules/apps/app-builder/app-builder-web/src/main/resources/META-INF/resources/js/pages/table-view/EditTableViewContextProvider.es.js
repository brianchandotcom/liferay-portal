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

import React, {useEffect, useReducer} from 'react';
import {withRouter} from 'react-router-dom';

import {getItem} from '../../utils/client.es';
import EditTableViewContext, {
	UPDATE_DATA_DEFINITION,
	UPDATE_DATA_LIST_VIEW,
	UPDATE_FIELD_TYPES,
	initialState,
	reducer
} from './EditTableViewContext.es';

export default withRouter(
	({
		children,
		match: {
			params: {dataDefinitionId, dataListViewId}
		}
	}) => {
		const [state, dispatch] = useReducer(reducer, initialState);

		useEffect(() => {
			getItem(
				`/o/data-engine/v2.0/data-definitions/data-definition-fields/field-types`
			).then(fieldTypes => {
				dispatch({payload: {fieldTypes}, type: UPDATE_FIELD_TYPES});
			});
		}, []);

		useEffect(() => {
			getItem(
				`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}`
			).then(dataDefinition => {
				dispatch({
					payload: {dataDefinition},
					type: UPDATE_DATA_DEFINITION
				});
			});
		}, [dataDefinitionId]);

		useEffect(() => {
			if (dataListViewId) {
				getItem(
					`/o/data-engine/v2.0/data-list-views/${dataListViewId}`
				).then(dataListView => {
					dispatch({
						payload: {dataListView},
						type: UPDATE_DATA_LIST_VIEW
					});
				});
			}
		}, [dataListViewId]);

		return (
			<EditTableViewContext.Provider value={[state, dispatch]}>
				{children}
			</EditTableViewContext.Provider>
		);
	}
);
