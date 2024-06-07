/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {useModal} from '@clayui/modal';
import {
	API,
	BuilderScreen,
	constantsUtils,
	invalidateRequired,
	stringUtils,
} from '@liferay/object-js-components-web';
import classNames from 'classnames';
import React, {ElementType, useCallback, useEffect, useState} from 'react';

import {
	FilterErrors,
	FilterValidation,
	ModalAddFilter,
	OnSaveProps,
} from '../../../ModalAddFilter';

import '../../EditObjectFieldContent.scss';

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

interface AggregationFilterProps {
	aggregationFilters: AggregationFilters[];
	containerWrapper: ElementType;
	creationLanguageId2?: Liferay.Language.Locale;
	filterOperators: TFilterOperators;
	modelBuilder: boolean;
	objectDefinitionExternalReferenceCode2?: string;
	onSubmit?: (editedObjectField?: Partial<ObjectField>) => void;
	setAggregationFilters: (values: AggregationFilters[]) => void;
	setCreationLanguageId2: (values: Liferay.Language.Locale) => void;
	setValues: (values: Partial<ObjectField>) => void;
	values: Partial<ObjectField>;
	workflowStatuses: LabelValueObject[];
}

interface CustomWindow extends Window {
	__isReactDndBackendSetUp?: boolean;
}

