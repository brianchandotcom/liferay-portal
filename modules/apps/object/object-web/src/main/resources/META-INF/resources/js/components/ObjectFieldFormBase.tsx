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

import ClayForm, {ClayToggle} from '@clayui/form';
import {
	API,
	AutoComplete,
	FormCustomSelect,
	FormError,
	Input,
	Select,
	Toggle,
	invalidateRequired,
	useForm,
} from '@liferay/object-js-components-web';
import {sub} from 'frontend-js-web';
import React, {
	ChangeEventHandler,
	ReactNode,
	useEffect,
	useMemo,
	useState,
} from 'react';

import {normalizeFieldSettings} from '../utils/fieldSettings';
import {toCamelCase} from '../utils/string';

import './ObjectFieldFormBase.scss';

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();
const REQUIRED_MSG = Liferay.Language.get('required');

const attachmentSources = [
	{
		description: Liferay.Language.get(
			'files-can-be-stored-in-an-object-entry-or-in-a-specific-folder-in-documents-and-media'
		),
		label: Liferay.Language.get('upload-directly-from-users-computer'),
		value: 'userComputer',
	},
	{
		description: Liferay.Language.get(
			'users-can-upload-or-select-existing-files-from-documents-and-media'
		),
		label: Liferay.Language.get(
			'upload-or-select-from-documents-and-media-item-selector'
		),
		value: 'documentsAndMedia',
	},
];

const aggregationFunctions = [
	{
		label: Liferay.Language.get('count'),
		value: 'COUNT',
	},
	{
		label: Liferay.Language.get('sum'),
		value: 'SUM',
	},
	{
		label: Liferay.Language.get('average'),
		value: 'AVERAGE',
	},
	{
		label: Liferay.Language.get('min'),
		value: 'MIN',
	},
	{
		label: Liferay.Language.get('max'),
		value: 'MAX',
	},
];

