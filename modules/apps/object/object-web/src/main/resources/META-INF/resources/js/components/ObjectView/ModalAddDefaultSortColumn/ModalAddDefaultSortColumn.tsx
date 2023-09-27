/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayButton from '@clayui/button';
import ClayForm from '@clayui/form';
import ClayModal from '@clayui/modal';
import {Observer} from '@clayui/modal/lib/types';
import {
	AutoComplete,
	SingleSelect,
	filterArrayByQuery,
} from '@liferay/object-js-components-web';
import React, {FormEvent, useEffect, useMemo, useState} from 'react';

import {TYPES, useViewContext} from '../objectViewContext';
import {TObjectViewColumn, TObjectViewSortColumn} from '../types';

interface IProps extends React.HTMLAttributes<HTMLElement> {
	editingObjectFieldName: string;
	header: string;
	isEditingSort: boolean;
	observer: Observer;
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
			creationLanguageId,
			objectFields,
			objectView: {objectViewColumns, objectViewSortColumns},
		},
		dispatch,
	] = useViewContext();

	const [availableViewColumns, setAvailableViewColumns] = useState<
		TObjectViewColumn[]
	>(objectViewColumns);

	useEffect(() => {
		const newAvailableViewColumns = objectViewColumns.filter(
			({defaultSort, objectFieldBusinessType}) =>
				!defaultSort &&
				objectFieldBusinessType !== 'Aggregation' &&
				objectFieldBusinessType !== 'Attachment' &&
				objectFieldBusinessType !== 'Encrypted' &&
				objectFieldBusinessType !== 'Formula' &&
				objectFieldBusinessType !== 'Relationship' &&
				objectFieldBusinessType !== 'RichText'
		);

		setAvailableViewColumns(newAvailableViewColumns);
	}, [objectViewColumns]);

	const [selectedObjectSortColumn, setSelectedObjectSortColumn] = useState<
		TObjectViewSortColumn
	>();
	const [selectedObjetSort, setSelectedObjetSort] = useState(SORT_OPTIONS[0]);
	const [query, setQuery] = useState<string>('');

	const filteredObjectSortColumn = useMemo(() => {
		return filterArrayByQuery({
			array: availableViewColumns,
			query,
			str: 'fieldLabel',
		});
	}, [availableViewColumns, query]);

	const onSubmit = (event: FormEvent) => {
		event.preventDefault();

		let objectFieldName = selectedObjectSortColumn?.objectFieldName;

		if (!objectFieldName && !!filteredObjectSortColumn.length) {
			objectFieldName = filteredObjectSortColumn[0].objectFieldName;
		}

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
					creationLanguageId,
					objectFieldName: objectFieldName!,
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
						<AutoComplete<TObjectViewColumn>
							emptyStateMessage={Liferay.Language.get(
								'there-are-no-columns-added-in-this-view-yet'
							)}
							items={filteredObjectSortColumn}
							label={Liferay.Language.get('columns')}
							onActive={(item) =>
								item.objectFieldName ===
								selectedObjectSortColumn?.objectFieldName
							}
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

					<SingleSelect
						label={Liferay.Language.get('sorting')}
						onChange={(item: TSortOptions) => {
							setSelectedObjetSort(item);
						}}
						options={SORT_OPTIONS}
						value={selectedObjetSort.label}
					/>
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
