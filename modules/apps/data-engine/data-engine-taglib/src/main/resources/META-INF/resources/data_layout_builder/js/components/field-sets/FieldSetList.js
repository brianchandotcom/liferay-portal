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
	DRAG_TYPES,
	EVENT_TYPES,
	useForm,
	useFormState,
} from 'data-engine-js-components-web';
import React, {useState} from 'react';

import {getLocalizedValue, getPluralMessage} from '../../utils/lang.es';
import {getSearchRegex} from '../../utils/search.es';
import EmptyState from '../empty-state/EmptyState.es';
import FieldType from '../field-types/FieldType.es';
import FieldSetModal from './FieldSetModal';
import useDeleteFieldSet from './actions/useDeleteFieldSet.es';
import usePropagateFieldSet from './actions/usePropagateFieldSet.es';

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
	const [modalState, setModalState] = useState({isVisible: false});
	const {fieldSets} = useFormState();
	const {dataDefinition} = useFormState({schema: ['dataDefinition']});
	const dispatch = useForm();
	const deleteFieldSet = useDeleteFieldSet();
	const propagateFieldSet = usePropagateFieldSet();
	const filteredFieldsets = getFilteredFieldsets(fieldSets, searchTerm);

	const fieldSetsInUse = new Set();
	dataDefinition.dataDefinitionFields.forEach(
		({customProperties: {ddmStructureId}, fieldType}) => {
			if (fieldType === 'fieldset') {
				fieldSetsInUse.add(parseInt(ddmStructureId, 10));
			}
		}
	);

	const toggleFieldSet = (fieldSet) => {
		setModalState(({isVisible}) => ({
			fieldSet,
			isVisible: !isVisible,
		}));
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
							const actions = [
								{
									action: () => toggleFieldSet(fieldSet),
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
									name: Liferay.Language.get('delete'),
								},
							];
							const description = getPluralMessage(
								Liferay.Language.get('x-field'),
								Liferay.Language.get('x-fields'),
								fieldSet.dataDefinitionFields.length
							);
							const disabled = fieldSetsInUse.has(fieldSet.id);
							const label = getLocalizedValue(
								fieldSet.defaultLanguageId,
								fieldSet.name
							);
							const onDoubleClick = () => {
								dispatch({
									payload: {fieldSet},
									type: EVENT_TYPES.FIELD_SET.ADD,
								});
							};

							return (
								<FieldType
									actions={actions}
									description={description}
									disabled={disabled}
									dragType={DRAG_TYPES.DRAG_FIELDSET_ADD}
									fieldSet={fieldSet}
									icon="forms"
									key={fieldSet.dataDefinitionKey}
									label={label}
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