export default function ObjectFieldFormBase({
	children,
	disabled,
	editingField,
	errors,
	handleChange,
	objectDefinitionId,
	objectField: values,
	objectFieldTypes,
	objectName,
	onAggregationFilterChange,
	onRelationshipChange,
	setValues,
}: IProps) {
	const businessTypeMap = useMemo(() => {
		const businessTypeMap = new Map<string, ObjectFieldType>();

		objectFieldTypes.forEach((type) => {
			businessTypeMap.set(type.businessType, type);
		});

		return businessTypeMap;
	}, [objectFieldTypes]);

	const [pickLists, setPickLists] = useState<PickList[]>([]);
	const [pickListItems, setPickListItems] = useState<PickListItem[]>([]);

	const picklistBusinessType = values.businessType === 'Picklist';
	const validListTypeDefinitionId =
		values.listTypeDefinitionId !== undefined &&
		values.listTypeDefinitionId !== 0;

	useEffect(() => {
		if (values.businessType === 'Picklist') {
			API.getPickLists().then(setPickLists);

			if (values.state) {
				API.getPickListItems(values.listTypeDefinitionId!).then(
					setPickListItems
				);
			}
		}

		// eslint-disable-next-line react-hooks/exhaustive-deps
	}, [values.businessType, values.listTypeDefinitionId]);

	const selectedPicklist = useMemo(() => {
		return pickLists.find(({id}) => values.listTypeDefinitionId === id);
	}, [pickLists, values.listTypeDefinitionId]);

	const handleTypeChange = async (option: ObjectFieldType) => {
		if (option.businessType === 'Picklist') {
			setPickLists(await API.getPickLists());
		}

		let objectFieldSettings: ObjectFieldSetting[] | undefined;

		switch (option.businessType) {
			case 'Attachment':
				objectFieldSettings = [
					{
						name: 'acceptedFileExtensions',
						value: 'jpeg, jpg, pdf, png',
					},
					{
						name: 'maximumFileSize',
						value: 100,
					},
				];
				break;

			case 'LongText':
			case 'Text':
				objectFieldSettings = [
					{
						name: 'showCounter',
						value: false,
					},
				];
				break;

			default:
				break;
		}

		const isSearchableByText =
			option.businessType === 'Attachment' || option.dbType === 'String';

		const indexedAsKeyword = isSearchableByText && values.indexedAsKeyword;

		const indexedLanguageId =
			isSearchableByText && !values.indexedAsKeyword
				? values.indexedLanguageId ?? defaultLanguageId
				: null;

		if (Liferay.FeatureFlags['LPS-152677']) {
			setValues({
				DBType: option.dbType,
				businessType: option.businessType,
				defaultValue: '',
				indexedAsKeyword,
				indexedLanguageId,
				listTypeDefinitionId: 0,
				objectFieldSettings,
				state: false,
			});
		}
		else {
			setValues({
				DBType: option.dbType,
				businessType: option.businessType,
				indexedAsKeyword,
				indexedLanguageId,
				listTypeDefinitionId: 0,
				objectFieldSettings,
			});
		}
	};

	return (
		<>
			<Input
				disabled={disabled}
				error={errors.name}
				label={Liferay.Language.get('field-name')}
				name="name"
				onChange={handleChange}
				required
				value={
					values.name ??
					toCamelCase(values.label?.[defaultLanguageId] ?? '')
				}
			/>

			<FormCustomSelect<ObjectFieldType>
				disabled={disabled}
				error={errors.businessType}
				label={Liferay.Language.get('type')}
				onChange={handleTypeChange}
				options={objectFieldTypes}
				required
				value={businessTypeMap.get(values.businessType ?? '')?.label}
			/>

			{values.businessType === 'Attachment' && (
				<AttachmentSourceProperty
					disabled={disabled}
					error={errors.fileSource}
					objectFieldSettings={
						values.objectFieldSettings as ObjectFieldSetting[]
					}
					objectName={objectName}
					setValues={setValues}
				/>
			)}

			{values.businessType === 'Aggregation' && (
				<AggregationSourceProperty
					editingField={editingField}
					errors={errors}
					objectDefinitionId={objectDefinitionId}
					objectFieldSettings={
						values.objectFieldSettings as ObjectFieldSetting[]
					}
					onAggregationFilterChange={onAggregationFilterChange}
					onRelationshipChange={onRelationshipChange}
					setValues={setValues}
				/>
			)}

			{picklistBusinessType && (
				<Select
					disabled={disabled}
					error={errors.listTypeDefinitionId}
					label={Liferay.Language.get('picklist')}
					onChange={({target: {value}}: any) => {
						if (Liferay.FeatureFlags['LPS-152677']) {
							setValues({
								defaultValue: '',
								listTypeDefinitionId: Number(
									pickLists[value].id
								),
								state: false,
							});
						}
						else {
							setValues({
								listTypeDefinitionId: Number(
									pickLists[value].id
								),
							});
						}
					}}
					options={pickLists.map(({name}) => name)}
					required
					value={
						(validListTypeDefinitionId &&
							selectedPicklist &&
							pickLists.indexOf(selectedPicklist)) as number
					}
				/>
			)}

			{children}

			<ClayForm.Group className="lfr-objects__object-field-form-base-form-group-toggles">
				{values.businessType !== 'Aggregation' && (
					<ClayToggle
						disabled={disabled || values.state}
						label={Liferay.Language.get('mandatory')}
						name="required"
						onToggle={(required) => setValues({required})}
						toggled={values.required || values.state}
					/>
				)}

				{Liferay.FeatureFlags['LPS-152677'] &&
					picklistBusinessType &&
					validListTypeDefinitionId && (
						<ClayToggle
							disabled={disabled}
							label={Liferay.Language.get('mark-as-state')}
							name="state"
							onToggle={async (state) => {
								if (state) {
									setValues({required: state, state});
									setPickListItems(
										await API.getPickListItems(
											values.listTypeDefinitionId!
										)
									);
								}
								else {
									setValues({
										defaultValue: '',
										required: state,
										state,
									});
								}
							}}
							toggled={values.state}
						/>
					)}
			</ClayForm.Group>

			{values.state && (
				<Select
					disabled={disabled}
					error={errors.defaultValue}
					label={Liferay.Language.get('default-value')}
					onChange={({target: {value}}: any) =>
						setValues({
							defaultValue: pickListItems[value].key,
						})
					}
					options={pickListItems.map(({name}) => name)}
					required
					value={
						values.defaultValue &&
						pickListItems.indexOf(
							pickListItems.find(
								({key}) => values.defaultValue === key
							)!
						)
					}
				/>
			)}
		</>
	);
}

