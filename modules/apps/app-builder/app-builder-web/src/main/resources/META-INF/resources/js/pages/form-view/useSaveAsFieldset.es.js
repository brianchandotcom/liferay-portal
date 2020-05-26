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
import {useContext} from 'react';

import {addItem} from '../../utils/client.es';
import {errorToast, successToast} from '../../utils/toast.es';
import FormViewContext from './FormViewContext.es';

export default () => {
	const [{dataDefinition, fieldSets}, dispatch] = useContext(FormViewContext);
	const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

	return (fieldName) => {
		const customProperties = {};
		const {
			customProperties: {rows},
			label,
			nestedDataDefinitionFields,
		} = dataDefinition.dataDefinitionFields.find(
			({name}) => fieldName === name
		);

		const fieldLabel = label[defaultLanguageId];

		const dataLayoutRows = JSON.parse(rows).map(({columns}) => {
			return columns.map(({fields: fieldNames, size: columnSize}) => ({
				dataLayoutColumns: [{columnSize, fieldNames}],
			}))[0];
		});

		const fieldSetDefinition = {
			availableLanguageIds: [defaultLanguageId],
			dataDefinitionFields: nestedDataDefinitionFields,
			name: {
				[defaultLanguageId]: fieldLabel,
			},
		};

		const fieldSetDataLayout = {
			dataLayoutPages: [
				{
					dataLayoutRows,
					description: {
						[defaultLanguageId]: '',
					},
					title: {
						[defaultLanguageId]: '',
					},
				},
			],
			name: {
				[defaultLanguageId]: `${fieldLabel}Layout`,
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

						return definitionField;
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
			.catch(({message}) => errorToast(message));
	};
};
