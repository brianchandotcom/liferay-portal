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
import ClayForm, {ClayRadio, ClayRadioGroup, ClayToggle} from '@clayui/form';
import {fetch} from 'frontend-js-web';
import React, {ChangeEvent, useMemo, useState} from 'react';

import {ERRORS} from '../utils/errors';
import Input from './Form/Input';
import InputLocalized from './Form/InputLocalized/InputLocalized';
import Select from './Form/Select';
import ObjectFieldFormBase, {
	ObjectFieldErrors,
	useObjectFieldForm,
} from './ObjectFieldFormBase';

import './EditObjectField.scss';

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId() as Locale;
const defaultSymbol = defaultLanguageId.replace('_', '-').toLocaleLowerCase();
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

const defaultLocale = locales.find(({symbol}) => symbol === defaultSymbol);

function closeSidePanel() {
	const parentWindow = Liferay.Util.getOpener();
	parentWindow.Liferay.fire('close-side-panel');
}

export default function EditObjectField({
	isApproved,
	objectField: initialValues,
	objectFieldTypes,
	readOnly,
}: IProps) {
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

		const parentWindow = Liferay.Util.getOpener();
		if (response.ok) {
			closeSidePanel();
			parentWindow.Liferay.Util.openToast({
				message: Liferay.Language.get(
					'the-object-field-was-updated-successfully'
				),
				type: 'success',
			});
		}
		else {
			const error = (await response.json()) as
				| {type?: string}
				| undefined;

			const message =
				(error?.type && ERRORS[error.type]) ??
				Liferay.Language.get('an-error-occurred');

			parentWindow.Liferay.Util.openToast({message, type: 'danger'});
		}
	};

	const {
		errors,
		handleChange,
		handleSubmit,
		setValues,
		values,
	} = useObjectFieldForm({initialValues, onSubmit});

	const disabled = !!(readOnly || isApproved || values.relationshipType);

	const [locale, setSelectedLocale] = useState(
		defaultLocale as {
			label: string;
			symbol: string;
		}
	);

	return (
		<ClayForm
			className="lfr-objects__edit-object-field"
			onSubmit={handleSubmit}
		>
			<div className="sheet">
				<h2 className="sheet-title">
					{Liferay.Language.get('basic-info')}
				</h2>

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
					setValues={setValues}
				>
					{values.businessType === 'Attachment' && (
						<AttachmentProperties
							errors={errors}
							objectFieldSettings={
								values.objectFieldSettings as ObjectFieldSetting[]
							}
							setValues={setValues}
						/>
					)}
				</ObjectFieldFormBase>
			</div>

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

			<div className="lfr-objects__edit-object-field-buttons">
				<ClayButton displayType="secondary" onClick={closeSidePanel}>
					{Liferay.Language.get('cancel')}
				</ClayButton>

				<ClayButton disabled={readOnly} type="submit">
					{Liferay.Language.get('save')}
				</ClayButton>
			</div>
		</ClayForm>
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
		(objectField.DBType === 'String' ||
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
		<div className="mt-4 sheet">
			<h2 className="sheet-title">
				{Liferay.Language.get('searchable')}
			</h2>

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
		</div>
	);
}

function AttachmentProperties({
	errors,
	objectFieldSettings,
	setValues,
}: IAttachmentPropertiesProps) {
	const settings: {[key in ObjectFieldSettingName]?: string | number} = {};

	objectFieldSettings.forEach(({name, value}) => {
		settings[name] = value;
	});

	const handleChange = ({
		target: {name, value},
	}: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) =>
		setValues({
			objectFieldSettings: objectFieldSettings.map((setting) =>
				setting.name === name ? {...setting, value} : setting
			),
		});

	return (
		<>
			<Input
				component="textarea"
				error={errors.acceptedFileExtensions}
				feedbackMessage={Liferay.Language.get(
					'enter-the-list-of-file-extensions-users-can-upload-use-commas-to-separate-extensions'
				)}
				label={Liferay.Language.get('accepted-file-extensions')}
				name="acceptedFileExtensions"
				onChange={handleChange}
				required
				value={settings.acceptedFileExtensions}
			/>

			<Input
				error={errors.maximumFileSize}
				feedbackMessage={Liferay.Language.get('maximum-file-size-help')}
				label={Liferay.Language.get('maximum-file-size')}
				min={0}
				name="maximumFileSize"
				onChange={handleChange}
				required
				type="number"
				value={settings.maximumFileSize}
			/>
		</>
	);
}

interface IAttachmentPropertiesProps {
	errors: ObjectFieldErrors;
	objectFieldSettings: ObjectFieldSetting[];
	setValues: (values: Partial<ObjectField>) => void;
}

interface IProps {
	isApproved: boolean;
	objectField: ObjectField;
	objectFieldTypes: ObjectFieldType[];
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
