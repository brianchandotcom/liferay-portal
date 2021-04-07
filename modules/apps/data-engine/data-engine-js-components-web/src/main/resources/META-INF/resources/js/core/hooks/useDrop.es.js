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
import {elementSetAdded} from '../thunks/elementSetAdded.es';
import {useConfig} from './useConfig.es';
import {useForm, useFormState} from './useForm.es';

export const DND_ORIGIN_TYPE = {
	EMPTY: 'empty',
	FIELD: 'field',
};

const DRAG_ELEMENT_SET_ADD = 'elementSet:add';

const isSameIndexes = (target, source) =>
	target.pageIndex === source.pageIndex &&
	target.rowIndex === source.rowIndex &&
	target.columnIndex === source.columnIndex;

/**
 * Checks whether Field is moving into itself. In conventional mode, we would be
 * visiting all the fields within it to check if the target belongs to the Field
 * root (in this case the source), but this can be very slow depending on the
 * depth and it is very expensive in this hot path instead this method implement
 * heuristic algorithm based on two assumptions:
 *
 * - The target indexes are the same as the source indexes
 * - The target depth is greater than the source
 *
 * The indexes are an Array<Object> that contains the index of the Field
 * according to the depth level, the head of the array is the current loc and
 * the tail is the root of the tree. It does not compare all target indexes just
 * up to the same level of depth as the source.
 *
 * [
 *  {pageIndex: 0, rowIndex: 1, columnIndex: 0}, <- Head (deeper)
 *  {pageIndex: 0, rowIndex: 0, columnIndex: 0},
 *  {pageIndex: 0, rowIndex: 0, columnIndex: 0}, <- Tail (root)
 * ]
 *
 * The comparison of the indexes also fails in the first index that is not equal
 * to the source index to fail early.
 */
const isMovingIntoItself = (targetParentLoc, sourceParentLoc) => {
	const targetLocClosestSourceTree = targetParentLoc.slice(
		-sourceParentLoc.length
	);

	return (
		!targetLocClosestSourceTree.some(
			(loc, index) => !isSameIndexes(loc, sourceParentLoc[index])
		) && targetParentLoc.length > sourceParentLoc.length
	);
};

/**
 * Just check if the Field is a FieldGroup before checking if it is moving into
 * itself. The index of the source and target are added to the loc, at the
 * level where `useDrop` is used it is not visible the loc of the Field being
 * rendered.
 */
const isFieldGroupMovingIntoItself = ({
	sourceIndexes,
	sourceParentField,
	targetIndexes,
	targetParentField,
	type,
}) =>
	type === 'fieldset' &&
	isMovingIntoItself(
		[targetIndexes, ...(targetParentField?.loc ?? [])],
		[sourceIndexes, ...(sourceParentField?.loc ?? [])]
	);

const isDroppingFieldGroupIntoField = (targetField, sourceField) =>
	sourceField?.type === 'fieldset' && targetField !== undefined;

/**
 * Determines whether the source Field is being moved into inside a Field
 * where its parent is a FieldGroup with just that element.
 */
const isDroppingFieldIntoSingleField = (origin, targetParentField) =>
	origin === DND_ORIGIN_TYPE.FIELD &&
	targetParentField?.type === 'fieldset' &&
	targetParentField.nestedFields.length === 1;

/**
 * Determines whether are moving the source Field into inside the Field
 * in the same FieldGroup to create another FieldGroup but with only
 * two fields.
 */
const isDroppingFieldIntoFieldAndSameGroup = (
	origin,
	sourceParentField,
	targetParentField
) =>
	origin === DND_ORIGIN_TYPE.FIELD &&
	sourceParentField?.type === 'fieldset' &&
	sourceParentField?.fieldName === targetParentField?.fieldName &&
	targetParentField.nestedFields.length === 2;

const isDroppingFieldIntoFieldset = (sourceField, targetField) =>
	sourceField.fieldName !== targetField?.fieldName &&
	targetField?.type === 'fieldset' &&
	!!targetField?.ddmStructureId;

