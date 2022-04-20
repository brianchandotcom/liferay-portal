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
import ClayIcon from '@clayui/icon';
import {useFeatureFlag} from 'data-engine-js-components-web';
import {fetch} from 'frontend-js-web';
import React, {useEffect, useMemo, useRef, useState} from 'react';
import {createTextMaskInputElement} from 'text-mask-core';

import {createAutoCorrectedNumberPipe} from '../utils/createAutoCorrectedNumberPipe';
import {ERRORS} from '../utils/errors';
import {
	normalizeFieldSettings,
	updateFieldSettings,
} from '../utils/fieldSettings';
import {defaultLanguageId, defaultLocale} from '../utils/locale';
import Card from './Card/Card';
import Input from './Form/Input';
import InputLocalized from './Form/InputLocalized/InputLocalized';
import Select from './Form/Select';
import ObjectFieldFormBase, {
	ObjectFieldErrors,
	useObjectFieldForm,
} from './ObjectFieldFormBase';
import {SidePanelForm, closeSidePanel, openToast} from './SidePanelContent';

import './EditObjectField.scss';

const locales: {label: string; symbol: string}[] = [];
const languageLabels: string[] = [];
const languages = Liferay.Language.available as LocalizedValue<string>;

Object.entries(languages).forEach(([languageId, label]) => {
	locales.push({
		label: languageId,
		symbol: languageId.replace('_', '-').toLocaleLowerCase(),
	});

	languageLabels.push(label);
});

export default function EditObjectField({
	forbiddenChars,
	forbiddenLastChars,
	forbiddenNames,
	isApproved,
	objectField: initialValues,
	objectFieldTypes,
	objectName,
	readOnly,
}: IProps) {
	const flags = useFeatureFlag();

	const onSubmit = async ({id, ...objectField}: ObjectField) => {
		const response = await fetch(
			`/o/object-admin/v1.0/object-fields/${id}`,
			{
				body: JSON.stringify(objectField),
				headers: new Headers({
					'Accept': 'application/json',
					'Content-Type': 'application/json',
				}),
				method: 'PUT',
			}
		);

		if (response.ok) {
			closeSidePanel();
			openToast({
				message: Liferay.Language.get(
					'the-object-field-was-updated-successfully'
				),
			});
		}
		else {
			const error = (await response.json()) as
				| {type?: string}
				| undefined;

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

	const disabled = !!(readOnly || isApproved || values.relationshipType);

	const [locale, setSelectedLocale] = useState(
		defaultLocale as {
			label: string;
			symbol: string;
		}
	);

	const handleSettingsChange = ({name, value}: ObjectFieldSetting) =>
		setValues({
			objectFieldSettings: updateFieldSettings(
				values.objectFieldSettings,
				{name, value}
			),
		});

	return (
		<SidePanelForm
			className="lfr-objects__edit-object-field"
			onSubmit={handleSubmit}
			readOnly={readOnly}
			title={Liferay.Language.get('field')}
		>
			<Card title={Liferay.Language.get('basic-info')}>
				<InputLocalized
					disabled={readOnly}
					error={errors.label}
					label={Liferay.Language.get('label')}
					locales={locales}
					onSelectedLocaleChange={setSelectedLocale}
					onTranslationsChange={(label) => setValues({label})}
					required
					selectedLocale={locale}
					translations={values.label as LocalizedValue<string>}
				/>

				<ObjectFieldFormBase
					disabled={disabled}
					errors={errors}
					handleChange={handleChange}
					objectField={values}
					objectFieldTypes={objectFieldTypes}
					objectName={objectName}
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

					{flags['LPS-146889'] &&
						(values.businessType === 'Text' ||
							values.businessType === 'LongText') && (
							<MaxLengthProperties
								disabled={readOnly}
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
			objectField.businessType === 'Attachment');

	const selectedLanguage = useMemo(() => {
		const selectedLabel =
			objectField.indexedLanguageId &&
			languages[objectField.indexedLanguageId];

		return selectedLabel
			? languageLabels.indexOf(selectedLabel)
			: undefined;
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
						onSelectedValueChange={(selected) => {
							const indexedAsKeyword = selected === 'true';
							const indexedLanguageId = indexedAsKeyword
								? null
								: defaultLanguageId;

							setValues({
								indexedAsKeyword,
								indexedLanguageId,
							});
						}}
						selectedValue={new Boolean(
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
					onChange={({target: {value}}: any) => {
						const selectedLabel = languageLabels[value];
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
					value={selectedLanguage}
				/>
			)}
		</Card>
	);
}

function MaxLengthProperties({
	disabled,
	errors,
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
				<ClayToggle
					disabled={disabled}
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
				/>
				&nbsp;
				<span
					data-tooltip-align="top"
					title={Liferay.Language.get(
						'when-enabled-a-character-counter-will-be-shown-to-the-user'
					)}
				>
					<ClayIcon
						className="lfr-objects__edit-object-field-tooltip-icon"
						symbol="question-circle-full"
					/>
				</span>
			</ClayForm.Group>
			<ClayForm.Group>
				{settings.showCounter && (
					<Input
						error={errors.maxLength}
						feedbackMessage={Liferay.Util.sub(
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
	const flags = useFeatureFlag();
	const settings = normalizeFieldSettings(objectFieldSettings);

	return (
		<>
			<ClayForm.Group>
				{flags['LPS-148112'] && settings.showFilesInDocumentsAndMedia && (
					<Input
						error={errors.storageDLFolderPath}
						feedbackMessage={Liferay.Util.sub(
							Liferay.Language.get(
								'input-the-path-of-the-chosen-folder-in-documents-and-media-an-example-of-a-valid-path-is-x'
							),
							'/myDocumentsAndMediaFolder'
						)}
						label={Liferay.Language.get('storage-folder')}
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

interface IAttachmentPropertiesProps {
	errors: ObjectFieldErrors;
	objectFieldSettings: ObjectFieldSetting[];
	onSettingsChange: (setting: ObjectFieldSetting) => void;
}

interface IMaxLengthPropertiesProps {
	disabled: boolean;
	errors: ObjectFieldErrors;
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
	objectField: ObjectField;
	objectFieldTypes: ObjectFieldType[];
	objectName: string;
	readOnly: boolean;
}

interface ISearchableProps {
	disabled: boolean;
	errors: ObjectFieldErrors;
	isApproved: boolean;
	objectField: Partial<ObjectField>;
	readOnly: boolean;
	setValues: (values: Partial<ObjectField>) => void;
}
