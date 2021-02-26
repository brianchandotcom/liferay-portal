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

import {addItem} from 'data-engine-js-components-web/js/utils/client.es';
import {
	errorToast,
	successToast,
} from 'data-engine-js-components-web/js/utils/toast.es';
import {DataLayoutBuilderActions} from 'data-engine-taglib';
import {useContext} from 'react';

import FormViewContext from './FormViewContext.es';

export default ({dataLayoutBuilder}) => {
	const [{dataDefinition, fieldSets}, dispatch] = useContext(FormViewContext);
	const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

	return (fieldName) => {
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
			defaultDataLayout: {
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
			},
			name: {
				[defaultLanguageId]: fieldLabel,
			},
		};

		addItem(
			`/o/data-engine/v2.0/data-definitions/by-content-type/app-builder-fieldset`,
			fieldSetDefinition
		)
			.then((dataDefinitionFieldSet) => {
				const ddmStructureId = dataDefinitionFieldSet.id;

				dispatch({
					payload: {
						fieldSets: [...fieldSets, dataDefinitionFieldSet],
					},
					type: DataLayoutBuilderActions.UPDATE_FIELDSETS,
				});

				const dataDefinitionFields = dataDefinition.dataDefinitionFields.map(
					(definitionField) => {
						if (definitionField.name === fieldName) {
							return {
								...definitionField,
								customProperties: {
									ddmStructureId,
									ddmStructureLayoutId:
										dataDefinitionFieldSet.defaultDataLayout
											.id,
									rows: '',
								},
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

				dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider?.dispatch?.(
					'fieldEdited',
					{
						fieldName,
						propertyName: 'ddmStructureId',
						propertyValue: ddmStructureId,
					}
				);

				successToast(Liferay.Language.get('fieldset-saved'));
			})
			.catch(({message}) => errorToast(message));
	};
};
