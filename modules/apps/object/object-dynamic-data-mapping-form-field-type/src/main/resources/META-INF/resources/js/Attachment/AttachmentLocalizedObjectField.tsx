/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ClayInput} from '@clayui/form';
import {
	EditingLocale,
	LocalesDropdown,
} from 'dynamic-data-mapping-form-field-type';
import {
	getEditingLocales,
	getLocale,
} from 'dynamic-data-mapping-form-field-type/src/main/resources/META-INF/resources/localizedObjectFields/util/locales';
import {
	FieldChangeEventHandler,
	LocalizedValue,
} from 'dynamic-data-mapping-form-field-type/src/main/resources/META-INF/resources/types';
import {AvailableLocale} from 'dynamic-data-mapping-form-field-type/src/main/resources/META-INF/resources/util/localizable/LocalesDropdown';
import React, {useState} from 'react';
import {flushSync} from 'react-dom';

import AttachmentBase, {
	AttachmentBaseProps,
	AttachmentFile,
} from './AttachmentBase';

export interface AttachmentLocalizedObjectFieldProps
	extends AttachmentBaseProps<string | LocalizedValue<string>> {
	availableLocales: AvailableLocale[];
	defaultLocale: EditingLocale;
	fieldName: string;
	fileEntryProperties: LocalizedValue<AttachmentFile>;
	onChange: FieldChangeEventHandler<LocalizedValue<string>>;
	value: LocalizedValue<string>;
}

export default function AttachmentLocalizedObjectField({
	availableLocales,
	defaultLocale,
	fieldName,
	fileEntryProperties,
	onChange,
	value,
	...otherProps
}: AttachmentLocalizedObjectFieldProps) {
	const initialEditingLocales = getEditingLocales(
		availableLocales,
		defaultLocale,
		value
	);

	const [editingLocales, setEditingLocales] = useState<EditingLocale[]>(
		initialEditingLocales
	);

	const [currentEditingLocale, setCurrentEditingLocale] = useState({
		...getLocale(editingLocales, defaultLocale, defaultLocale.localeId),
	});

	const [attachment, setAttachment] =
		useState<LocalizedValue<AttachmentFile>>(fileEntryProperties);

	const getAttachment = () => {
		if (!attachment[currentEditingLocale.localeId]) {
			return null;
		}

		return attachment[currentEditingLocale.localeId] as AttachmentFile;
	};

	const handleAttachmentChange = (
		attachmentValue: AttachmentFile,
		fileId: string
	) => {
		const newValue = {
			...value,
			[currentEditingLocale.localeId]: fileId,
		};

		onChange({target: {value: newValue}});

		setAttachment((previous) => {
			return {
				...previous,
				[currentEditingLocale.localeId]: attachmentValue,
			};
		});
	};

	const handleDelete = () => {
		const currentLocaleId = currentEditingLocale.localeId;

		if (Object.hasOwn(attachment, currentLocaleId)) {
			const newAttachment = {...attachment};
			delete newAttachment[currentLocaleId];
			setAttachment(newAttachment);
		}

		if (Object.hasOwn(value, currentLocaleId)) {
			const newValue = {...value};
			delete newValue[currentLocaleId];
			onChange({target: {value: newValue}});
		}
	};

	const handleTranslationChange = (localeId: Liferay.Language.Locale) => {
		if (!Object.hasOwn(value, localeId)) {
			const newValue = {
				...value,
				[localeId]: value[defaultLocale.localeId],
			};

			flushSync(() => {
				onChange({target: {value: newValue}});
			});
		}

		if (!Object.hasOwn(attachment, localeId)) {
			setAttachment((previous) => {
				return {
					...previous,
					[localeId]: attachment[defaultLocale.localeId],
				};
			});
		}

		const currentLocale = getLocale(
			editingLocales,
			defaultLocale,
			localeId
		);

		const updatedLocale = {...currentLocale, isTranslated: true};

		setEditingLocales((previous) =>
			previous.map((locale) =>
				locale.localeId === localeId ? updatedLocale : locale
			)
		);

		setCurrentEditingLocale(updatedLocale);
	};

	return (
		<ClayInput.Group>
			<ClayInput.GroupItem className="ddm-object-field-attachment-localized">
				<AttachmentBase
					{...otherProps}
					attachment={getAttachment()}
					handleDelete={handleDelete}
					onAttachmentChange={handleAttachmentChange}
					value={value}
				/>
			</ClayInput.GroupItem>

			<ClayInput.GroupItem shrink>
				<LocalesDropdown
					availableLocales={editingLocales}
					editingLocale={currentEditingLocale}
					fieldName={fieldName}
					onLanguageClicked={handleTranslationChange}
				/>
			</ClayInput.GroupItem>
		</ClayInput.Group>
	);
}
