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
	API,
	AutoComplete,
	DatePicker,
	FormCustomSelect,
	Input,
	invalidateRequired,
} from '@liferay/object-js-components-web';
import React, {
	FormEvent,
	useCallback,
	useEffect,
	useMemo,
	useState,
} from 'react';

import {
	DATE_OPERATORS,
	NUMERIC_OPERATORS,
	PICKLIST_OPERATORS,
} from '../utils/filterOperators';

import './ModalAddFilter.scss';

const REQUIRED_MSG = Liferay.Language.get('required');
const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

export function ModalAddFilter({
	currentFilters,
	disableDateValues,
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

	const [
		selectedFilterType,
		setSelectedFilterType,
	] = useState<LabelValueObject | null>();
	const [value, setValue] = useState<string>();

	const [errors, setErrors] = useState<TErrors>({});

	const [query, setQuery] = useState<string>('');

	const [filterStartDate, setFilterStartDate] = useState('');
	const [filterEndtDate, setFilterEndDate] = useState('');

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
					const items = await API.getPickListItems(
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

	useEffect(() => {
		if (editingFilter) {
			const editingObjectFieldFilter = objectFields.find(
				(objectField) => objectField.name === editingObjectFieldName
			);

			setSelectedFilterBy(editingObjectFieldFilter);
		}
	}, [editingFilter, editingObjectFieldName, objectFields]);

	const validate = (checkedItems: IItem[]) => {
		setErrors({});
		const currentErrors: TErrors = {};

		if (!selectedFilterBy) {
			currentErrors.selectedFilterBy = REQUIRED_MSG;
		}
		if (!selectedFilterType && !disableDateValues) {
			currentErrors.selectedFilterType = REQUIRED_MSG;
		}
		if (
			(selectedFilterBy?.name === 'status' ||
				selectedFilterBy?.businessType === 'Picklist') &&
			!checkedItems.length
		) {
			currentErrors.items = REQUIRED_MSG;
		}
		if (
			selectedFilterBy?.businessType === 'Date' &&
			selectedFilterType?.value === 'range' &&
			!disableDateValues
		) {
			const startDate = items.find((date) => date.value === 'ge');
			const endDate = items.find((date) => date.value === 'le');

			if (!startDate) {
				currentErrors.startDate = REQUIRED_MSG;
			}

			if (!endDate) {
				currentErrors.endDate = REQUIRED_MSG;
			}
		}
		if (
			(selectedFilterBy?.businessType === 'Integer' ||
				selectedFilterBy?.businessType === 'LongInteger') &&
			invalidateRequired(value)
		) {
			currentErrors.value = REQUIRED_MSG;
		}

		setErrors(currentErrors);

		return currentErrors;
	};

	const handleSaveFilter = (event: FormEvent) => {
		event.preventDefault();

		const checkedItems = items.filter((item) => item.checked);

		const currentErrors = validate(checkedItems);

		if (Object.keys(currentErrors).length) {
			return;
		}

		if (editingFilter) {
			onSave(
				selectedFilterBy?.name,
				selectedFilterBy?.label,
				selectedFilterBy?.businessType,
				selectedFilterType?.value,
				editingObjectFieldName,
				selectedFilterBy?.name === 'status' ||
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
				selectedFilterBy?.name === 'status' ||
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
						error={errors.selectedFilterBy}
						items={filteredAvailableFields}
						label={Liferay.Language.get('filter-by')}
						onChangeQuery={setQuery}
						onSelectItem={(item) => {
							setSelectedFilterBy(item);
							setSelectedFilterType(null);
							setValue('');
						}}
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

				{selectedFilterBy?.businessType !== 'Date' && (
					<FormCustomSelect
						error={errors.selectedFilterType}
						label={Liferay.Language.get('filter-type')}
						onChange={(target: LabelValueObject) =>
							setSelectedFilterType(target)
						}
						options={
							selectedFilterBy?.businessType === 'Integer' ||
							selectedFilterBy?.businessType === 'LongInteger'
								? NUMERIC_OPERATORS
								: PICKLIST_OPERATORS
						}
						required
						value={selectedFilterType?.label ?? ''}
					/>
				)}

				{selectedFilterBy?.businessType === 'Date' &&
					!disableDateValues && (
						<FormCustomSelect
							error={errors.selectedFilterType}
							label={Liferay.Language.get('filter-type')}
							onChange={(target: LabelValueObject) =>
								setSelectedFilterType(target)
							}
							options={DATE_OPERATORS}
							required
							value={selectedFilterType?.label ?? ''}
						/>
					)}

				{(selectedFilterBy?.businessType === 'Integer' ||
					selectedFilterBy?.businessType === 'LongInteger') && (
					<Input
						error={errors.value}
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

				{(selectedFilterBy?.name === 'status' ||
					selectedFilterBy?.businessType === 'Picklist') && (
					<FormCustomSelect
						error={errors.items}
						label={Liferay.Language.get('value')}
						multipleChoice
						options={items}
						required
						setOptions={setItems}
					/>
				)}

				{selectedFilterBy?.businessType === 'Date' &&
					!disableDateValues && (
						<div className="row">
							<div className="col-lg-6">
								<DatePicker
									error={errors.startDate}
									label={Liferay.Language.get('start')}
									onChange={(value) => {
										setItems([
											...items.filter(
												(item) => item.value !== 'ge'
											),
											{
												label: value,
												value: 'ge',
											},
										]);

										setFilterStartDate(value);
									}}
									required
									value={filterStartDate}
								/>
							</div>

							<div className="col-lg-6">
								<DatePicker
									error={errors.endDate}
									label={Liferay.Language.get('end')}
									onChange={(value) => {
										setItems([
											...items.filter(
												(item) => item.value !== 'le'
											),
											{
												label: value,
												value: 'le',
											},
										]);

										setFilterEndDate(value);
									}}
									required
									value={filterEndtDate}
								/>
							</div>
						</div>
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
	disableDateValues?: boolean;
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

type TErrors = {
	endDate?: string;
	items?: string;
	selectedFilterBy?: string;
	selectedFilterType?: string;
	startDate?: string;
	value?: string;
};

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
