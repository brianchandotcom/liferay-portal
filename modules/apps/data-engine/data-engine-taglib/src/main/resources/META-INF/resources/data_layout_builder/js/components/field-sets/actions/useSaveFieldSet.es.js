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

import {useContext} from 'react';

import AppContext from '../../../AppContext.es';
import {UPDATE_DATA_DEFINITION, UPDATE_FIELDSETS} from '../../../actions.es';
import DataLayoutBuilderContext from '../../../data-layout-builder/DataLayoutBuilderContext.es';
import {updateItem} from '../../../utils/client.es';
import {getDataDefinitionFieldSet} from '../../../utils/dataDefinition.es';
import {containsField} from '../../../utils/dataLayoutVisitor.es';
import {
	normalizeDataDefinition,
	normalizeDataLayout,
	normalizeDataLayoutRows,
} from '../../../utils/normalizers.es';
import {errorToast, successToast} from '../../../utils/toast.es';

export default ({
	availableLanguageIds,
	childrenContext,
	defaultLanguageId,
	fieldSet,
	onEditingLanguageIdChange: setEditingLanguageId,
}) => {
	const [context, dispatch] = useContext(AppContext);
	const [dataLayoutBuilder] = useContext(DataLayoutBuilderContext);
	const {dataDefinition, dataLayout, fieldSets} = context;
	const {state: childrenState} = childrenContext;
	const {
		contentTypeConfig: {allowInvalidAvailableLocalesForProperty},
	} = dataLayoutBuilder.props;

	return (name) => {
		const {
			dataDefinition: {dataDefinitionFields},
			dataLayout: {dataLayoutPages},
		} = childrenState;

		let newDataDefinition = {
			...fieldSet,
			availableLanguageIds,
			dataDefinitionFields,
			name,
		};

		if (!newDataDefinition.name[defaultLanguageId]) {
			setEditingLanguageId(defaultLanguageId);

			return Promise.reject(
				new Error(Liferay.Language.get('please-enter-a-valid-title'))
			);
		}

		if (!allowInvalidAvailableLocalesForProperty) {
			newDataDefinition = normalizeDataDefinition(
				newDataDefinition,
				fieldSet.defaultLanguageId
			);
		}

		const normalizedFieldSet = {
			...newDataDefinition,
			defaultDataLayout: normalizeDataLayout(
				{
					...fieldSet.defaultDataLayout,
					dataLayoutPages,
				},
				fieldSet.defaultLanguageId
			),
		};

		return updateItem(
			`/o/data-engine/v2.0/data-definitions/${fieldSet.id}`,
			normalizedFieldSet
		)
			.then(() => {
				const dataDefinitionFieldSet = getDataDefinitionFieldSet(
					dataDefinition.dataDefinitionFields,
					fieldSet.id
				);

				if (dataDefinitionFieldSet) {
					const fieldName = dataDefinitionFieldSet.name;

					if (containsField(dataLayout.dataLayoutPages, fieldName)) {
						dataLayoutBuilder.dispatch('fieldEditedProperties', {
							defaultLanguageId: fieldSet.defaultLanguageId,
							fieldName,
							properties: [
								{
									name: 'nestedFields',
									value: dataDefinitionFields.map(({name}) =>
										dataLayoutBuilder.getDDMFormField(
											childrenState.dataDefinition,
											name
										)
									),
								},
								{
									name: 'rows',
									value: normalizeDataLayoutRows(
										dataLayoutPages
									),
								},
							],
						});
					}
					else {
						dispatch({
							payload: {
								dataDefinition: {
									...dataDefinition,
									dataDefinitionFields: dataDefinition.dataDefinitionFields.map(
										(field) => {
											const {
												customProperties: {
													ddmStructureId,
												},
											} = field;

											if (ddmStructureId == fieldSet.id) {
												return {
													...field,
													nestedDataDefinitionFields: dataDefinitionFields,
												};
											}

											return field;
										}
									),
								},
							},
							type: UPDATE_DATA_DEFINITION,
						});
					}
				}

				return Promise.resolve();
			})
			.then(() => {
				dispatch({
					payload: {
						fieldSets: fieldSets.map((field) => {
							if (fieldSet.id === field.id) {
								return normalizedFieldSet;
							}

							return field;
						}),
					},
					type: UPDATE_FIELDSETS,
				});

				successToast(Liferay.Language.get('fieldset-saved'));

				return Promise.resolve();
			})
			.catch(({message}) => errorToast(message));
	};
};
