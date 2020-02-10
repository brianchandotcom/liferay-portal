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

import {DataLayoutBuilderActions, DataLayoutVisitor} from 'data-engine-taglib';
import React, {useContext, useEffect} from 'react';

import generateDataDefinitionFieldName from '../../utils/generateDataDefinitionFieldName.es';
import DataLayoutBuilderContext from './DataLayoutBuilderInstanceContext.es';
import FormViewContext from './FormViewContext.es';
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
		const provider = dataLayoutBuilder.getLayoutProvider();

		provider.props.fieldActions = [
			{
				action: indexes =>
					dataLayoutBuilder.dispatch('fieldDuplicated', {indexes}),
				label: Liferay.Language.get('duplicate')
			},
			{
				action: indexes => {
					const fieldName = DataLayoutVisitor.getFieldNameFromIndexes(
						dataLayout,
						indexes
					);

					dispatch({
						payload: {fieldName},
						type: DataLayoutBuilderActions.DELETE_DATA_LAYOUT_FIELD
					});

					dataLayoutBuilder.dispatch('fieldDeleted', {indexes});
				},
				label: Liferay.Language.get('remove'),
				separator: true
			},
			{
				action: indexes => {
					const fieldName = DataLayoutVisitor.getFieldNameFromIndexes(
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
		const provider = dataLayoutBuilder.getLayoutProvider();

		provider.props.fieldNameGenerator = desiredFieldName =>
			generateDataDefinitionFieldName(dataDefinition, desiredFieldName);
	}, [dataDefinition, dataLayoutBuilder]);

	return (
		<DataLayoutBuilderContext.Provider
			value={[dataLayoutBuilder, dataLayoutBuilder.dispatch]}
		>
			{children}
		</DataLayoutBuilderContext.Provider>
	);
};
