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

import {EVENT_TYPES} from 'dynamic-data-mapping-form-renderer';
import React, {useContext, useEffect} from 'react';

import AppContext from '../AppContext.es';
import {
	ADD_DATA_LAYOUT_RULE,
	UPDATE_DATA_LAYOUT_RULE,
	UPDATE_EDITING_LANGUAGE_ID,
	UPDATE_FIELD_TYPES,
	UPDATE_FOCUSED_FIELD,
	UPDATE_PAGES,
} from '../actions.es';
import {getDropHandler} from '../drag-and-drop/getDropHandler.es';
import DataLayoutBuilderContext from './DataLayoutBuilderContext.es';

export default ({children, dataLayoutBuilder}) => {
	const [{dataDefinition}, dispatch] = useContext(AppContext);

	useEffect(() => {
		const provider = dataLayoutBuilder.getLayoutProvider();

		const eventHandler = provider.on(
			'editingLanguageIdChanged',
			({newVal}) => {
				provider.once('rendered', () => {
					dispatch({
						payload: newVal,
						type: UPDATE_EDITING_LANGUAGE_ID,
					});
				});
			}
		);

		return () => eventHandler.removeListener();
	}, [dataLayoutBuilder, dispatch]);

	useEffect(() => {
		const provider = dataLayoutBuilder.getLayoutProvider();

		const eventHandler = provider.on('focusedFieldChanged', ({newVal}) => {
			dispatch({
				payload: {focusedField: newVal},
				type: UPDATE_FOCUSED_FIELD,
			});
		});

		return () => eventHandler.removeListener();
	}, [dataLayoutBuilder, dispatch]);

	useEffect(() => {
		const provider = dataLayoutBuilder.getLayoutProvider();

		const eventHandler = provider.on('pagesChanged', ({newVal}) => {
			dispatch({payload: {pages: newVal}, type: UPDATE_PAGES});
		});

		return () => eventHandler.removeListener();
	}, [dataLayoutBuilder, dispatch]);

	useEffect(() => {
		const provider = dataLayoutBuilder.getLayoutProvider();

		const ruleAddedEventHandler = provider.on('ruleAdded', (dataRule) => {
			provider.once('rendered', () => {
				dispatch({
					payload: {dataRule},
					type: ADD_DATA_LAYOUT_RULE,
				});
			});
		});

		const ruleEditedEventHandler = provider.on('ruleEdited', (dataRule) => {
			provider.once('rendered', () => {
				dispatch({
					payload: {dataRule},
					type: UPDATE_DATA_LAYOUT_RULE,
				});
			});
		});

		return () => {
			ruleAddedEventHandler.removeListener();
			ruleEditedEventHandler.removeListener();
		};
	}, [dataLayoutBuilder, dispatch]);

	useEffect(() => {
		const fieldTypes = dataLayoutBuilder.getFieldTypes();

		dispatch({payload: {fieldTypes}, type: UPDATE_FIELD_TYPES});
	}, [dataLayoutBuilder, dispatch]);

	useEffect(() => {
		const provider = dataLayoutBuilder.getLayoutProvider();
		const onDrop = getDropHandler({dataDefinition, dataLayoutBuilder});

		const eventHandler = provider.on(EVENT_TYPES.FIELD_DROP, onDrop);

		return () => eventHandler.removeListener();
	}, [dataLayoutBuilder, dataDefinition]);

	return (
		<DataLayoutBuilderContext.Provider
			value={[dataLayoutBuilder, dataLayoutBuilder.dispatch]}
		>
			{children}
		</DataLayoutBuilderContext.Provider>
	);
};
