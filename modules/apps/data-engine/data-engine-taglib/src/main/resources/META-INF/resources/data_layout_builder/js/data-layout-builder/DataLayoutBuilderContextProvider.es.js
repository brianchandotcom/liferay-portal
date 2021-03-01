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

import React, {useContext, useEffect} from 'react';

import AppContext from '../AppContext.es';
import {
	UPDATE_EDITING_LANGUAGE_ID,
	UPDATE_FIELD_TYPES,
	UPDATE_FOCUSED_FIELD,
	UPDATE_HOVERED_FIELD,
	UPDATE_PAGES,
} from '../actions.es';
import DataLayoutBuilderContext from './DataLayoutBuilderContext.es';

export default ({children, dataLayoutBuilder}) => {
	const [, dispatch] = useContext(AppContext);

	useEffect(() => {
		const provider =
			dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider;

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
		const provider =
			dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider;

		const eventHandler = provider.on('focusedFieldChanged', ({newVal}) => {
			dispatch({
				payload: {focusedField: newVal},
				type: UPDATE_FOCUSED_FIELD,
			});
		});

		return () => eventHandler.removeListener();
	}, [dataLayoutBuilder, dispatch]);

	useEffect(() => {
		const provider =
			dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider;

		const eventHandler = provider.on('fieldHovered', (newVal) => {
			dispatch({
				payload: {hoveredField: newVal},
				type: UPDATE_HOVERED_FIELD,
			});
		});

		return () => eventHandler.removeListener();
	}, [dataLayoutBuilder, dispatch]);

	useEffect(() => {
		const provider =
			dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider;

		const eventHandler = provider.on('pagesChanged', ({newVal}) => {
			dispatch({payload: {pages: newVal}, type: UPDATE_PAGES});
		});

		return () => eventHandler.removeListener();
	}, [dataLayoutBuilder, dispatch]);

	useEffect(() => {
		const {fieldTypes} = dataLayoutBuilder.props;

		dispatch({payload: {fieldTypes}, type: UPDATE_FIELD_TYPES});
	}, [dataLayoutBuilder, dispatch]);

	const dataLayoutBuilderDispatch = (...args) =>
		dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider?.dispatch?.(
			...args
		);

	return (
		<DataLayoutBuilderContext.Provider
			value={[dataLayoutBuilder, dataLayoutBuilderDispatch]}
		>
			{children}
		</DataLayoutBuilderContext.Provider>
	);
};
