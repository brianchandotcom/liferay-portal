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
import saveAsFieldSet from './saveAsFieldSet.es';
import useDeleteDefinitionField from './useDeleteDefinitionField.es';
import useDeleteDefinitionFieldModal from './useDeleteDefinitionFieldModal.es';

export default ({children, dataLayoutBuilder}) => {
	const [
		{dataDefinition, editingLanguageId, focusedField},
		dispatch,
	] = useContext(FormViewContext);
	const deleteDefinitionField = useDeleteDefinitionField({dataLayoutBuilder});
	const onDeleteDefinitionField = useDeleteDefinitionFieldModal((event) => {
		deleteDefinitionField(event);
	});

	const onSaveFieldSet = saveAsFieldSet();

	useEffect(() => {
		const provider = dataLayoutBuilder.getLayoutProvider();

		provider.props = {
			...provider.props,
			availableLanguageIds: [
				...new Set([
					...provider.props.availableLanguageIds,
					editingLanguageId,
				]),
			],
			editingLanguageId,
		};

		if (Object.keys(focusedField).length) {
			provider.getEvents().fieldClicked(focusedField);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [dataLayoutBuilder, dispatch, editingLanguageId]);

	useEffect(() => {
		const provider = dataLayoutBuilder.getLayoutProvider();

		let fieldActions = [
			{
				action: (event) =>
					dataLayoutBuilder.dispatch('fieldDuplicated', event),
				label: Liferay.Language.get('duplicate'),
			},
			{
				action: (event) => {
					dispatch({
						payload: {fieldName: event.fieldName},
						type: DataLayoutBuilderActions.DELETE_DATA_LAYOUT_FIELD,
					});

					dataLayoutBuilder.dispatch('fieldDeleted', event);
				},
				label: Liferay.Language.get('remove'),
				separator: true,
			},
			{
				action: (event) => {
					onDeleteDefinitionField(event);
				},
				label: Liferay.Language.get('delete-from-object'),
				style: 'danger',
			},
		];

		if (
			focusedField &&
			focusedField.type === 'fieldset' &&
			!focusedField.ddmStructureId
		) {
			const saveFieldSetAction = {
				action: ({fieldName}) => onSaveFieldSet(fieldName),
				label: Liferay.Language.get('save-as-fieldset'),
				separator: true,
			};

			fieldActions = fieldActions.map((action) => ({
				...action,
				separator: false,
			}));
			fieldActions.splice(2, 0, saveFieldSetAction);
		}

		provider.props.fieldActions = fieldActions;

		provider.props.shouldAutoGenerateName = () => false;
	}, [
		dataLayoutBuilder,
		dispatch,
		focusedField,
		onDeleteDefinitionField,
		onSaveFieldSet,
	]);

	useEffect(() => {
		const provider = dataLayoutBuilder.getLayoutProvider();

		provider.props.fieldNameGenerator = (
			desiredFieldName,
			currentFieldName
		) =>
			generateDataDefinitionFieldName(
				dataDefinition,
				desiredFieldName,
				currentFieldName
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