export function useObjectFieldForm({
	forbiddenChars,
	forbiddenLastChars,
	forbiddenNames,
	initialValues,
	onSubmit,
}: IUseObjectFieldForm) {
	const validate = (field: Partial<ObjectField>) => {
		const getSourceFolderError = (folderPath: string) => {

			// folder name cannot end with invalid last characters

			const lastChar = folderPath[folderPath.length - 1];

			if (forbiddenLastChars?.some((char) => char === lastChar)) {
				return sub(
					Liferay.Language.get(
						'the-folder-name-cannot-end-with-the-following-characters-x'
					),
					forbiddenLastChars.join(' ')
				);
			}

			// folder name cannot contain invalid characters

			if (forbiddenChars?.some((symbol) => folderPath.includes(symbol))) {
				return sub(
					Liferay.Language.get(
						'the-folder-name-cannot-contain-the-following-invalid-characters-x'
					),
					forbiddenChars.join(' ')
				);
			}

			// folder name cannot be a reserved word

			const reservedNames = new Set(forbiddenNames);

			if (
				forbiddenNames &&
				folderPath.split('/').some((name) => reservedNames.has(name))
			) {
				return sub(
					Liferay.Language.get(
						'the-folder-name-cannot-have-a-reserved-word-such-as-x'
					),
					forbiddenNames.join(', ')
				);
			}

			return null;
		};

		const errors: ObjectFieldErrors = {};

		const label = field.label?.[defaultLanguageId];

		const settings = normalizeFieldSettings(field.objectFieldSettings);

		if (invalidateRequired(label)) {
			errors.label = REQUIRED_MSG;
		}

		if (invalidateRequired(field.name ?? label)) {
			errors.name = REQUIRED_MSG;
		}

		if (!field.businessType) {
			errors.businessType = REQUIRED_MSG;
		}
		else if (field.businessType === 'Aggregation') {
			if (!settings.function) {
				errors.function = REQUIRED_MSG;
			}

			if (settings.function !== 'COUNT' && !settings.objectFieldName) {
				errors.objectFieldName = REQUIRED_MSG;
			}

			if (!settings.objectRelationshipName) {
				errors.objectRelationshipName = REQUIRED_MSG;
			}
		}
		else if (field.businessType === 'Attachment') {
			const uploadRequestSizeLimit = Math.floor(
				Liferay.PropsValues.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE /
					1048576
			);

			if (
				invalidateRequired(
					settings.acceptedFileExtensions as string | undefined
				)
			) {
				errors.acceptedFileExtensions = REQUIRED_MSG;
			}
			if (!settings.fileSource) {
				errors.fileSource = REQUIRED_MSG;
			}
			if (!settings.maximumFileSize && settings.maximumFileSize !== 0) {
				errors.maximumFileSize = REQUIRED_MSG;
			}
			else if (settings.maximumFileSize > uploadRequestSizeLimit) {
				errors.maximumFileSize = sub(
					Liferay.Language.get(
						'file-size-is-larger-than-the-allowed-overall-maximum-upload-request-size-x-mb'
					),
					uploadRequestSizeLimit
				);
			}
			else if (settings.maximumFileSize < 0) {
				errors.maximumFileSize = sub(
					Liferay.Language.get(
						'only-integers-greater-than-or-equal-to-x-are-allowed'
					),
					0
				);
			}

			if (settings.showFilesInDocumentsAndMedia) {
				if (
					invalidateRequired(
						settings.storageDLFolderPath as string | undefined
					)
				) {
					errors.storageDLFolderPath = REQUIRED_MSG;
				}
				else {
					const sourceFolderError = getSourceFolderError(
						settings.storageDLFolderPath as string
					);

					if (sourceFolderError !== null) {
						errors.storageDLFolderPath = sourceFolderError;
					}
				}
			}
		}
		else if (
			field.businessType === 'Text' ||
			field.businessType === 'LongText'
		) {
			if (settings.showCounter && !settings.maxLength) {
				errors.maxLength = REQUIRED_MSG;
			}
		}
		else if (field.businessType === 'Picklist') {
			if (!field.listTypeDefinitionId) {
				errors.listTypeDefinitionId = REQUIRED_MSG;
			}

			if (
				Liferay.FeatureFlags['LPS-152677'] &&
				field.state &&
				!field.defaultValue
			) {
				errors.defaultValue = REQUIRED_MSG;
			}
		}

		return errors;
	};

	const {errors, handleChange, handleSubmit, setValues, values} = useForm<
		ObjectField,
		{[key in ObjectFieldSettingName]: any}
	>({
		initialValues,
		onSubmit,
		validate,
	});

	return {errors, handleChange, handleSubmit, setValues, values};
}

