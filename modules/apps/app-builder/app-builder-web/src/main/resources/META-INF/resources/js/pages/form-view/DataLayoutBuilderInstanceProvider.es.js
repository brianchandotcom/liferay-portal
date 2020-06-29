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

import generateDataDefinitionFieldName from '../../utils/generateDataDefinitionFieldName.es';
import DataLayoutBuilderContext from './DataLayoutBuilderInstanceContext.es';
import FormViewContext from './FormViewContext.es';
import useDeleteDefinitionField from './useDeleteDefinitionField.es';
import useDeleteDefinitionFieldModal from './useDeleteDefinitionFieldModal.es';
import useSaveAsFieldset from './useSaveAsFieldset.es';

export default ({children, dataLayoutBuilder}) => {
	const [
		{
			config: {allowNestedFields},
			dataDefinition,
			editingLanguageId,
			focusedField,
		},
		dispatch,
	] = useContext(FormViewContext);
	const deleteDefinitionField = useDeleteDefinitionField({dataLayoutBuilder});
	const onDeleteDefinitionField = useDeleteDefinitionFieldModal((event) => {
		deleteDefinitionField(event);
	});

	const saveAsFieldset = useSaveAsFieldset({dataLayoutBuilder});

	useEffect(() => {
		const provider = dataLayoutBuilder.getLayoutProvider();
		const availableLanguageIds = [
			...new Set([
				...provider.props.availableLanguageIds,
				editingLanguageId,
			]),
		];

		provider.props = {
			...provider.props,
			availableLanguageIds,
			editingLanguageId,
		};

		dataLayoutBuilder.formBuilderWithLayoutProvider.props.layoutProviderProps = {
			...dataLayoutBuilder.formBuilderWithLayoutProvider.props
				.layoutProviderProps,
			availableLanguageIds,
			defaultLanguageId: themeDisplay.getDefaultLanguageId(),
			editingLanguageId,
		};

		dataLayoutBuilder.formBuilderWithLayoutProvider.props.layoutProviderProps =
			dataLayoutBuilder.formBuilderWithLayoutProvider.props.layoutProviderProps; // eslint-disable-line

		if (Object.keys(focusedField).length) {
			provider.getEvents().fieldClicked(focusedField);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [dataLayoutBuilder, dispatch, editingLanguageId]);

	useEffect(() => {
		const provider = dataLayoutBuilder.getLayoutProvider();

		const {fieldHovered} = provider.state;

		let fieldActions = [];

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

		fieldActions = [
			duplicateAction,
			{
				...removeAction,
				separator: true,
			},
			deleteFromObjectAction,
		];

		if (
			allowNestedFields &&
			Object.keys(fieldHovered).length &&
			fieldHovered.type === 'fieldset' &&
			!fieldHovered.ddmStructureId
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

		provider.props = {
			...provider.props,
			fieldActions,
			shouldAutoGenerateName: () => false,
		};

		provider.getEvents().fieldHovered(fieldHovered);
	}, [
		allowNestedFields,
		dataLayoutBuilder,
		dispatch,
		onDeleteDefinitionField,
		saveAsFieldset,
	]);

	useEffect(() => {
		const provider = dataLayoutBuilder.getLayoutProvider();

		provider.props.fieldNameGenerator = (
			desiredFieldName,
			currentFieldName,
			blacklist
		) =>
			generateDataDefinitionFieldName(
				dataDefinition,
				desiredFieldName,
				currentFieldName,
				blacklist
			);
	}, [dataDefinition, dataLayoutBuilder]);

	return (
		<DataLayoutBuilderContext.Provider
			value={[dataLayoutBuilder, dataLayoutBuilder.dispatch]}
		>
			{children}
		</DataLayoutBuilderContext.Provider>
	);
};
