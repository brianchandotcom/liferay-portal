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
} from '@liferay/object-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {
	FormEvent,
	useCallback,
	useEffect,
	useMemo,
	useState,
} from 'react';

import {HEADERS} from '../utils/constants';
import {defaultLanguageId, locale} from '../utils/locale';

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
	const [availableFields, setAvailableFields] = useState(objectFields);

	const [items, setItems] = useState<IItem[]>([]);

	const [selectedFilterBy, setSelectedFilterBy] = useState<TObjectField>();

	const [selectedFilterType, setSelectedFilterType] = useState<
		LabelValueObject
	>();

	const [query, setQuery] = useState<string>('');

	const filteredAvailableFields = useMemo(() => {
		return availableFields.filter(({label}) => {
			return label[defaultLanguageId]
				.toLowerCase()
				.includes(query.toLowerCase());
		});
	}, [availableFields, query]);

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

	const getCheckedPickListItems = (itemValues: TPickListValue[]): IItem[] => {
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
		(objectField: TObjectField) => {
			if (objectField?.businessType === 'Picklist') {
				const makeFetch = async () => {
					const response = await fetch(
						`/o/headless-admin-list-type/v1.0/list-type-definitions/${objectField.listTypeDefinitionId}/list-type-entries`,
						{
							headers: HEADERS,
							method: 'GET',
						}
					);

					const {items = []} = (await response.json()) as {
						items?: TPickListValue[];
					};

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
		const filteredPickListFields = objectFields.filter((objectField) => {
			if (
				objectField.businessType === 'Picklist' ||
				objectField.name === 'dateCreated' ||
				objectField.name === 'dateModified' ||
				(objectField.name === 'status' && !objectField.hasFilter)
			) {
				return objectField;
			}
		});

		setAvailableFields(filteredPickListFields);
	}, [objectFields]);

	useEffect(() => {
		if (!selectedFilterBy && !editingObjectFieldName) {
			setItems([]);
		}
		else {
			if (selectedFilterBy) {
				setFieldValues(selectedFilterBy);
			}
			else {
				const objectField = objectFields.find((objectField) => {
					if (objectField.name === editingObjectFieldName) {
						return objectField;
					}
				});

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
				selectedFilterType?.value,
				editingObjectFieldName,
				checkedItems
			);
		}
		else {
			onSave(
				selectedFilterType?.value,
				selectedFilterBy?.name,
				checkedItems
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
					disabled={
						!editingFilter &&
						(!selectedFilterBy ||
							(selectedFilterBy.businessType !==
								'Workflow Status' &&
								selectedFilterBy.businessType !== 'Picklist'))
					}
					label={Liferay.Language.get('filter-type')}
					onChange={(target: LabelValueObject) =>
						setSelectedFilterType(target)
					}
					options={PICKLIST_OPERATORS}
					value={selectedFilterType?.label}
				/>

				<FormCustomSelect
					disabled={!selectedFilterType?.value}
					label={Liferay.Language.get('value')}
					multipleChoice
					options={items}
					setOptions={setItems}
				/>
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
	objectFields: TObjectField[];
	observer: any;
	onClose: () => void;
	onSave: (
		filterType?: string,
		objectFieldName?: string,
		valueList?: IItem[]
	) => void;
	workflowStatusJSONArray: TWorkflowStatus[];
}

interface IItem extends LabelValueObject {
	checked?: boolean;
}

type TPickListValue = {
	dateCreated: string;
	dateModified: number;
	id: number;
	key: string;
	name: string;
	name_i18n: TName;
	type: string;
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

type TObjectField = {
	businessType: string;
	checked: boolean;
	filtered?: boolean;
	hasFilter?: boolean;
	id: number;
	indexed: boolean;
	indexedAsKeyword: boolean;
	indexedLanguageId: string;
	label: TName;
	listTypeDefinitionId: boolean;
	name: string;
	required: boolean;
	type: string;
};