function AggregationSourceProperty({
	disabled,
	errors,
	editingField,
	onAggregationFilterChange,
	onRelationshipChange,
	objectDefinitionId,
	objectFieldSettings = [],
	setValues,
}: IAggregationSourcePropertyProps) {
	const [query, setQuery] = useState<string>('');
	const [
		selectedRelatedObjectRelationship,
		setSelectRelatedObjectRelationship,
	] = useState<TObjectRelationship>();
	const [selectedSummarizeField, setSelectedSummarizeField] = useState<
		string
	>();
	const [
		selectedAggregationFunction,
		setSelectedAggregationFunction,
	] = useState<{label: string; value: string}>();
	const [objectRelationships, setObjectRelationships] = useState<
		TObjectRelationship[]
	>();
	const [objectRelationshipFields, setObjectRelationshipFields] = useState<
		ObjectField[]
	>();

	useEffect(() => {
		const makeFetch = async () => {
			setObjectRelationships(
				await API.getObjectRelationships(objectDefinitionId)
			);
		};

		makeFetch();
	}, [objectDefinitionId]);

	useEffect(() => {
		if (editingField && objectRelationships) {
			const makeFetch = async () => {
				const settings = normalizeFieldSettings(objectFieldSettings);

				const currentRelatedObjectRelationship = objectRelationships.find(
					(relationship) =>
						relationship.name === settings.objectRelationshipName
				) as ObjectRelationship;

				const currentFunction = aggregationFunctions.find(
					(aggregationFunction) =>
						aggregationFunction.value === settings.function
				);

				const relatedFields = await API.getObjectFields(
					currentRelatedObjectRelationship.objectDefinitionId2
				);

				const currentSummarizeField = relatedFields.find(
					(relatedField) =>
						relatedField.name === settings.objectFieldName
				) as ObjectField;

				if (onRelationshipChange) {
					onRelationshipChange(
						currentRelatedObjectRelationship.objectDefinitionId2
					);
				}

				setObjectRelationshipFields(
					relatedFields.filter(
						(objectField) =>
							objectField.businessType === 'Integer' ||
							objectField.businessType === 'LongInteger' ||
							objectField.businessType === 'Decimal' ||
							objectField.businessType === 'PrecisionDecimal'
					)
				);

				setSelectRelatedObjectRelationship(
					currentRelatedObjectRelationship
				);

				setSelectedAggregationFunction(currentFunction);

				if (currentSummarizeField) {
					setSelectedSummarizeField(
						currentSummarizeField.label[defaultLanguageId]
					);
				}
			};

			makeFetch();
		}
	}, [
		editingField,
		objectRelationships,
		objectFieldSettings,
		onRelationshipChange,
	]);

	const handleChangeRelatedObjectRelationship = async (
		objectRelationship: TObjectRelationship
	) => {
		setSelectRelatedObjectRelationship(objectRelationship);
		setSelectedSummarizeField('');

		const relatedFields = await API.getObjectFields(
			objectRelationship.objectDefinitionId2
		);

		const numericFields = relatedFields.filter(
			(objectField) =>
				objectField.businessType === 'Integer' ||
				objectField.businessType === 'LongInteger' ||
				objectField.businessType === 'Decimal' ||
				objectField.businessType === 'PrecisionDecimal'
		);

		setObjectRelationshipFields(numericFields);

		const fieldSettingWithoutSummarizeField = objectFieldSettings.filter(
			(fieldSettings) =>
				fieldSettings.name !== 'objectFieldName' &&
				fieldSettings.name !== 'filters' &&
				fieldSettings.name !== 'objectRelationshipName'
		);

		const newObjectFieldSettings: ObjectFieldSetting[] | undefined = [
			...fieldSettingWithoutSummarizeField,
			{
				name: 'objectRelationshipName',
				value: objectRelationship.name,
			},
			{
				name: 'filters',
				value: [],
			},
		];

		if (onAggregationFilterChange) {
			onAggregationFilterChange([]);
		}

		setValues({
			objectFieldSettings: newObjectFieldSettings,
		});

		if (onRelationshipChange) {
			onRelationshipChange(objectRelationship.objectDefinitionId2);
		}
	};

	const handleAggregationFunctionChange = ({
		label,
		value,
	}: {
		label: string;
		value: string;
	}) => {
		setSelectedAggregationFunction({label, value});

		let newObjectFieldSettings: ObjectFieldSetting[] | undefined;

		if (value === 'COUNT') {
			setSelectedSummarizeField('');

			const fieldSettingWithoutSummarizeField = objectFieldSettings.filter(
				(fieldSettings) => fieldSettings.name !== 'objectFieldName'
			);

			newObjectFieldSettings = [
				...fieldSettingWithoutSummarizeField.filter(
					(fieldSettings) => fieldSettings.name !== 'function'
				),
				{
					name: 'function',
					value,
				},
			];

			setValues({
				objectFieldSettings: newObjectFieldSettings,
			});

			return;
		}

		newObjectFieldSettings = [
			...objectFieldSettings.filter(
				(fieldSettings) => fieldSettings.name !== 'function'
			),
			{
				name: 'function',
				value,
			},
		];

		setValues({
			objectFieldSettings: newObjectFieldSettings,
		});
	};

	const handleSummarizeFieldChange = (objectField: ObjectField) => {
		setSelectedSummarizeField(objectField.label[defaultLanguageId]);

		const newObjectFieldSettings: ObjectFieldSetting[] | undefined = [
			...objectFieldSettings.filter(
				(fieldSettings) => fieldSettings.name !== 'objectFieldName'
			),
			{
				name: 'objectFieldName',
				value: objectField.name as string,
			},
		];

		setValues({
			objectFieldSettings: newObjectFieldSettings,
		});
	};

	return (
		<>
			<AutoComplete
				emptyStateMessage={Liferay.Language.get(
					'no-relationships-were-found'
				)}
				error={errors.objectRelationshipName}
				items={objectRelationships ?? []}
				label={Liferay.Language.get('relationship')}
				onChangeQuery={setQuery}
				onSelectItem={(item: TObjectRelationship) => {
					handleChangeRelatedObjectRelationship(item);
				}}
				query={query}
				required
				value={
					selectedRelatedObjectRelationship?.label[defaultLanguageId]
				}
			>
				{({label}) => (
					<div className="d-flex justify-content-between">
						<div>{label[defaultLanguageId]}</div>
					</div>
				)}
			</AutoComplete>

			<FormCustomSelect
				disabled={disabled}
				error={errors.function}
				label={Liferay.Language.get('function')}
				onChange={handleAggregationFunctionChange}
				options={aggregationFunctions}
				required
				value={selectedAggregationFunction?.label}
			/>

			{selectedAggregationFunction?.value !== 'COUNT' && (
				<AutoComplete
					emptyStateMessage={Liferay.Language.get(
						'no-fields-were-found'
					)}
					error={errors.objectFieldName}
					items={objectRelationshipFields ?? []}
					label={Liferay.Language.get('field')}
					onChangeQuery={setQuery}
					onSelectItem={(item: ObjectField) => {
						handleSummarizeFieldChange(item);
					}}
					query={query}
					required
					value={selectedSummarizeField}
				>
					{({label}) => (
						<div className="d-flex justify-content-between">
							<div>{label[defaultLanguageId]}</div>
						</div>
					)}
				</AutoComplete>
			)}
		</>
	);
}

