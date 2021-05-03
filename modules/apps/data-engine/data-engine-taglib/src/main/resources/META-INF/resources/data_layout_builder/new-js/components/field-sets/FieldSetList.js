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

import ClayButton from '@clayui/button';
import {
	EVENT_TYPES,
	PagesVisitor,
	useConfig,
	useForm,
	useFormState,
} from 'data-engine-js-components-web';
import React, {useEffect, useState} from 'react';

import EmptyState from '../../../js/components/empty-state/EmptyState.es';
import {useNewDeleteFieldSet} from '../../../js/components/field-sets/actions/useDeleteFieldSet.es';
import {usePropagateFieldSet} from '../../../js/components/field-sets/actions/usePropagateFieldSet.es';
import FieldType from '../../../js/components/field-types/FieldType.es';
import {DRAG_FIELDSET_ADD} from '../../../js/drag-and-drop/dragTypes.es';
import {getDataDefinitionFieldSet} from '../../../js/utils/dataConverter.es';
import {getLocalizedValue, getPluralMessage} from '../../../js/utils/lang.es';
import {getSearchRegex} from '../../../js/utils/search.es';
import FieldSetModal from './FieldSetModal';

function getSortedFieldsets(fieldsets) {
	return fieldsets.sort((a, b) => {
		const localizedValueA = getLocalizedValue(a.defaultLanguageId, a.name);
		const localizedValueB = getLocalizedValue(b.defaultLanguageId, b.name);

		return localizedValueA.localeCompare(localizedValueB);
	});
}

function getFilteredFieldsets(fieldsets, keywords) {
	const regex = getSearchRegex(keywords);
	const filteredFieldsets = fieldsets.filter(({defaultLanguageId, name}) =>
		regex.test(getLocalizedValue(defaultLanguageId, name))
	);

	return getSortedFieldsets(filteredFieldsets);
}

export default function FieldSetList({searchTerm}) {
	const [modalState, setModalState] = useState({
		isVisible: false,
	});
	const [fieldSetsInUse, setFieldSetsInUse] = useState(new Set());

	const {
		activePage,
		availableLanguageIds,
		defaultLanguageId,
		editingLanguageId,
		fieldSets,
		pages,
	} = useFormState();

	const {allowInvalidAvailableLocalesForProperty, fieldTypes} = useConfig();
	const dispatch = useForm();

	const filteredFieldsets = getFilteredFieldsets(fieldSets, searchTerm);

	useEffect(() => {
		const fieldsInuse = new Set();

		new PagesVisitor(pages).mapFields((field) => {
			if (field.type === 'fieldset') {
				fieldsInuse.add(field.ddmStructureId);
			}
		});
		setFieldSetsInUse(fieldsInuse);
	}, [pages]);

	const toggleFieldSet = (fieldSet) => {
		setModalState(({isVisible}) => ({
			fieldSet,
			isVisible: !isVisible,
		}));
	};

	const deleteFieldSet = useNewDeleteFieldSet();

	const propagateFieldSet = usePropagateFieldSet();

	const onDoubleClick = ({fieldSet}) => {
		dispatch({
			payload: {
				indexes: {
					columnIndex: 0,
					pageIndex: activePage,
					rowIndex: pages[activePage].rows.length,
				},
				...getDataDefinitionFieldSet({
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
	};

	const CreateNewFieldsetButton = () => (
		<ClayButton
			block
			className="add-fieldset"
			displayType="secondary"
			onClick={() => toggleFieldSet()}
		>
			{Liferay.Language.get('create-new-fieldset')}
		</ClayButton>
	);

	return (
		<>
			{filteredFieldsets.length ? (
				<>
					<CreateNewFieldsetButton />

					<div className="mt-3">
						{filteredFieldsets.map((fieldSet) => {
							const fieldSetName = getLocalizedValue(
								fieldSet.defaultLanguageId,
								fieldSet.name
							);

							return (
								<FieldType
									actions={[
										{
											action: () =>
												toggleFieldSet(fieldSet),
											name: Liferay.Language.get('edit'),
										},
										{
											action: () =>
												propagateFieldSet({
													fieldSet,
													isDeleteAction: true,
													modal: {
														actionMessage: Liferay.Language.get(
															'delete'
														),
														fieldSetMessage: Liferay.Language.get(
															'the-fieldset-will-be-deleted-permanently-from'
														),
														headerMessage: Liferay.Language.get(
															'delete'
														),
														status: 'danger',
														warningMessage: Liferay.Language.get(
															'this-action-may-erase-data-permanently'
														),
													},
													onPropagate: deleteFieldSet,
												}),
											name: Liferay.Language.get(
												'delete'
											),
										},
									]}
									description={getPluralMessage(
										Liferay.Language.get('x-field'),
										Liferay.Language.get('x-fields'),
										fieldSet.dataDefinitionFields.length
									)}
									disabled={fieldSetsInUse.has(fieldSet.id)}
									dragType={DRAG_FIELDSET_ADD}
									fieldSet={fieldSet}
									icon="forms"
									key={fieldSet.dataDefinitionKey}
									label={fieldSetName}
									onDoubleClick={onDoubleClick}
								/>
							);
						})}
					</div>
				</>
			) : (
				<div className="mt-2">
					<EmptyState
						emptyState={{
							button: CreateNewFieldsetButton,

							description: Liferay.Language.get(
								'there-are-no-fieldsets-description'
							),
							title: Liferay.Language.get(
								'there-are-no-fieldsets'
							),
						}}
						keywords={searchTerm}
						small
					/>
				</div>
			)}
			{modalState.isVisible && (
				<FieldSetModal
					fieldSet={modalState.fieldSet}
					onClose={toggleFieldSet}
				/>
			)}
		</>
	);
}