const isSameField = (targetField, sourceField) =>
	targetField && targetField.fieldName === sourceField.fieldName;

export const useDrop = ({
	columnIndex,
	field,
	origin,
	pageIndex,
	parentField,
	rowIndex,
}) => {
	const {editingLanguageId} = useFormState();
	const {allowInvalidAvailableLocalesForProperty, fieldTypes} = useConfig();

	const dispatch = useForm();

	const indexes = {columnIndex, pageIndex, rowIndex};
	const {
		DRAG_DATA_DEFINITION_FIELD_ADD,
		DRAG_FIELD_TYPE_ADD,
		DRAG_FIELD_TYPE_MOVE,
		DRAG_FIELDSET_ADD,
	} = DragTypes;

	const [{canDrop, overTarget}, drop] = useDndDrop({
		accept: [...Object.values(DragTypes), DRAG_ELEMENT_SET_ADD],
		canDrop: (item) =>
			!isSameField(field, item.data) &&
			!isDroppingFieldGroupIntoField(field, item.data) &&
			!isDroppingFieldIntoFieldset(item.data, field) &&
			!isFieldGroupMovingIntoItself({
				sourceIndexes: item.sourceIndexes,
				sourceParentField: item.sourceParentField,
				targetIndexes: {
					columnIndex,
					pageIndex,
					rowIndex,
				},
				targetParentField: parentField,
				type: item.data.type,
			}),
		collect: (monitor) => ({
			canDrop: monitor.canDrop(),
			overTarget: monitor.isOver({shallow: true}),
		}),
		drop: ({data, sourceIndexes, sourceParentField, type}, monitor) => {
			if (
				monitor.didDrop() ||
				!monitor.canDrop() ||
				isDroppingFieldIntoSingleField(origin, parentField) ||
				isDroppingFieldIntoFieldAndSameGroup(
					origin,
					sourceParentField,
					parentField
				)
			) {
				return;
			}

			switch (type) {
				case DRAG_FIELD_TYPE_ADD:
					dispatch({
						payload: {
							data: {
								fieldName: field?.fieldName,
								parentFieldName: parentField?.fieldName,
							},
							fieldType: {
								...fieldTypes.find(({name}) => {
									return name === data.name;
								}),
								editable: true,
							},
							indexes,
						},
						type:
							origin === DND_ORIGIN_TYPE.EMPTY
								? EVENT_TYPES.FIELD.ADD
								: EVENT_TYPES.SECTION.ADD,
					});
					break;

				case DRAG_FIELD_TYPE_MOVE:
					dispatch({
						payload: {
							sourceFieldName: data.fieldName,
							sourceFieldPage: sourceIndexes.pageIndex,
							sourceParentField,
							targetFieldName: field?.fieldName,
							targetIndexes: indexes,
							targetParentFieldName: parentField?.fieldName,
						},
						type: EVENT_TYPES.DND.MOVE,
					});
					break;

				case DRAG_DATA_DEFINITION_FIELD_ADD: {
					const {dataDefinition, name} = data;

					const {
						fieldType,
						label,
						settingsContext,
					} = DataConverter.getDataDefinitionFieldByFieldName({
						dataDefinition,
						editingLanguageId,
						fieldName: name,
						fieldTypes,
					});

					dispatch({
						payload: {
							data: {
								fieldName: field?.fieldName,
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
							indexes,
							skipFieldNameGeneration: true,
						},
						type:
							origin === DND_ORIGIN_TYPE.EMPTY
								? EVENT_TYPES.FIELD.ADD
								: EVENT_TYPES.SECTION.ADD,
					});
					break;
				}
				case DRAG_FIELDSET_ADD: {
					const {fieldSet, properties, useFieldName} = data;

					const {availableLanguageIds, defaultLanguageId} = fieldSet;

					dispatch({
						payload: {
							availableLanguageIds,
							defaultLanguageId,
							fieldName: field?.fieldName,
							indexes,
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
				}

				case DRAG_ELEMENT_SET_ADD:
					dispatch(elementSetAdded({indexes, ...data.payload}));
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
