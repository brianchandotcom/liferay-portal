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

import React, {useEffect, useReducer, useCallback} from 'react';
import {getItem} from '../../utils/client.es';
import FormViewContext, {
	initialState,
	createReducer
} from './FormViewContext.es';
import useDataLayoutBuilder from './useDataLayoutBuilder.es';
import {
	actionBuilders,
	UPDATE_DATA_DEFINITION,
	UPDATE_DATA_LAYOUT,
	UPDATE_IDS
} from './actions.es';

export default ({
	dataDefinitionId,
	dataLayoutBuilder,
	dataLayoutId,
	children
}) => {
	const reducer = createReducer(dataLayoutBuilder);
	const [state, dispatch] = useReducer(reducer, initialState);

	const dispatchAction = useCallback(
		({payload, type}) => {
			const actionBuilder = actionBuilders[type];

			if (actionBuilder) {
				dispatch(
					actionBuilder({
						dataLayoutBuilder,
						payload,
						type
					})
				);
			} else {
				dispatch({payload, type});
			}
		},
		[dataLayoutBuilder, dispatch]
	);

	const dataLayoutBuilderDispatcher = useCallback(
		(event, payload) => {
			dataLayoutBuilder.dispatch(event, payload);
		},
		[dataLayoutBuilder]
	);

	useDataLayoutBuilder(dataLayoutBuilder, dispatchAction);

	useEffect(() => {
		dispatch({
			payload: {
				dataDefinitionId,
				dataLayoutId
			},
			type: UPDATE_IDS
		});
	}, [dataDefinitionId, dataLayoutId]);

	useEffect(() => {
		if (dataLayoutId) {
			getItem(`/o/data-engine/v1.0/data-layouts/${dataLayoutId}`).then(
				dataLayout =>
					dispatchAction({
						payload: {dataLayout},
						type: UPDATE_DATA_LAYOUT
					})
			);
		}
	}, [dataLayoutId, dispatchAction]);

	useEffect(() => {
		getItem(
			`/o/data-engine/v1.0/data-definitions/${dataDefinitionId}`
		).then(dataDefinition =>
			dispatchAction({
				payload: {dataDefinition},
				type: UPDATE_DATA_DEFINITION
			})
		);
	}, [dataDefinitionId, dispatchAction]);

	return (
		<FormViewContext.Provider
			value={[state, dispatchAction, dataLayoutBuilderDispatcher]}
		>
			{children}
		</FormViewContext.Provider>
	);
};
