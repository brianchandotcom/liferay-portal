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

import customFields from '../../utils/formRendererCustomFields.es';
import DataLayoutBuilderContext from './DataLayoutBuilderInstanceContext.es';
import FormViewContext from './FormViewContext.es';
import useDeleteDefinitionField from './useDeleteDefinitionField.es';
import useDeleteDefinitionFieldModal from './useDeleteDefinitionFieldModal.es';
import useDuplicateField from './useDuplicateField.es';
import useSaveAsFieldset from './useSaveAsFieldset.es';

export default ({children, dataLayoutBuilder}) => {
	const [
		{
			config: {allowNestedFields},
			dataDefinition,
			editingLanguageId,
			focusedCustomObjectField,
			hoveredField,
		},
		dispatch,
	] = useContext(FormViewContext);
	const {defaultLanguageId} = dataDefinition;

	const deleteDefinitionField = useDeleteDefinitionField({dataLayoutBuilder});
	const deleteDefinitionFieldModal = useDeleteDefinitionFieldModal(
		(event) => {
			deleteDefinitionField(event);
		}
	);
	const duplicateField = useDuplicateField({dataLayoutBuilder});
	const saveAsFieldset = useSaveAsFieldset({dataLayoutBuilder});

	useEffect(() => {
		dataLayoutBuilder.onEditingLanguageIdChange({
			defaultLanguageId,
			editingLanguageId,
		});
	}, [dataLayoutBuilder, defaultLanguageId, editingLanguageId]);

	useEffect(() => {
		if (Object.keys(focusedCustomObjectField).length > 0) {
			const dataDefinitionField = focusedCustomObjectField;

			dispatch({
				payload: {dataDefinitionField},
				type:
					DataLayoutBuilderActions.UPDATE_FOCUSED_CUSTOM_OBJECT_FIELD,
			});
		}
		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [dispatch, defaultLanguageId, editingLanguageId]);

	useEffect(() => {
		const duplicateAction = {
			action: (event) => duplicateField(event),
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
			action: (event) => deleteDefinitionFieldModal(event),
			label: Liferay.Language.get('delete-from-object'),
		};

		let fieldActions = [
			duplicateAction,
			removeAction,
			{
				type: 'divider',
			},
			deleteFromObjectAction,
		];

		if (
			allowNestedFields &&
			Object.keys(hoveredField).length &&
			hoveredField.fieldType === 'fieldset' &&
			!hoveredField.customProperties.ddmStructureId
		) {
			fieldActions = [
				duplicateAction,
				removeAction,
				{
					action: ({fieldName}) => saveAsFieldset(fieldName),
					label: Liferay.Language.get('save-as-fieldset'),
				},
				{
					type: 'divider',
				},
				deleteFromObjectAction,
			];
		}

		if (hoveredField.fieldType === 'fieldset') {
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
		duplicateField,
		hoveredField,
		deleteDefinitionFieldModal,
		saveAsFieldset,
	]);

	useEffect(() => {
		dispatch({
			payload: customFields,
			type: DataLayoutBuilderActions.SET_FORM_RENDERER_CUSTOM_FIELDS,
		});
	}, [dispatch]);

	return (
		<DataLayoutBuilderContext.Provider
			value={[dataLayoutBuilder, dataLayoutBuilder.dispatch]}
		>
			{children}
		</DataLayoutBuilderContext.Provider>
	);
};
