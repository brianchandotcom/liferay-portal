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

import AppContext, {createReducer, initialState} from './AppContext.es';
import {
	UPDATE_DATA_DEFINITION,
	UPDATE_DATA_LAYOUT,
	UPDATE_IDS
} from './actions.es';
import {getItem} from './utils/client.es';

export default ({
	children,
	dataDefinitionId,
	dataLayoutBuilder,
	dataLayoutId
}) => {
	const reducer = createReducer(dataLayoutBuilder);
	const [state, dispatch] = useReducer(reducer, initialState);

	useEffect(() => {
		dispatch({
			payload: {
				dataDefinitionId,
				dataLayoutId
			},
			type: UPDATE_IDS
		});
	}, [dataDefinitionId, dataLayoutId, dispatch]);

	useEffect(() => {
		if (dataLayoutId) {
			getItem(`/o/data-engine/v2.0/data-layouts/${dataLayoutId}`).then(
				dataLayout =>
					dispatch({
						payload: {dataLayout},
						type: UPDATE_DATA_LAYOUT
					})
			);
		}
	}, [dataLayoutId, dispatch]);

	useEffect(() => {
		if (dataDefinitionId) {
			getItem(
				`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}`
			).then(dataDefinition =>
				dispatch({
					payload: {dataDefinition},
					type: UPDATE_DATA_DEFINITION
				})
			);
		}
	}, [dataDefinitionId, dispatch]);

	return (
		<AppContext.Provider value={[state, dispatch]}>
			{children}
		</AppContext.Provider>
	);
};
