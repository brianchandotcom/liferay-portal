/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import classNames from 'classnames';
import {Locale} from 'frontend-js-components-web';
import React, {useCallback, useMemo, useState} from 'react';

import TranslationManager from './TranslationManager';
import TranslationOptions from './TranslationOptions';

export type Field = Record<Liferay.Language.Locale, string>;
export type Fields = Record<string, Field>;
export interface TranslationsWrapper {
	defaultLanguageId: Liferay.Language.Locale;
	fields: Fields;
	locales: Locale[];
	selectedLanguageId: Liferay.Language.Locale;
}

export default function TranslationsWrapper({
	defaultLanguageId,
	fields: initialFields,
	locales,
	selectedLanguageId: initialSelectedLanguageId,
}: TranslationsWrapper) {
	const [fields, setFields] = useState(initialFields);
	const [translations, setTranslations] = useState(
		fieldToTranslations(initialFields)
	);
	const [selectedLanguageId, setSelectedLanguageId] = useState<
		Liferay.Language.Locale
	>(initialSelectedLanguageId);

	const updateTranslations = useCallback(
		(fields: Fields) => {
			if (!fields) {
				return;
			}

			const newTranslations = Object.keys(fields).map((fieldName) => {
				const languages = Array.from(
					document.querySelectorAll<HTMLInputElement>(
						`[type="hidden"][data-field-name="${fieldName}"]`
					)
				)
					.filter((input) => input.value)
					.map(
						(input) =>
							input.dataset.languageid as Liferay.Language.Locale
					);

				return {
					fieldName,
					languages,
				};
			});

			setTranslations(newTranslations);
		},
		[setTranslations]
	);

	const getLocalizableFields = useCallback(() => {
		const localizableFields = getAllLocalizableFields(fields);

		setFields(localizableFields);

		updateTranslations(localizableFields);
	}, [fields, setFields, updateTranslations]);

	const translatedItems = useMemo(
		() =>
			locales.reduce((acc, locale) => {
				const translatedItems = translations.filter(({languages}) =>
					languages.includes(locale.id)
				).length;

				return {
					...acc,
					...(translatedItems && {[locale.id]: translatedItems}),
				};
			}, {}),
		[translations, locales]
	);

	const translationProgress = Object.keys(translatedItems).length
		? {
				totalItems: Object.keys(fields).length,
				translatedItems,
		  }
		: null;

	return (
		<>
			<div
				className={classNames({
					'translation-manager': Liferay.FeatureFlags['LPD-11253'],
				})}
			>
				<TranslationManager
					defaultLanguageId={defaultLanguageId}
					fields={fields}
					getLocalizableFields={getLocalizableFields}
					locales={locales}
					selectedLanguageId={selectedLanguageId}
					setFields={setFields}
					setSelectedLanguageId={setSelectedLanguageId}
					setTranslations={setTranslations}
					translationProgress={translationProgress}
					updateTranslations={updateTranslations}
				/>
			</div>

			{Liferay.FeatureFlags['LPD-11253'] && (
				<div className="c-ml-2">
					<TranslationOptions
						defaultLanguageId={defaultLanguageId}
						fields={initialFields}
						getLocalizableFields={getLocalizableFields}
						selectedLanguageId={selectedLanguageId}
						translationProgress={translationProgress}
					/>
				</div>
			)}
		</>
	);
}

export function fieldToTranslations(fields: Record<string, Field>) {
	const translations = [];

	for (const fieldName in fields) {
		const languages = fields[fieldName]
			? (Object.keys(fields[fieldName]) as Liferay.Language.Locale[])
			: [];

		translations.push({
			fieldName,
			languages,
		});
	}

	return translations;
}

export function getAllLocalizableFields(initialFields: Record<string, Field>) {
	const ddmFields = Array.from(
		document.querySelectorAll<HTMLInputElement>(
			`[data-ddm-localizable-field-id]`
		)
	)
		.map(
			(field) =>
				`${field.dataset.fieldName}${field.dataset.ddmLocalizableFieldId}`
		)
		.reduce((acc, name) => ({...acc, [name]: {}}), {});

	return {...initialFields, ...ddmFields};
}
