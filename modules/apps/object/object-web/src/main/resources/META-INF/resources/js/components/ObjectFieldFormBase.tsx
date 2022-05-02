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
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import {useFeatureFlag} from 'data-engine-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {ChangeEventHandler, ReactNode, useMemo, useState} from 'react';

import useForm, {FormError, invalidateRequired} from '../hooks/useForm';
import {
	normalizeFieldSettings,
	updateFieldSettings,
} from '../utils/fieldSettings';
import {defaultLanguageId} from '../utils/locale';
import {toCamelCase} from '../utils/string';
import CustomSelect from './Form/CustomSelect/CustomSelect';
import Input from './Form/Input';
import Select from './Form/Select';

import './ObjectFieldFormBase.scss';

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

const headers = new Headers({
	'Accept': 'application/json',
	'Content-Type': 'application/json',
});

async function fetchPickList() {
	const result = await fetch(
		'/o/headless-admin-list-type/v1.0/list-type-definitions?pageSize=-1',
		{
			headers,
			method: 'GET',
		}
	);

	const {items = []} = (await result.json()) as {
		items: IPickList[] | undefined;
	};

	return items.map(({id, name}) => ({id, name}));
}

export default function ObjectFieldFormBase({
	children,
	disabled,
	errors,
	handleChange,
	objectField: values,
	objectFieldTypes,
	objectName,
	setValues,
}: IProps) {
	const businessTypeMap = useMemo(() => {
		const businessTypeMap = new Map<string, ObjectFieldType>();

		objectFieldTypes.forEach((type) => {
			businessTypeMap.set(type.businessType, type);
		});

		return businessTypeMap;
	}, [objectFieldTypes]);

	const [pickList, setPickList] = useState<IPickList[]>([]);

	const handleSettingsChange = ({name, value}: ObjectFieldSetting) =>
		setValues({
			objectFieldSettings: updateFieldSettings(
				values.objectFieldSettings,
				{name, value}
			),
		});

	const handleTypeChange = async (option: ObjectFieldType) => {
		if (option.businessType === 'Picklist') {
			setPickList(await fetchPickList());
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

		setValues({
			DBType: option.dbType,
			businessType: option.businessType,
			indexedAsKeyword,
			indexedLanguageId,
			objectFieldSettings,
		});
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

			<CustomSelect<ObjectFieldType>
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
					onSettingsChange={handleSettingsChange}
					setValues={setValues}
				/>
			)}

			{values.businessType === 'Picklist' && (
				<Select
					disabled={disabled}
					error={errors.listTypeDefinitionId}
					label={Liferay.Language.get('picklist')}
					onChange={({target: {value}}: any) =>
						setValues({
							listTypeDefinitionId: Number(pickList[value].id),
						})
					}
					options={pickList.map(({name}) => name)}
					required
				/>
			)}
			{children}
			<ClayToggle
				disabled={disabled}
				label={Liferay.Language.get('mandatory')}
				name="required"
				onToggle={(required) => setValues({required})}
				toggled={values.required}
			/>
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
				return Liferay.Util.sub(
					Liferay.Language.get(
						'the-folder-name-cannot-end-with-the-following-characters-x'
					),
					forbiddenLastChars.join(' ')
				);
			}

			// folder name cannot contain invalid characters

			if (forbiddenChars?.some((symbol) => folderPath.includes(symbol))) {
				return Liferay.Util.sub(
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
				return Liferay.Util.sub(
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
				errors.maximumFileSize = Liferay.Util.sub(
					Liferay.Language.get(
						'file-size-is-larger-than-the-allowed-overall-maximum-upload-request-size-x-mb'
					),
					uploadRequestSizeLimit
				);
			}
			else if (settings.maximumFileSize < 0) {
				errors.maximumFileSize = Liferay.Util.sub(
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

function AttachmentSourceProperty({
	disabled,
	error,
	objectFieldSettings,
	objectName,
	onSettingsChange,
	setValues,
}: IAttachmentSourcePropertyProps) {
	const flags = useFeatureFlag();
	const settings = normalizeFieldSettings(objectFieldSettings);

	const attachmentSource = attachmentSources.find(
		({value}) => value === settings.fileSource
	);

	const handleAttachmentSourceChange = ({value}: {value: string}) => {
		const fileSource: ObjectFieldSetting = {name: 'fileSource', value};
		if (!flags['LPS-148112']) {
			onSettingsChange(fileSource);

			return;
		}

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
			<CustomSelect
				disabled={disabled}
				error={error}
				label={Liferay.Language.get('request-files')}
				onChange={handleAttachmentSourceChange}
				options={attachmentSources}
				required
				value={attachmentSource?.label}
			/>

			{flags['LPS-148112'] && settings.fileSource === 'userComputer' && (
				<ClayForm.Group className="lfr-objects__object-field-form-base-container">
					<ClayToggle
						disabled={disabled}
						label={Liferay.Language.get(
							'show-files-in-documents-and-media'
						)}
						name="showFilesInDocumentsAndMedia"
						onToggle={toggleShowFiles}
						toggled={!!settings.showFilesInDocumentsAndMedia}
					/>

					<ClayTooltipProvider>
						<div
							data-tooltip-align="top"
							title={Liferay.Language.get(
								'when-activated-users-can-define-a-folder-within-documents-and-media-to-display-the-files-leave-it-unchecked-for-files-to-be-stored-individually-per-entry'
							)}
						>
							<ClayIcon
								className="lfr-objects__object-field-form-base-tooltip-icon"
								symbol="question-circle-full"
							/>
						</div>
					</ClayTooltipProvider>
				</ClayForm.Group>
			)}
		</>
	);
}

interface IAttachmentSourcePropertyProps {
	disabled?: boolean;
	error?: string;
	objectFieldSettings: ObjectFieldSetting[];
	objectName: string;
	onSettingsChange: (setting: ObjectFieldSetting) => void;
	setValues: (values: Partial<ObjectField>) => void;
}
interface IUseObjectFieldForm {
	forbiddenChars?: string[];
	forbiddenLastChars?: string[];
	forbiddenNames?: string[];
	initialValues: Partial<ObjectField>;
	onSubmit: (field: ObjectField) => void;
}
interface IPickList {
	id: string;
	name: string;
}

interface IProps {
	children?: ReactNode;
	disabled?: boolean;
	errors: ObjectFieldErrors;
	handleChange: ChangeEventHandler<HTMLInputElement>;
	objectField: Partial<ObjectField>;
	objectFieldTypes: ObjectFieldType[];
	objectName: string;
	setValues: (values: Partial<ObjectField>) => void;
}

export type ObjectFieldErrors = FormError<
	ObjectField & {[key in ObjectFieldSettingName]: any}
>;
