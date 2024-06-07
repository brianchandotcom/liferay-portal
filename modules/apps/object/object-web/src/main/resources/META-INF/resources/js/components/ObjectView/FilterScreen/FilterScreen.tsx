/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/modal';
import {
	BuilderScreen,
	Card,
	constantsUtils,
} from '@liferay/object-js-components-web';
import React, {useState} from 'react';

import {
	FilterErrors,
	FilterValidation,
	ModalAddFilter,
	OnSaveProps,
} from '../../ModalAddFilter';
import {TYPES, useViewContext} from '../objectViewContext';

export function FilterScreen() {
	const [
		{
			creationLanguageId,
			filterOperators,
			objectFields,
			objectView,
			workflowStatuses,
		},
		dispatch,
	] = useViewContext();

	const {objectViewFilterColumns} = objectView;

	const [editingObjectFieldName, setEditingObjectFieldName] = useState('');
	const [editingFilter, setEditingFilter] = useState(false);

	const [visibleModal, setVisibleModal] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => {
			setEditingFilter(false);
			setVisibleModal(false);
		},
	});

	const handleDeleteColumn = (objectFieldName: string) => {
		dispatch({
			payload: {objectFieldName},
			type: TYPES.DELETE_OBJECT_VIEW_FILTER_COLUMN,
		});
	};

	const saveFilterColumn = ({
		filterType,
		objectFieldName,
		valueList,
	}: OnSaveProps) => {
		if (editingFilter) {
			dispatch({
				payload: {
					filterType,
					objectFieldName,
					valueList,
				},
				type: TYPES.EDIT_OBJECT_VIEW_FILTER_COLUMN,
			});
		}
		else {
			dispatch({
				payload: {
					creationLanguageId,
					filterType,
					objectFieldName,
					valueList,
				},
				type: TYPES.ADD_OBJECT_VIEW_FILTER_COLUMN,
			});
		}
	};

	const validateFilters = ({
		checkedItems,
		disableDateValues,
		selectedFilterBy,
		selectedFilterTypeValue,
		setErrors,
	}: FilterValidation) => {
		setErrors({});
		const currentErrors: FilterErrors = {};

		if (!selectedFilterBy) {
			currentErrors.selectedFilterBy = constantsUtils.REQUIRED_MSG;
		}

		if (
			!selectedFilterTypeValue &&
			!disableDateValues &&
			(selectedFilterBy?.name !== 'status' ||
				selectedFilterBy?.businessType !== 'Picklist')
		) {
			currentErrors.selectedFilterType = constantsUtils.REQUIRED_MSG;
		}

		if (
			selectedFilterTypeValue &&
			(selectedFilterBy?.name === 'status' ||
				selectedFilterBy?.businessType === 'Picklist' ||
				selectedFilterBy?.businessType === 'Relationship') &&
			!checkedItems.length
		) {
			currentErrors.items = constantsUtils.REQUIRED_MSG;
		}

		setErrors(currentErrors);

		return currentErrors;
	};

	return (
		<>
			<Card title={Liferay.Language.get('filters')}>
				<BuilderScreen
					builderScreenItems={
						objectViewFilterColumns.map((filterColumn) => {
							if (
								filterColumn.objectFieldName === 'createDate' ||
								filterColumn.objectFieldName === 'modifiedDate'
							) {
								return {
									...filterColumn,
									disableEdit: true,
								};
							}
							else {
								return filterColumn;
							}
						}) ?? []
					}
					creationLanguageId={creationLanguageId}
					emptyState={{
						buttonText: Liferay.Language.get('new-filter'),
						description: Liferay.Language.get(
							'start-creating-a-filter-to-display-specific-data'
						),
						title: Liferay.Language.get(
							'no-filter-was-created-yet'
						),
					}}
					filter
					firstColumnHeader={Liferay.Language.get('filter-by')}
					onDeleteColumn={handleDeleteColumn}
					onEditing={setEditingFilter}
					onEditingObjectFieldName={setEditingObjectFieldName}
					onVisibleEditModal={setVisibleModal}
					openModal={() => setVisibleModal(true)}
					secondColumnHeader={Liferay.Language.get('type')}
					thirdColumnHeader={Liferay.Language.get('value')}
				/>
			</Card>

			{visibleModal && (
				<ModalAddFilter
					creationLanguageId={creationLanguageId}
					currentFilters={objectViewFilterColumns}
					disableDateValues
					editingFilter={editingFilter}
					editingObjectFieldName={editingObjectFieldName}
					filterOperators={filterOperators}
					header={Liferay.Language.get('new-filter')}
					objectFields={
						editingFilter
							? objectFields
							: objectFields.filter(
									(objectField: ObjectFieldView) => {
										if (
											objectField.businessType ===
												'Picklist' ||
											objectField.businessType ===
												'MultiselectPicklist' ||
											objectField.businessType ===
												'Relationship' ||
											objectField.name === 'createDate' ||
											objectField.name ===
												'modifiedDate' ||
											(objectField.name === 'status' &&
												!objectField.hasFilter)
										) {
											return objectField;
										}
									}
								)
					}
					observer={observer}
					onClose={onClose}
					onSave={saveFilterColumn}
					validate={validateFilters}
					workflowStatuses={workflowStatuses}
				/>
			)}
		</>
	);
}