export function AggregationFilterContainer({
	aggregationFilters,
	containerWrapper: ContainerWrapper,
	creationLanguageId2,
	filterOperators,
	modelBuilder,
	objectDefinitionExternalReferenceCode2,
	onSubmit,
	setAggregationFilters,
	setCreationLanguageId2,
	setValues,
	values,
	workflowStatuses,
}: AggregationFilterProps) {
	const [editingFilter, setEditingFilter] = useState(false);
	const [editingObjectFieldName, setEditingObjectFieldName] =
		useState<string>('');
	const [objectFields, setObjectFields] = useState<ObjectField[]>();
	const [visibleModal, setVisibleModal] = useState(false);

	const {observer, onClose} = useModal({
		onClose: () => {
			setEditingFilter(false);
			setVisibleModal(false);
		},
	});

	const getPicklistFilterJSONValues = (
		filterType: string,
		parsedFilter: ObjectFieldFilterSetting
	) => {
		let picklistFilterValues: string[] | number[] = [];

		if (filterType === 'includes') {
			picklistFilterValues = (
				parsedFilter.json as IncludesFilterOperator
			)['in'];
		}
		else {
			picklistFilterValues = (
				parsedFilter.json as ExcludesFilterOperator
			)['not']['in'];
		}

		return picklistFilterValues;
	};

	useEffect(() => {
		if (objectDefinitionExternalReferenceCode2) {
			const makeFetch = async () => {
				const items =
					await API.getObjectDefinitionByExternalReferenceCodeObjectFields(
						objectDefinitionExternalReferenceCode2!
					);

				const objectDefinition2 =
					await API.getObjectDefinitionByExternalReferenceCode(
						objectDefinitionExternalReferenceCode2!
					);
				setCreationLanguageId2(objectDefinition2.defaultLanguageId);

				setObjectFields(items);
			};

			makeFetch();
		}
	}, [objectDefinitionExternalReferenceCode2, setCreationLanguageId2]);

	useEffect(() => {
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
			const newAggregationFilters = filterValues.map((parsedFilter) => {
				const objectField = objectFields.find(
					(objectField) => objectField.name === parsedFilter.filterBy
				);

				const filterType = parsedFilter.filterType as string;

				if (objectField && filterType) {
					const aggregationFilter: AggregationFilters = {
						fieldLabel: stringUtils.getLocalizableLabel(
							creationLanguageId2 as Liferay.Language.Locale,
							objectField.label,
							objectField.name
						),
						filterBy: parsedFilter.filterBy,
						filterType,
						label: objectField.label,
						objectFieldBusinessType: objectField.businessType,
						objectFieldName: objectField.name,
						value:
							objectField.businessType === 'Integer' ||
							objectField.businessType === 'LongInteger'
								? (
										parsedFilter.json as {
											[key: string]: string;
										}
									)[filterType]
								: undefined,
					};

					if (
						objectField.businessType === 'Date' &&
						parsedFilter.filterType === 'range'
					) {
						const dateRangeFilterValues =
							parsedFilter.json as ObjectFieldDateRangeFilterSettings;

						const aggregationFilterDateRangeValues: LabelValueObject[] =
							[
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
						const picklistFilterValues =
							getPicklistFilterJSONValues(
								filterType,
								parsedFilter
							) as string[];

						const picklistValueList: LabelValueObject[] =
							picklistFilterValues.map((picklistFilterValue) => {
								return {
									checked: true,
									label: picklistFilterValue,
									value: picklistFilterValue,
								};
							});

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
								const currentStatus = workflowStatuses.find(
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
							valueList:
								workflowStatusValueList as LabelValueObject[],
						};

						return statusAggregationFilter;
					}

					return aggregationFilter;
				}
			});

			setAggregationFilters(
				newAggregationFilters as AggregationFilters[]
			);
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [objectFields]);

	const validateFilters = useCallback(
		({
			checkedItems,
			items,
			selectedFilterBy,
			selectedFilterTypeValue,
			setErrors,
			value,
		}: FilterValidation) => {
			setErrors({});
			const currentErrors: FilterErrors = {};

			if (!selectedFilterBy) {
				currentErrors.selectedFilterBy = constantsUtils.REQUIRED_MSG;
			}

			if (!selectedFilterTypeValue) {
				currentErrors.selectedFilterType = constantsUtils.REQUIRED_MSG;
			}

			if (
				(selectedFilterBy?.name === 'status' ||
					selectedFilterBy?.businessType === 'Picklist') &&
				!checkedItems.length
			) {
				currentErrors.items = constantsUtils.REQUIRED_MSG;
			}

			if (
				selectedFilterBy?.businessType === 'Date' &&
				selectedFilterTypeValue === 'range'
			) {
				const startDate = items.find((date) => date.value === 'ge');
				const endDate = items.find((date) => date.value === 'le');

				if (!startDate) {
					currentErrors.startDate = constantsUtils.REQUIRED_MSG;
				}

				if (!endDate) {
					currentErrors.endDate = constantsUtils.REQUIRED_MSG;
				}
			}

			if (
				(selectedFilterBy?.businessType === 'Integer' ||
					selectedFilterBy?.businessType === 'LongInteger') &&
				invalidateRequired(value)
			) {
				currentErrors.value = constantsUtils.REQUIRED_MSG;
			}

			setErrors(currentErrors);

			return currentErrors;
		},
		[]
	);

	const handleSaveFilterColumn = useCallback(
		({
			fieldLabel,
			filterBy,
			filterType,
			objectFieldBusinessType,
			objectFieldName,
			value,
			valueList,
		}: OnSaveProps) => {
			const newAggregationFilters = [
				...aggregationFilters,
				{
					fieldLabel: stringUtils.getLocalizableLabel(
						creationLanguageId2 as Liferay.Language.Locale,
						fieldLabel,
						objectFieldName
					),
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
				else if (objectFieldBusinessType === 'Relationship') {
					newFilterValues = [
						...(filter.value as ObjectFieldFilterSetting[]),
						{
							filterBy: objectFieldName,
							filterType,
							json: {},
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

				const newObjectFieldSettings: ObjectFieldSetting[] | undefined =
					[
						...(objectFieldSettings?.filter(
							(fieldSetting) => fieldSetting.name !== 'filters'
						) as ObjectFieldSetting[]),
						newFilter,
					];

				setAggregationFilters(newAggregationFilters);
				setValues({
					objectFieldSettings: newObjectFieldSettings,
				});

				if (onSubmit) {
					onSubmit({
						...values,
						objectFieldSettings: newObjectFieldSettings,
					});
				}
			}
		},

		// eslint-disable-next-line react-hooks/exhaustive-deps
		[aggregationFilters, creationLanguageId2, values]
	);

	const handleDeleteFilterColumn = useCallback(
		(objectFieldName?: string) => {
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

			const newAggregationFilters = aggregationFilters!.filter(
				(aggregationFilter) =>
					aggregationFilter.filterBy !== objectFieldName
			);

			setAggregationFilters(newAggregationFilters);

			setValues({
				objectFieldSettings: newObjectFieldSettings,
			});

			if (onSubmit) {
				onSubmit({
					...values,
					objectFieldSettings: newObjectFieldSettings,
				});
			}
		},

		// eslint-disable-next-line react-hooks/exhaustive-deps
		[aggregationFilters, setAggregationFilters, setValues, values]
	);

	const isValidAggregationFilterField = ({
		businessType,
		name,
		objectFieldSettings,
	}: ObjectField) => {
		const userRelationship = !!objectFieldSettings?.find(
			({name, value}) =>
				name === 'objectDefinition1ShortName' && value === 'User'
		);

		if (businessType === 'Relationship' && userRelationship) {
			return true;
		}

		return (
			businessType === 'Date' ||
			businessType === 'Integer' ||
			businessType === 'LongInteger' ||
			businessType === 'Picklist' ||
			name === 'status'
		);
	};

	if ((window as CustomWindow).__isReactDndBackendSetUp) {
		(window as CustomWindow).__isReactDndBackendSetUp = false;
	}

	return (
		<>
			<ContainerWrapper
				displayTitle={Liferay.Language.get('filters')}
				displayType="unstyled"
				title={Liferay.Language.get('filters')}
			>
				<div
					className={classNames({
						'lfr-objects__edit-object-field-model-builder-panel':
							modelBuilder,
					})}
				>
					<BuilderScreen
						builderScreenItems={aggregationFilters}
						creationLanguageId={
							creationLanguageId2 as Liferay.Language.Locale
						}
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
						onDeleteColumn={handleDeleteFilterColumn}
						onEditingObjectFieldName={setEditingObjectFieldName}
						onVisibleEditModal={setVisibleModal}
						openModal={() => setVisibleModal(true)}
						secondColumnHeader={Liferay.Language.get('type')}
						thirdColumnHeader={Liferay.Language.get('value')}
					/>
				</div>
			</ContainerWrapper>

			{visibleModal && (
				<ModalAddFilter
					aggregationFilter
					currentFilters={[]}
					disableAutoClose
					editingFilter={editingFilter}
					editingObjectFieldName={editingObjectFieldName}
					filterOperators={filterOperators}
					filterTypeRequired
					header={Liferay.Language.get('filter')}
					objectFields={
						objectFields?.filter((objectField) => {
							if (isValidAggregationFilterField(objectField)) {
								return objectField;
							}
						}) ?? []
					}
					observer={observer}
					onClose={onClose}
					onSave={handleSaveFilterColumn}
					validate={validateFilters}
					workflowStatuses={workflowStatuses}
				/>
			)}
		</>
	);
}
