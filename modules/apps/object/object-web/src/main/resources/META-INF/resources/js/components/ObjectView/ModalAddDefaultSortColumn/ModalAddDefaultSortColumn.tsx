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
import ClayForm from '@clayui/form';
import ClayModal from '@clayui/modal';
import React, {
	FormEvent,
	useContext,
	useEffect,
	useMemo,
	useState,
} from 'react';

import AutoComplete from '../../Form/AutoComplete';
import CustomSelect from '../../Form/CustomSelect/CustomSelect';
import ViewContext, {TYPES} from '../context';
import {TObjectViewColumn, TObjectViewSortColumn} from '../types';

interface IProps extends React.HTMLAttributes<HTMLElement> {
	editingObjectFieldName: string;
	header: string;
	isEditingSort: boolean;
	observer: any;
	onClose: () => void;
}

type TSortOptions = {
	label: string;
	value: string;
};

const SORT_OPTIONS: TSortOptions[] = [
	{
		label: Liferay.Language.get('ascending'),
		value: 'asc',
	},
	{
		label: Liferay.Language.get('descending'),
		value: 'desc',
	},
];

export function ModalAddDefaultSortColumn({
	editingObjectFieldName,
	header,
	isEditingSort,
	observer,
	onClose,
}: IProps) {
	const [
		{
			objectFields,
			objectView: {objectViewColumns, objectViewSortColumns},
		},
		dispatch,
	] = useContext(ViewContext);

	const [availableViewColumns, setAvailableViewColumns] = useState<
		TObjectViewColumn[]
	>(objectViewColumns);

	useEffect(() => {
		const newAvailableViewColumns = objectViewColumns.filter(
			(viewColumn) => !viewColumn.isDefaultSort
		);

		setAvailableViewColumns(newAvailableViewColumns);
	}, [objectViewColumns]);

	const [selectedObjectSortColumn, setSelectedObjectSortColumn] = useState<
		TObjectViewSortColumn
	>();
	const [selectedObjetSort, setSelectedObjetSort] = useState(SORT_OPTIONS[0]);
	const [query, setQuery] = useState<string>('');

	const filtredObjectSortColumn = useMemo(() => {
		return availableViewColumns.filter(({fieldLabel}) => {
			return fieldLabel.toLowerCase().includes(query.toLowerCase());
		});
	}, [availableViewColumns, query]);

	const onSubmit = (event: FormEvent) => {
		event.preventDefault();

		const objectFieldName = selectedObjectSortColumn?.objectFieldName;

		if (isEditingSort) {
			dispatch({
				payload: {
					editingObjectFieldName,
					selectedObjectSort: selectedObjetSort.value,
				},
				type: TYPES.EDIT_OBJECT_VIEW_SORT_COLUMN_SORT_ORDER,
			});
		}
		else {
			dispatch({
				payload: {
					objectFieldName,
					objectFields,
					objectViewSortColumns,
					selectedObjetSort,
				},
				type: TYPES.ADD_OBJECT_VIEW_SORT_COLUMN,
			});
		}

		onClose();
	};

	return (
		<ClayModal observer={observer}>
			<ClayForm onSubmit={onSubmit}>
				<ClayModal.Header>{header}</ClayModal.Header>

				<ClayModal.Body>
					{!isEditingSort && (
						<AutoComplete
							emptyStateMessage={Liferay.Language.get(
								'there-are-no-columns-added-in-this-view-yet'
							)}
							items={filtredObjectSortColumn}
							label={Liferay.Language.get('columns')}
							onChangeQuery={setQuery}
							onSelectItem={(item) => {
								setSelectedObjectSortColumn(item);
							}}
							query={query}
							required
							value={selectedObjectSortColumn?.fieldLabel}
						>
							{({fieldLabel}) => (
								<div className="d-flex justify-content-between">
									<div>{fieldLabel}</div>
								</div>
							)}
						</AutoComplete>
					)}

					<CustomSelect
						label={Liferay.Language.get('sorting')}
						onChange={(item: any) => {
							setSelectedObjetSort(item);
						}}
						options={SORT_OPTIONS}
						value={selectedObjetSort.label}
					>
						{({label}: any) => <div>{label}</div>}
					</CustomSelect>
				</ClayModal.Body>

				<ClayModal.Footer
					last={
						<ClayButton.Group key={1} spaced>
							<ClayButton
								displayType="secondary"
								onClick={() => onClose()}
							>
								{Liferay.Language.get('cancel')}
							</ClayButton>

							<ClayButton
								disabled={
									isEditingSort
										? false
										: !selectedObjectSortColumn
								}
								displayType="primary"
								type="submit"
							>
								{Liferay.Language.get('save')}
							</ClayButton>
						</ClayButton.Group>
					}
				/>
			</ClayForm>
		</ClayModal>
	);
}
