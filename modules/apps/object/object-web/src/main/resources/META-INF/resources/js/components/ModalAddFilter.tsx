/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	filterArrayByQuery,
	getLocalizableLabel,
} from '@liferay/object-js-components-web';
import React, {
	FormEvent,
	useCallback,
	useEffect,
	useMemo,
	useState,
} from 'react';

import {
	getCheckedListTypeEntries,
	getCheckedObjectRelationshipItems,
	getCheckedWorkflowStatusItems,
	getSystemObjectFieldLabelFromObjectEntry,
} from '../utils/filter';

import './ModalAddFilter.scss';

interface IProps {
	aggregationFilter?: boolean;
	creationLanguageId?: Liferay.Language.Locale;
	currentFilters: CurrentFilter[];
	disableAutoClose?: boolean;
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
	workflowStatusJSONArray: LabelValueObject[];
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

type CurrentFilter = {
	definition: {
		[key: string]: string[] | number[];
	} | null;
	fieldLabel?: string;
	filterBy?: string;
	filterType: string | null;
	label: LocalizedValue<string>;
	objectFieldBusinessType?: string;
	objectFieldName?: string;
	value?: string;
	valueList?: LabelValueObject[];
};

type AttachmentEntry = {
	id: number;
	link: {
		href: string;
		label: string;
	};
	name: string;
};

export function ModalAddFilter({
	aggregationFilter,
	creationLanguageId,
	currentFilters,
	disableAutoClose = false,
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
		return filterArrayByQuery({
			array: objectFields,
			creationLanguageId: creationLanguageId as Liferay.Language.Locale,
			query,
			str: 'label',
		});
	}, [creationLanguageId, objectFields, query]);

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

	const setFieldValues = useCallback(
		(objectField: ObjectField) => {
			if (
				objectField.businessType === 'MultiselectPicklist' ||
				objectField?.businessType === 'Picklist'
			) {
				const makeFetch = async () => {
					if (objectField.listTypeDefinitionId) {
						const items = await API.getListTypeDefinitionListTypeEntries(
							objectField.listTypeDefinitionId
						);

						if (editingFilter) {
							setItems(
								getCheckedListTypeEntries(
									items,
									setEditingFilterType
								)
							);
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
					}
				};

				makeFetch();
			}
			else if (objectField.name === 'status') {
				let newItems: IItem[] = [];

				if (editingFilter) {
					newItems = getCheckedWorkflowStatusItems(
						workflowStatusJSONArray,
						setEditingFilterType
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
						{
							objectFields,
							restContextPath,
							system,
							titleObjectFieldName,
						},
					] = await API.getObjectDefinitions(
						`filter=name eq '${value}'`
					);

					const titleObjectField = objectFields.find(
						(objectField) =>
							objectField.name === titleObjectFieldName
					) as ObjectField;

					const relatedObjectEntries = await API.getList<ObjectEntry>(
						`${restContextPath}`
					);

					if (!relatedObjectEntries) {
						setItems([]);

						return;
					}

					if (editingFilter) {
						setItems(
							getCheckedObjectRelationshipItems(
								relatedObjectEntries,
								titleObjectField.name,
								titleObjectField.system as boolean,
								system,
								setEditingFilterType
							)
						);
					}
					else {
						const newItems = relatedObjectEntries.map(
							(objectEntry) => {
								const newItemsObject = {
									value: system
										? String(objectEntry.id)
										: objectEntry.externalReferenceCode,
								} as LabelValueObject;

								if (titleObjectField.system) {
									return getSystemObjectFieldLabelFromObjectEntry(
										titleObjectField.name,
										objectEntry,
										newItemsObject
									) as LabelValueObject;
								}

								let label = objectEntry[
									titleObjectField?.name
								] as string;

								if (
									titleObjectField.businessType ===
									'Attachment'
								) {
									label = (objectEntry as {
										[key: string]: AttachmentEntry;
									})[titleObjectField.name].name;
								}

								return {
									...newItemsObject,
									label,
								};
							}
						);

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
					selectedFilterBy?.businessType === 'MultiselectPicklist' ||
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
					selectedFilterBy?.businessType === 'MultiselectPicklist' ||
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

	const isMultiSelectValue = () => {
		if (
			aggregationFilter &&
			selectedFilterBy?.businessType === 'Relationship'
		) {
			return false;
		}

		if (
			selectedFilterType &&
			(selectedFilterBy?.name === 'status' ||
				selectedFilterBy?.businessType === 'MultiselectPicklist' ||
				selectedFilterBy?.businessType === 'Picklist' ||
				selectedFilterBy?.businessType === 'Relationship')
		) {
			return true;
		}
	};

	const aggregationRelationshipOrDateFieldType =
		selectedFilterBy?.businessType === 'Date' ||
		(aggregationFilter &&
			selectedFilterBy?.businessType === 'Relationship');

	return (
		<ClayModal disableAutoClose={disableAutoClose} observer={observer}>
			<ClayModal.Header>{header}</ClayModal.Header>

			<ClayModal.Body>
				{!editingFilter && (
					<AutoComplete<ObjectField>
						emptyStateMessage={Liferay.Language.get(
							'there-are-no-columns-available'
						)}
						error={errors.selectedFilterBy}
						items={filteredAvailableFields}
						label={Liferay.Language.get('filter-by')}
						onActive={(item) =>
							item.name === selectedFilterBy?.name
						}
						onChangeQuery={setQuery}
						onSelectItem={(item) => {
							const userRelationship = !!item.objectFieldSettings?.find(
								({name, value}) =>
									name === 'objectDefinition1ShortName' &&
									value === 'User'
							);

							setSelectedFilterBy(item);
							setValue('');

							if (
								item.businessType === 'Relationship' &&
								userRelationship &&
								aggregationFilter
							) {
								return setSelectedFilterType({
									label: 'currentUser',
									value: 'currentUser',
								});
							}

							setSelectedFilterType(null);
						}}
						query={query}
						required
						value={getLocalizableLabel(
							creationLanguageId as Liferay.Language.Locale,
							selectedFilterBy?.label
						)}
					>
						{({label, name}) => (
							<div className="d-flex justify-content-between">
								<div>
									{getLocalizableLabel(
										creationLanguageId as Liferay.Language.Locale,
										label,
										name
									)}
								</div>
							</div>
						)}
					</AutoComplete>
				)}

				{selectedFilterBy &&
					!aggregationRelationshipOrDateFieldType && (
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

				{isMultiSelectValue() && (
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
									type="Date"
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
									type="Date"
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
