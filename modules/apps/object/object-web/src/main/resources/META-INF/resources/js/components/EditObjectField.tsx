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

import ClayForm, {ClayRadio, ClayRadioGroup, ClayToggle} from '@clayui/form';
import {useModal} from '@clayui/modal';
import {
	API,
	BuilderScreen,
	Card,
	Input,
	InputLocalized,
	Select,
	SidePanelForm,
	Toggle,
	openToast,
	saveAndReload,
} from '@liferay/object-js-components-web';
import {fetch, sub} from 'frontend-js-web';
import React, {useEffect, useMemo, useRef, useState} from 'react';
import {createTextMaskInputElement} from 'text-mask-core';

import {HEADERS} from '../utils/constants';
import {createAutoCorrectedNumberPipe} from '../utils/createAutoCorrectedNumberPipe';
import {ERRORS} from '../utils/errors';
import {
	normalizeFieldSettings,
	updateFieldSettings,
} from '../utils/fieldSettings';
import {ModalAddFilter} from './ModalAddFilter';
import ObjectFieldFormBase, {
	ObjectFieldErrors,
	useObjectFieldForm,
} from './ObjectFieldFormBase';

import './EditObjectField.scss';

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();
const languages = Liferay.Language.available;
const languageLabels = Object.values(languages);

