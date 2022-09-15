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
import {Observer} from '@clayui/modal/lib/types';
import {
	API,
	AutoComplete,
	DatePicker,
	Input,
	MultipleSelect,
	SingleSelect,
	stringIncludesQuery,
} from '@liferay/object-js-components-web';
import React, {
	FormEvent,
	useCallback,
	useEffect,
	useMemo,
	useState,
} from 'react';

import './ModalAddFilter.scss';

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

export function ModalAddFilter({
	currentFilters,
	disableDateValues,
	editingFilter,
	editingObjectFieldName,
	filterOperators,
	filterTypeRequired,
	header,
	objectFields,
	observer,
	onClose,
	onSave,
	validate,
	workflowStatusJSONArray,
}: IProps) {
	const [items, setItems] = useState<IItem[]>([]);

	const [selectedFilterBy, setSelectedFilterBy] = useState<ObjectField>();

	const [
		selectedFilterType,
		setSelectedFilterType,
	] = useState<LabelValueObject | null>();
	const [value, setValue] = useState<string>();

	const [errors, setErrors] = useState<FilterErrors>({});

	const [query, setQuery] = useState<string>('');

	const [filterStartDate, setFilterStartDate] = useState('');
	const [filterEndDate, setFilterEndDate] = useState('');

	const filteredAvailableFields = useMemo(() => {
		return objectFields.filter(({label}: ObjectField) =>
			stringIncludesQuery(label[defaultLanguageId] as string, query)
		);
	}, [objectFields, query]);

	const setEditingFilterType = () => {
		const currentFilterColumn = currentFilters.find((filterColumn) => {
			if (filterColumn.objectFieldName === editingObjectFieldName) {
				return filterColumn;
			}
		});

		const definition = currentFilterColumn?.definition;
		const filterType = currentFilterColumn?.filterType;

		const valuesArray =
			definition && filterType ? definition[filterType] : null;

		const editingFilterType = filterOperators.picklistOperators.find(
			(filterType) => filterType.value === currentFilterColumn?.filterType
		);

		if (editingFilterType) {
			setSelectedFilterType({
				label: editingFilterType.label,
				value: editingFilterType.value,
			});
		}

		return valuesArray;
	};

	const getCheckedWorkflowStatusItems = (
		itemValues: TWorkflowStatus[]
	): IItem[] => {
		let newItemsValues: IItem[] = [];

		const valuesArray = setEditingFilterType() as number[];

		newItemsValues = itemValues.map((itemValue) => {
			const item = {
				checked: false,
				label: itemValue.label,
				value: itemValue.value,
			};

			if (valuesArray?.includes(Number(itemValue.value))) {
				item.checked = true;
			}

			return item;
		});

		return newItemsValues;
	};

	const getCheckedPickListItems = (itemValues: PickListItem[]): IItem[] => {
		let newItemsValues: IItem[] = [];

		const valuesArray = setEditingFilterType() as string[];

		newItemsValues = (itemValues as PickListItem[]).map((itemValue) => {
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

	const getCheckedRelationshipItems = (
		relatedEntries: ObjectEntry[],
		titleFieldName: string,
		systemField: boolean
	): IItem[] => {
		let newItemsValues: IItem[] = [];

		const valuesArray = setEditingFilterType() as string[];

		newItemsValues = relatedEntries.map((entry) => {
			let item = {
				checked: false,
				value: entry.externalReferenceCode,
			} as IItem;

			if (systemField && titleFieldName === 'creator') {
				const {name} = entry.creator;

				item = {
					...item,
					label: name,
				};
			}

			if (systemField && titleFieldName === 'status') {
				const {label_i18n} = entry.status;

				item = {
					...item,
					label: label_i18n,
				};
			}
			else {
				item = {
					...item,
					label: entry[titleFieldName] as string,
				};
			}

			if (valuesArray.includes(entry.externalReferenceCode)) {
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
			else if (objectField.name === 'status') {
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
			else if (objectField.businessType === 'Relationship') {
				const makeFetch = async () => {
					const {objectFieldSettings} = objectField;

					const [{value}] = objectFieldSettings as NameValueObject[];

					const [
						{objectFields, restContextPath, titleObjectFieldId},
					] = await API.getObjectDefinitions(
						`filter=name eq '${value}'`
					);

					const titleField = objectFields.find((objectField) =>
						titleObjectFieldId === 0
							? objectField.system && objectField.name === 'id'
							: objectField.id === titleObjectFieldId
					) as ObjectField;

					const relatedEntries = await API.getList<ObjectEntry>(
						`${restContextPath}`
					);

					if (editingFilter) {
						setItems(
							getCheckedRelationshipItems(
								relatedEntries,
								titleField.name,
								titleField.system as boolean
							)
						);
					}
					else {
						const newItems = relatedEntries.map((entry) => {
							const newItemsObject = {
								value: entry.externalReferenceCode,
							} as LabelValueObject;

							if (
								titleField.system &&
								titleField.name === 'creator'
							) {
								const {name} = entry.creator;

								return {
									...newItemsObject,
									label: name,
								};
							}

							if (
								titleField.system &&
								titleField.name === 'status'
							) {
								const {label_i18n} = entry.status;

								return {
									...newItemsObject,
									label: label_i18n,
								};
							}

							return {
								...newItemsObject,
								label: entry[titleField?.name] as string,
							};
						});

						setItems(newItems);
					}
				};

				makeFetch();
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

	const handleSaveFilter = (event: FormEvent) => {
		event.preventDefault();

		const checkedItems = items.filter((item) => item.checked);

		const currentErrors = validate({
			checkedItems,
			disableDateValues,
			items,
			selectedFilterBy,
			selectedFilterType,
			setErrors,
			value,
		});

		if (Object.keys(currentErrors).length) {
			return;
		}

		if (editingFilter) {
			onSave(
				editingObjectFieldName,
				selectedFilterBy?.name,
				selectedFilterBy?.label,
				selectedFilterBy?.businessType,
				selectedFilterType?.value,
				selectedFilterBy?.name === 'status' ||
					selectedFilterBy?.businessType === 'Picklist' ||
					selectedFilterBy?.businessType === 'Relationship'
					? checkedItems
					: undefined,
				value ?? undefined
			);
		}
		else {
			onSave(
				selectedFilterBy?.name!,
				selectedFilterBy?.name,
				selectedFilterBy?.label,
				selectedFilterBy?.businessType,
				selectedFilterType?.value,
				selectedFilterBy?.name === 'status' ||
					selectedFilterBy?.businessType === 'Picklist' ||
					selectedFilterBy?.businessType === 'Relationship'
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

				{selectedFilterBy &&
					selectedFilterBy?.businessType !== 'Date' && (
						<SingleSelect
							error={errors.selectedFilterType}
							label={Liferay.Language.get('filter-type')}
							onChange={(target: LabelValueObject) =>
								setSelectedFilterType(target)
							}
							options={
								selectedFilterBy?.businessType === 'Integer' ||
								selectedFilterBy?.businessType === 'LongInteger'
									? filterOperators.numericOperators
									: filterOperators.picklistOperators
							}
							required={filterTypeRequired}
							value={selectedFilterType?.label ?? ''}
						/>
					)}

				{selectedFilterBy &&
					selectedFilterBy?.businessType === 'Date' &&
					!disableDateValues && (
						<SingleSelect
							error={errors.selectedFilterType}
							label={Liferay.Language.get('filter-type')}
							onChange={(target: LabelValueObject) =>
								setSelectedFilterType(target)
							}
							options={filterOperators.dateOperators}
							required={filterTypeRequired}
							value={selectedFilterType?.label ?? ''}
						/>
					)}

				{selectedFilterType &&
					(selectedFilterBy?.businessType === 'Integer' ||
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

				{selectedFilterType &&
					(selectedFilterBy?.name === 'status' ||
						selectedFilterBy?.businessType === 'Picklist' ||
						selectedFilterBy?.businessType === 'Relationship') && (
						<MultipleSelect
							error={errors.items}
							label={Liferay.Language.get('value')}
							options={items}
							required
							setOptions={setItems}
						/>
					)}

				{selectedFilterType &&
					selectedFilterBy?.businessType === 'Date' &&
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
									value={filterEndDate}
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
	filterOperators: TFilterOperators;
	filterTypeRequired?: boolean;
	header: string;
	objectFields: ObjectField[];
	observer: Observer;
	onClose: () => void;
	onSave: (
		objectFieldName: string,
		filterBy?: string,
		fieldLabel?: LocalizedValue<string>,
		objectFieldBusinessType?: string,
		filterType?: string,
		valueList?: IItem[],
		value?: string
	) => void;
	validate: ({
		checkedItems,
		disableDateValues,
		items,
		selectedFilterBy,
		selectedFilterType,
		setErrors,
		value,
	}: FilterValidation) => FilterErrors;
	workflowStatusJSONArray: TWorkflowStatus[];
}

interface IItem extends LabelValueObject {
	checked?: boolean;
}

export type FilterErrors = {
	endDate?: string;
	items?: string;
	selectedFilterBy?: string;
	selectedFilterType?: string;
	startDate?: string;
	value?: string;
};

export type FilterValidation = {
	checkedItems: IItem[];
	disableDateValues?: boolean;
	items: IItem[];
	selectedFilterBy?: ObjectField;
	selectedFilterType?: LabelValueObject | null;
	setErrors: (value: FilterErrors) => void;
	value?: string;
};

type TCurrentFilter = {
	definition: {
		[key: string]: string[] | number[];
	} | null;
	fieldLabel?: string;
	filterBy?: string;
	filterType: string | null;
	label: TName;
	objectFieldBusinessType?: string;
	objectFieldName?: string;
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
