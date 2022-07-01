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
import ClayModal from '@clayui/modal';
import {
	AutoComplete,
	FormCustomSelect,
	Input,
} from '@liferay/object-js-components-web';
import React, {
	FormEvent,
	useCallback,
	useEffect,
	useMemo,
	useState,
} from 'react';

import {getPickListItems} from '../utils/api';
import {HEADERS} from '../utils/constants';
import {defaultLanguageId, locale} from '../utils/locale';

import './ModalAddFilter.scss';

HEADERS.append('Accept-Language', locale!.symbol);

const PICKLIST_OPERATORS: LabelValueObject[] = [
	{
		label: Liferay.Language.get('choose-an-option'),
		value: '',
	},
	{
		label: Liferay.Language.get('includes'),
		value: 'includes',
	},
	{
		label: Liferay.Language.get('excludes'),
		value: 'excludes',
	},
];

const DATE_OPERATORS: LabelValueObject[] = [
	{
		label: Liferay.Language.get('range'),
		value: 'range',
	},
];

const NUMERIC_OPERATORS: LabelValueObject[] = [
	{
		label: Liferay.Language.get('equals-to'),
		value: 'eq',
	},
	{
		label: Liferay.Language.get('not-equals-to'),
		value: 'ne',
	},
];

export function ModalAddFilter({
	currentFilters,
	editingFilter,
	editingObjectFieldName,
	header,
	objectFields,
	observer,
	onClose,
	onSave,
	workflowStatusJSONArray,
}: IProps) {
	const [items, setItems] = useState<IItem[]>([]);

	const [selectedFilterBy, setSelectedFilterBy] = useState<ObjectField>();

	const [selectedFilterType, setSelectedFilterType] = useState<
		LabelValueObject
	>();
	const [value, setValue] = useState<string>();

	const [query, setQuery] = useState<string>('');

	const filteredAvailableFields = useMemo(() => {
		return objectFields.filter(({label}: ObjectField) => {
			return label[defaultLanguageId]
				?.toLowerCase()
				.includes(query.toLowerCase());
		});
	}, [objectFields, query]);

	const getCheckedWorkflowStatusItems = (
		itemValues: TWorkflowStatus[]
	): IItem[] => {
		let newItemsValues: IItem[] = [];

		const currentFilterColumn = currentFilters.find((filterColumn) => {
			if (filterColumn.objectFieldName === editingObjectFieldName) {
				return filterColumn;
			}
		});

		const definition = currentFilterColumn?.definition;
		const filterType = currentFilterColumn?.filterType;

		const valuesArray =
			definition && filterType ? definition[filterType] : null;

		const editingFilterType = PICKLIST_OPERATORS.find(
			(filterType) => filterType.value === currentFilterColumn?.filterType
		);

		if (editingFilterType) {
			setSelectedFilterType({
				label: editingFilterType.label,
				value: editingFilterType.value,
			});
		}

		newItemsValues = itemValues.map((itemValue) => {
			const item = {
				checked: false,
				label: itemValue.label,
				value: itemValue.value,
			};

			if (valuesArray?.includes(itemValue.value)) {
				item.checked = true;
			}

			return item;
		});

		return newItemsValues;
	};

	const getCheckedPickListItems = (itemValues: PickListItem[]): IItem[] => {
		let newItemsValues: IItem[] = [];

		const currentFilterColumn = currentFilters.find((filterColumn) => {
			if (filterColumn.objectFieldName === editingObjectFieldName) {
				return filterColumn;
			}
		});

		const definition = currentFilterColumn?.definition;
		const filterType = currentFilterColumn?.filterType;

		const valuesArray =
			definition && filterType ? definition[filterType] : null;

		const editingFilterType = PICKLIST_OPERATORS.find(
			(filterType) => filterType.value === currentFilterColumn?.filterType
		);

		if (editingFilterType) {
			setSelectedFilterType({
				label: editingFilterType.label,
				value: editingFilterType.value,
			});
		}

		newItemsValues = itemValues.map((itemValue) => {
			const item = {
				checked: false,
				label: itemValue.name,
				value: itemValue.key,
			};

			if (valuesArray?.includes(itemValue.key)) {
				item.checked = true;
			}

			return item;
		});

		return newItemsValues;
	};

	const setFieldValues = useCallback(
		(objectField: ObjectField) => {
			if (objectField?.businessType === 'Picklist') {
				const makeFetch = async () => {
					const items = await getPickListItems(
						objectField.listTypeDefinitionId
					);

					if (editingFilter) {
						setItems(getCheckedPickListItems(items));
					}
					else {
						setItems(
							items.map((item) => {
								return {
									label: item.name,
									value: item.key,
								};
							})
						);
					}
				};

				makeFetch();
			}
			else {
				let newItems: IItem[] = [];

				if (editingFilter) {
					newItems = getCheckedWorkflowStatusItems(
						workflowStatusJSONArray
					);
				}
				else {
					newItems = workflowStatusJSONArray.map((workflowStatus) => {
						return {
							label: workflowStatus.label,
							value: workflowStatus.value,
						};
					});
				}

				setItems(newItems);
			}
		},
		// eslint-disable-next-line react-hooks/exhaustive-deps
		[]
	);

	useEffect(() => {
		if (!selectedFilterBy && !editingObjectFieldName) {
			setItems([]);
		}
		else {
			if (selectedFilterBy) {
				setFieldValues(
					(selectedFilterBy as unknown) as ObjectFieldView
				);
			}
			else {
				const objectField = objectFields.find(
					({name}) => name === editingObjectFieldName
				);

				objectField && setFieldValues(objectField);
			}
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [
		editingFilter,
		setFieldValues,
		selectedFilterBy,
		workflowStatusJSONArray,
	]);

	const handleSaveFilter = (event: FormEvent) => {
		event.preventDefault();

		const checkedItems = items.filter((item) => item.checked);

		if (editingFilter) {
			onSave(
				selectedFilterBy?.name,
				selectedFilterBy?.label,
				selectedFilterBy?.businessType,
				selectedFilterType?.value,
				editingObjectFieldName,
				selectedFilterBy?.businessType === 'Workflow Status' ||
					selectedFilterBy?.businessType === 'Picklist'
					? checkedItems
					: undefined,
				value ?? undefined
			);
		}
		else {
			onSave(
				selectedFilterBy?.name,
				selectedFilterBy?.label,
				selectedFilterBy?.businessType,
				selectedFilterType?.value,
				selectedFilterBy?.name,
				selectedFilterBy?.businessType === 'Workflow Status' ||
					selectedFilterBy?.businessType === 'Picklist'
					? checkedItems
					: selectedFilterBy?.businessType === 'Date'
					? items
					: undefined,
				value ?? undefined
			);
		}

		onClose();
	};

	return (
		<ClayModal observer={observer}>
			<ClayModal.Header>{header}</ClayModal.Header>

			<ClayModal.Body>
				{!editingFilter && (
					<AutoComplete
						emptyStateMessage={Liferay.Language.get(
							'there-are-no-columns-available'
						)}
						items={filteredAvailableFields}
						label={Liferay.Language.get('filter-by')}
						onChangeQuery={setQuery}
						onSelectItem={setSelectedFilterBy}
						query={query}
						required
						value={selectedFilterBy?.label[defaultLanguageId]}
					>
						{({label}) => (
							<div className="d-flex justify-content-between">
								<div>{label[defaultLanguageId]}</div>
							</div>
						)}
					</AutoComplete>
				)}

				<FormCustomSelect
					disabled={!editingFilter && !selectedFilterBy}
					label={Liferay.Language.get('filter-type')}
					onChange={(target: LabelValueObject) =>
						setSelectedFilterType(target)
					}
					options={
						selectedFilterBy?.businessType === 'Integer' ||
						selectedFilterBy?.businessType === 'LongInteger'
							? NUMERIC_OPERATORS
							: selectedFilterBy?.businessType === 'Date'
							? DATE_OPERATORS
							: PICKLIST_OPERATORS
					}
					required
					value={selectedFilterType?.label}
				/>

				{(selectedFilterBy?.businessType === 'Integer' ||
					selectedFilterBy?.businessType === 'LongInteger') && (
					<Input
						label={Liferay.Language.get('value')}
						onChange={({target: {value}}) => {
							const newValue = value.replace(/[\D]/g, '');
							setValue(newValue);
						}}
						required
						type="number"
						value={value}
					/>
				)}

				{(selectedFilterBy?.businessType === 'Workflow Status' ||
					selectedFilterBy?.businessType === 'Picklist') && (
					<FormCustomSelect
						disabled={!selectedFilterType?.value}
						label={Liferay.Language.get('value')}
						multipleChoice
						options={items}
						setOptions={setItems}
					/>
				)}

				{selectedFilterBy?.businessType === 'Date' && (
					<>
						<Input
							label={Liferay.Language.get('start')}
							onChange={({target: {value}}) => {
								setItems([
									...items.filter(
										(item) => item.value !== 'ge'
									),
									{
										label: value,
										value: 'ge',
									},
								]);
							}}
							required
							type="date"
						/>

						<Input
							label={Liferay.Language.get('end')}
							onChange={({target: {value}}) => {
								setItems([
									...items.filter(
										(item) => item.value !== 'le'
									),
									{
										label: value,
										value: 'le',
									},
								]);
							}}
							required
							type="date"
						/>
					</>
				)}
			</ClayModal.Body>

			<ClayModal.Footer
				last={
					<ClayButton.Group spaced>
						<ClayButton
							displayType="secondary"
							onClick={() => onClose()}
						>
							{Liferay.Language.get('cancel')}
						</ClayButton>

						<ClayButton
							disabled={!selectedFilterBy && !editingFilter}
							displayType="primary"
							onClick={handleSaveFilter}
						>
							{Liferay.Language.get('save')}
						</ClayButton>
					</ClayButton.Group>
				}
			/>
		</ClayModal>
	);
}

interface IProps {
	currentFilters: TCurrentFilter[];
	editingFilter: boolean;
	editingObjectFieldName: string;
	header: string;
	objectFields: ObjectField[];
	observer: any;
	onClose: () => void;
	onSave: (
		filterBy?: string,
		fieldLabel?: LocalizedValue<string>,
		objectFieldBusinessType?: string,
		filterType?: string,
		objectFieldName?: string,
		valueList?: IItem[],
		value?: string
	) => void;
	workflowStatusJSONArray: TWorkflowStatus[];
}

interface IItem extends LabelValueObject {
	checked?: boolean;
}

type TCurrentFilter = {
	definition: {[key: string]: string[]} | null;
	fieldLabel: string;
	filterBy: string;
	filterType: string | null;
	label: TName;
	objectFieldBusinessType?: string;
	objectFieldName: string;
	value?: string;
	valueList?: LabelValueObject[];
};

type TWorkflowStatus = {
	label: string;
	value: string;
};

type TName = {
	[key: string]: string;
};
