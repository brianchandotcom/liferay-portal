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

import {DataLayoutBuilderActions} from 'data-engine-taglib';
import React, {useContext, useEffect} from 'react';

import DataLayoutBuilderContext from './DataLayoutBuilderInstanceContext.es';
import FormViewContext from './FormViewContext.es';
import useDeleteDefinitionField from './useDeleteDefinitionField.es';
import useDeleteDefinitionFieldModal from './useDeleteDefinitionFieldModal.es';
import useSaveAsFieldset from './useSaveAsFieldset.es';

export default ({children, dataLayoutBuilder}) => {
	const [
		{
			config: {allowNestedFields},
			editingLanguageId,
			hoveredField,
		},
		dispatch,
	] = useContext(FormViewContext);
	const deleteDefinitionField = useDeleteDefinitionField({dataLayoutBuilder});
	const onDeleteDefinitionField = useDeleteDefinitionFieldModal((event) => {
		deleteDefinitionField(event);
	});

	const saveAsFieldset = useSaveAsFieldset({dataLayoutBuilder});

	useEffect(() => {
		dataLayoutBuilder.onEditingLanguageIdChange({
			editingLanguageId,
		});
	}, [dataLayoutBuilder, editingLanguageId]);

	useEffect(() => {
		const duplicateAction = {
			action: (event) =>
				dataLayoutBuilder.dispatch('fieldDuplicated', event),
			label: Liferay.Language.get('duplicate'),
		};

		const removeAction = {
			action: (event) => {
				dispatch({
					payload: {fieldName: event.fieldName},
					type: DataLayoutBuilderActions.DELETE_DATA_LAYOUT_FIELD,
				});

				dataLayoutBuilder.dispatch('fieldDeleted', event);
			},
			label: Liferay.Language.get('remove'),
		};

		const deleteFromObjectAction = {
			action: (event) => {
				onDeleteDefinitionField(event);
			},
			label: Liferay.Language.get('delete-from-object'),
			style: 'danger',
		};

		let fieldActions = [
			duplicateAction,
			{
				...removeAction,
				separator: true,
			},
			deleteFromObjectAction,
		];

		if (
			allowNestedFields &&
			Object.keys(hoveredField).length &&
			hoveredField.type === 'fieldset' &&
			!hoveredField.ddmStructureId
		) {
			fieldActions = [
				duplicateAction,
				removeAction,
				{
					action: ({fieldName}) => saveAsFieldset(fieldName),
					label: Liferay.Language.get('save-as-fieldset'),
					separator: true,
				},
				deleteFromObjectAction,
			];
		}

		if (hoveredField.type === 'fieldset') {
			fieldActions.splice(fieldActions.indexOf(duplicateAction), 1);
		}

		const provider = dataLayoutBuilder.getLayoutProvider();

		provider.props = {
			...provider.props,
			fieldActions,
		};

		provider.getEvents().fieldHovered(hoveredField);
	}, [
		allowNestedFields,
		dataLayoutBuilder,
		dispatch,
		hoveredField,
		onDeleteDefinitionField,
		saveAsFieldset,
	]);

	return (
		<DataLayoutBuilderContext.Provider
			value={[dataLayoutBuilder, dataLayoutBuilder.dispatch]}
		>
			{children}
		</DataLayoutBuilderContext.Provider>
	);
};
