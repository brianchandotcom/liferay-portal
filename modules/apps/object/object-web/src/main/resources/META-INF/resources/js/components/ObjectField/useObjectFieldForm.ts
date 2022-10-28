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

import {invalidateRequired, useForm} from '@liferay/object-js-components-web';
import {sub} from 'frontend-js-web';

import {normalizeFieldSettings} from '../../utils/fieldSettings';
import {ObjectFieldErrors} from './ObjectFieldFormBase';

interface IUseObjectFieldForm {
	forbiddenChars?: string[];
	forbiddenLastChars?: string[];
	forbiddenNames?: string[];
	initialValues: Partial<ObjectField>;
	onSubmit: (field: ObjectField) => void;
}

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();
const REQUIRED_MSG = Liferay.Language.get('required');

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
		else if (field.businessType === 'Formula') {
			if (invalidateRequired(settings.output as string)) {
				errors.output = REQUIRED_MSG;
			}
		}
		else if (
			field.businessType === 'LongText' ||
			field.businessType === 'Text'
		) {
			if (settings.showCounter && !settings.maxLength) {
				errors.maxLength = REQUIRED_MSG;
			}
		}
		else if (field.businessType === 'Picklist') {
			if (!field.listTypeDefinitionId) {
				errors.listTypeDefinitionId = REQUIRED_MSG;
			}

			if (field.state && !field.defaultValue) {
				errors.defaultValue = REQUIRED_MSG;
			}
		}

		return errors;
	};

	const {errors, handleChange, handleSubmit, setValues, values} = useForm<
		ObjectField,
		{[key in ObjectFieldSettingName]: unknown}
	>({
		initialValues,
		onSubmit,
		validate,
	});

	return {errors, handleChange, handleSubmit, setValues, values};
}
