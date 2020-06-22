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

import classNames from 'classnames';
import {
	DataLayoutBuilderActions,
	DataLayoutVisitor,
	DragTypes,
	FieldTypeList,
} from 'data-engine-taglib';
import React, {useContext} from 'react';

import useDoubleClick from '../../hooks/useDoubleClick.es';
import {findFieldByName} from '../../utils/findFieldByName.es';
import DataLayoutBuilderContext from './DataLayoutBuilderInstanceContext.es';
import FormViewContext from './FormViewContext.es';
import useDeleteDefinitionField from './useDeleteDefinitionField.es';
import useDeleteDefinitionFieldModal from './useDeleteDefinitionFieldModal.es';

const createFieldSet = ({
	defaultLanguageId,
	fieldSetName,
	nestedDataDefinitionFields,
}) => {
	return {
		availableLanguageIds: [defaultLanguageId],
		dataDefinitionFields: nestedDataDefinitionFields,
		defaultLanguageId,
		description: {},
		name: {
			[defaultLanguageId]: fieldSetName,
		},
	};
};

const getFieldSet = (data) => {
	const {
		defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId(),
		fieldSetName,
		fieldSets,
	} = data;
	const fieldSet = fieldSets.find(
		({name}) => name[defaultLanguageId] === fieldSetName
	);

	return fieldSet || createFieldSet({...data, defaultLanguageId});
};

const getFieldTypes = ({
	dataDefinition,
	dataLayout,
	editingLanguageId,
	fieldSets,
	fieldTypes,
	focusedCustomObjectField,
}) => {
	const customDataDefinitionFields = [];
	const nativeDataDefinitionFields = [];
	const {dataLayoutPages} = dataLayout;
	const {dataDefinitionFields: fields} = dataDefinition;

	const setDefinitionField = (
		{
			customProperties,
			fieldType,
			label,
			name,
			nestedDataDefinitionFields = [],
		},
		nested
	) => {
		const {ddmStructureId} = customProperties;

		if (fieldType === 'section') {
			return;
		}

		const fieldTypeSettings = fieldTypes.find(({name}) => {
			return name === fieldType;
		});

		if (label[editingLanguageId]) {
			label = label[editingLanguageId];
		}
		else {
			label = label[Liferay.ThemeDisplay.getDefaultLanguageId()];
		}

		const isFieldGroup = fieldType === 'fieldset';
		const isFieldSet = isFieldGroup && ddmStructureId;

		const FieldTypeLabel = isFieldSet
			? Liferay.Language.get('fieldset')
			: fieldTypeSettings.label;

		const getDescription = () => {
			let description = '';

			if (isFieldGroup && !nested) {
				description = `- ${
					nestedDataDefinitionFields.length
				} ${Liferay.Language.get('fields')}`;
			}

			return `${FieldTypeLabel} ${description}`;
		};

		const dataDefinitionField = {
			active: name === focusedCustomObjectField.name,
			className: nested
				? 'custom-object-field-children'
				: 'custom-object-field',
			description: getDescription(),
			disabled: DataLayoutVisitor.containsField(dataLayoutPages, name),
			dragAlignment: 'right',
			dragType: isFieldGroup
				? DragTypes.DRAG_FIELDSET
				: DragTypes.DRAG_DATA_DEFINITION_FIELD,
			icon: fieldTypeSettings.icon,
			isCustomField: !customProperties['nativeField'],
			isFieldSet,
			...(isFieldGroup && {
				fieldSet: getFieldSet({
					fieldSetName: label,
					fieldSets,
					nestedDataDefinitionFields,
				}),
				useFieldName: name,
			}),
			label,
			name,
			nestedDataDefinitionFields: nestedDataDefinitionFields.map(
				(nestedField) => setDefinitionField(nestedField, true)
			),
		};

		if (nested) {
			return dataDefinitionField;
		}

		if (dataDefinitionField.isCustomField) {
			customDataDefinitionFields.push(dataDefinitionField);
		}
		else {
			nativeDataDefinitionFields.push(dataDefinitionField);
		}
	};

	fields.forEach((fieldType) => {
		setDefinitionField(fieldType);
	});

	return [customDataDefinitionFields, nativeDataDefinitionFields];
};

const FieldCategory = ({categoryName}) => (
	<div
		className={classNames('custom-object-sidebar-header', 'ml-1 pt-2 pb-2')}
	>
		<div className="autofit-row autofit-row-center">
			<>
				<div className="autofit-col autofit-col-expand">
					<h3 className="category-text">{categoryName}</h3>
				</div>
			</>
		</div>
	</div>
);

export default ({keywords}) => {
	const [dataLayoutBuilder] = useContext(DataLayoutBuilderContext);
	const [state, dispatch] = useContext(FormViewContext);
	const {dataDefinition, fieldSets} = state;
	const {dataDefinitionFields} = dataDefinition;
	const [customFieldTypes, nativeFieldTypes] = getFieldTypes(state);

	const onClick = ({name}) => {
		const dataDefinitionField = findFieldByName(dataDefinitionFields, name);

		dispatch({
			payload: {dataDefinitionField},
			type: DataLayoutBuilderActions.UPDATE_FOCUSED_CUSTOM_OBJECT_FIELD,
		});
	};
	const onDoubleClick = ({name}) => {
		const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();
		const {activePage, pages} = dataLayoutBuilder.getStore();
		const indexes = {
			columnIndex: 0,
			pageIndex: activePage,
			rowIndex: pages[activePage].rows.length,
		};

		const {fieldType, label, nestedDataDefinitionFields} = findFieldByName(
			dataDefinitionFields,
			name
		);

		if (fieldType === 'fieldset') {
			return dataLayoutBuilder.dispatch(
				'fieldSetAdded',
				DataLayoutBuilderActions.dropFieldSet({
					dataLayoutBuilder,
					fieldName: name,
					fieldSet: getFieldSet({
						defaultLanguageId,
						fieldSetName: label[defaultLanguageId],
						fieldSets,
						nestedDataDefinitionFields,
					}),
					indexes,
					useFieldName: name,
				})
			);
		}

		dataLayoutBuilder.dispatch(
			'fieldAdded',
			DataLayoutBuilderActions.dropCustomObjectField({
				addedToPlaceholder: true,
				dataDefinition,
				dataDefinitionFieldName: name,
				dataLayoutBuilder,
				indexes,
			})
		);
	};

	const [handleOnClick, handleOnDoubleClick] = useDoubleClick(
		onClick,
		onDoubleClick
	);

	const deleteField = useDeleteDefinitionField({dataLayoutBuilder});

	const onDeleteDefinitionField = useDeleteDefinitionFieldModal((event) =>
		deleteField(event)
	);
	const showCategories =
		!!customFieldTypes.length && !!nativeFieldTypes.length;

	const fieldTypeListProps = {
		deleteLabel: Liferay.Language.get('delete-from-object'),
		keywords,
		onClick: handleOnClick,
		onDelete: (fieldName) =>
			onDeleteDefinitionField({activePage: 0, fieldName}),
		onDoubleClick: handleOnDoubleClick,
	};

	return (
		<>
			{showCategories && (
				<FieldCategory
					categoryName={Liferay.Language.get('custom-fields')}
				/>
			)}

			<FieldTypeList
				{...fieldTypeListProps}
				fieldTypes={customFieldTypes}
			/>

			{showCategories && (
				<FieldCategory
					categoryName={Liferay.Language.get('native-fields')}
				/>
			)}

			<FieldTypeList
				{...fieldTypeListProps}
				fieldTypes={nativeFieldTypes}
			/>
		</>
	);
};
