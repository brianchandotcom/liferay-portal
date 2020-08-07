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
import {dropFieldSet} from '../../actions.es';
import DataLayoutBuilderContext from '../../data-layout-builder/DataLayoutBuilderContext.es';
import {DRAG_FIELDSET} from '../../drag-and-drop/dragTypes.es';
import {containsFieldSet} from '../../utils/dataDefinition.es';
import {getLocalizedValue} from '../../utils/lang.es';
import EmptyState from '../empty-state/EmptyState.es';
import FieldType from '../field-types/FieldType.es';
import FieldSetModal from './FieldSetModal.es';
import useDeleteFieldSet from './actions/useDeleteFieldSet.es';
import usePropagateFieldSet from './actions/usePropagateFieldSet.es';

export default function FieldSets({keywords}) {
	const [dataLayoutBuilder] = useContext(DataLayoutBuilderContext);
	const [{appProps, dataDefinition, fieldSets}] = useContext(AppContext);
	const [state, setState] = useState({
		childrenAppProps: {},
		editingDataDefinition: null,
		fieldSet: null,
		isVisible: false,
	});

	let defaultLanguageId = themeDisplay.getDefaultLanguageId();

	const toggleFieldSet = (fieldSet, editingDataDefinition) => {
		let childrenAppProps = {
			availableLanguageIds: [defaultLanguageId],
			context: {},
			dataDefinitionId: null,
			dataLayoutId: null,
			editingLanguageId: defaultLanguageId,
		};

		if (fieldSet) {
			const {context} = appProps;
			const {defaultDataLayout, id: dataDefinitionId} = fieldSet;
			const ddmForm = dataLayoutBuilder.getDDMForm(
				fieldSet,
				defaultDataLayout
			);
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
			childrenAppProps,
			defaultLanguageId,
			editingDataDefinition,
			fieldSet,
			isVisible: !state.isVisible,
		});
	};

	const deleteFieldSet = useDeleteFieldSet({dataLayoutBuilder});
	const propagateFieldSet = usePropagateFieldSet();

	const onDoubleClick = ({fieldSet: {name: fieldName}, fieldSet}) => {
		const {activePage, pages} = dataLayoutBuilder.getStore();

		dataLayoutBuilder.dispatch(
			'fieldSetAdded',
			dropFieldSet({
				dataLayoutBuilder,
				fieldName,
				fieldSet,
				indexes: {
					columnIndex: 0,
					pageIndex: activePage,
					rowIndex: pages[activePage].rows.length,
				},
			})
		);
	};

	const AddButton = () => (
		<ClayButton
			block
			className="add-fieldset"
			displayType="secondary"
			onClick={() => toggleFieldSet(null, dataDefinition)}
		>
			{Liferay.Language.get('create-new-fieldset')}
		</ClayButton>
	);

	const filteredFieldSets = fieldSets
		.filter(({defaultLanguageId}) =>
			new RegExp(keywords, 'ig').test(
				getLocalizedValue(defaultLanguageId, name)
			)
		)
		.sort((a, b) => {
			const localizedValueA = getLocalizedValue(
				a.defaultLanguageId,
				a.name
			);
			const localizedValueB = getLocalizedValue(
				b.defaultLanguageId,
				b.name
			);

			return localizedValueA.localeCompare(localizedValueB);
		});

	return (
		<>
			{filteredFieldSets.length ? (
				<>
					<AddButton />

					<div className="mt-3">
						{filteredFieldSets.map((fieldSet) => {
							const fieldSetName = getLocalizedValue(
								fieldSet.defaultLanguageId,
								fieldSet.name
							);

							const dropDownActions = [
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

							const disabled =
								dataDefinition.name[defaultLanguageId] ===
								fieldSetName;

							return (
								<FieldType
									actions={dropDownActions}
									description={`${
										fieldSet.dataDefinitionFields.length
									} ${Liferay.Language.get('fields')}`}
									disabled={
										disabled ||
										containsFieldSet(
											dataDefinition,
											fieldSet.id
										)
									}
									dragType={DRAG_FIELDSET}
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
							button: AddButton,
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
