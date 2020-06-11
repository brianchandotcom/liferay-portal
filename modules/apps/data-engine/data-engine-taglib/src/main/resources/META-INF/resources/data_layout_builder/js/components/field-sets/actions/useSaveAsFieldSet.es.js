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
import {
	containsField,
	normalizeDataLayoutRows,
} from '../../../utils/dataLayoutVisitor.es';
import {errorToast, successToast} from '../../../utils/toast.es';

export default ({childrenContext, fieldSet, otherProps: {DataLayout}}) => {
	const [{dataDefinition, dataLayout, fieldSets}, dispatch] = useContext(
		AppContext
	);
	const {state: childrenState} = childrenContext;
	const [dataLayoutBuilder] = useContext(DataLayoutBuilderContext);

	return () => {
		const {id} = fieldSet;
		const {
			dataDefinition: {dataDefinitionFields},
			dataLayout: {dataLayoutPages},
		} = childrenState;

		fieldSet.dataDefinitionFields = dataDefinitionFields;
		fieldSet.defaultDataLayout.dataLayoutPages = dataLayoutPages;

		const updatedFieldSets = fieldSets.map((field) => {
			if (field.id === id) {
				return fieldSet;
			}

			return field;
		});

		dispatch({
			payload: {
				fieldSets: updatedFieldSets,
			},
			type: UPDATE_FIELDSETS,
		});

		const dataDefinitionField = dataDefinition.dataDefinitionFields.find(
			({customProperties: {ddmStructureId}}) => ddmStructureId == id
		);

		if (dataDefinitionField) {
			const fieldName = dataDefinitionField.name;
			if (containsField(dataLayout.dataLayoutPages, fieldName)) {
				dataLayoutBuilder.dispatch('fieldEdited', {
					fieldName,
					propertyName: 'nestedFields',
					propertyValue: dataDefinitionFields.map(({name}) =>
						DataLayout.getDDMFormField(
							childrenState.dataDefinition,
							name
						)
					),
				});

				dataLayoutBuilder.dispatch('fieldEdited', {
					fieldName,
					propertyName: 'rows',
					propertyValue: normalizeDataLayoutRows(dataLayoutPages),
				});
			}
			else {
				const ddFields = dataDefinition.dataDefinitionFields.map(
					(ddField) => {
						if (ddField.name === fieldName) {
							return {
								...ddField,
								nestedDataDefinitionFields: dataDefinitionFields,
							};
						}

						return ddField;
					}
				);

				dispatch({
					payload: {
						dataDefinition: {
							...dataDefinition,
							dataDefinitionFields: ddFields,
						},
					},
					type: UPDATE_DATA_DEFINITION,
				});
			}
		}

		updateItem(`/o/data-engine/v2.0/data-definitions/${id}`, fieldSet)
			.then(() => {
				successToast(Liferay.Language.get('fieldset-saved'));
			})
			.catch(({message}) => errorToast(message));
	};
};
