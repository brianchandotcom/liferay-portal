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

import React, {useContext} from 'react';

import FieldTypeList from '../../components/field-types/FieldTypeList.es';
import {containsField} from '../../utils/dataLayoutVisitor.es';
import {DRAG_CUSTOM_OBJECT_FIELD} from '../../utils/dragTypes.es';
import DataLayoutBuilderContext from './DataLayoutBuilderContext.es';
import FormViewContext from './FormViewContext.es';
import {
	dropCustomObjectField,
	UPDATE_FOCUSED_CUSTOM_OBJECT_FIELD
} from './actions.es';
import useDeleteDefinitionField from './useDeleteDefinitionField.es';
import useDeleteDefinitionFieldModal from './useDeleteDefinitionFieldModal.es';

const getFieldTypes = ({
	dataDefinition,
	dataLayout,
	fieldTypes,
	focusedCustomObjectField
}) => {
	const {dataDefinitionFields} = dataDefinition;
	const {dataLayoutPages} = dataLayout;

	return dataDefinitionFields.map(({fieldType, label, name}) => {
		const fieldTypeSettings = fieldTypes.find(({name}) => {
			return name === fieldType;
		});

		return {
			active: name === focusedCustomObjectField.name,
			className: 'custom-object-field',
			description: fieldTypeSettings.label,
			disabled: containsField(dataLayoutPages, name),
			dragAlignment: 'right',
			dragType: DRAG_CUSTOM_OBJECT_FIELD,
			icon: fieldTypeSettings.icon,
			label: label.en_US,
			name
		};
	});
};

export default ({keywords}) => {
	const [dataLayoutBuilder] = useContext(DataLayoutBuilderContext);
	const [state, dispatch] = useContext(FormViewContext);
	const {dataDefinition} = state;
	const {dataDefinitionFields} = dataDefinition;
	const fieldTypes = getFieldTypes(state);
	const onClick = ({name}) => {
		const dataDefinitionField = dataDefinitionFields.find(
			({name: currentName}) => currentName === name
		);

		dispatch({
			payload: {dataDefinitionField},
			type: UPDATE_FOCUSED_CUSTOM_OBJECT_FIELD
		});
	};
	const onDoubleClick = ({name}) => {
		const {activePage, pages} = dataLayoutBuilder.getStore();

		dataLayoutBuilder.dispatch(
			'fieldAdded',
			dropCustomObjectField({
				addedToPlaceholder: true,
				dataDefinition,
				dataDefinitionFieldName: name,
				dataLayoutBuilder,
				indexes: {
					columnIndex: 0,
					pageIndex: activePage,
					rowIndex: pages[activePage].rows.length
				},
				skipFieldNameGeneration: true
			})
		);
	};

	const deleteField = useDeleteDefinitionField({dataLayoutBuilder});

	const onDeleteDefinitionField = useDeleteDefinitionFieldModal(fieldName =>
		deleteField(fieldName)
	);

	return (
		<FieldTypeList
			deleteLabel={Liferay.Language.get('delete-from-object')}
			fieldTypes={fieldTypes}
			keywords={keywords}
			onClick={onClick}
			onDelete={onDeleteDefinitionField}
			onDoubleClick={onDoubleClick}
		/>
	);
};
