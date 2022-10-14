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

import {useModal} from '@clayui/modal';
import {
	API,
	BuilderScreen,
	Card,
	Input,
	InputLocalized,
	SidePanelForm,
	invalidateRequired,
	openToast,
	saveAndReload,
} from '@liferay/object-js-components-web';
import React, {useEffect, useState} from 'react';

import {updateFieldSettings} from '../../utils/fieldSettings';
import {
	FilterErrors,
	FilterValidation,
	ModalAddFilter,
} from '../ModalAddFilter';
import {AttachmentProperties} from './AttachmentProperties';
import {MaxLengthProperties} from './MaxLengthProperties';
import ObjectFieldFormBase from './ObjectFieldFormBase';
import {SearchableContainer} from './SearchableContainer';
import {useObjectFieldForm} from './useObjectFieldForm';

import './EditObjectField.scss';
import {FormulaContainer} from './FormulaContainer';

interface AggregationFilters {
	defaultSort?: boolean;
	fieldLabel?: string;
	filterBy?: string;
	filterType?: string;
	label: LocalizedValue<string>;
	objectFieldBusinessType?: string;
	objectFieldName: string;
	priority?: number;
	sortOrder?: string;
	type?: string;
	value?: string;
	valueList?: LabelValueObject[];
}

interface IItem extends LabelValueObject {
	checked?: boolean;
}
interface IProps {
	filterOperators: TFilterOperators;
	forbiddenChars: string[];
	forbiddenLastChars: string[];
	forbiddenNames: string[];
	isApproved: boolean;
	isDefaultStorageType: boolean;
	objectDefinitionId: number;
	objectField: ObjectField;
	objectFieldTypes: ObjectFieldType[];
	objectName: string;
	objectRelationshipId: number;
	readOnly: boolean;
	workflowStatusJSONArray: LabelValueObject[];
}

const REQUIRED_MSG = Liferay.Language.get('required');
const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

