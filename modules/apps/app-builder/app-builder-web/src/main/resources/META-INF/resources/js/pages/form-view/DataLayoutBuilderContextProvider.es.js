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

import React, {useEffect, useContext} from 'react';

import {getFieldNameFromIndexes} from '../../utils/dataLayoutVisitor.es';
import generateDataDefinitionFieldName from '../../utils/generateDataDefinitionFieldName.es';
import DataLayoutBuilderContext from './DataLayoutBuilderContext.es';
import FormViewContext from './FormViewContext.es';
import {
	DELETE_DATA_LAYOUT_FIELD,
	UPDATE_FIELD_TYPES,
	UPDATE_FOCUSED_FIELD,
	UPDATE_PAGES
} from './actions.es';
import useDeleteDefinitionField from './useDeleteDefinitionField.es';
import useDeleteDefinitionFieldModal from './useDeleteDefinitionFieldModal.es';

export default ({children, dataLayoutBuilder}) => {
	const [{dataDefinition, dataLayout}, dispatch] = useContext(
		FormViewContext
	);
	const deleteDefinitionField = useDeleteDefinitionField({dataLayoutBuilder});
	const onDeleteDefinitionField = useDeleteDefinitionFieldModal(fieldName => {
		deleteDefinitionField(fieldName);
	});

	useEffect(() => {
		const provider = dataLayoutBuilder.getProvider();

		provider.props.fieldActions = [
			{
				action: indexes =>
					dataLayoutBuilder.dispatch('fieldDuplicated', {indexes}),
				label: Liferay.Language.get('duplicate')
			},
			{
				action: indexes => {
					const fieldName = getFieldNameFromIndexes(
						dataLayout,
						indexes
					);

					dispatch({
						payload: {fieldName},
						type: DELETE_DATA_LAYOUT_FIELD
					});

					dataLayoutBuilder.dispatch('fieldDeleted', {indexes});
				},
				label: Liferay.Language.get('remove'),
				separator: true
			},
			{
				action: indexes => {
					const fieldName = getFieldNameFromIndexes(
						dataLayout,
						indexes
					);

					onDeleteDefinitionField(fieldName);
				},
				label: Liferay.Language.get('delete-from-object'),
				style: 'danger'
			}
		];
	}, [dataLayout, dataLayoutBuilder, dispatch, onDeleteDefinitionField]);

	useEffect(() => {
		const provider = dataLayoutBuilder.getProvider();

		provider.props.fieldNameGenerator = desiredFieldName =>
			generateDataDefinitionFieldName(dataDefinition, desiredFieldName);
	}, [dataDefinition, dataLayoutBuilder]);

	useEffect(() => {
		const provider = dataLayoutBuilder.getProvider();

		const eventHandler = provider.on('focusedFieldChanged', ({newVal}) => {
			provider.once('rendered', () => {
				dispatch({
					payload: {focusedField: newVal},
					type: UPDATE_FOCUSED_FIELD
				});
			});
		});

		return () => eventHandler.removeListener();
	}, [dataLayoutBuilder, dispatch]);

	useEffect(() => {
		const provider = dataLayoutBuilder.getProvider();

		const eventHandler = provider.on('pagesChanged', ({newVal}) => {
			provider.once('rendered', () => {
				dispatch({payload: {pages: newVal}, type: UPDATE_PAGES});
			});
		});

		return () => eventHandler.removeListener();
	}, [dataLayoutBuilder, dispatch]);

	useEffect(() => {
		const fieldTypes = dataLayoutBuilder.getFieldTypes();

		dispatch({payload: {fieldTypes}, type: UPDATE_FIELD_TYPES});
	}, [dataLayoutBuilder, dispatch]);

	return (
		<DataLayoutBuilderContext.Provider
			value={[dataLayoutBuilder, dataLayoutBuilder.dispatch]}
		>
			{children}
		</DataLayoutBuilderContext.Provider>
	);
};
