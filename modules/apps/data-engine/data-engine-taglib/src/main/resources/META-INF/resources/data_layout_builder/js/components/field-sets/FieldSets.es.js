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
import DataLayoutBuilder from '../../data-layout-builder/DataLayoutBuilder.es';
import DataLayoutBuilderContext from '../../data-layout-builder/DataLayoutBuilderContext.es';
import {DRAG_FIELDSET} from '../../drag-and-drop/dragTypes.es';
import {containsFieldSet} from '../../utils/dataDefinition.es';
import EmptyState from '../empty-state/EmptyState.es';
import FieldType from '../field-types/FieldType.es';
import FieldSetModal from './FieldSetModal.es';
import useDeleteFieldSet from './actions/useDeleteFieldSet.es';
import usePropagateFieldSet from './actions/usePropagateFieldSet.es';

export default function FieldSets({keywords}) {
	const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

	const [{appProps, dataDefinition, fieldSets}] = useContext(AppContext);
	const [state, setState] = useState({
		childrenAppProps: {},
		fieldSet: null,
		isVisible: false,
	});

	const toggleFieldSet = (fieldSet) => {
		let childrenAppProps = {
			context: {},
			dataDefinitionId: null,
			dataLayoutId: null,
		};

		if (fieldSet) {
			const {context, fieldTypes} = appProps;
			const {defaultDataLayout, id: dataDefinitionId} = fieldSet;
			const DataLayout = new DataLayoutBuilder({
				editingLanguageId: defaultLanguageId,
				fieldTypes,
			});
			const ddmForm = DataLayout.getDDMForm(fieldSet, defaultDataLayout);
			const [{rows}] = ddmForm.pages;

			delete ddmForm.pages;

			childrenAppProps = {
				DataLayout,
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
			};
		}

		setState({
			childrenAppProps,
			fieldSet,
			isVisible: !state.isVisible,
		});
	};

	const [dataLayoutBuilder] = useContext(DataLayoutBuilderContext);
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
			onClick={() => toggleFieldSet()}
		>
			{Liferay.Language.get('create-new-fieldset')}
		</ClayButton>
	);

	const filteredFieldSets = fieldSets
		.filter(({name}) =>
			new RegExp(keywords, 'ig').test(name[defaultLanguageId])
		)
		.sort(({name: a}, {name: b}) =>
			a[defaultLanguageId].localeCompare(b[defaultLanguageId])
		);

	return (
		<>
			{filteredFieldSets.length ? (
				<>
					<AddButton />
					<div className="mt-3">
						{filteredFieldSets.map((fieldSet) => {
							const fieldSetName =
								fieldSet.name[defaultLanguageId];
							const dropDownActions = [
								{
									action: () => toggleFieldSet(fieldSet),
									name: Liferay.Language.get('edit'),
								},
								{
									action: () =>
										propagateFieldSet({
											fieldSet,
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
				<div className="mt--2">
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