function AttachmentSourceProperty({
	disabled,
	error,
	objectFieldSettings,
	objectName,
	setValues,
}: IAttachmentSourcePropertyProps) {
	const settings = normalizeFieldSettings(objectFieldSettings);

	const attachmentSource = attachmentSources.find(
		({value}) => value === settings.fileSource
	);

	const handleAttachmentSourceChange = ({value}: {value: string}) => {
		const fileSource: ObjectFieldSetting = {name: 'fileSource', value};

		const updatedSettings = objectFieldSettings.filter(
			(setting) =>
				setting.name !== 'fileSource' &&
				setting.name !== 'showFilesInDocumentsAndMedia' &&
				setting.name !== 'storageDLFolderPath'
		);

		updatedSettings.push(fileSource);

		if (value === 'userComputer') {
			updatedSettings.push({
				name: 'showFilesInDocumentsAndMedia',
				value: false,
			});
		}

		setValues({objectFieldSettings: updatedSettings});
	};

	const toggleShowFiles = (value: boolean) => {
		const updatedSettings = objectFieldSettings.filter(
			(setting) =>
				setting.name !== 'showFilesInDocumentsAndMedia' &&
				setting.name !== 'storageDLFolderPath'
		);

		updatedSettings.push({
			name: 'showFilesInDocumentsAndMedia',
			value,
		});

		if (value) {
			updatedSettings.push({
				name: 'storageDLFolderPath',
				value: `/${objectName}`,
			});
		}

		setValues({objectFieldSettings: updatedSettings});
	};

	return (
		<>
			<FormCustomSelect
				disabled={disabled}
				error={error}
				label={Liferay.Language.get('request-files')}
				onChange={handleAttachmentSourceChange}
				options={attachmentSources}
				required
				value={attachmentSource?.label}
			/>

			{settings.fileSource === 'userComputer' && (
				<ClayForm.Group className="lfr-objects__object-field-form-base-container">
					<Toggle
						disabled={disabled}
						label={Liferay.Language.get(
							'show-files-in-documents-and-media'
						)}
						name="showFilesInDocumentsAndMedia"
						onToggle={toggleShowFiles}
						toggled={!!settings.showFilesInDocumentsAndMedia}
						tooltip={Liferay.Language.get(
							'when-activated-users-can-define-a-folder-within-documents-and-media-to-display-the-files-leave-it-unchecked-for-files-to-be-stored-individually-per-entry'
						)}
						tooltipAlign="top"
					/>
				</ClayForm.Group>
			)}
		</>
	);
}