export default function EditObjectField({
	forbiddenChars,
	forbiddenLastChars,
	forbiddenNames,
	isApproved,
	isDefaultStorageType,
	objectDefinitionId,
	objectField: initialValues,
	objectFieldTypes,
	objectName,
	readOnly,
	workflowStatusJSONArray,
}: IProps) {
	const [editingObjectFieldName, setEditingObjectFieldName] = useState('');
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
		delete objectField.system;

		const response = await fetch(
			`/o/object-admin/v1.0/object-fields/${id}`,
			{
				body: JSON.stringify(objectField),
				headers: HEADERS,
				method: 'PUT',
			}
		);

		if (response.ok) {
			saveAndReload();
			openToast({
				message: Liferay.Language.get(
					'the-object-field-was-updated-successfully'
				),
			});
		}
		else {
			const error: {type?: string} | undefined = await response.json();

			const message =
				(error?.type && ERRORS[error.type]) ??
				Liferay.Language.get('an-error-occurred');

			openToast({message, type: 'danger'});
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

	const disabled = !!(
		readOnly ||
		isApproved ||
		values.relationshipType ||
		values.system
	);

	const handleSettingsChange = ({name, value}: ObjectFieldSetting) =>
		setValues({
			objectFieldSettings: updateFieldSettings(
				values.objectFieldSettings,
				{name, value}
			),
		});

	const handleDeleteFilterColumn = (objectFieldName: string) => {
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
		filterBy?: string,
		fieldLabel?: LocalizedValue<string>,
		objectFieldBusinessType?: string,
		filterType?: string,
		objectFieldName?: string,
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

		if (filterSetting?.length === 0 && objectFieldSettings) {
			let newObjectFieldSettings: ObjectFieldSetting[] | undefined = [];

			if (objectFieldBusinessType === 'Date') {
				const dateJson: ObjectFieldDateRangeFilterSettings = {};

				valueList?.forEach(({label, value}) => {
					dateJson[value] = label;
				});

				newObjectFieldSettings = [
					...objectFieldSettings,
					{
						name: 'filters',
						value: [
							{
								filterBy: objectFieldName,
								filterType,
								json: {
									[filterType as string]: value
										? value
										: dateJson,
								},
							},
						],
					},
				];
			}
			else {
				newObjectFieldSettings = [
					...objectFieldSettings,
					{
						name: 'filters',
						value: [
							{
								filterBy: objectFieldName,
								filterType,
								json: {
									[filterType as string]: value
										? value
										: valueList?.map(({value}) => value),
								},
							},
						],
					},
				];
			}

			setAggregationFilters(newAggregationFilters);
			setValues({
				objectFieldSettings: newObjectFieldSettings,
			});
		}
		else {
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

				const newObjectFieldSettings:
					| ObjectFieldSetting[]
					| undefined = [
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
						const objectField = objectFields?.find(
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
										? // @ts-ignore

										  parsedFilter.json[filterType]
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
			readOnly={
				values.system && objectName !== 'AccountEntry'
					? disabled
					: readOnly
			}
			title={Liferay.Language.get('field')}
		>
			<Card title={Liferay.Language.get('basic-info')}>
				<InputLocalized
					disabled={
						values.system && objectName !== 'AccountEntry'
							? disabled
							: readOnly
					}
					error={errors.label}
					label={Liferay.Language.get('label')}
					onChange={(label) => setValues({label})}
					required
					translations={values.label as LocalizedValue<string>}
				/>

				<ObjectFieldFormBase
					disabled={disabled}
					editingField
					errors={errors}
					handleChange={handleChange}
					objectDefinitionId={objectDefinitionId}
					objectField={values}
					objectFieldTypes={objectFieldTypes}
					objectName={objectName}
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
							disabled={
								values.system && objectName !== 'AccountEntry'
									? disabled
									: readOnly
							}
							errors={errors}
							isSystemObjectField={!!values.system}
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
					workflowStatusJSONArray={workflowStatusJSONArray}
				/>
			)}

			{values.DBType !== 'Blob' && (
				<SearchableContainer
					disabled={disabled}
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

function SearchableContainer({
	disabled,
	isApproved,
	objectField,
	readOnly,
	setValues,
}: ISearchableProps) {
	const isSearchableString =
		objectField.indexed &&
		(objectField.DBType === 'Clob' ||
			objectField.DBType === 'String' ||
			objectField.businessType === 'Attachment') &&
		objectField.businessType !== 'Aggregation';

	const selectedLanguageIndex = useMemo(() => {
		const label =
			objectField.indexedLanguageId &&
			languages[objectField.indexedLanguageId];

		return label ? languageLabels.indexOf(label) : undefined;
	}, [objectField.indexedLanguageId]);

	return (
		<Card title={Liferay.Language.get('searchable')}>
			<ClayForm.Group>
				<ClayToggle
					disabled={disabled}
					label={Liferay.Language.get('searchable')}
					name="indexed"
					onToggle={(indexed) => setValues({indexed})}
					toggled={objectField.indexed}
				/>
			</ClayForm.Group>

			{isSearchableString && (
				<ClayForm.Group>
					<ClayRadioGroup
						onChange={(selected: string | number) => {
							const indexedAsKeyword = selected === 'true';
							const indexedLanguageId = indexedAsKeyword
								? null
								: defaultLanguageId;

							setValues({
								indexedAsKeyword,
								indexedLanguageId,
							});
						}}
						value={new Boolean(
							objectField.indexedAsKeyword
						).toString()}
					>
						<ClayRadio
							disabled={readOnly || isApproved}
							label={Liferay.Language.get('keyword')}
							value="true"
						/>

						<ClayRadio
							disabled={readOnly || isApproved}
							label={Liferay.Language.get('text')}
							value="false"
						/>
					</ClayRadioGroup>
				</ClayForm.Group>
			)}

			{isSearchableString && !objectField.indexedAsKeyword && (
				<Select
					disabled={disabled}
					label={Liferay.Language.get('language')}
					name="indexedLanguageId"
					onChange={({target: {value}}) => {
						const selectedLabel =
							languageLabels[
								value as keyof typeof languageLabels
							];
						const [indexedLanguageId] = Object.entries(
							languages
						).find(([, label]) => selectedLabel === label) as [
							Locale,
							string
						];
						setValues({indexedLanguageId});
					}}
					options={languageLabels}
					required
					value={selectedLanguageIndex}
				/>
			)}
		</Card>
	);
}

function MaxLengthProperties({
	disabled,
	errors,
	isSystemObjectField,
	objectField,
	objectFieldSettings,
	onSettingsChange,
	setValues,
}: IMaxLengthPropertiesProps) {
	const [defaultMaxLength, defaultMaxLengthText] =
		objectField.businessType === 'Text' ? [280, '280'] : [65000, '65,000'];

	const settings = normalizeFieldSettings(objectFieldSettings);

	const inputRef = useRef(null);
	const maskRef = useRef();

	useEffect(() => {
		if (settings.showCounter) {
			maskRef.current = createTextMaskInputElement({
				guide: false,
				inputElement: inputRef.current,
				keepCharPositions: true,
				mask:
					objectField.businessType === 'Text'
						? [/\d/, /\d/, /\d/]
						: [/\d/, /\d/, /\d/, /\d/, /\d/],
				pipe: createAutoCorrectedNumberPipe(defaultMaxLength, 1),
				showMask: true,
			});
		}
	}, [defaultMaxLength, objectField.businessType, settings.showCounter]);

	return (
		<>
			<ClayForm.Group>
				<Toggle
					disabled={isSystemObjectField ?? disabled}
					label={Liferay.Language.get('limit-characters')}
					name="showCounter"
					onToggle={(value) => {
						const updatedSettings: ObjectFieldSetting[] = [
							{name: 'showCounter', value},
						];

						if (value) {
							updatedSettings.push({
								name: 'maxLength',
								value: defaultMaxLength,
							});
						}

						setValues({objectFieldSettings: updatedSettings});
					}}
					toggled={!!settings.showCounter}
					tooltip={Liferay.Language.get(
						'when-enabled-a-character-counter-will-be-shown-to-the-user'
					)}
				/>
			</ClayForm.Group>
			<ClayForm.Group>
				{settings.showCounter && (
					<Input
						error={errors.maxLength}
						feedbackMessage={sub(
							Liferay.Language.get(
								'set-the-maximum-number-of-characters-accepted-this-value-cant-be-less-than-x-or-greater-than-x'
							),
							'1',
							defaultMaxLengthText
						)}
						label={Liferay.Language.get(
							'maximum-number-of-characters'
						)}
						onChange={({target: {value}}) =>
							onSettingsChange({
								name: 'maxLength',
								value: value && Number(value),
							})
						}
						onInput={({target: {value}}: any) =>
							(maskRef.current as any).update(value)
						}
						ref={inputRef}
						required
						value={`${settings.maxLength}`}
					/>
				)}
			</ClayForm.Group>
		</>
	);
}

function AttachmentProperties({
	errors,
	objectFieldSettings,
	onSettingsChange,
}: IAttachmentPropertiesProps) {
	const settings = normalizeFieldSettings(objectFieldSettings);

	return (
		<>
			<ClayForm.Group>
				{settings.showFilesInDocumentsAndMedia && (
					<Input
						error={errors.storageDLFolderPath}
						feedbackMessage={sub(
							Liferay.Language.get(
								'input-the-path-of-the-chosen-folder-in-documents-and-media-an-example-of-a-valid-path-is-x'
							),
							'/myDocumentsAndMediaFolder'
						)}
						label={Liferay.Language.get('storage-folder')}
						maxLength={255}
						onChange={({target: {value}}) =>
							onSettingsChange({
								name: 'storageDLFolderPath',
								value,
							})
						}
						required
						value={settings.storageDLFolderPath as string}
					/>
				)}
			</ClayForm.Group>
			<Input
				component="textarea"
				error={errors.acceptedFileExtensions}
				feedbackMessage={Liferay.Language.get(
					'enter-the-list-of-file-extensions-users-can-upload-use-commas-to-separate-extensions'
				)}
				label={Liferay.Language.get('accepted-file-extensions')}
				onChange={({target: {value}}) =>
					onSettingsChange({name: 'acceptedFileExtensions', value})
				}
				required
				value={settings.acceptedFileExtensions as string}
			/>

			<Input
				error={errors.maximumFileSize}
				feedbackMessage={Liferay.Language.get('maximum-file-size-help')}
				label={Liferay.Language.get('maximum-file-size')}
				min={0}
				onChange={({target: {value}}) =>
					onSettingsChange({
						name: 'maximumFileSize',
						value: value && Number(value),
					})
				}
				required
				type="number"
				value={settings.maximumFileSize as number}
			/>
		</>
	);
}

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
interface IAttachmentPropertiesProps {
	errors: ObjectFieldErrors;
	objectFieldSettings: ObjectFieldSetting[];
	onSettingsChange: (setting: ObjectFieldSetting) => void;
}

interface IMaxLengthPropertiesProps {
	disabled: boolean;
	errors: ObjectFieldErrors;
	isSystemObjectField: boolean;
	objectField: Partial<ObjectField>;
	objectFieldSettings: ObjectFieldSetting[];
	onSettingsChange: (setting: ObjectFieldSetting) => void;
	setValues: (values: Partial<ObjectField>) => void;
}

interface IProps {
	forbiddenChars: string[];
	forbiddenLastChars: string[];
	forbiddenNames: string[];
	isApproved: boolean;
	isDefaultStorageType: boolean;
	objectDefinitionId: number;
	objectField: ObjectField;
	objectFieldTypes: ObjectFieldType[];
	objectName: string;
	readOnly: boolean;
	workflowStatusJSONArray: LabelValueObject[];
}

interface ISearchableProps {
	disabled: boolean;
	errors: ObjectFieldErrors;
	isApproved: boolean;
	objectField: Partial<ObjectField>;
	readOnly: boolean;
	setValues: (values: Partial<ObjectField>) => void;
}
