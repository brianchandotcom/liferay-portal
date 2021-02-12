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

import {DataConverter, DragTypes} from 'data-engine-taglib';
import {useDrop as useDndDrop} from 'react-dnd';

import {EVENT_TYPES} from '../actions/eventTypes.es';
import {useConfig} from './useConfig.es';
import {useForm, useFormState} from './useForm.es';

export const DND_ORIGIN_TYPE = {
	EMPTY: 'empty',
	FIELD: 'field',
};

export const useDrop = ({
	columnIndex,
	fieldName,
	origin,
	pageIndex,
	parentField,
	rowIndex,
}) => {
	const {
		allowInvalidAvailableLocalesForProperty,
		editingLanguageId,
	} = useFormState();
	const {fieldTypes} = useConfig();

	const dispatch = useForm();

	const [{canDrop, overTarget}, drop] = useDndDrop({
		accept: [
			DragTypes.DRAG_FIELD_TYPE_ADD,
			DragTypes.DRAG_FIELD_TYPE_MOVE,
			DragTypes.DRAG_DATA_DEFINITION_FIELD_ADD,
			DragTypes.DRAG_FIELD_TYPE_ADD,
			DragTypes.DRAG_FIELDSET_ADD,
		],
		collect: (monitor) => ({
			canDrop: monitor.canDrop(),
			overTarget: monitor.isOver({shallow: true}),
		}),
		drop: ({data, pageIndex: sourceFieldPage, type}, monitor) => {
			if (monitor.didDrop()) {
				return;
			}
			const {
				dataDefinition,
				fieldSet,
				name,
				properties,
				useFieldName,
			} = data;

			const {fieldType, label, settingsContext} =
				(dataDefinition &&
					DataConverter.getDataDefinitionFieldByFieldName({
						dataDefinition,
						editingLanguageId,
						fieldName: name,
						fieldTypes,
					})) ??
				{};
			const {availableLanguageIds, defaultLanguageId} = fieldSet ?? {};
			switch (type) {
				case DragTypes.DRAG_FIELD_TYPE_ADD:
					dispatch({
						payload: {
							data: {
								fieldName,
								parentFieldName: parentField?.fieldName,
							},
							fieldType: {
								...fieldTypes.find(({name}) => {
									return name === data.name;
								}),
								editable: true,
							},
							indexes: {columnIndex, pageIndex, rowIndex},
						},
						type:
							origin === DND_ORIGIN_TYPE.EMPTY
								? EVENT_TYPES.FIELD.ADD
								: EVENT_TYPES.SECTION.ADD,
					});
					break;
				case DragTypes.DRAG_FIELD_TYPE_MOVE:
					dispatch({
						payload: {
							sourceFieldName: data.fieldName,
							sourceFieldPage,
							targetFieldName: fieldName,
							targetIndexes: {
								columnIndex,
								pageIndex,
								rowIndex,
							},
							targetParentFieldName: parentField?.fieldName,
						},
						type: EVENT_TYPES.DND.MOVE,
					});
					break;
				case DragTypes.DRAG_DATA_DEFINITION_FIELD_ADD:
					dispatch({
						payload: {
							data: {
								fieldName,
								parentFieldName: parentField?.fieldName,
							},
							fieldType: {
								...fieldTypes.find(({name}) => {
									return name === fieldType;
								}),
								editable: true,
								label:
									label[
										editingLanguageId ??
											themeDisplay.getLanguageId()
									],
								settingsContext,
							},
							indexes: {columnIndex, pageIndex, rowIndex},
							skipFieldNameGeneration: true,
						},
						type:
							origin === DND_ORIGIN_TYPE.EMPTY
								? EVENT_TYPES.FIELD.ADD
								: EVENT_TYPES.SECTION.ADD,
					});
					break;
				case DragTypes.DRAG_FIELDSET_ADD:
					dispatch({
						payload: {
							availableLanguageIds,
							defaultLanguageId,
							fieldName,
							indexes: {columnIndex, pageIndex, rowIndex},
							parentFieldName: parentField?.fieldName,
							properties,
							useFieldName,
							...DataConverter.getDataDefinitionFieldSet({
								allowInvalidAvailableLocalesForProperty,
								availableLanguageIds,
								defaultLanguageId,
								editingLanguageId,
								fieldSet,
								fieldTypes,
							}),
						},
						type: EVENT_TYPES.FIELD_SET.ADD,
					});
					break;
				default:
					break;
			}
		},
	});

	return {
		canDrop,
		drop,
		overTarget,
	};
};