interface IAggregationSourcePropertyProps {
	disabled?: boolean;
	editingField?: boolean;
	errors: ObjectFieldErrors;
	objectDefinitionId: number;
	objectFieldSettings: ObjectFieldSetting[];
	onAggregationFilterChange?: (aggregationFilterArray: []) => void;
	onRelationshipChange?: (objectDefinitionId2: number) => void;
	setValues: (values: Partial<ObjectField>) => void;
}

interface IAttachmentSourcePropertyProps {
	disabled?: boolean;
	error?: string;
	objectFieldSettings: ObjectFieldSetting[];
	objectName: string;
	setValues: (values: Partial<ObjectField>) => void;
}
interface IUseObjectFieldForm {
	forbiddenChars?: string[];
	forbiddenLastChars?: string[];
	forbiddenNames?: string[];
	initialValues: Partial<ObjectField>;
	onSubmit: (field: ObjectField) => void;
}

interface IProps {
	children?: ReactNode;
	disabled?: boolean;
	editingField?: boolean;
	errors: ObjectFieldErrors;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	objectDefinitionId: number;
	objectField: Partial<ObjectField>;
	objectFieldTypes: ObjectFieldType[];
	objectName: string;
	onAggregationFilterChange?: (aggregationFilterArray: []) => void;
	onRelationshipChange?: (objectDefinitionId2: number) => void;
	setValues: (values: Partial<ObjectField>) => void;
}

type TObjectRelationship = {
	label: LocalizedValue<string>;
	name: string;
	objectDefinitionId2: number;
};

export type ObjectFieldErrors = FormError<
	ObjectField & {[key in ObjectFieldSettingName]: any}
>;
