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
import {getItem} from '../../utils/client.es';
import FormViewContext, {
	actions,
	initialState,
	createReducer
} from './FormViewContext.es';
import useDataLayoutBuilder from './useDataLayoutBuilder.es';

export default ({
	dataDefinitionId,
	dataLayoutBuilder,
	dataLayoutId,
	children
}) => {
	const reducer = createReducer(dataLayoutBuilder);
	const [state, dispatch] = useReducer(reducer, initialState);

	useDataLayoutBuilder(dataLayoutBuilder, dispatch);

	useEffect(() => {
		dispatch({dataDefinitionId, dataLayoutId, type: actions.UPDATE_IDS});
	}, [dataDefinitionId, dataLayoutId]);

	useEffect(() => {
		if (dataLayoutId) {
			getItem(`/o/data-engine/v1.0/data-layouts/${dataLayoutId}`).then(
				dataLayout =>
					dispatch({dataLayout, type: actions.UPDATE_DATA_LAYOUT})
			);
		}
	}, [dataLayoutId, dispatch]);

	useEffect(() => {
		getItem(
			`/o/data-engine/v1.0/data-definitions/${dataDefinitionId}`
		).then(dataDefinition =>
			dispatch({dataDefinition, type: actions.UPDATE_DATA_DEFINITION})
		);
	}, [dataDefinitionId, dispatch]);

	return (
		<FormViewContext.Provider value={[state, dispatch]}>
			{children}
		</FormViewContext.Provider>
	);
};
