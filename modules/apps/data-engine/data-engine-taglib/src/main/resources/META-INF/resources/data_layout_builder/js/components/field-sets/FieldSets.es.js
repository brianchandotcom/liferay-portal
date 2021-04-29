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
import React, {useContext, useState} from 'react';

import AppContext from '../../AppContext.es';
import DataLayoutBuilderContext from '../../data-layout-builder/DataLayoutBuilderContext.es';
import {DRAG_FIELDSET_ADD} from '../../drag-and-drop/dragTypes.es';
import {
	getDataDefinitionFieldSet,
	getFieldSetDDMForm,
} from '../../utils/dataConverter.es';
import {containsFieldSet} from '../../utils/dataDefinition.es';
import {getLocalizedValue} from '../../utils/lang.es';
import {getSearchRegex} from '../../utils/search.es';
import EmptyState from '../empty-state/EmptyState.es';
import FieldType from '../field-types/FieldType.es';
import {getPluralMessage} from './../../utils/lang.es';
import FieldSetModal from './FieldSetModal.es';
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

const CreateNewFieldsetButton = ({onClick}) => (
	<ClayButton
		block
		className="add-fieldset"
		displayType="secondary"
		onClick={onClick}
	>
		{Liferay.Language.get('create-new-fieldset')}
	</ClayButton>
);

export default function FieldSets({keywords}) {
	const [dataLayoutBuilder] = useContext(DataLayoutBuilderContext);
	const [{appProps, dataDefinition, fieldSets}] = useContext(AppContext);

	const [state, setState] = useState({
		childrenAppProps: {},
		editingDataDefinition: null,
		fieldSet: null,
		isVisible: false,
	});

	const filteredFieldsets = getFilteredFieldsets(fieldSets, keywords);

	let defaultLanguageId = dataDefinition.id
		? dataDefinition.defaultLanguageId
		: appProps.defaultLanguageId;

	const toggleFieldSet = (fieldSet, editingDataDefinition) => {
		const {contentType} = fieldSet ?? {};

		let childrenAppProps = {
			availableLanguageIds: [defaultLanguageId],
			contentType,
			context: {},
			dataDefinitionId: null,
			dataLayoutId: null,
			editingLanguageId: defaultLanguageId,
		};

		if (fieldSet) {
			const {context} = appProps;
			const {defaultDataLayout, id: dataDefinitionId} = fieldSet;
			const {
				contentTypeConfig: {allowInvalidAvailableLocalesForProperty},
				editingLanguageId,
				fieldTypes,
			} = dataLayoutBuilder.props;

			const ddmForm = getFieldSetDDMForm({
				allowInvalidAvailableLocalesForProperty,
				editingLanguageId,
				fieldSet,
				fieldTypes,
			});

			const [{rows}] = ddmForm.pages;

			delete ddmForm.pages;

			defaultLanguageId = fieldSet.defaultLanguageId;

			childrenAppProps = {
				availableLanguageIds: fieldSet.availableLanguageIds,
				context: {
					...context,
					pages: [
						{
							...ddmForm,
							description: '',
							rows,
							title: '',
						},
					],
				},
				dataDefinitionId,
				dataLayoutId: defaultDataLayout.id,
				editingLanguageId: defaultLanguageId,
			};
		}

		setState({
			childrenAppProps: {
				...childrenAppProps,
				contentType,
			},
			defaultLanguageId,
			editingDataDefinition,
			fieldSet,
			isVisible: !state.isVisible,
		});
	};

	const deleteFieldSet = useDeleteFieldSet({dataLayoutBuilder});
	const propagateFieldSet = usePropagateFieldSet();

	const onDoubleClick = ({fieldSet}) => {
		const {
			dispatch,
			state: {activePage, pages},
		} = dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider;
		const {availableLanguageIds, defaultLanguageId} = dataDefinition;
		const {
			contentTypeConfig: {allowInvalidAvailableLocalesForProperty},
			editingLanguageId,
			fieldTypes,
		} = dataLayoutBuilder.props;
		const indexes = {
			columnIndex: 0,
			pageIndex: activePage,
			rowIndex: pages[activePage].rows.length,
		};

		dispatch('fieldSetAdded', {
			availableLanguageIds,
			defaultLanguageId,
			indexes,
			...getDataDefinitionFieldSet({
				allowInvalidAvailableLocalesForProperty,
				availableLanguageIds,
				defaultLanguageId,
				editingLanguageId,
				fieldSet,
				fieldTypes,
			}),
		});
	};

	const onClickCreateNewFieldset = () => toggleFieldSet(null, dataDefinition);

	return (
		<>
			{filteredFieldsets.length ? (
				<>
					<CreateNewFieldsetButton
						onClick={onClickCreateNewFieldset}
					/>

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
											action: () => {
												const {
													contentTypeConfig: {
														allowReferencedDataDefinitionDeletion,
													},
												} = dataLayoutBuilder.props;

												propagateFieldSet({
													fieldSet,
													isDeleteAction: true,
													modal: {
														actionMessage: Liferay.Language.get(
															'delete'
														),
														allowReferencedDataDefinitionDeletion,
														fieldSetMessage: Liferay.Language.get(
															'to-delete,-you-first-need-to-remove-it-from-the-following-structures'
														),
														headerMessage: Liferay.Language.get(
															'delete'
														),
														status: 'danger',
														warningMessage: Liferay.Language.get(
															'the-structure-cannot-be-deleted-because-it-is-being-used-by-one-or-more-structure-links'
														),
													},
													onPropagate: deleteFieldSet,
												});
											},
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
									disabled={
										dataDefinition.name[
											defaultLanguageId
										] === fieldSetName ||
										containsFieldSet(
											dataDefinition,
											fieldSet.id
										)
									}
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
							button: () => (
								<CreateNewFieldsetButton
									onClick={onClickCreateNewFieldset}
								/>
							),
							description: Liferay.Language.get(
								'there-are-no-fieldsets-description'
							),
							title: Liferay.Language.get(
								'there-are-no-fieldsets'
							),
						}}
						keywords={keywords}
						small
					/>
				</div>
			)}

			<FieldSetModal
				defaultLanguageId={defaultLanguageId}
				onClose={() => toggleFieldSet()}
				{...state}
			/>
		</>
	);
}
