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
import React, {useCallback, useContext, useEffect} from 'react';

import {addItem} from '../../utils/client.es';
import generateDataDefinitionFieldName from '../../utils/generateDataDefinitionFieldName.es';
import {errorToast, successToast} from '../../utils/toast.es';
import DataLayoutBuilderContext from './DataLayoutBuilderInstanceContext.es';
import FormViewContext from './FormViewContext.es';
import useDeleteDefinitionField from './useDeleteDefinitionField.es';
import useDeleteDefinitionFieldModal from './useDeleteDefinitionFieldModal.es';

export default ({children, dataLayoutBuilder}) => {
	const [
		{dataDefinition, editingLanguageId, fieldSets, focusedField},
		dispatch,
	] = useContext(FormViewContext);
	const deleteDefinitionField = useDeleteDefinitionField({dataLayoutBuilder});
	const onDeleteDefinitionField = useDeleteDefinitionFieldModal((event) => {
		deleteDefinitionField(event);
	});

	const saveAsFieldSet = useCallback(
		(fieldName) => {
			const customProperties = {};
			const {
				customProperties: {rows},
				label,
				nestedDataDefinitionFields,
			} = dataDefinition.dataDefinitionFields.find(
				({name}) => fieldName === name
			);

			const dataDefinitionRows = JSON.parse(rows);

			const fieldLabel = label[editingLanguageId];

			const dataLayoutRows = dataDefinitionRows.map(({columns}) => {
				const column = columns.map(
					({fields: fieldNames, size: columnSize}) => ({
						dataLayoutColumns: [{columnSize, fieldNames}],
					})
				);

				return column[0];
			});

			const fieldSetDefinition = {
				availableLanguageIds: ['en_US'],
				dataDefinitionFields: nestedDataDefinitionFields,
				name: {
					[editingLanguageId]: fieldLabel,
				},
			};

			const fieldSetDataLayout = {
				dataLayoutPages: [
					{
						dataLayoutRows,
						description: {
							en_US: '',
						},
						title: {
							en_US: '',
						},
					},
				],
				name: {
					[editingLanguageId]: `${fieldLabel}Layout`,
				},
			};

			addItem(
				`/o/data-engine/v2.0/data-definitions/by-content-type/app-builder-fieldset`,
				fieldSetDefinition
			)
				.then((dataDefinitionFieldSet) => {
					const {id: ddmStructureId} = dataDefinitionFieldSet;
					customProperties.ddmStructureId = ddmStructureId;

					dispatch({
						payload: {
							fieldSets: [...fieldSets, dataDefinitionFieldSet],
						},
						type: DataLayoutBuilderActions.UPDATE_FIELDSETS,
					});

					return addItem(
						`/o/data-engine/v2.0/data-definitions/${ddmStructureId}/data-layouts`,
						fieldSetDataLayout
					);
				})
				.then(({id: ddmStructureLayoutId}) => {
					customProperties.ddmStructureLayoutId = ddmStructureLayoutId;
					const dataDefinitionFields = dataDefinition.dataDefinitionFields.map(
						(definitionField) => {
							if (definitionField.name === fieldName) {
								return {
									...definitionField,
									customProperties: {
										...customProperties,
										rows: '',
									},
									nestedDataDefinitionFields: [],
								};
							}

							return dataDefinition;
						}
					);

					dispatch({
						payload: {
							dataDefinition: {
								...dataDefinition,
								dataDefinitionFields,
							},
						},
						type: DataLayoutBuilderActions.UPDATE_DATA_DEFINITION,
					});

					successToast(Liferay.Language.get('fieldset-saved'));
				})
				.catch((error) => errorToast(error.message));
		},
		[dataDefinition, dispatch, editingLanguageId, fieldSets]
	);

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

		if (focusedField && focusedField.type === 'fieldset') {
			const saveFieldSetAction = {
				action: ({fieldName}) => saveAsFieldSet(fieldName),
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
		saveAsFieldSet,
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
