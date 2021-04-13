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
	DataConverter,
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

const createFieldSet = ({name, nestedDataDefinitionFields, ...otherProps}) => {
	const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

	return {
		...otherProps,
		availableLanguageIds: [defaultLanguageId],
		dataDefinitionFields: nestedDataDefinitionFields,
		defaultLanguageId,
		description: {},
		name,
	};
};

const getFieldSet = ({customProperties, fieldSets, name, ...otherProps}) => {
	const fieldSet = fieldSets.find(
		({id}) => id === Number(customProperties.ddmStructureId)
	);

	if (fieldSet) {
		return {
			...fieldSet,
			name,
		};
	}

	return createFieldSet({
		customProperties,
		name,
		...otherProps,
	});
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
	const {dataDefinitionFields: fields, defaultLanguageId} = dataDefinition;

	const setDefinitionField = (
		{
			customProperties = {},
			fieldType,
			label,
			name,
			required,
			nestedDataDefinitionFields = [],
			repeatable,
			showLabel,
		},
		nested
	) => {
		if (fieldType === 'section') {
			return;
		}

		const {ddmStructureId} = customProperties;
		const fieldTypeSettings = fieldTypes.find(({name}) => {
			return name === fieldType;
		});

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
				? DragTypes.DRAG_FIELDSET_ADD
				: DragTypes.DRAG_DATA_DEFINITION_FIELD_ADD,
			icon: fieldTypeSettings.icon,
			isCustomField: !customProperties.nativeField,
			isFieldSet,
			...(isFieldGroup && {
				fieldSet: getFieldSet({
					customProperties,
					fieldSets,
					name: label,
					nestedDataDefinitionFields,
				}),
				properties: {
					collapsible: customProperties.collapsible,
					repeatable,
					showLabel,
				},
				useFieldName: name,
			}),
			label: label[editingLanguageId] || label[defaultLanguageId],
			name,
			nestedDataDefinitionFields: nestedDataDefinitionFields.map(
				(nestedField) => setDefinitionField(nestedField, true)
			),
			required,
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
					<span className="category-text">{categoryName}</span>
				</div>
			</>
		</div>
	</div>
);

const CustomObjectFieldsList = ({keywords}) => {
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
		const {
			dispatch: layoutProviderDispatch,
			state: {activePage, pages},
		} = dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider;

		const indexes = {
			columnIndex: 0,
			pageIndex: activePage,
			rowIndex: pages[activePage].rows.length,
		};

		const {
			customProperties,
			fieldType,
			label,
			repeatable,
			showLabel,
			...otherFieldProps
		} = findFieldByName(dataDefinitionFields, name);

		if (fieldType === 'fieldset') {
			const {
				contentTypeConfig: {allowInvalidAvailableLocalesForProperty},
				editingLanguageId,
				fieldTypes,
			} = dataLayoutBuilder.props;

			const fieldSet = getFieldSet({
				...otherFieldProps,
				customProperties,
				fieldSets,
				name: label,
			});

			layoutProviderDispatch?.('fieldSetAdded', {
				defaultLanguageId: fieldSet.defaultLanguageId,
				fieldName: name,
				indexes,
				properties: {
					collapsible: customProperties.collapsible,
					repeatable,
					showLabel,
				},
				useFieldName: name,
				...DataConverter.getDataDefinitionFieldSet({
					allowInvalidAvailableLocalesForProperty,
					editingLanguageId,
					fieldSet,
					fieldTypes,
				}),
			});

			return;
		}

		layoutProviderDispatch?.(
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
				dataDefinition={dataDefinition}
				fieldTypes={customFieldTypes}
				onDelete={(fieldName) =>
					onDeleteDefinitionField({activePage: 0, fieldName})
				}
				showEmptyState={false}
			/>

			{showCategories && (
				<FieldCategory
					categoryName={Liferay.Language.get('native-fields')}
				/>
			)}

			<FieldTypeList
				{...fieldTypeListProps}
				dataDefinition={dataDefinition}
				fieldTypes={nativeFieldTypes}
				showEmptyState={false}
			/>
		</>
	);
};

CustomObjectFieldsList.displayName = 'CustomObjectFieldsList';
export default CustomObjectFieldsList;