export default function EditObjectField({
	filterOperators,
	forbiddenChars,
	forbiddenLastChars,
	forbiddenNames,
	isApproved,
	isDefaultStorageType,
	objectDefinitionId,
	objectField: initialValues,
	objectFieldTypes,
	objectName,
	objectRelationshipId,
	readOnly,
	workflowStatusJSONArray,
}: IProps) {
	const [editingObjectFieldName, setEditingObjectFieldName] = useState<
		string
	>('');

	const [editingFilter, setEditingFilter] = useState(false);
	const [objectFields, setObjectFields] = useState<ObjectField[]>();
	const [objectDefinitionId2, setObjectDefinitionId2] = useState<number>();
	const [aggregationFilters, setAggregationFilters] = useState<
		AggregationFilters[]
	>([]);
	const [visibleModal, setVisibleModal] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => {
			setEditingFilter(false);
			setVisibleModal(false);
		},
	});

	const onSubmit = async ({id, ...objectField}: ObjectField) => {
		delete objectField.listTypeDefinitionId;
		delete objectField.system;

		try {
			await API.save(
				`/o/object-admin/v1.0/object-fields/${id}`,
				objectField
			);

			saveAndReload();
			openToast({
				message: Liferay.Language.get(
					'the-object-field-was-updated-successfully'
				),
			});
		}
		catch (error) {
			openToast({message: (error as Error).message, type: 'danger'});
		}
	};

	const {
		errors,
		handleChange,
		handleSubmit,
		setValues,
		values,
	} = useObjectFieldForm({
		forbiddenChars,
		forbiddenLastChars,
		forbiddenNames,
		initialValues,
		onSubmit,
	});

	const disableFieldFormBase = !!(
		isApproved ||
		values.system ||
		values.relationshipType
	);

	const handleSettingsChange = ({name, value}: ObjectFieldSetting) =>
		setValues({
			objectFieldSettings: updateFieldSettings(
				values.objectFieldSettings,
				{name, value}
			),
		});

	const handleDeleteFilterColumn = (objectFieldName?: string) => {
		const {objectFieldSettings} = values;

		const [filter] = objectFieldSettings?.filter(
			(fieldSetting) => fieldSetting.name === 'filters'
		) as ObjectFieldSetting[];

		const filterValues = filter.value as ObjectFieldFilterSetting[];

		const newFilterValues: ObjectFieldFilterSetting[] = [
			...filterValues.filter(
				(filterValue) => filterValue.filterBy !== objectFieldName
			),
		];

		const newFilter: ObjectFieldSetting = {
			name: filter.name,
			value: newFilterValues,
		};

		const newObjectFieldSettings: ObjectFieldSetting[] | undefined = [
			...(objectFieldSettings?.filter(
				(fieldSettings) => fieldSettings.name !== 'filters'
			) as ObjectFieldSetting[]),
			newFilter,
		];

		const newAggregationFilters = aggregationFilters.filter(
			(aggregationFilter) =>
				aggregationFilter.filterBy !== objectFieldName
		);

		setAggregationFilters(newAggregationFilters);

		setValues({
			objectFieldSettings: newObjectFieldSettings,
		});
	};

	const handleSaveFilterColumn = (
		objectFieldName: string,
		filterBy?: string,
		fieldLabel?: LocalizedValue<string>,
		objectFieldBusinessType?: string,
		filterType?: string,
		valueList?: IItem[],
		value?: string
	) => {
		const newAggregationFilters = [
			...aggregationFilters,
			{
				fieldLabel: fieldLabel ? fieldLabel[defaultLanguageId] : '',
				filterBy,
				filterType,
				label: fieldLabel,
				objectFieldBusinessType,
				objectFieldName,
				value,
				valueList,
			},
		] as AggregationFilters[];

		const {objectFieldSettings} = values;

		const filterSetting = objectFieldSettings?.filter(
			({name}) => name === 'filters'
		);

		if (filterSetting) {
			const [filter] = filterSetting;

			let newFilterValues: ObjectFieldFilterSetting[] = [];

			if (objectFieldBusinessType === 'Date') {
				const dateJson: ObjectFieldDateRangeFilterSettings = {};

				valueList?.forEach(({label, value}) => {
					dateJson[value] = label;
				});

				newFilterValues = [
					...(filter.value as ObjectFieldFilterSetting[]),
					{
						filterBy: objectFieldName,
						filterType,
						json: dateJson,
					},
				];
			}
			else if (
				objectFieldBusinessType === 'Picklist' ||
				objectFieldName === 'status'
			) {
				let picklistJson:
					| ExcludesFilterOperator
					| IncludesFilterOperator;

				if (filterType === 'excludes') {
					picklistJson = {
						not: {
							in: valueList?.map(({value}) => value) as
								| string[]
								| number[],
						},
					};
				}
				else {
					picklistJson = {
						in: valueList?.map(({value}) => value) as
							| string[]
							| number[],
					};
				}

				newFilterValues = [
					...(filter.value as ObjectFieldFilterSetting[]),
					{
						filterBy: objectFieldName,
						filterType,
						json: picklistJson,
					},
				];
			}
			else {
				newFilterValues = [
					...(filter.value as ObjectFieldFilterSetting[]),
					{
						filterBy: objectFieldName,
						filterType,
						json: {
							[filterType as string]: value
								? value
								: valueList?.map(({value}) => value),
						},
					},
				];
			}

			const newFilter: ObjectFieldSetting = {
				name: filter.name,
				value: newFilterValues,
			};

			const newObjectFieldSettings: ObjectFieldSetting[] | undefined = [
				...(objectFieldSettings?.filter(
					(fieldSetting) => fieldSetting.name !== 'filters'
				) as ObjectFieldSetting[]),
				newFilter,
			];

			setAggregationFilters(newAggregationFilters);
			setValues({
				objectFieldSettings: newObjectFieldSettings,
			});
		}
	};

	const getPicklistFilterJSONValues = (
		filterType: string,
		parsedFilter: ObjectFieldFilterSetting
	) => {
		let picklistFilterValues: string[] | number[] = [];

		if (filterType === 'includes') {
			picklistFilterValues = (parsedFilter.json as IncludesFilterOperator)[
				'in'
			];
		}
		else {
			picklistFilterValues = (parsedFilter.json as ExcludesFilterOperator)[
				'not'
			]['in'];
		}

		return picklistFilterValues;
	};

	const validateFilters = ({
		checkedItems,
		items,
		selectedFilterBy,
		selectedFilterType,
		setErrors,
		value,
	}: FilterValidation) => {
		setErrors({});
		const currentErrors: FilterErrors = {};

		if (!selectedFilterBy) {
			currentErrors.selectedFilterBy = REQUIRED_MSG;
		}

		if (!selectedFilterType) {
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
			selectedFilterType?.value === 'range'
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

	useEffect(() => {
		if (values.businessType === 'Aggregation' && objectDefinitionId2) {
			API.getObjectFields(objectDefinitionId2).then(setObjectFields);
		}
	}, [values.businessType, objectDefinitionId2]);

	useEffect(() => {
		if (values.businessType === 'Aggregation') {
			const filters = values.objectFieldSettings?.find(
				(settings) => settings.name === 'filters'
			);

			const filterValues = filters?.value as ObjectFieldFilterSetting[];

			if (!filterValues.length) {
				setAggregationFilters([]);

				return;
			}

			if (
				values.objectFieldSettings &&
				objectFields &&
				filterValues.length !== 0
			) {
				const newAggregationFilters = filterValues.map(
					(parsedFilter) => {
						const objectField = objectFields.find(
							(objectField) =>
								objectField.name === parsedFilter.filterBy
						);

						const filterType = parsedFilter.filterType as string;

						if (objectField && filterType) {
							const aggregationFilter: AggregationFilters = {
								fieldLabel:
									objectField.label[defaultLanguageId],
								filterBy: parsedFilter.filterBy,
								filterType,
								label: objectField.label,
								objectFieldBusinessType:
									objectField.businessType,
								objectFieldName: objectField.name,
								value:
									objectField.businessType === 'Integer' ||
									objectField.businessType === 'LongInteger'
										? (parsedFilter.json as {
												[key: string]: string;
										  })[filterType]
										: undefined,
							};

							if (
								objectField.businessType === 'Date' &&
								parsedFilter.filterType === 'range'
							) {
								const dateRangeFilterValues = parsedFilter.json as ObjectFieldDateRangeFilterSettings;

								const aggregationFilterDateRangeValues: LabelValueObject[] = [
									{
										label: dateRangeFilterValues['ge'],
										value: 'ge',
									},
									{
										label: dateRangeFilterValues['le'],
										value: 'le',
									},
								];

								const dateRangeAggregationFilter: AggregationFilters = {
									...aggregationFilter,
									valueList: aggregationFilterDateRangeValues,
								};

								return dateRangeAggregationFilter;
							}

							if (objectField.businessType === 'Picklist') {
								const picklistFilterValues = getPicklistFilterJSONValues(
									filterType,
									parsedFilter
								) as string[];

								const picklistValueList: LabelValueObject[] = picklistFilterValues.map(
									(picklistFilterValue) => {
										return {
											checked: true,
											label: picklistFilterValue,
											value: picklistFilterValue,
										};
									}
								);

								const picklistAggregationFilter: AggregationFilters = {
									...aggregationFilter,
									valueList: picklistValueList,
								};

								return picklistAggregationFilter;
							}

							if (objectField.name === 'status') {
								const statusFilterValues = getPicklistFilterJSONValues(
									filterType,
									parsedFilter
								) as number[];

								const workflowStatusValueList = statusFilterValues.map(
									(statusValue) => {
										const currentStatus = workflowStatusJSONArray.find(
											(workflowStatus) =>
												Number(workflowStatus.value) ===
												statusValue
										);

										return {
											label: currentStatus?.label,
											value: currentStatus?.label,
										};
									}
								);

								const statusAggregationFilter: AggregationFilters = {
									...aggregationFilter,
									valueList: workflowStatusValueList as LabelValueObject[],
								};

								return statusAggregationFilter;
							}

							return aggregationFilter;
						}
					}
				);

				setAggregationFilters(
					newAggregationFilters as AggregationFilters[]
				);
			}
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [objectFields]);

	return (
		<SidePanelForm
			className="lfr-objects__edit-object-field"
			onSubmit={handleSubmit}
			readOnly={readOnly}
			title={Liferay.Language.get('field')}
		>
			<Card title={Liferay.Language.get('basic-info')}>
				<InputLocalized
					disableFlag={readOnly}
					disabled={readOnly}
					error={errors.label}
					label={Liferay.Language.get('label')}
					onChange={(label) => setValues({label})}
					required
					translations={values.label as LocalizedValue<string>}
				/>

				<ObjectFieldFormBase
					disabled={disableFieldFormBase}
					editingField
					errors={errors}
					handleChange={handleChange}
					objectDefinitionId={objectDefinitionId}
					objectField={values}
					objectFieldTypes={objectFieldTypes}
					objectName={objectName}
					objectRelationshipId={objectRelationshipId}
					onAggregationFilterChange={setAggregationFilters}
					onRelationshipChange={setObjectDefinitionId2}
					setValues={setValues}
				>
					{values.businessType === 'Attachment' && (
						<AttachmentProperties
							errors={errors}
							objectFieldSettings={
								values.objectFieldSettings as ObjectFieldSetting[]
							}
							onSettingsChange={handleSettingsChange}
						/>
					)}

					{(values.businessType === 'Text' ||
						values.businessType === 'LongText') && (
						<MaxLengthProperties
							disabled={values.system}
							errors={errors}
							objectField={values}
							objectFieldSettings={
								values.objectFieldSettings as ObjectFieldSetting[]
							}
							onSettingsChange={handleSettingsChange}
							setValues={setValues}
						/>
					)}
				</ObjectFieldFormBase>
			</Card>

			{values.businessType === 'Aggregation' && (
				<BuilderScreen
					disableEdit
					emptyState={{
						buttonText: Liferay.Language.get('new-filter'),
						description: Liferay.Language.get(
							'use-conditions-to-specify-which-fields-will-be-considered-in-the-aggregation'
						),
						title: Liferay.Language.get(
							'no-filter-was-created-yet'
						),
					}}
					filter
					firstColumnHeader={Liferay.Language.get('filter-by')}
					objectColumns={aggregationFilters}
					onDeleteColumn={handleDeleteFilterColumn}
					onEditingObjectFieldName={setEditingObjectFieldName}
					onVisibleEditModal={setVisibleModal}
					openModal={() => setVisibleModal(true)}
					secondColumnHeader={Liferay.Language.get('type')}
					thirdColumnHeader={Liferay.Language.get('value')}
					title={Liferay.Language.get('filters')}
				/>
			)}

			{visibleModal && (
				<ModalAddFilter
					currentFilters={[]}
					editingFilter={editingFilter}
					editingObjectFieldName={editingObjectFieldName}
					filterOperators={filterOperators}
					filterTypeRequired
					header={Liferay.Language.get('filter')}
					objectFields={
						objectFields?.filter((objectField) => {
							if (
								objectField.businessType === 'Date' ||
								objectField.businessType === 'Integer' ||
								objectField.businessType === 'LongInteger' ||
								objectField.businessType === 'Picklist' ||
								objectField.name === 'status'
							) {
								return objectField;
							}
						}) ?? []
					}
					observer={observer}
					onClose={onClose}
					onSave={handleSaveFilterColumn}
					validate={validateFilters}
					workflowStatusJSONArray={workflowStatusJSONArray}
				/>
			)}

			{values.businessType === 'Formula' && (
				<FormulaContainer
					errors={errors}
					objectFieldSettings={
						values.objectFieldSettings as ObjectFieldSetting[]
					}
					setValues={setValues}
				/>
			)}

			{values.DBType !== 'Blob' && values.businessType !== 'Formula' && (
				<SearchableContainer
					errors={errors}
					isApproved={isApproved}
					objectField={values}
					readOnly={readOnly}
					setValues={setValues}
				/>
			)}

			{Liferay.FeatureFlags['LPS-135430'] && !isDefaultStorageType && (
				<Card title={Liferay.Language.get('external-data-source')}>
					<Input
						label={Liferay.Language.get('external-reference-code')}
						name="externalReferenceCode"
						onChange={handleChange}
						value={values.externalReferenceCode}
					/>
				</Card>
			)}
		</SidePanelForm>
	);
}
